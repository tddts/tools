package com.github.tddts.tools.web.oauth.handler;

import org.apache.http.NameValuePair;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public interface AuthHandler {

  void process(AuthHandlerCallback callback, List<NameValuePair> params);

}
