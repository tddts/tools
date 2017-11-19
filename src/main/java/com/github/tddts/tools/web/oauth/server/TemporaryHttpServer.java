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

import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * {@code TemporaryHttpServer} is a {@link TemporaryServer} implementation that simply wraps Apache's {@link HttpServer}.
 * Default server stop timeout is 5 minutes.
 *
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 */
public class TemporaryHttpServer implements TemporaryServer {

  private static final Logger logger = LogManager.getLogger(TemporaryHttpServer.class);


  private final HttpServer server;
  private final Thread shutdownHook;

  private boolean started;
  private long timeout = 5 * 60 * 1000; // Default timeout is 5 minutes

  private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
  private Future<?> serverTimeoutTaskFuture;

  public TemporaryHttpServer(HttpServer httpServer) {
    this.server = httpServer;
    this.shutdownHook = createShutdownHook();
  }

  public TemporaryHttpServer(HttpServer httpServer, long timeout, TimeUnit timeUnit) {
    this.server = httpServer;
    this.timeout = timeUnit.toMillis(timeout);
    this.shutdownHook = createShutdownHook();
  }

  public TemporaryHttpServer(ServerBootstrap bootstrap) {
    this.server = bootstrap.create();
    this.shutdownHook = createShutdownHook();
  }

  public TemporaryHttpServer(ServerBootstrap bootstrap, long timeout, TimeUnit timeUnit) {
    this.server = bootstrap.create();
    this.timeout = timeUnit.toMillis(timeout);
    this.shutdownHook = createShutdownHook();
  }

  @Override
  public void start() {
    if (started) {
      serverTimeoutTaskFuture.cancel(false);
      scheduleServerTimeoutStop();
      logger.debug("Server timeout refreshed.");
    }
    else {
      try {
        server.start();
        addShutdownHook();
        scheduleServerTimeoutStop();
        started = true;
        logger.debug("Server started.");
      } catch (IOException e) {
        throw new UncheckedIOException(e.getMessage(), e);
      }
    }
  }

  @Override
  public void start(final long timeout, TimeUnit timeUnit) {
    setTimeout(timeout, timeUnit);
    start();
  }

  @Override
  public void setTimeout(final long timeout, TimeUnit timeUnit) {
    this.timeout = timeUnit.toMillis(timeout);
  }

  @Override
  public void stop() {
    removeShutdownHook();
    server.stop();
    started = false;
    logger.debug("Stopping server...");
  }

  @Override
  public void shutdown(long gracePeriod, TimeUnit timeUnit) {
    logger.debug("Server shutdown...");
    server.shutdown(gracePeriod, timeUnit);
    logger.debug("Server was shut down.");
  }

  @Override
  public void awaitTermination(final long timeout, final TimeUnit timeUnit) {
    try {
      server.awaitTermination(timeout, timeUnit);
    } catch (InterruptedException e) {
      server.stop(); // call stop just in case
      Thread.currentThread().interrupt();
    }
  }

  private void addShutdownHook() {
    Runtime.getRuntime().addShutdownHook(shutdownHook);
  }

  private void removeShutdownHook() {
    Runtime.getRuntime().removeShutdownHook(shutdownHook);
  }

  private Thread createShutdownHook() {
    return new Thread(() -> server.shutdown(5, TimeUnit.SECONDS));
  }

  private void scheduleServerTimeoutStop() {
    serverTimeoutTaskFuture = executor.schedule(this::stopByTimeout, timeout, TimeUnit.MILLISECONDS);
  }

  private void stopByTimeout() {
    logger.debug("Timeout expired. Stopping server.");
    stop();
  }

}
