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

import com.github.tddts.tools.core.pagination.builder.SerialPaginationConditionData;

import java.util.function.Predicate;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
class SerialPaginationBuilderParams<T> extends PaginationBuilderParams<T> {

  private Predicate<SerialPaginationConditionData<T>> condition;

  @Override
  void validate() throws IllegalStateException {
    validateBasicParams();

    if (condition == null && getLastPage() == Integer.MAX_VALUE) {
      throw new IllegalStateException("No last page or pagination condition defined!");
    }
    if (condition != null && getLastPage() != Integer.MAX_VALUE) {
      throw new IllegalStateException("There should be either max page or pagination condition, but not both!");
    }
  }

  Predicate<SerialPaginationConditionData<T>> getCondition() {
    return condition;
  }

  void setCondition(Predicate<SerialPaginationConditionData<T>> condition) {
    this.condition = condition;
  }

  boolean isConditionSet() {
    return condition != null;
  }

}
