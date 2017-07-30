package com.github.tddts.tools.core.pagination;

/**
 * {@code ParallelPaginationBuilder} is a {@link PaginationBuilder} that can be used
 * to build {@link Pagination} which will be processing pages in parallel.
 *
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public interface ParallelPaginationBuilder<T> extends PaginationBuilder<T, ParallelPaginationBuilder<T>> {

  /**
   * Set maximum number of pages processed in parallel.
   *
   * @param parallelPages number of pages processed in parallel
   * @return current builder instance
   */
  ParallelPaginationBuilder<T> parallel(int parallelPages);

}
