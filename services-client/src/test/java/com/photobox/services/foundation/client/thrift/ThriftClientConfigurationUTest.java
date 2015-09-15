package com.photobox.services.foundation.client.thrift;

import com.photobox.services.foundation.client.ClientConfiguration;
import com.photobox.services.foundation.client.fixtures.ClientConfigurationFixtures;

import org.junit.Assert;
import org.junit.Test;

public class ThriftClientConfigurationUTest {

  @Test
  public void thriftClientConfiguration_validAttributes() {
    ThriftClientConfiguration conf =
        new ThriftClientConfiguration(ClientType.SIMPLE, "localhost", 123);
    assertConf(conf, ClientType.SIMPLE, "localhost", 123);
  }

  @Test
  public void thriftClientConfiguration_validAttributesDefaultType() {
    ThriftClientConfiguration conf = new ThriftClientConfiguration("localhost", 123);
    assertConf(conf, ThriftClientConfiguration.DEFAULT_CLIENT_TYPE, "localhost", 123);
  }

  @Test
  public void thriftClientConfiguration_fromValidConfiguration() {
    ClientConfiguration baseConf = ClientConfigurationFixtures.clientConfiguration();
    ThriftClientConfiguration conf = new ThriftClientConfiguration(baseConf);
    assertConf(conf, ThriftClientConfiguration.DEFAULT_CLIENT_TYPE, baseConf.getHost(), baseConf.getPort());
  }

  @Test(expected = NullPointerException.class)
  public void thriftClientConfiguration_nullType() {
    new ThriftClientConfiguration(null, "localhost", 123);
  }

  @Test(expected = NullPointerException.class)
  public void thriftClientConfiguration_nullConfiguration() {
    new ThriftClientConfiguration(null);
  }

  private void assertConf(
      ThriftClientConfiguration actualConf, ClientType clientType, String host, int port) {
    Assert.assertEquals(clientType, actualConf.getClientType());
    Assert.assertEquals(host, actualConf.getHost());
    Assert.assertEquals(port, actualConf.getPort());
  }
}