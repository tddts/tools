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

package com.github.tddts.tools.fx.table.cell;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public class CustomizableCellFactory<T, U> implements Callback<TableColumn<T, U>, TableCell<T, U>> {

  private List<CellUpdater<T,U>> updaters = new ArrayList<>();

  public CustomizableCellFactory() {
  }

  public CustomizableCellFactory(CellUpdater<T,U> updater) {
    updaters.add(updater);
  }

  @SafeVarargs
  public CustomizableCellFactory(CellUpdater<T,U>... updaters) {
    this.updaters = Arrays.asList(updaters);
  }

  public CustomizableCellFactory(List<CellUpdater<T,U>> updaters) {
    this.updaters = updaters;
  }

  public void addUpdater(CellUpdater<T,U> updater) {
    updaters.add(updater);
  }

  @Override
  public TableCell<T, U> call(TableColumn<T, U> param) {
    return new CustomizableTableCell();
  }

  private class CustomizableTableCell extends TableCell<T, U> {

    @Override
    protected void updateItem(U item, boolean empty) {
      super.updateItem(item, empty);
      updaters.forEach(updater -> updater.updateItem(this, item, empty));
    }
  }
}
