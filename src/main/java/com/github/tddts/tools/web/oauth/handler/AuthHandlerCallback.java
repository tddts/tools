package com.github.tddts.tools.web.oauth.handler;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.nio.protocol.HttpAsyncExchange;
import org.apache.http.protocol.HttpContext;

import java.net.URL;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public interface AuthHandlerCallback {

  /**
   * Initialize callback with HTTP excnahge data.
   *
   * @param request  HTTP request data
   * @param response HTTP response
   * @param context  HTTP context
   */
  void init(HttpRequest request, HttpResponse response, HttpContext context);

  /**
   * Send response with status <b>200</b> and given message as a content.
   *
   * @param text message
   */
  void returnMessage(String text);

  /**
   * Send response with defined status and given message as a content.
   *
   * @param status HTTP status code
   * @param text   message
   */
  void returnMessage(int status, String text);

  /**
   * Send response with status <b>500</b> and given message.
   *
   * @param errorMessage error message
   */
  void returnError(String errorMessage);

}
