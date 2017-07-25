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

package com.github.tddts.tools.core.task.impl;


import com.github.tddts.tools.core.task.Task;
import com.github.tddts.tools.core.task.TaskChain;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public class ReusableTaskChain<T> implements TaskChain<T> {

  private final LinkedList<Task> taskList;
  private LinkedList<Runnable> finallyRunnableList = new LinkedList<>();

  private volatile boolean running;
  private volatile boolean finished;

  private Task currentTask;
  private T object;

  public ReusableTaskChain() {
    taskList = new LinkedList<>();
  }

  private ReusableTaskChain(LinkedList<Task> taskList) {
    this.taskList = taskList;
  }

  @Override
  public TaskChain<T> perform(Runnable action) {
    taskList.add(new SimpleTask(action));
    return this;
  }

  @Override
  public TaskChain<T> consume(Consumer<T> consumer) {
    taskList.add(new SimpleTask(() -> consumer.accept(object)));
    return this;
  }

  @Override
  public <R> TaskChain<R> process(Function<T, R> function) {
    ReusableTaskChain<R> taskChain = new ReusableTaskChain<>(taskList);
    taskList.add(new SimpleTask(() -> taskChain.object = function.apply(object)));
    return taskChain;
  }

  @Override
  public <R> TaskChain<R> supply(Supplier<R> supplier) {
    ReusableTaskChain<R> taskChain = new ReusableTaskChain<>(taskList);
    taskList.add(new SimpleTask(() -> taskChain.object = supplier.get()));
    return taskChain;
  }

  @Override
  public TaskChain<T> onStop(Runnable action) {
    taskList.getLast().onStop(action);
    return this;
  }

  @Override
  public TaskChain<T> finallyAction(Runnable action) {
    finallyRunnableList.add(action);
    return this;
  }

  @Override
  public TaskChain<T> execute() {
    try {
      begin();

      Iterator<Task> taskIterator = taskList.iterator();

      while (taskIterator.hasNext() && running) {
        setNextTask(taskIterator);
        currentTask.run();
      }

    } finally {
      finish();
    }
    return this;
  }

  private synchronized void begin() {
    if (running || !finished) stopAndWait();
    running = true;
    finished = false;
  }

  private synchronized void setNextTask(Iterator<Task> taskIterator) {
    if (running) {
      currentTask = taskIterator.next();
    }
  }

  private synchronized void finish() {
    finallyRunnableList.forEach(Runnable::run);
    running = false;
    finished = true;
    notifyAll();
  }


  @Override
  public synchronized void stop() {
    stopInternal();
  }

  @Override
  public synchronized void stopAndWait() {
    if (stopInternal()) await();
  }

  private boolean stopInternal() {
    if (finished || !running) return false;

    running = false;
    currentTask.stop();

    return true;
  }

  @Override
  public T getResult() {
    return object;
  }

  @Override
  public boolean isFinished() {
    return finished;
  }

  private void await() {
    try {
      wait();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
