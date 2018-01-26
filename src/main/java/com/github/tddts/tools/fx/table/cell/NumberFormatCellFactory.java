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

import java.text.DecimalFormat;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public class NumberFormatCellFactory<T, U extends Number> implements Callback<TableColumn<T, U>, TableCell<T, U>> {

  private DecimalFormat format;

  public NumberFormatCellFactory(String pattern) {
    this.format = new DecimalFormat(pattern);
  }

  public NumberFormatCellFactory() {
    this("#.##");
  }

  @Override
  public TableCell<T, U> call(TableColumn<T, U> param) {
    return new NumberFormatTableCell();
  }

  private class NumberFormatTableCell extends TableCell<T, U> {

    @Override
    protected void updateItem(U item, boolean empty) {
      super.updateItem(item, empty);
      if (item != null) setText(format.format(item));
    }
  }
}
