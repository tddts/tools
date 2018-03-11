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

import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.FloatStringConverter;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public class PercentageFormatStringConverter extends NumericFormatStringConverter<Double> {

  public PercentageFormatStringConverter() {
    super(new DoubleStringConverter(), "#%");
  }

  @Override
  public Double fromString(String value) {
    // If the specified value is null or zero-length, return null
    if (value == null) {
      return null;
    }

    value = value.trim();

    if (value.length() < 1) {
      return null;
    }

    return Double.valueOf(value) / 100;
  }
}
