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

import com.github.tddts.tools.web.oauth.handler.AuthHandlerCallback;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public abstract class AbstractAuthHandlerCallback implements AuthHandlerCallback {

  private HttpRequest request;
  private HttpResponse response;
  private HttpContext context;

  @Override
  public void init(HttpRequest request, HttpResponse response, HttpContext context) {
    this.request = this.request;
    this.response = response;
    this.context = context;
  }

  protected HttpRequest getRequest() {
    return request;
  }

  protected HttpResponse getResponse() {
    return response;
  }

  protected HttpContext getContext() {
    return context;
  }

}
