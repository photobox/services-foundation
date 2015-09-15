package com.photobox.services.foundation.client.thrift;

import static com.photobox.services.foundation.client.fixtures.ClientConfigurationFixtures.TEST_POOLED_CONFIGURATION;
import static com.photobox.services.foundation.client.fixtures.ClientConfigurationFixtures.TEST_SIMPLE_CONFIGURATION;

import com.photobox.services.foundation.client.fixtures.TestServiceClient;

import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.apache.thrift.protocol.TProtocol;
import org.junit.Assert;
import org.junit.Test;

public class ThriftServiceClientsUTest {

  @Test
  public void newThriftClient_createsPooledClient() {
    TestServiceClient testServiceClient = ThriftServiceClients.newThriftClient(
        new TServiceClientFactoryStub(), TestServiceClient.class, TEST_POOLED_CONFIGURATION);
    Assert.assertNotNull(testServiceClient);
  }

  @Test
  public void newThriftClient_pooledClient_hasConfiguration() {
    TestServiceClient testServiceClient = ThriftServiceClients.newThriftClient(
        new TServiceClientFactoryStub(), TestServiceClient.class, TEST_POOLED_CONFIGURATION);
    Assert.assertEquals(TEST_POOLED_CONFIGURATION, testServiceClient.getConfiguration());
  }

  @Test
  public void newThriftClient_createsSimpleClient() {
    TestServiceClient testServiceClient = ThriftServiceClients.newThriftClient(
        new TServiceClientFactoryStub(), TestServiceClient.class, TEST_SIMPLE_CONFIGURATION);
    Assert.assertNotNull(testServiceClient);
  }

  @Test
  public void newThriftClient_simpleClient_hasConfiguration() {
    TestServiceClient testServiceClient = ThriftServiceClients.newThriftClient(
        new TServiceClientFactoryStub(), TestServiceClient.class, TEST_SIMPLE_CONFIGURATION);
    Assert.assertEquals(TEST_SIMPLE_CONFIGURATION, testServiceClient.getConfiguration());
  }

  @Test(expected = NullPointerException.class)
  public void newThriftClient_nullFactory() {
    ThriftServiceClients.newThriftClient(null, TestServiceClient.class, TEST_POOLED_CONFIGURATION);
  }

  @Test(expected = NullPointerException.class)
  public void newThriftClient_nullInterface() {
    ThriftServiceClients.newThriftClient(
        new TServiceClientFactoryStub(), null, TEST_POOLED_CONFIGURATION);
  }

  @Test(expected = NullPointerException.class)
  public void newThriftClient_nullConfiguration() {
    ThriftServiceClients.newThriftClient(
        new TServiceClientFactoryStub(), TestServiceClient.class, null);
  }

  // stubbed thrift client factory used in the tests
  private static class TServiceClientFactoryStub implements TServiceClientFactory {
    @Override
    public TServiceClient getClient(TProtocol tProtocol) {
      return new TServiceClient(tProtocol) {};
    }
    @Override
    public TServiceClient getClient(TProtocol tProtocol, TProtocol tProtocol1) {
      return new TServiceClient(tProtocol, tProtocol1) {};
    }
  }

}