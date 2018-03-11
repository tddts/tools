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


import java.util.function.IntUnaryOperator;

/**
 * {@code Pagination} is an interface for classes performing pagination process.
 *
 * @param <T> page generic type
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public interface Pagination<T> {

  /**
   * Perform pagination using preset parameters.
   */
  void perform();

  /**
   * Perform pagination for given range of pages.
   *
   * @param firstPage number of a first page in range
   * @param lastPage  number of a last page in range
   */
  void perform(int firstPage, int lastPage);

  /**
   * Perform pagination for given range of pages, using specified operator for number incrementation.
   *
   * @param firstPage            number of a first page
   * @param lastPage             number of a last page
   * @param incrementingOperator incrementing operator
   */
  void perform(int firstPage, int lastPage, IntUnaryOperator incrementingOperator);

  /**
   * Stop pagination process.
   * Does not interrupt processing of a current page (or pages).
   */
  void stop();

  /**
   * Check if pagination was stopped externally by invoking {@link #stop()}.
   *
   * @return <b>true</b> if pagination was stopped, <b>false</b> otherwise.
   */
  boolean isStopped();

}
