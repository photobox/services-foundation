package com.photobox.services.foundation.client.thrift;

import static com.photobox.services.foundation.client.fixtures.ClientConfigurationFixtures.HOST;
import static com.photobox.services.foundation.client.fixtures.ClientConfigurationFixtures.POOL_CONFIG;
import static com.photobox.services.foundation.client.fixtures.ClientConfigurationFixtures.PORT;

import org.junit.Assert;
import org.junit.Test;

public class ThriftClientConfigurationUTest {

  @Test
  public void defaultConf_validAttributes() {
    ThriftClientConfiguration conf = ThriftClientConfiguration.defaultConfiguration(HOST, PORT);
    Assert.assertEquals(HOST, conf.getHost());
    Assert.assertEquals(PORT, conf.getPort());
    Assert.assertEquals(ThriftClientConfiguration.DEFAULT_CLIENT_TYPE, conf.getClientType());
  }

  @Test
  public void simpleConf_validAttributes() {
    ThriftClientConfiguration conf = ThriftClientConfiguration.simpleClientConfiguration(HOST, PORT);
    Assert.assertEquals(HOST, conf.getHost());
    Assert.assertEquals(PORT, conf.getPort());
    Assert.assertEquals(ClientType.SIMPLE, conf.getClientType());
  }

  @Test
     public void pooledConf_validAttributesDefaultPoolConf() {
    ThriftClientConfiguration conf = ThriftClientConfiguration.pooledClientConfiguration(HOST, PORT);
    Assert.assertEquals(HOST, conf.getHost());
    Assert.assertEquals(PORT, conf.getPort());
    Assert.assertEquals(ClientType.POOLED, conf.getClientType());
    PoolConfigUTest.assertPoolConfigEquals(PoolConfig.defaultPoolConfig(), conf.getPoolConfig());
  }

  @Test
  public void pooledConf_validAttributesAndPoolConf() {
    ThriftClientConfiguration conf =
        ThriftClientConfiguration.pooledClientConfiguration(HOST, PORT, POOL_CONFIG);
    Assert.assertEquals(HOST, conf.getHost());
    Assert.assertEquals(PORT, conf.getPort());
    Assert.assertEquals(ClientType.POOLED, conf.getClientType());
    PoolConfigUTest.assertPoolConfigEquals(POOL_CONFIG, conf.getPoolConfig());
  }

  @Test
  public void pooledConf_validAttributesAndNullPoolConf() {
    ThriftClientConfiguration conf =
        ThriftClientConfiguration.pooledClientConfiguration(HOST, PORT, POOL_CONFIG);
    Assert.assertEquals(HOST, conf.getHost());
    Assert.assertEquals(PORT, conf.getPort());
    Assert.assertEquals(ClientType.POOLED, conf.getClientType());
    PoolConfigUTest.assertPoolConfigEquals(PoolConfig.defaultPoolConfig(), conf.getPoolConfig());
  }

}