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


import com.github.tddts.tools.core.pagination.SerialPagination;
import com.github.tddts.tools.core.pagination.builder.SerialPaginationBuilder;
import com.github.tddts.tools.core.pagination.builder.SerialPaginationConditionData;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
final class SerialPaginationBuilderImpl<T>
    extends AbstractPaginationBuilder<T, SerialPagination<T>, SerialPaginationBuilderParams<T>, SerialPaginationBuilder<T>>
    implements SerialPaginationBuilder<T> {

  SerialPaginationBuilderImpl() {
    super(new SerialPaginationBuilderParams<>());
  }

  @Override
  protected SerialPaginationBuilder<T> getCurrentInstance() {
    return this;
  }

  @Override
  public SerialPaginationBuilder<T> loadWhile(Predicate<SerialPaginationConditionData<T>> condition) {
    getParams().setCondition(condition);
    return this;
  }

  @Override
  public SerialPagination<T> build() {
    SerialPaginationBuilderParams<T> params = getParams();
    params.validate();
    Consumer<SerialPagination<T>> presetPagination = buildPresetPagination(params);
    return new SerialPaginationImpl<>(
        presetPagination,
        params.getLoadFunction(),
        params.getLoadingResultConsumer(),
        params.getRetryNumber(),
        params.getRetryTimeout(),
        params.isSkipPageOnRetry());
  }

  private Consumer<SerialPagination<T>> buildPresetPagination(SerialPaginationBuilderParams<T> params) {
    Consumer<SerialPagination<T>> presetPagination = null;

    if (params.isLastPageSet()) {
      if (params.isIncrementingperatorSet()) {
        presetPagination = (pagination) -> pagination.perform(params.getFirstPage(), params.getLastPage(), params.getIncrementingOperator());
      }
      else {
        presetPagination = (pagination) -> pagination.perform(params.getFirstPage(), params.getLastPage());
      }
    }
    else {
      if (params.isIncrementingperatorSet()) {
        presetPagination = (pagination) -> pagination.perform(params.getFirstPage(), params.getIncrementingOperator(), params.getCondition());
      }
      else {
        presetPagination = (pagination) -> pagination.perform(params.getFirstPage(), params.getCondition());
      }
    }

    return presetPagination;
  }
}
