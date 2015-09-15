package com.photobox.services.foundation.server.thrift;

import org.apache.thrift.transport.TTransportException;

/**
 * Defines the interaction with a Thrift server.
 */
public interface ThriftServer {
  void start() throws TTransportException;
  void stop();
}
