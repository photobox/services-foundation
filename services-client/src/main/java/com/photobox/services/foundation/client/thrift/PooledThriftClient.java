package com.photobox.services.foundation.client.thrift;

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
 * through a factory (e.g. {@link ThriftClients}) that instantiates it as a
 * {@link Proxy} reflecting the calls to the service.
 */
final class PooledThriftClient extends ThriftClientTemplate<ThriftClientConfiguration> {

  private final ThriftClientPool<? extends TServiceClient> pool;

  PooledThriftClient(TServiceClientFactory clientFactory, ThriftClientConfiguration conf) {
    super(conf);
    List<ServiceInfo> serverList =
        Collections.singletonList(new ServiceInfo(conf.getHost(), conf.getPort()));
    pool = new ThriftClientPool<>(
        serverList,
        transport -> clientFactory.getClient(new TBinaryProtocol(transport)),
        extractPoolConfig(conf));
  }

  @Override
  protected CloseableThriftClient getThriftClient() {
    return new InternalPooledThriftClient(pool.getClient());
  }

  @Override
  protected void afterInvocation(CloseableThriftClient client) {
    // cast exception not caught - we want to see it
    InternalPooledThriftClient pooledClient = (InternalPooledThriftClient) client;
    pooledClient.finish();
  }

  private com.wealoha.thrift.PoolConfig extractPoolConfig(ThriftClientConfiguration conf) {
    PoolConfig poolConfig = conf.getPoolConfig();
    if (poolConfig == null) {
      poolConfig = PoolConfig.defaultPoolConfig();
    }
    return toInternalPoolConfig(poolConfig);
  }

  private com.wealoha.thrift.PoolConfig toInternalPoolConfig(PoolConfig poolConfig) {
    com.wealoha.thrift.PoolConfig internalConfig = new com.wealoha.thrift.PoolConfig();
    internalConfig.setTimeout(poolConfig.getTimeout());
    internalConfig.setMaxTotal(poolConfig.getMaxTotal());
    internalConfig.setMaxIdle(poolConfig.getMaxIdle());
    internalConfig.setMinIdle(poolConfig.getMinIdle());
    return internalConfig;
  }

  /**
   * A client that wrap the Aloha implementation of a pooled-client.
   */
  static class InternalPooledThriftClient extends CloseableThriftClient {

    private final ThriftClient pooledClient;

    public InternalPooledThriftClient(ThriftClient pooledClient) {
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
