/*
 * Copyright 2017 Tigran Dadaiants
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.tddts.tools.core.pagination.impl;

import com.github.tddts.tools.core.function.ObjIntFunction;
import com.github.tddts.tools.core.pagination.ParallelPagination;
import com.github.tddts.tools.core.pagination.builder.ParallelPaginationErrorHandler;
import com.github.tddts.tools.core.pagination.builder.SinglePageErrorHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
final class ParallelPaginationImpl<T> implements ParallelPagination<T> {

  private final ObjIntFunction<T, SinglePageErrorHandler> loadFunction;
  private final ObjIntConsumer<T> loadingResultConsumer;
  private final Consumer<ParallelPagination<T>> presetPagination;
  private final ParallelPaginationErrorHandler errorHandler;

  private final int retryNumber;
  private final long retryTimeout;
  private final boolean skipPageOnRetry;
  private final int parallelPages;

  private ExecutorService executorService;
  private Set<ErrorHandlingCallable> callablesToRetry;

  private List<Future<LoadResult>> futures;

  private volatile boolean stop;

  ParallelPaginationImpl(Consumer<ParallelPagination<T>> presetPagination,
                         ObjIntFunction<T, SinglePageErrorHandler> loadFunction,
                         ObjIntConsumer<T> loadingResultConsumer,
                         ParallelPaginationErrorHandler errorHandler,
                         int retryNumber, long retryTimeout,
                         boolean skipPageOnRetry,
                         int parallelPages) {

    this.loadFunction = loadFunction;
    this.loadingResultConsumer = loadingResultConsumer;
    this.presetPagination = presetPagination;
    this.errorHandler = errorHandler;
    this.retryNumber = retryNumber;
    this.retryTimeout = retryTimeout;
    this.skipPageOnRetry = skipPageOnRetry;
    this.parallelPages = parallelPages;
    this.callablesToRetry = new TreeSet<>();
  }

  @Override
  public void perform() {
    presetPagination.accept(this);
  }

  @Override
  public void perform(int firstPage, int lastPage) {
    perform(firstPage, lastPage, PaginationBuilderParams.SINGLE_INCREMENT_OPERATOR);
  }

  @Override
  public void perform(int firstPage, int lastPage, IntUnaryOperator incrementingOperator) {
    try {
      executorService = Executors.newFixedThreadPool(parallelPages);

      List<Callable<LoadResult>> callables;
      boolean positive = lastPage > firstPage;
      int lastLoadedPage = firstPage;

      do {
        // Generate range of pages
        int[] pagesRange = generateRange(lastLoadedPage, lastPage, positive, incrementingOperator);
        if (pagesRange.length > 0) lastLoadedPage = pagesRange[pagesRange.length - 1];
        // Create list of callables
        callables = createCallables(pagesRange);
        // Execute callables and for all of them to finish
        futures = executorService.invokeAll(callables);
        // Pass all loaded pages to consumers
        for (Future<LoadResult> future : futures) {
          LoadResult loadResult = future.get();
          loadingResultConsumer.accept(loadResult.page, loadResult.pageNumber);
        }
        // Process while there are max number of callables available
      } while (!stop && !callablesToRetry.isEmpty() && callables.size() >= parallelPages);

    } catch (InterruptedException e) {
      errorHandler.handleInterruptedException(e);
    } catch (ExecutionException e) {
      errorHandler.handleExecutionException(e);
    } finally {
      executorService.shutdown();
    }
  }

  private int[] generateRange(int lastLoadedPage, int lastPage, boolean positive, IntUnaryOperator inc) {

    if (positive ? (lastLoadedPage >= lastPage) : (lastLoadedPage <= lastPage)) {
      return new int[0];
    }

    int[] pageNumbers = new int[parallelPages];
    int page = lastLoadedPage;
    int pageIndex;

    for (pageIndex = 0; pageIndex < parallelPages; pageIndex++) {
      page = inc.applyAsInt(page);
      pageNumbers[pageIndex] = page;
    }

    if (pageIndex < parallelPages - 1) {
      return Arrays.copyOfRange(pageNumbers, 0, pageIndex);
    }

    return pageNumbers;
  }

  private List<Callable<LoadResult>> createCallables(int[] pagesRange) {
    // Create callables from range of pages
    List<Callable<LoadResult>> callables = new ArrayList<>(pagesRange.length + callablesToRetry.size());
    for (int page : pagesRange) {
      callables.add(new ErrorHandlingCallable(page, this));
    }
    // Add callables that should be retried
    callables.addAll(callablesToRetry);
    callablesToRetry.clear();

    return callables;
  }

  @Override
  public void stop() {
    stop = true;
    executorService.shutdown();
    if (futures != null) futures.forEach((future) -> future.cancel(false));
  }

  @Override
  public boolean isStopped() {
    return stop;
  }

  private class LoadResult {

    int pageNumber;
    T page;

    LoadResult(int pageNumber, T page) {
      this.pageNumber = pageNumber;
      this.page = page;
    }
  }

  private class ErrorHandlingCallable implements Callable<LoadResult>, SinglePageErrorHandler {

    int page;
    int retries;
    long timeout;
    ParallelPagination pagination;

    ErrorHandlingCallable(int page, ParallelPagination pagination) {
      this.page = page;
      this.pagination = pagination;
    }

    @Override
    public void stop() {
      pagination.stop();
    }

    @Override
    public void skipPage() {
      // do nothing
    }

    @Override
    public void retryPage() {
      if (retries >= retryNumber) {
        if (skipPageOnRetry) {
          skipPage();
        } else {
          stop();
        }
      }

      timeout = retryTimeout;
      callablesToRetry.add(this);
      retries++;
    }

    @Override
    public LoadResult call() throws Exception {
      if (timeout > 0) {
        sleepForTimeout();
        timeout = 0;
      }
      return new LoadResult(page, loadFunction.apply(this, page));
    }

    private void sleepForTimeout() {
      try {
        Thread.sleep(timeout);
      } catch (InterruptedException e) {
        stop();
        Thread.currentThread().interrupt();
      }
    }
  }

}
