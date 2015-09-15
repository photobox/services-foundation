package com.photobox.services.foundation.client.thrift;

import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.lang.reflect.Proxy;

/**
 * Implementation of a simple service client based on Thrift and creates
 * a new connection for every call.
 *
 * This class is not intended to be used directly, but has to be instantiated
 * through a factory (e.g. {@link ThriftServiceClients}) that instantiates it as a
 * {@link Proxy} reflecting the calls to the service.
 */
final class SimpleThriftServiceClient extends ThriftServiceClientTemplate {

  // the factory used to generate Thrift clients
  private final TServiceClientFactory clientFactory;

  SimpleThriftServiceClient(TServiceClientFactory clientFactory, ThriftClientConfiguration conf) {
    super(conf);
    this.clientFactory = clientFactory;
  }

  @Override
  protected CloseableThriftClient getThriftClient() {
    // each time creates a new client and opens a new connection
    // that will be disposed at the end of the call
    TTransport transport = new TSocket(conf.getHost(), conf.getPort());
    TProtocol protocol = new TBinaryProtocol(transport);
    TServiceClient client = clientFactory.getClient(protocol);
    try {
      transport.open();
    } catch (TTransportException e) {
      throw new ThriftServiceException(e);
    }
    return new CloseableThriftClient(client);
  }
}
