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


import com.github.tddts.tools.core.pagination.builder.ParallelPaginationErrorHandler;

import java.util.concurrent.ExecutionException;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
class ParallelPaginationBuilderParams<T> extends PaginationBuilderParams<T> {

  private int parallelPages = 2;
  private ParallelPaginationErrorHandler parallelErrorHandler = new DefaultParallelPaginationErrorHandler();

  @Override
  void validate() throws IllegalStateException {
    super.validate();

    if (parallelPages <= 0 || parallelPages == Integer.MAX_VALUE) {
      throw new IllegalStateException("Number of parallel pages is invalid! (" + parallelPages + ")");
    }
  }

  int getParallelPages() {
    return parallelPages;
  }

  void setParallelPages(int parallelPages) {
    this.parallelPages = parallelPages;
  }

  ParallelPaginationErrorHandler getParallelErrorHandler() {
    return parallelErrorHandler;
  }

  void setParallelErrorHandler(ParallelPaginationErrorHandler parallelErrorHandler) {
    this.parallelErrorHandler = parallelErrorHandler;
  }

  boolean isPresentErrorHandler() {
    return parallelErrorHandler != null;
  }

  private static class DefaultParallelPaginationErrorHandler implements ParallelPaginationErrorHandler {

    @Override
    public void handleInterruptedException(InterruptedException e) {
      e.printStackTrace();
    }

    @Override
    public void handleExecutionException(ExecutionException e) {
      e.printStackTrace();
    }
  }
}
