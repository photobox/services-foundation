package com.photobox.services.foundation.client.thrift;

import com.wealoha.thrift.PoolConfig;
import com.wealoha.thrift.ServiceInfo;
import com.wealoha.thrift.ThriftClient;
import com.wealoha.thrift.ThriftClientPool;

import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.apache.thrift.protocol.TBinaryProtocol;

import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of a simple service client based on Thrift and uses a pool of clients
 * to connect to it.
 *
 * This implementation is based on the Aloha thrift-client-pool project:
 * https://github.com/aloha-app/thrift-client-pool-java
 *
 * This class is not intended to be used directly, but has to be instantiated
 * through a factory (e.g. {@link ThriftServiceClients}) that instantiates it as a
 * {@link Proxy} reflecting the calls to the service.
 */
final class PooledThriftServiceClient extends ThriftServiceClientTemplate {

  private final ThriftClientPool<? extends TServiceClient> pool;

  PooledThriftServiceClient(TServiceClientFactory clientFactory, ThriftClientConfiguration conf) {
    super(conf);
    List<ServiceInfo> serverList =
        Collections.singletonList(new ServiceInfo(conf.getHost(), conf.getPort()));
    pool = new ThriftClientPool<>(
        serverList,
        transport -> clientFactory.getClient(new TBinaryProtocol(transport)),
        defaultPoolConfig());
  }

  @Override
  protected PooledThriftClient getThriftClient() {
    return new PooledThriftClient(pool.getClient());
  }

  @Override
  protected void afterInvocation(CloseableThriftClient client) {
    // cast exception not caught - we want to see it
    PooledThriftClient pooledClient = (PooledThriftClient) client;
    pooledClient.finish();
  }

  private PoolConfig defaultPoolConfig() {
    PoolConfig config = new PoolConfig();
    config.setTimeout(1000);
    // PoolConfig is a instance of GenericObjectPoolConfig
    config.setMinIdle(3);
    config.setMaxTotal(30);
    return config;
  }

  /**
   * A client that wrap the Aloha implementation of a pooled-client.
   */
  static class PooledThriftClient extends CloseableThriftClient {

    private final ThriftClient pooledClient;

    public PooledThriftClient(ThriftClient pooledClient) {
      super(pooledClient.iFace());
      this.pooledClient = pooledClient;
    }

    public void finish() {
      pooledClient.finish();
    }

    @Override
    public void close() throws Exception {
      pooledClient.close();
    }
  }

}
