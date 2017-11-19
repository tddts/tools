package com.github.tddts.tools.web.oauth.handler;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Tigran_Dadaiants@epam.com
 */
public interface AuthHandler<T> {

  /**
   * Process response parameters.
   *
   * @param paramsConsumer response callback consumer
   */
  void onParams(BiConsumer<AuthHandlerCallback, T> paramsConsumer);

  /**
   * Process request with no parameters.
   *
   * @param callbackConsumer response callback consumer
   */
  void onEmptyParams(Consumer<AuthHandlerCallback> callbackConsumer);

}
