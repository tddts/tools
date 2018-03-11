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

package com.github.tddts.tools.core.pagination.builder;

import com.github.tddts.tools.core.function.ObjIntFunction;
import com.github.tddts.tools.core.pagination.Pagination;

import java.util.concurrent.TimeUnit;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public interface PaginationBuilder<T, P extends Pagination<T>, B extends PaginationBuilder<T, P, B>> {

  /**
   * Set function that will be loading data for given page.
   *
   * @param loadFunction page loading function
   * @return current builder instance
   */
  B loadPage(ObjIntFunction<T, SinglePageErrorHandler> loadFunction);

  /**
   * Set consumer processing loaded page data.
   *
   * @param loadingResultConsumer page data consumer
   * @return current builder instance
   */
  B processPage(ObjIntConsumer<T> loadingResultConsumer);

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
   * Skip page if retry was unsuccessful or shutdown overall pagination process.
   * Set <b>false</b> to shutdown pagination.
   * Default value is <b>true</b>.
   *
   * @param skipPageOnRetry skip flag
   * @return current builder instance
   */
  B skiPageOnRetry(boolean skipPageOnRetry);

  /**
   * Build pagination object using given parameters.
   * <p>
   * <b>Note</b> that this default pagination implementation is not thread safe and cannot be performed in multiple threads
   * simultaneously.
   *
   * @return pagination object.
   * @throws IllegalStateException if builder state is invalid.
   */
  P build() throws IllegalStateException;
}
