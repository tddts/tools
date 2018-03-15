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

import com.github.tddts.tools.core.pagination.Pagination;
import com.github.tddts.tools.core.pagination.SerialPagination;

import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * {@code SerialPaginationBuilder} is a {@link PaginationBuilder} that can be used
 * to build {@link Pagination} which will be processing pages in series.
 *
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public interface SerialPaginationBuilder<T> extends PaginationBuilder<T, SerialPagination<T>, SerialPaginationBuilder<T>> {

  /**
   * Perform pagination while condition is true.
   *
   * @param condition pagination condition
   * @return current builder instance
   */
  SerialPaginationBuilder<T> loadWhile(Predicate<SerialPaginationConditionData<T>> condition);

  /**
   * Set loading rate. (time between loading of each page)
   *
   * @param rate     loading rate
   * @param timeUnit rate time unit
   * @return current builder instance
   */
  SerialPaginationBuilder<T> withRate(long rate, TimeUnit timeUnit);

}
