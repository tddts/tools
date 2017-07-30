package com.github.tddts.tools.core.pagination.impl;

import com.github.tddts.tools.core.pagination.Pagination;
import com.github.tddts.tools.core.pagination.PaginationBuilder;

import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
abstract class AbstractPaginationBuilder<T, B extends PaginationBuilder<T, B>> implements PaginationBuilder<T, B> {

  private final PaginationBuilderParams<T> params = new PaginationBuilderParams<>();

  AbstractPaginationBuilder() {
  }

  protected abstract B getCurrentInstance();

  PaginationBuilderParams<T> getParams() {
    return params;
  }

  @Override
  public B loadPage(IntFunction<T> loadFunction) {
    this.params.setLoadFunction(loadFunction);
    return getCurrentInstance();
  }

  @Override
  public B processPage(BiConsumer<Pagination<T>, T> loadingResultConsumer) {
    this.params.setLoadingResultConsumer(loadingResultConsumer);
    return getCurrentInstance();
  }

  @Override
  public B startWith(int page) {
    params.setFirstPage(page);
    return getCurrentInstance();
  }

  @Override
  public B finishOn(int page) {
    params.setLastPage(page);
    return getCurrentInstance();
  }

  @Override
  public B incrementBy(IntUnaryOperator incrementingOperator) {
    this.params.setIncrementingOperator(incrementingOperator);
    return getCurrentInstance();
  }

  @Override
  public B retryNumber(int retryNumber) {
    this.params.setRetryNumber(retryNumber);
    return getCurrentInstance();
  }

  @Override
  public B retryTimeout(long retryTimeout, TimeUnit timeUnit) {
    this.params.setRetryTimeout(timeUnit.toMillis(retryTimeout));
    return getCurrentInstance();
  }

  @Override
  public B skiPageOnRetry(boolean skipPageOnRetry) {
    this.params.setSkipPageOnRetry(skipPageOnRetry);
    return getCurrentInstance();
  }

  @Override
  public B whileTrue(Predicate<Pagination> condition) {
    this.params.setCondition(condition);
    return getCurrentInstance();
  }

}
