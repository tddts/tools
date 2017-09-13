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
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * {@code SimplePaginationExecutor} is a most simple {@code PaginationExecutor} implemetation.
 *
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public class SimplePaginationExecutor implements PaginationExecutor {

  private Set<Pagination<?>> paginationSet = new HashSet<>();
  private int maxPoolSize = 7;

  private ExecutorService executorService;

  public SimplePaginationExecutor(Set<Pagination<?>> paginationSet, int maxPoolSize) {
    this.paginationSet = paginationSet;
    this.maxPoolSize = maxPoolSize;
  }

  public SimplePaginationExecutor(Set<Pagination<?>> paginationSet) {
    this.paginationSet = paginationSet;
  }

  public SimplePaginationExecutor() {
  }

  @Override
  public void add(Pagination<?> pagination) {
    paginationSet.add(pagination);
  }

  @Override
  public void addAll(Collection<Pagination<?>> paginationCollection) {
    paginationSet.addAll(paginationCollection);
  }

  @Override
  public void execute() throws PaginationExecutorException {
    if (paginationSet.isEmpty()) return;

    executorService = Executors.newFixedThreadPool(getPoolSize());

    try {
      executorService.invokeAll(createCallableList());
    } catch (InterruptedException e) {
      throw new PaginationExecutorException(e.getMessage(), e);
    } finally {
      stop();
    }
  }

  private Collection<Callable<Pagination>> createCallableList() {
    return paginationSet.stream().map(this::createCallable).collect(Collectors.toList());
  }

  private Callable<Pagination> createCallable(Pagination pagination) {
    return () -> {
      pagination.perform();
      return pagination;
    };
  }

  private int getPoolSize() {
    int size = paginationSet.size();
    return size > maxPoolSize ? maxPoolSize : size;
  }

  public void stop() {
    paginationSet.forEach(Pagination::stop);
    paginationSet.clear();
    if(executorService!=null) executorService.shutdown();
  }

}
