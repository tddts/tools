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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.List;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public class CustomizableListCellFactory<T> extends AbstractCustomizableCellFactory<T, ListCell<T>>
    implements Callback<ListView<T>, ListCell<T>> {


  public CustomizableListCellFactory() {
  }

  public CustomizableListCellFactory(int initialCapacity) {
    super(initialCapacity);
  }

  public CustomizableListCellFactory(CellUpdater<T, ListCell<T>> updater) {
    super(updater);
  }

  @SafeVarargs
  public CustomizableListCellFactory(CellUpdater<T, ListCell<T>>... updaters) {
    super(updaters);
  }

  public CustomizableListCellFactory(List<CellUpdater<T, ListCell<T>>> cellUpdaters) {
    super(cellUpdaters);
  }

  @Override
  public ListCell<T> call(ListView<T> param) {
    return new CustomizableListCell();
  }

  private class CustomizableListCell extends ListCell<T> {

    @Override
    protected void updateItem(T item, boolean empty) {
      super.updateItem(item, empty);
      customUpdate(this, item, empty);
    }
  }
}
