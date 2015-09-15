package com.photobox.services.foundation.server.thrift.dropwizard;

import com.photobox.services.foundation.server.thrift.ThriftServer;

import org.apache.thrift.transport.TTransportException;

import io.dropwizard.lifecycle.Managed;

/**
 * A {@link ThriftServer} decorated to be managed inside a Dropwizard application.
 */
public final class ManagedThriftServer implements ThriftServer, Managed {

  private final ThriftServer thriftServer;

  /**
   * @param thriftServer the actual implementation of the ThriftServer that has to be managed
   */
  public ManagedThriftServer(ThriftServer thriftServer) {
    this.thriftServer = thriftServer;
  }

  @Override
  public void start() throws TTransportException {
    thriftServer.start();
  }

  @Override
  public void stop() {
    thriftServer.stop();
  }
}
