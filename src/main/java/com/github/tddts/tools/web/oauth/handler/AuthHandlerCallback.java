package com.github.tddts.tools.web.oauth.handler;

import java.net.URL;

/**
 * @author Tigran_Dadaiants@epam.com
 */
public interface AuthHandlerCallback {

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
   * @param text message
   */
  void returnMessage(int status, String text);

  /**
   * Send response with status <b>200</b> and file text as a content.
   *
   * @param file HTML page file
   */
  void returnPage(URL file);

  /**
   * Send response with status <b>500</b> and given message.
   *
   * @param errorMessage error message
   */
  void returnError(String errorMessage);

}
