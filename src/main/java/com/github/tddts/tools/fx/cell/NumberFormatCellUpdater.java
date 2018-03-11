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

import javafx.scene.control.TableCell;

import java.text.DecimalFormat;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public class NumberFormatCellUpdater<S, T extends Number> implements CellUpdater<T, TableCell<S, T>> {

  private DecimalFormat format;

  public NumberFormatCellUpdater(DecimalFormat format) {
    this.format = format;
  }

  public NumberFormatCellUpdater(String pattern) {
    this.format = new DecimalFormat(pattern);
  }

  public NumberFormatCellUpdater() {
    this("#.##");
  }

  @Override
  public void updateItem(TableCell<S, T> cell, T item, boolean empty) {
    if (item != null) {
      cell.setText(format.format(item));
    }
    else {
      cell.setText(null);
    }
  }
}
