package com.github.tddts.tools.core.pagination.impl;

import com.github.tddts.tools.core.pagination.Pagination;

import java.util.function.BiConsumer;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;

class PaginationBuilderParams<T> {

  static final IntUnaryOperator SINGLE_INCREMENT_OPERATOR = (page) -> page + 1;
  
  private IntFunction<T> loadFunction;
  private BiConsumer<Pagination<T>, T> loadingResultConsumer;
  private Predicate<Pagination> condition;
  private IntUnaryOperator incrementingOperator;
  private int firstPage;
  private int lastPage;
  private int retryNumber = 3;
  private long retryTimeout = 100;
  private boolean skipPageOnRetry = true;

  IntFunction<T> getLoadFunction() {
    return loadFunction;
  }

  void setLoadFunction(IntFunction<T> loadFunction) {
    this.loadFunction = loadFunction;
  }

  BiConsumer<Pagination<T>, T> getLoadingResultConsumer() {
    return loadingResultConsumer;
  }

  void setLoadingResultConsumer(BiConsumer<Pagination<T>, T> loadingResultConsumer) {
    this.loadingResultConsumer = loadingResultConsumer;
  }

  Predicate<Pagination> getCondition() {
    return condition;
  }

  void setCondition(Predicate<Pagination> condition) {
    this.condition = condition;
  }

  IntUnaryOperator getIncrementingOperator() {
    return incrementingOperator;
  }

  void setIncrementingOperator(IntUnaryOperator incrementingOperator) {
    this.incrementingOperator = incrementingOperator;
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