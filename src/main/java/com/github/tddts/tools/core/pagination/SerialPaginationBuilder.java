package com.github.tddts.tools.core.pagination;

/**
 * {@code SerialPaginationBuilder} is a {@link PaginationBuilder} that can be used
 * to build {@link Pagination} which will be processing pages in series.
 *
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public interface SerialPaginationBuilder<T> extends PaginationBuilder<T, SerialPaginationBuilder<T>> {

}
