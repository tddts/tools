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

package com.github.tddts.tools.fx.cell.factory;

import com.github.tddts.tools.fx.cell.updater.CellUpdater;
import javafx.scene.control.Cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public abstract class AbstractCustomizableCellFactory<T, C extends Cell<T>> implements CustomizableCellFactory<T, C> {

  private final List<CellUpdater<T, C>> updaters;

  public AbstractCustomizableCellFactory() {
    updaters = new ArrayList<>();
  }

  public AbstractCustomizableCellFactory(int initialCapacity) {
    updaters = new ArrayList<>(initialCapacity);
  }

  public AbstractCustomizableCellFactory(CellUpdater<T, C> updater) {
    updaters = new ArrayList<>(1);
    updaters.add(updater);
  }

  @SafeVarargs
  public AbstractCustomizableCellFactory(CellUpdater<T, C>... updaters) {
    this.updaters = Arrays.asList(updaters);
  }

  public AbstractCustomizableCellFactory(List<CellUpdater<T, C>> updaters) {
    this.updaters = updaters;
  }

  public void addUpdater(CellUpdater<T, C> updater) {
    updaters.add(updater);
  }

  protected List<CellUpdater<T, C>> getUpdaters() {
    return updaters;
  }

  protected void customUpdate(C cell, T item, boolean empty) {
    updaters.forEach(updater -> updater.updateItem(cell, item, empty));
  }
}
