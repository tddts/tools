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

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

/**
 * A utility class with static methods for desktop operations.
 *
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public class DesktopUtil {


  /**
   * Display URI with default browser.
   *
   * @param uri URI to display
   * @return <b>true</b> if URI was successfully displayed, <b>false</b> otherwise.
   */
  private static boolean browseURI(URI uri) {
    try {

      Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
      if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
        desktop.browse(uri);
        return true;
      }
      return false;

    } catch (IOException e) {
      return false;
    }
  }

}
