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

package com.github.tddts.tools.core.pagination;

import com.github.tddts.tools.core.pagination.builder.SerialPaginationConditionData;

import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;

/**
 * {@code SerialPagination} is a variety of {@code Pagination} that prorcesses pages one by one.
 *
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public interface SerialPagination<T> extends Pagination<T>{

  /**
   * Perform pagination starting with given page while condition is fulfilled.
   *
   * @param beginningPage        beginning page
   * @param incrementingOperator incrementing operator
   * @param condition            pagination condition
   */
  void perform(int beginningPage, IntUnaryOperator incrementingOperator, Predicate<SerialPaginationConditionData<T>> condition);


  /**
   * Perform pagination starting with given page while condition is fulfilled.
   *
   * @param beginningPage beginning page
   * @param condition     pagination condition
   */
  void perform(int beginningPage, Predicate<SerialPaginationConditionData<T>> condition);
}
