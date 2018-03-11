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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.List;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public class CustomizableTableCellFactory<S, T> extends AbstractCustomizableCellFactory<T, TableCell<S, T>>
    implements Callback<TableColumn<S, T>, TableCell<S, T>> {


  public CustomizableTableCellFactory() {
  }

  public CustomizableTableCellFactory(int initialCapacity) {
    super(initialCapacity);
  }

  public CustomizableTableCellFactory(CellUpdater<T, TableCell<S, T>> updater) {
    super(updater);
  }

  @SafeVarargs
  public CustomizableTableCellFactory(CellUpdater<T, TableCell<S, T>>... updaters) {
    super(updaters);
  }

  public CustomizableTableCellFactory(List<CellUpdater<T, TableCell<S, T>>> cellUpdaters) {
    super(cellUpdaters);
  }

  @Override
  public TableCell<S, T> call(TableColumn<S, T> param) {
    return new CustomizableTableCell();
  }

  private class CustomizableTableCell extends TableCell<S, T> {

    @Override
    protected void updateItem(T item, boolean empty) {
      super.updateItem(item, empty);
      customUpdate(this, item, empty);
    }
  }
}
