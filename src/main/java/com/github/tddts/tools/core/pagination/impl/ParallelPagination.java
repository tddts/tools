package com.github.tddts.tools.core.pagination.impl;

import com.github.tddts.tools.core.pagination.Pagination;
import com.github.tddts.tools.core.pagination.PaginationErrorHandler;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
final class ParallelPagination<T> implements Pagination<T>, PaginationErrorHandler {

  private final IntFunction<T> loadFunction;
  private final BiConsumer<Pagination<T>, T> loadingResultConsumer;
  private final Consumer<Pagination<T>> presetPagination;

  private final int retryNumber;
  private final long retryTimeout;
  private final boolean skipPageOnRetry;
  private final int parallelPages;

  private volatile boolean stop;

  public ParallelPagination(IntFunction<T> loadFunction,
                            BiConsumer<Pagination<T>, T> loadingResultConsumer,
                            Consumer<Pagination<T>> presetPagination,
                            int retryNumber, long retryTimeout,
                            boolean skipPageOnRetry,
                            int parallelPages) {

    this.loadFunction = loadFunction;
    this.loadingResultConsumer = loadingResultConsumer;
    this.presetPagination = presetPagination;
    this.retryNumber = retryNumber;
    this.retryTimeout = retryTimeout;
    this.skipPageOnRetry = skipPageOnRetry;
    this.parallelPages = parallelPages;
  }

  //TODO: implement it all!

  @Override
  public void skipPage() {

  }

  @Override
  public void retryPage() {

  }

  @Override
  public void perform() {

  }

  @Override
  public void perform(int firstPage, int lastPage) {

  }

  @Override
  public void perform(int minPage, int maxPage, IntUnaryOperator incrementingOperator) {

  }

  @Override
  public void perform(int beginningPage, IntUnaryOperator incrementingOperator, Predicate<Pagination> condition) {

  }

  @Override
  public void perform(int beginningPage, Predicate<Pagination> condition) {

  }

  @Override
  public void stop() {

  }

  @Override
  public boolean isStopped() {
    return false;
  }

  @Override
  public PaginationErrorHandler getErrorHandler() {
    return null;
  }

  @Override
  public int getCurrentPageNumber() {
    return 0;
  }

  @Override
  public T getLastPage() {
    return null;
  }
}
