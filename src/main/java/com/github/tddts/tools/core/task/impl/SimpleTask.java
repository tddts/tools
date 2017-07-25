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

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public class SimpleTask implements Task {

  private static final Runnable DUMMY_ACTION = () -> {
  };

  private Runnable action = DUMMY_ACTION;
  private Runnable onStop = DUMMY_ACTION;

  SimpleTask(Runnable action) {
    this.action = action;
  }

  @Override
  public void run() {
    action.run();
  }

  @Override
  public void stop() {
    onStop.run();
  }

  @Override
  public void onStop(Runnable action) {
    this.onStop = action;
  }
}
