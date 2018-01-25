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

import com.github.tddts.tools.web.oauth.handler.AuthHandler;
import com.github.tddts.tools.web.oauth.handler.AuthHandlerCallback;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.nio.protocol.*;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public class CallbackRequestHandler implements HttpAsyncRequestHandler<HttpRequest> {

  private AuthHandler authHandler;
  private AuthHandlerCallback callback;

  public CallbackRequestHandler(AuthHandler authHandler, AuthHandlerCallback callback) {
    this.authHandler = authHandler;
    this.callback = callback;
  }

  @Override
  public HttpAsyncRequestConsumer<HttpRequest> processRequest(HttpRequest request, HttpContext context) throws HttpException, IOException {
    return new BasicAsyncRequestConsumer();
  }

  @Override
  public void handle(HttpRequest data, HttpAsyncExchange httpExchange, HttpContext context) throws HttpException, IOException {
    List<NameValuePair> params = URLEncodedUtils.parse(data.getRequestLine().getUri(), StandardCharsets.UTF_8);
    callback.init(data, httpExchange, context);
    authHandler.process(callback, params);
    httpExchange.submitResponse(new BasicAsyncResponseProducer(httpExchange.getResponse()));
  }
}
