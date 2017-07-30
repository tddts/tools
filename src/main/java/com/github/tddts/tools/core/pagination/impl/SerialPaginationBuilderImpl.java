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
import com.github.tddts.tools.core.pagination.SerialPaginationBuilder;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
final class SerialPaginationBuilderImpl<T> extends AbstractPaginationBuilder<T, SerialPaginationBuilder<T>>
    implements SerialPaginationBuilder<T> {

  SerialPaginationBuilderImpl() {
  }

  @Override
  protected SerialPaginationBuilder<T> getCurrentInstance() {
    return this;
  }

  @Override
  public Pagination<T> build() {
    PaginationBuilderParams<T> params = getParams();

    return null;
  }
}
