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

package com.github.tddts.tools.fx.cell;

import javafx.scene.control.Cell;

/**
 * {@code CellUpdater} represents an interface allowing to define custom customUpdate logic for JavaFX's {@link Cell}.
 *
 * @param <T> The type of the item contained within the Cell.
 * @param <C> The generic type of the Cell.
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 * @see Cell#updateItem(java.lang.Object, boolean)
 */
@FunctionalInterface
public interface CellUpdater<T, C extends Cell<T>> {

  /**
   * @param item  The new item for the cell.
   * @param empty whether or not this cell represents data from the list. If it
   *              is empty, then it does not represent any domain data, but is a cell
   *              being used to render an "empty" row.
   * @see Cell#updateItem(java.lang.Object, boolean)
   */
  void updateItem(C cell, T item, boolean empty);
}
