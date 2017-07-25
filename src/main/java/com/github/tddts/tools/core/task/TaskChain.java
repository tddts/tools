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

package com.github.tddts.tools.core.task;


import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * {@code TaskChain} allows to build an "assembly line" using such functional interfaces
 * as {@link Runnable}, {@link Consumer}, {@link Supplier} and {@link Function}, then run it and get some result.
 * Code example:
 * <pre>
 * ResultObj result = new TaskChainImpl<>()
 * .perform(() -> doSomething())
 * .perform(() -> doSomethingElse())
 * .onStop(()  -> handleStop())
 * .perform(() -> doAnotherThing())
 * .supply(()  -> getTestObject())
 * .process(testObject -> getResultFromObject(testObject))
 * .execute()
 * .getResult();
 * </pre>
 * It is not supposed to be executed in multiple threads, but it is possible to stop execution by calling
 * {@link #stop()} or {@link #stopAndWait()} from another thread.
 *
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public interface TaskChain<T> {

  /**
   * Add new task to chain.
   *
   * @param action task action
   * @return current TaskChain
   */
  TaskChain<T> perform(Runnable action);

  /**
   * Add new task that consumes last result of current task chain.
   *
   * @param consumer task consumer
   * @return current TaskChain
   */
  TaskChain<T> consume(Consumer<T> consumer);

  /**
   * Add new task that consumes last result of current task chain
   * and provides another result.
   *
   * @param function task function
   * @param <R>      new result type
   * @return TaskChain of a new type
   */
  <R> TaskChain<R> process(Function<T, R> function);

  /**
   * Add new task that supplies a new result object
   *
   * @param supplier task supplier
   * @param <R>      result type
   * @return TaskChain of a new type
   */
  <R> TaskChain<R> supply(Supplier<R> supplier);

  /**
   * Set on-stop action for last added task.
   *
   * @param action on-stop action
   * @return current TaskChain
   */
  TaskChain<T> onStop(Runnable action);

  /**
   * Set actions that executed at the end no matter if execution has finished successfully or not.
   *
   * @param action action to execute
   */
  TaskChain<T> finallyAction(Runnable action);

  /**
   * Start chain execution.
   */
  TaskChain<T> execute();

  /**
   * Get resulted object.
   *
   * @return result
   */
  T getResult();

  /**
   * Stop chain execution.
   */
  void stop();

  /**
   * Stop chain execution and wait for it to finish.
   */
  void stopAndWait();

  /**
   * @return true if execution is finished, false otherwise.
   */
  boolean isFinished();
}
