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

package com.github.tddts.tools.fx.controls;

import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public class ItemListTextField extends TextField {

  public ItemListTextField() {
    setEditable(false);
  }

  public void addItems(String... items) {
    StringBuilder itemsBuilder = new StringBuilder(getText());
    for (String item : items) {
      if (item == null) continue;
      if (!itemsBuilder.toString().contains(item)) {
        if (itemsBuilder.length() > 0) itemsBuilder.append(",");
        itemsBuilder.append(item);
      }
    }
    setText(itemsBuilder.toString());
  }

  public void addItem(String item) {
    if (item == null) return;

    String items = getText();
    if (!items.contains(item)) {
      if (!items.isEmpty()) items = items + ",";
      items = items + item;
    }
    setText(items);
  }

  public List<String> getItems() {
    return getText().isEmpty() ? Collections.emptyList() : Arrays.asList(getText().split(","));
  }
}
