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

import java.util.*;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public class ItemListTextField<T> extends TextField {

  public static final String DEFAULT_DELIMITER = ",";

  private final String delimiter;

  private LinkedList<T> items = new LinkedList<>();
  private StringJoiner stringJoiner;

  public ItemListTextField(String delimiter) {
    setEditable(false);
    this.delimiter = delimiter;
    stringJoiner = new StringJoiner(delimiter);
  }

  public ItemListTextField() {
    this(DEFAULT_DELIMITER);
  }

  @SafeVarargs
  public final void addAll(T... objects) {
    addAll(Arrays.asList(objects));
  }

  public void addAll(Collection<T> objects) {
    items.forEach(this::add);
    refresh();
  }

  public void add(T object) {
    if (!items.contains(object)) {
      items.add(object);
      stringJoiner.add(object.toString());
      setText(stringJoiner.toString());
    }
  }

  public void remove(T object) {
    if (items.remove(object)) {
      cleanAndRefresh();
    }
  }

  public void removeAll(Collection<T> objects) {
    if (items.removeAll(objects)) {
      cleanAndRefresh();
    }
  }

  public void removeAll() {
    items.clear();
    cleanAndRefresh();
  }

  public void removeLast() {
    if (!items.isEmpty()) items.removeLast();
  }

  private void refresh() {
    items.forEach((item) -> stringJoiner.add(item.toString()));
    setText(stringJoiner.toString());
  }

  private void cleanAndRefresh() {
    stringJoiner = new StringJoiner(delimiter);
    refresh();
  }

  public List<T> getList() {
    return Collections.unmodifiableList(items);
  }
}
