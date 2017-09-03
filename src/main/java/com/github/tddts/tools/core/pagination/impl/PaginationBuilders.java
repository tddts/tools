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

import com.github.tddts.tools.core.pagination.Pagination;
import com.github.tddts.tools.core.pagination.builder.PaginationBuilder;
import com.github.tddts.tools.core.pagination.builder.ParallelPaginationBuilder;
import com.github.tddts.tools.core.pagination.builder.SerialPaginationBuilder;

/**
 * {@code PaginationBuilders} is a factory for {@link PaginationBuilder} instances of different types.
 *
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public interface PaginationBuilders {

  /**
   * Create builder for "serial" {@link Pagination}.
   * It simply processes pages one-by-one.
   *
   * @param <T> builder generic type
   * @return builder for serial pagination
   */
  static <T> SerialPaginationBuilder<T> serial() {
    return new SerialPaginationBuilderImpl<>();
  }

  /**
   * Create builder for "parallel" {@link Pagination}.
   * It allows to process multiple pages simultaneously in several threads.
   *
   * @param <T> builder generic type
   * @return builder for parallel pagination
   */
  static <T> ParallelPaginationBuilder<T> parallel() {
    return new ParallelPaginationBuilderImpl<>();
  }
}
