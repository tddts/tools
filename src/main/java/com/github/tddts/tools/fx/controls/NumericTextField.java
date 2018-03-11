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

import com.github.tddts.tools.fx.converter.NumericFormatStringConverter;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public abstract class NumericTextField<T extends Number> extends TextField {

  private final TextFormatter<T> formatter;
  private final T defaultValue;

  public NumericTextField(NumericFormatStringConverter<T> converter, T defaultValue) {
    formatter = new TextFormatter<>(converter);
    this.defaultValue = defaultValue;
    setTextFormatter(formatter);
  }

  public NumericTextField(StringConverter<T> converter, String pattern, T defaultValue) {
    this(new NumericFormatStringConverter<>(converter, pattern), defaultValue);
  }

  public T getValue() {
    T value = formatter.getValue();
    return value != null ? value : defaultValue;
  }

  public void setValue(T value) {
    formatter.setValue(value == null ? defaultValue : value);
  }

}
