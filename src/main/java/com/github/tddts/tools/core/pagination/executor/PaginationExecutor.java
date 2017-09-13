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

package com.github.tddts.tools.core.pagination.executor;

import com.github.tddts.tools.core.pagination.Pagination;
import com.github.tddts.tools.core.pagination.exception.PaginationExecutorException;

import java.util.Collection;

/**
 * {@code PaginationExecutor} allows to execute multiple pagination processes simultaneously.
 *
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public interface PaginationExecutor {


  /**
   * Add Pagination object for future execution.
   *
   * @param pagination Pagination object
   */
  void add(Pagination<?> pagination);

  /**
   * Add a collection of Pagination objects for future execution.
   *
   * @param paginationCollection collection of Pagination objects
   */
  void addAll(Collection<Pagination<?>> paginationCollection);

  /**
   * Launch execution.
   */
  void execute() throws PaginationExecutorException;

  /**
   * Stop execution.
   */
  void stop();
}
