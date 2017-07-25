/*
 * Copyright 2017 Tigran Dadaiants
 *
 * Licensed under the Apache LicensVersion 2.0 (the "License");
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

import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;

/**
 * {@code PaginationBuilder} represents a builder for default {@link Pagination} implementation.
 *
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public class PaginationBuilder<T> {

  private IntFunction<T> loadFunction;
  private BiConsumer<Pagination<T>, T> loadingResultConsumer;
  private Predicate<Pagination> condition;
  private IntUnaryOperator incrementingOperator;

  private int firstPage = Integer.MIN_VALUE;
  private int lastPage = Integer.MAX_VALUE;

  private int retryNumber = 3;
  private long retryTimeout = 100;
  private boolean skipPageOnRetry = true;

  public PaginationBuilder() {
  }

  /**
   * Set function that will be loading data for given page.
   *
   * @param loadFunction page loading function
   * @return current builder instance
   */
  public PaginationBuilder<T> loadPage(IntFunction<T> loadFunction) {
    this.loadFunction = loadFunction;
    return this;
  }

  /**
   * Set consumer processing loaded page data.
   *
   * @param loadingResultConsumer page data consumer
   * @return current builder instance
   */
  public PaginationBuilder<T> processPage(BiConsumer<Pagination<T>, T> loadingResultConsumer) {
    this.loadingResultConsumer = loadingResultConsumer;
    return this;
  }

  /**
   * Set page for pagination to start with.
   *
   * @param page page number.
   * @return current builder instance
   */
  public PaginationBuilder<T> startWith(int page) {
    firstPage = page;
    return this;
  }

  /**
   * Set page for pagination to finish on.
   *
   * @param page page number
   * @return current builder instance
   */
  public PaginationBuilder<T> finishOn(int page) {
    lastPage = page;
    return this;
  }

  /**
   * Set function incrementing page number.
   *
   * @param incrementingOperator incrementing operator
   * @return current builder instance
   */
  public PaginationBuilder<T> incrementBy(IntUnaryOperator incrementingOperator) {
    this.incrementingOperator = incrementingOperator;
    return this;
  }

  /**
   * Set maximum number of retries for each page in case of error.
   * Default value is <i>3</i>.
   *
   * @param retryNumber number of retries
   * @return current builder instance
   */
  public PaginationBuilder<T> retryNumber(int retryNumber) {
    this.retryNumber = retryNumber;
    return this;
  }

  /**
   * Set timeout before each retry.
   * Default timeout is <i>100 milliseconds</i>.
   *
   * @param retryTimeout timeout
   * @param timeUnit     timeout time unit
   * @return current builder instance
   */
  public PaginationBuilder<T> retryTimeout(long retryTimeout, TimeUnit timeUnit) {
    this.retryTimeout = timeUnit.toMillis(retryTimeout);
    return this;
  }

  /**
   * Skip page if retry was unsuccessful or stop overall pagination process.
   * Set <b>false</b> to stop pagination.
   * Default value is <b>true</b>.
   *
   * @param skipPageOnRetry skip flag
   * @return current builder instance
   */
  public PaginationBuilder<T> skiPageOnRetry(boolean skipPageOnRetry) {
    this.skipPageOnRetry = skipPageOnRetry;
    return this;
  }

  /**
   * Perform pagination while condition is true.
   *
   * @param condition pagination condition
   * @return current builder instance
   */
  public PaginationBuilder<T> whileTrue(Predicate<Pagination> condition) {
    this.condition = condition;
    return this;
  }

  /**
   * Build pagination object using given parameters.
   * <p>
   * <b>Note</b> that this default pagination implementation is not thread safe and cannot be performed in multiple threads
   * simultaneously.
   *
   * @return pagination object.
   */
  public Pagination<T> build() {
    if (loadFunction == null || loadingResultConsumer == null) {
      throw new IllegalStateException("Pagination can not be initialized! Some of required fields are NULL!");
    }

    if (condition != null && firstPage != Integer.MIN_VALUE) {
      if (incrementingOperator != null) {
        return create((pagination) -> pagination.perform(firstPage, incrementingOperator, condition));
      }
      else {
        return create((pagination) -> pagination.perform(firstPage, condition));
      }
    }

    if (firstPage != Integer.MIN_VALUE && lastPage != Integer.MAX_VALUE) {

      if (incrementingOperator != null) {
        return create((pagination) -> pagination.perform(firstPage, lastPage, incrementingOperator));
      }
      else {
        return create((pagination) -> pagination.perform(firstPage, lastPage));
      }
    }

    throw new IllegalStateException("Pagination can not be initialized! Parameters are invalid!");
  }

  private Pagination<T> create(Consumer<Pagination<T>> defaultAction) {
    return new PaginationImpl<>(defaultAction, loadFunction, loadingResultConsumer, retryNumber, retryTimeout, skipPageOnRetry);
  }
}
