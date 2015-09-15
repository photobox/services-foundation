package com.photobox.services.foundation.client.thrift;

import com.wealoha.thrift.ThriftUtil;

import org.apache.thrift.TServiceClient;

/**
 * Adds auto-closing capabilities to a Thrift client.
 */
class CloseableThriftClient implements AutoCloseable {

  private final TServiceClient iface;

  CloseableThriftClient(TServiceClient iface) {
    this.iface = iface;
  }

  @Override
  public void close() throws Exception {
    ThriftUtil.closeClient(iface);
  }

  /**
   * @return the implementation of the client that execute the calls to the Thrift server
   */
  public final TServiceClient getIface() {
    return iface;
  }
}
