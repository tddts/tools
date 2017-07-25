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

package com.github.tddts.tools.core.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

/**
 * A utility class with static methods for various purposes.
 *
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public class Util {

  /**
   * Load content of given resource as String.
   *
   * @param resourceName path to resource
   * @param charset      charset
   * @return file content as String
   * @throws UncheckedIOException in case of any IO errors, or if given resource was not found
   */
  public static String loadContent(String resourceName, Charset charset) throws UncheckedIOException {
    try (InputStream inputStream = Util.class.getResourceAsStream(resourceName)) {

      if (inputStream == null) {
        String errorMessage = "Resource not found: " + resourceName;
        IOException exception = new IOException(errorMessage);
        throw new UncheckedIOException(exception.getMessage(), exception);
      }

      return new String(IOUtils.toByteArray(inputStream), charset);
    } catch (IOException e) {
      throw new UncheckedIOException(e.getMessage(), e);
    }
  }

  /**
   * Load content of given resource as String.
   *
   * @param resourceName path to resource
   * @param charset      charset name
   * @return file content as String
   * @throws UncheckedIOException in case of any IO errors, or if given resource was not found
   */
  public static String loadContent(String resourceName, String charset) throws UncheckedIOException, UnsupportedCharsetException {
    return loadContent(resourceName, Charset.forName(charset));
  }


  /**
   * Load content of given resource as String using UTF-8 charset.
   *
   * @param resourceName path to resource
   * @return file content as String
   * @throws UncheckedIOException in case of any IO errors, or if given resource was not found
   */
  public static String loadContent(String resourceName) throws UncheckedIOException {
    return loadContent(resourceName, StandardCharsets.UTF_8);
  }

}
