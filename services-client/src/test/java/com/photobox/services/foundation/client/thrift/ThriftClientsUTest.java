package com.photobox.services.foundation.client.thrift;

import static com.photobox.services.foundation.client.fixtures.ClientConfigurationFixtures.THRIFT_CONFIGURATION_POOLED;
import static com.photobox.services.foundation.client.fixtures.ClientConfigurationFixtures.THRIFT_CONFIGURATION_SIMPLE;
import static org.junit.Assert.assertEquals;

import com.photobox.services.foundation.client.fixtures.TestServiceClient;

import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.apache.thrift.protocol.TProtocol;
import org.junit.Assert;
import org.junit.Test;

public class ThriftClientsUTest {

  @Test
  public void newThriftClient_createsPooledClient() {
    TestServiceClient testServiceClient = ThriftClients.newThriftClient(
        new TServiceClientFactoryStub(), TestServiceClient.class, THRIFT_CONFIGURATION_POOLED);
    Assert.assertNotNull(testServiceClient);
  }

  @Test
  public void newThriftClient_pooledClient_setThriftConfiguration() {
    TestServiceClient testServiceClient = ThriftClients.newThriftClient(
        new TServiceClientFactoryStub(), TestServiceClient.class, THRIFT_CONFIGURATION_POOLED);
    assertEquals(THRIFT_CONFIGURATION_POOLED, testServiceClient.getConfiguration());
  }

  @Test
  public void newThriftClient_pooledClient_setPooledConfiguration() {
    TestServiceClient testServiceClient = ThriftClients.newThriftClient(
        new TServiceClientFactoryStub(), TestServiceClient.class, THRIFT_CONFIGURATION_POOLED);
    assertEquals(THRIFT_CONFIGURATION_POOLED, testServiceClient.getConfiguration());
  }

  @Test
  public void newThriftClient_createsSimpleClient() {
    TestServiceClient testServiceClient = ThriftClients.newThriftClient(
        new TServiceClientFactoryStub(), TestServiceClient.class, THRIFT_CONFIGURATION_SIMPLE);
    Assert.assertNotNull(testServiceClient);
  }

  @Test
  public void newThriftClient_simpleClient_hasConfiguration() {
    TestServiceClient testServiceClient = ThriftClients.newThriftClient(
        new TServiceClientFactoryStub(), TestServiceClient.class, THRIFT_CONFIGURATION_SIMPLE);
    assertEquals(THRIFT_CONFIGURATION_SIMPLE, testServiceClient.getConfiguration());
  }

  @Test(expected = NullPointerException.class)
  public void newThriftClient_nullFactory() {
    ThriftClients.newThriftClient(null, TestServiceClient.class, THRIFT_CONFIGURATION_POOLED);
  }

  @Test(expected = NullPointerException.class)
  public void newThriftClient_nullInterface() {
    ThriftClients.newThriftClient(
        new TServiceClientFactoryStub(), null, THRIFT_CONFIGURATION_POOLED);
  }

  @Test(expected = NullPointerException.class)
  public void newThriftClient_nullConfiguration() {
    ThriftClients.newThriftClient(
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