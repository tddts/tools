package com.github.tddts.tools.core.pagination.impl;

import com.github.tddts.tools.core.pagination.Pagination;
import com.github.tddts.tools.core.pagination.ParallelPaginationBuilder;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
final class ParallelPaginationBuilderImpl<T> extends AbstractPaginationBuilder<T, ParallelPaginationBuilder<T>>
    implements ParallelPaginationBuilder<T> {

  private int parallelPages;

  ParallelPaginationBuilderImpl() {
  }

  protected int getParallelPages() {
    return parallelPages;
  }

  @Override
  protected ParallelPaginationBuilder<T> getCurrentInstance() {
    return this;
  }


  @Override
  public ParallelPaginationBuilder<T> parallel(int parallelPages) {
    this.parallelPages = parallelPages;
    return this;
  }

  @Override
  public Pagination<T> build() {
    return null;
  }

}
