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

package com.github.tddts.tools.fx.converter;

import javafx.util.StringConverter;

import java.text.DecimalFormat;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public class NumericFormatStringConverter<T extends Number> extends StringConverter<T> {

  private StringConverter<T> stringConverter;
  private DecimalFormat format;

  public NumericFormatStringConverter(StringConverter<T> stringConverter, String pattern) {
    this.stringConverter = stringConverter;
    this.format = new DecimalFormat(pattern);
  }

  @Override
  public String toString(T object) {
    // If the specified value is null, return a zero-length String
    if (object == null) {
      return "";
    }

    return format.format(object);
  }

  @Override
  public T fromString(String string) {
    return stringConverter.fromString(string);
  }

  public void setPattern(String pattern) {
    format.applyPattern(pattern);
  }
}
