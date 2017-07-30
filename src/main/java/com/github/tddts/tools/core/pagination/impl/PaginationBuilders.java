package com.github.tddts.tools.core.pagination.impl;

import com.github.tddts.tools.core.pagination.Pagination;
import com.github.tddts.tools.core.pagination.PaginationBuilder;
import com.github.tddts.tools.core.pagination.ParallelPaginationBuilder;
import com.github.tddts.tools.core.pagination.SerialPaginationBuilder;

/**
 * {@code PaginationBuilders} is a factory for {@link PaginationBuilder} instances of different types.
 *
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public interface PaginationBuilders {

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
}
