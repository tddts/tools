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
import com.github.tddts.tools.core.pagination.ParallelPagination;

import java.util.List;
import java.util.function.Predicate;

/**
 * {@code ParallelPaginationBuilder} is a {@link PaginationBuilder} that can be used
 * to build {@link Pagination} which will be processing pages in parallel.
 *
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public interface ParallelPaginationBuilder<T> extends PaginationBuilder<T, ParallelPagination<T>, ParallelPaginationBuilder<T>> {

  /**
   * Set maximum number of pages processed in parallel.
   * The default number is <b>2</b>.
   *
   * @param parallelPages number of pages processed in parallel
   * @return current builder instance
   */
  ParallelPaginationBuilder<T> parallel(int parallelPages);

  /**
   * Set error handler parallel execution.
   *
   * @param errorHandler error handler parallel execution
   * @return current builder instance
   */
  ParallelPaginationBuilder<T> errorHandler(ParallelPaginationErrorHandler errorHandler);
}
