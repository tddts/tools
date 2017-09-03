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

import com.github.tddts.tools.core.function.ObjIntFunction;
import com.github.tddts.tools.core.pagination.Pagination;
import com.github.tddts.tools.core.pagination.builder.PaginationBuilder;
import com.github.tddts.tools.core.pagination.builder.SinglePageErrorHandler;

import java.util.concurrent.TimeUnit;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public abstract class AbstractPaginationBuilder
    <T, P extends Pagination<T>,
        R extends PaginationBuilderParams<T>,
        B extends PaginationBuilder<T, P, B>>
    implements PaginationBuilder<T, P, B> {

  private final R params;

  AbstractPaginationBuilder(R params) {
    this.params = params;
  }

  protected abstract B getCurrentInstance();

  R getParams() {
    return params;
  }

  @Override
  public B loadPage(ObjIntFunction<T, SinglePageErrorHandler> loadFunction) {
    this.params.setLoadFunction(loadFunction);
    return getCurrentInstance();
  }

  @Override
  public B processPage(ObjIntConsumer<T> loadingResultConsumer) {
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
    getParams().setRetryNumber(retryNumber);
    return getCurrentInstance();
  }

  @Override
  public B retryTimeout(long retryTimeout, TimeUnit timeUnit) {
    getParams().setRetryTimeout(timeUnit.toMillis(retryTimeout));
    return getCurrentInstance();
  }

  @Override
  public B skiPageOnRetry(boolean skipPageOnRetry) {
    getParams().setSkipPageOnRetry(skipPageOnRetry);
    return getCurrentInstance();
  }

}
