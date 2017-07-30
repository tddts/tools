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

import com.github.tddts.tools.core.pagination.Pagination;
import com.github.tddts.tools.core.pagination.PaginationErrorHandler;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;

/**
 * {@code SerialPagination} is a serial implementation for {@link Pagination}.
 * Instances should be created using {@link SerialPaginationBuilderImpl}.
 *
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
final class SerialPagination<T> implements Pagination<T>, PaginationErrorHandler {

  private final IntFunction<T> loadFunction;
  private final BiConsumer<Pagination<T>, T> loadingResultConsumer;
  private final Consumer<Pagination<T>> presetPagination;

  private final int retryNumber;
  private final long retryTimeout;
  private final boolean skipPageOnRetry;

  private volatile boolean stop;

  private int page;
  private int retryCount;

  private T lastPage;

  private IntUnaryOperator incrementingOperator = PaginationBuilderParams.SINGLE_INCREMENT_OPERATOR;


  SerialPagination(Consumer<Pagination<T>> presetPagination,
                   IntFunction<T> loadFunction,
                   BiConsumer<Pagination<T>, T> loadingResultConsumer,
                   int retryNumber, long retryTimeout,
                   boolean skipPageOnRetry) {

    this.loadFunction = loadFunction;
    this.loadingResultConsumer = loadingResultConsumer;
    this.presetPagination = presetPagination;
    this.retryNumber = retryNumber;
    this.retryTimeout = retryTimeout;
    this.skipPageOnRetry = skipPageOnRetry;
  }

  @Override
  public void perform() {
    presetPagination.accept(this);
  }

  @Override
  public void perform(int firstPage, int lastPage) {
    performInternal(firstPage, PaginationBuilderParams.SINGLE_INCREMENT_OPERATOR, rangeCondition(firstPage, lastPage));
  }

  @Override
  public void perform(int minPage, int maxPage, IntUnaryOperator incrementingOperator) {
    performInternal(minPage, incrementingOperator, rangeCondition(minPage, maxPage));
  }

  @Override
  public void perform(int beginningPage, IntUnaryOperator incrementingOperator, Predicate<Pagination> condition) {
    performInternal(beginningPage, incrementingOperator, condition);
  }

  @Override
  public void perform(int firstPage, Predicate<Pagination> condition) {
    page = firstPage;
    stop = false;
    do {
      lastPage = loadFunction.apply(page);
      loadingResultConsumer.accept(this, lastPage);
      incrementPage();
    }
    while (!stop && condition.test(this));
  }

  private void incrementPage() {
    page = incrementingOperator.applyAsInt(page);
  }

  private void performInternal(int firstPage, IntUnaryOperator incrementingOperator, Predicate<Pagination> condition) {
    this.incrementingOperator = incrementingOperator;
    perform(firstPage, condition);
  }

  private Predicate<Pagination> rangeCondition(int minPage, int maxPage) {
    return (pagination) -> page >= minPage && page <= maxPage;
  }

  @Override
  public void stop() {
    stop = true;
  }

  @Override
  public boolean isStopped() {
    return stop;
  }

  @Override
  public void skipPage() {
    incrementPage();
  }

  @Override
  public void retryPage() {
    if (retryCount < retryNumber) {
      // Retry after timeout
      sleepForTimeout(retryTimeout);
      retryCount++;
    }
    else {
      // Set retry count to zero
      retryCount = 0;
      // Skip or stop
      if (skipPageOnRetry) {
        skipPage();
      }
      else {
        stop();
      }
    }
  }

  private void sleepForTimeout(long retryTimeout) {
    try {
      Thread.sleep(retryTimeout);
    } catch (InterruptedException e) {
      stop();
      Thread.currentThread().interrupt();
    }
  }

  @Override
  public PaginationErrorHandler getErrorHandler() {
    return this;
  }

  @Override
  public int getCurrentPageNumber() {
    return page;
  }

  @Override
  public T getLastPage() {
    return lastPage;
  }
}
