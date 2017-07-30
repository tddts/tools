package com.github.tddts.tools.core.pagination;

import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public interface PaginationBuilder<T, B extends PaginationBuilder<T, B>> {

  /**
   * Set function that will be loading data for given page.
   *
   * @param loadFunction page loading function
   * @return current builder instance
   */
  B loadPage(IntFunction<T> loadFunction);

  /**
   * Set consumer processing loaded page data.
   *
   * @param loadingResultConsumer page data consumer
   * @return current builder instance
   */
  B processPage(BiConsumer<Pagination<T>, T> loadingResultConsumer);

  /**
   * Set page for pagination to start with.
   *
   * @param page page number.
   * @return current builder instance
   */
  B startWith(int page);

  /**
   * Set page for pagination to finish on.
   *
   * @param page page number
   * @return current builder instance
   */
  B finishOn(int page);

  /**
   * Set function incrementing page number.
   *
   * @param incrementingOperator incrementing operator
   * @return current builder instance
   */
  B incrementBy(IntUnaryOperator incrementingOperator);

  /**
   * Set maximum number of retries for each page in case of error.
   * Default value is <i>3</i>.
   *
   * @param retryNumber number of retries
   * @return current builder instance
   */
  B retryNumber(int retryNumber);

  /**
   * Set timeout before each retry.
   * Default timeout is <i>100 milliseconds</i>.
   *
   * @param retryTimeout timeout
   * @param timeUnit     timeout time unit
   * @return current builder instance
   */
  B retryTimeout(long retryTimeout, TimeUnit timeUnit);

  /**
   * Skip page if retry was unsuccessful or stop overall pagination process.
   * Set <b>false</b> to stop pagination.
   * Default value is <b>true</b>.
   *
   * @param skipPageOnRetry skip flag
   * @return current builder instance
   */
  B skiPageOnRetry(boolean skipPageOnRetry);

  /**
   * Perform pagination while condition is true.
   *
   * @param condition pagination condition
   * @return current builder instance
   */
  B whileTrue(Predicate<Pagination> condition);

  /**
   * Build pagination object using given parameters.
   * <p>
   * <b>Note</b> that this default pagination implementation is not thread safe and cannot be performed in multiple threads
   * simultaneously.
   *
   * @return pagination object.
   */
  Pagination<T> build();
}
