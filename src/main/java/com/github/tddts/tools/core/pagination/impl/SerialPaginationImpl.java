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
import com.github.tddts.tools.core.pagination.Pagination;
import com.github.tddts.tools.core.pagination.SerialPagination;
import com.github.tddts.tools.core.pagination.builder.SerialPaginationConditionData;
import com.github.tddts.tools.core.pagination.builder.SinglePageErrorHandler;

import java.util.function.Consumer;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;

/**
 * {@code SerialPaginationImpl} is a serial implementation for {@link Pagination}.
 * Instances should be created using {@link SerialPaginationBuilderImpl}.
 *
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
final class SerialPaginationImpl<T> implements SerialPagination<T>, SerialPaginationConditionData<T>, SinglePageErrorHandler {

  private final ObjIntFunction<T, SinglePageErrorHandler> loadFunction;
  private final ObjIntConsumer<T> loadingResultConsumer;
  private final Consumer<SerialPagination<T>> presetPagination;

  private final int retryNumber;
  private final long retryTimeout;
  private final long loadingRate;
  private final boolean skipPageOnRetry;

  private volatile boolean stop;

  private int page;
  private int retryCount;

  private boolean retry;
  private boolean skip;

  private T lastPage;

  private IntUnaryOperator incrementingOperator = PaginationBuilderParams.SINGLE_INCREMENT_OPERATOR;


  SerialPaginationImpl(Consumer<SerialPagination<T>> presetPagination,
                       ObjIntFunction<T, SinglePageErrorHandler> loadFunction,
                       ObjIntConsumer<T> loadingResultConsumer,
                       int retryNumber, long retryTimeout, long loadingRate,
                       boolean skipPageOnRetry) {

    this.loadFunction = loadFunction;
    this.loadingResultConsumer = loadingResultConsumer;
    this.presetPagination = presetPagination;
    this.retryNumber = retryNumber;
    this.retryTimeout = retryTimeout;
    this.loadingRate = loadingRate;
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
  public void perform(int firstPage, int lastPage, IntUnaryOperator incrementingOperator) {
    performInternal(firstPage, incrementingOperator, rangeCondition(firstPage, lastPage));
  }

  @Override
  public void perform(int beginningPage, IntUnaryOperator incrementingOperator, Predicate<SerialPaginationConditionData<T>> condition) {
    performInternal(beginningPage, incrementingOperator, condition);
  }

  @Override
  public void perform(int firstPage, Predicate<SerialPaginationConditionData<T>> condition) {
    performInternal(firstPage, PaginationBuilderParams.SINGLE_INCREMENT_OPERATOR, condition);
  }

  private void incrementPage() {
    page = incrementingOperator.applyAsInt(page);
  }

  private void performInternal(int firstPage, IntUnaryOperator incrementingOperator, Predicate<SerialPaginationConditionData<T>> condition) {
    this.incrementingOperator = incrementingOperator;
    page = firstPage;
    stop = false;
    do {
      retry = false;
      skip = false;
      sleepFor(loadingRate);
      lastPage = loadFunction.apply(this, page);
      if (retry || skip) continue;
      loadingResultConsumer.accept(lastPage, page);
      incrementPage();
    }
    while (!stop && condition.test(this));
  }

  private Predicate<SerialPaginationConditionData<T>> rangeCondition(int firstPage, int lastPage) {
    int min;
    int max;

    if (firstPage > lastPage) {
      min = lastPage;
      max = firstPage;
    } else {
      min = firstPage;
      max = lastPage;
    }

    return (pagination) -> page >= min && page <= max;
  }

  @Override
  public int lastPageNumber() {
    return page;
  }

  @Override
  public T lastPage() {
    return lastPage;
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
    skip = true;
    incrementPage();
  }

  @Override
  public void retryPage() {
    if (retryCount < retryNumber) {
      // Retry after timeout
      sleepFor(retryTimeout);
      retry = true;
      retryCount++;
    } else {
      // Set retry count to zero
      retryCount = 0;
      // Skip or shutdown
      if (skipPageOnRetry) {
        skipPage();
      } else {
        stop();
      }
    }
  }

  private void sleepFor(long time) {
    if (time <= 0) return;
    try {
      Thread.sleep(time);
    } catch (InterruptedException e) {
      stop();
      Thread.currentThread().interrupt();
    }
  }
}
