package com.photobox.services.foundation.client.fixtures;

import com.photobox.services.foundation.client.ClientConfiguration;
import com.photobox.services.foundation.client.thrift.PoolConfig;
import com.photobox.services.foundation.client.thrift.ThriftClientConfiguration;

public class ClientConfigurationFixtures {

  public static final String HOST = "localhost";
  public static final int PORT = 10084;
  public static final PoolConfig POOL_CONFIG = PoolConfig.defaultPoolConfig();

  public static final ThriftClientConfiguration THRIFT_CONFIGURATION_POOLED =
      ThriftClientConfiguration.pooledClientConfiguration(HOST, PORT);

  public static final ThriftClientConfiguration THRIFT_CONFIGURATION_SIMPLE =
      ThriftClientConfiguration.simpleClientConfiguration(HOST, PORT);

  public static ClientConfiguration clientConfiguration() {
    return new ClientConfiguration(HOST, PORT);
  }

  public static ThriftClientConfiguration pooledConfiguration(PoolConfig poolConfig) {
    return ThriftClientConfiguration.pooledClientConfiguration(HOST, PORT, poolConfig);
  }
}
