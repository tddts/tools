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
import org.apache.http.nio.protocol.HttpAsyncExchange;
import org.apache.http.protocol.HttpContext;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public abstract class AbstractAuthHandlerCallback implements AuthHandlerCallback {

  private HttpRequest data;
  private HttpAsyncExchange httpExchange;
  private HttpContext context;

  @Override
  public void init(HttpRequest data, HttpAsyncExchange httpExchange, HttpContext context) {
    this.data = data;
    this.httpExchange = httpExchange;
    this.context = context;
  }

  protected HttpRequest getData() {
    return data;
  }

  protected HttpAsyncExchange getHttpExchange() {
    return httpExchange;
  }

  protected HttpContext getContext() {
    return context;
  }

  protected HttpResponse getResponse() {
   checkExchange();
   return httpExchange.getResponse();
  }

  protected HttpRequest getRequest(){
    checkExchange();
    return httpExchange.getRequest();
  }

  private void checkData() {
    if (data == null) throwCallbackNotInitialized();
  }

  private void checkExchange() {
    if (httpExchange == null) throwCallbackNotInitialized();
  }

  private void checkContext() {
    if (context == null) throwCallbackNotInitialized();
  }

  private void throwCallbackNotInitialized() {
    throw new IllegalStateException("Callback is not initialized!");
  }
}
