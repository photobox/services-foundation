package com.photobox.services.foundation.server.thrift;

import org.apache.thrift.TProcessor;

/**
 * A collection of static factories providing access to different implementations of Thrift servers.
 *
 * This class provides only static methods and is not instantiable.
 */
public final class ThriftServers {

  private ThriftServers() {
    // suppress default constructor to prevent instantiation
  }

  /**
   * Create a multi-threaded Thrift server.
   *
   * @param port
   * @param processor
   * @return a multi-threaded Thrift server
   */
  public static ThriftServer createMultiThreadedThriftServer(int port, TProcessor processor) {
    return new MultiThreadedThriftServer(port, processor);
  }
}
