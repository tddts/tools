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

package com.github.tddts.tools.web.oauth.handler.impl;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import java.nio.charset.StandardCharsets;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public class SimpleAuthHandlerCallback extends AbstractAuthHandlerCallback {

  @Override
  public void returnMessage(String text) {
    returnMessage(HttpStatus.SC_OK, text);
  }

  @Override
  public void returnError(String errorMessage) {
    returnMessage(HttpStatus.SC_INTERNAL_SERVER_ERROR, errorMessage);
  }

  @Override
  public void returnMessage(int status, String text) {
    HttpResponse response = getResponse();

    AbstractHttpEntity httpEntity = new StringEntity(text, StandardCharsets.UTF_8);
    httpEntity.setContentType(ContentType.TEXT_HTML.toString());

    response.setStatusCode(status);
    response.setEntity(httpEntity);
  }
}
