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

package com.github.tddts.tools.web.oauth.server;

import java.io.UncheckedIOException;
import java.util.concurrent.TimeUnit;

/**
 * {@code TemporaryServer} represents an embedded server that processes responses for a short period of time
 * and then shuts down automatically.
 * Intended for use in desktop applications.
 *
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public interface TemporaryServer {

  /**
   * Launch server for predefined period of time.
   *
   * @throws UncheckedIOException in case of any I/O errors
   * @see #setTimeout
   */
  void start() throws UncheckedIOException;

  /**
   * Launch server for given period of time.
   *
   * @param timeout  server shutdown timeout
   * @param timeUnit the time unit of the {@code timeout} argument
   * @throws UncheckedIOException in case of any I/O errors
   */
  void start(long timeout, TimeUnit timeUnit) throws UncheckedIOException;

  /**
   * Set server shutdown timeout.
   *
   * @param timeout  server shutdown timeout
   * @param timeUnit the time unit of the {@code timeout} argument
   */
  void setTimeout(long timeout, TimeUnit timeUnit);

  /**
   * Shut down server manually if it's still running.
   */
  void stop();

  /**
   * Stop server and await it's termination.
   *
   * @param gracePeriod grace period
   * @param timeUnit    the time unit of the {@code gracePeriod} argument
   */
  void shutdown(long gracePeriod, TimeUnit timeUnit);

  /**
   * Await for server termination.
   *
   * @param gracePeriod grace period
   * @param timeUnit    the time unit of the {@code gracePeriod} argument
   */
  void awaitTermination(long gracePeriod, TimeUnit timeUnit);
}
