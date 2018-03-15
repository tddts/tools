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

import com.github.tddts.tools.core.pagination.ParallelPagination;
import com.github.tddts.tools.core.pagination.builder.ParallelPaginationErrorHandler;
import com.github.tddts.tools.core.pagination.builder.ParallelPaginationBuilder;

import java.util.function.Consumer;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
final class ParallelPaginationBuilderImpl<T>
    extends AbstractPaginationBuilder<T, ParallelPagination<T>, ParallelPaginationBuilderParams<T>, ParallelPaginationBuilder<T>>
    implements ParallelPaginationBuilder<T> {


  ParallelPaginationBuilderImpl() {
    super(new ParallelPaginationBuilderParams<>());
  }

  @Override
  protected ParallelPaginationBuilder<T> getCurrentInstance() {
    return this;
  }

  @Override
  public ParallelPaginationBuilder<T> parallel(int parallelPages) {
    getParams().setParallelPages(parallelPages);
    return this;
  }

  @Override
  public ParallelPaginationBuilder<T> errorHandler(ParallelPaginationErrorHandler errorHandler) {
    getParams().setParallelErrorHandler(errorHandler);
    return this;
  }

  @Override
  public ParallelPagination<T> build() {
    ParallelPaginationBuilderParams<T> params = getParams();
    params.validate();

    Consumer<ParallelPagination<T>> presetPagination = buildPresetPagination(params);
    return new ParallelPaginationImpl<>(
        presetPagination,
        params.getLoadFunction(),
        params.getLoadingResultConsumer(),
        params.getParallelErrorHandler(),
        params.getRetryNumber(),
        params.getRetryTimeout(),
        params.isSkipPageOnRetry(),
        params.getParallelPages());
  }

  private Consumer<ParallelPagination<T>> buildPresetPagination(PaginationBuilderParams<T> params) {
    Consumer<ParallelPagination<T>> presetPagination;

    if (params.isIncrementinOperatorSet()) {
      presetPagination = (pagination) -> pagination.perform(params.getFirstPage(), params.getLastPage(), params.getIncrementingOperator());
    } else {
      presetPagination = (pagination) -> pagination.perform(params.getFirstPage(), params.getLastPage());
    }

    return presetPagination;
  }

}
