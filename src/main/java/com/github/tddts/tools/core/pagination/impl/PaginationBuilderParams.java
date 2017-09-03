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
import com.github.tddts.tools.core.pagination.builder.SinglePageErrorHandler;

import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;

class PaginationBuilderParams<T> {

  static final IntUnaryOperator SINGLE_INCREMENT_OPERATOR = (page) -> page + 1;

  private ObjIntFunction<T, SinglePageErrorHandler> loadFunction;
  private ObjIntConsumer<T> loadingResultConsumer;

  private IntUnaryOperator incrementingOperator;
  private int firstPage = Integer.MIN_VALUE;
  private int lastPage = Integer.MAX_VALUE;
  private int retryNumber = 3;
  private long retryTimeout = 100;
  private boolean skipPageOnRetry = true;


  void validate() throws IllegalStateException {
    validateBasicParams();

    if (lastPage == Integer.MAX_VALUE) {
      throw new IllegalStateException("Last page is not defined!");
    }
  }

  final void validateBasicParams() throws IllegalStateException {
    if (loadFunction == null) {
      throw new IllegalStateException("No load function present!");
    }
    if (loadingResultConsumer == null) {
      throw new IllegalStateException("No result consumer present!");
    }
    if (firstPage == Integer.MIN_VALUE) {
      throw new IllegalStateException("First page is not defined!");
    }
  }

  ObjIntFunction<T, SinglePageErrorHandler> getLoadFunction() {
    return loadFunction;
  }

  void setLoadFunction(ObjIntFunction<T, SinglePageErrorHandler> loadFunction) {
    this.loadFunction = loadFunction;
  }

  ObjIntConsumer<T> getLoadingResultConsumer() {
    return loadingResultConsumer;
  }

  void setLoadingResultConsumer(ObjIntConsumer<T> loadingResultConsumer) {
    this.loadingResultConsumer = loadingResultConsumer;
  }

  IntUnaryOperator getIncrementingOperator() {
    return incrementingOperator;
  }

  void setIncrementingOperator(IntUnaryOperator incrementingOperator) {
    this.incrementingOperator = incrementingOperator;
  }

  boolean isIncrementingperatorSet() {
    return incrementingOperator != null;
  }

  int getFirstPage() {
    return firstPage;
  }

  void setFirstPage(int firstPage) {
    this.firstPage = firstPage;
  }

  int getLastPage() {
    return lastPage;
  }

  void setLastPage(int lastPage) {
    this.lastPage = lastPage;
  }

  boolean isLastPageSet() {
    return lastPage != Integer.MAX_VALUE;
  }

  int getRetryNumber() {
    return retryNumber;
  }

  void setRetryNumber(int retryNumber) {
    this.retryNumber = retryNumber;
  }

  long getRetryTimeout() {
    return retryTimeout;
  }

  void setRetryTimeout(long retryTimeout) {
    this.retryTimeout = retryTimeout;
  }

  boolean isSkipPageOnRetry() {
    return skipPageOnRetry;
  }

  void setSkipPageOnRetry(boolean skipPageOnRetry) {
    this.skipPageOnRetry = skipPageOnRetry;
  }
}