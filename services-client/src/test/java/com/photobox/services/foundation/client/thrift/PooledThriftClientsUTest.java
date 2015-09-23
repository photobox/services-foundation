package com.photobox.services.foundation.client.thrift;

import static com.photobox.services.foundation.client.fixtures.ClientConfigurationFixtures.THRIFT_CONFIGURATION_POOLED;
import static org.junit.Assert.assertEquals;

import com.photobox.services.foundation.client.fixtures.TestServiceClient;

import org.junit.Test;

/**
 * Tests the behaviour of the different Thrift-based service clients.
 */
public class PooledThriftClientsUTest extends AbstractThriftClientTest {

  @Test
  public void newThriftClient_pooledClientBehaviour_resultReturnedFromTheClient() {
    TestServiceClient testServiceClient = testClient(THRIFT_CONFIGURATION_POOLED);
    Object result = testServiceClient.test();
    // the returned object is the same obtained from the client
    assertEquals(TServiceClientFake.CLIENT_RESULT, result);
  }

  @Test
  public void newThriftClient_pooledClientBehaviour_usesPooledConnection() {
    TestServiceClient testServiceClient = testClient(THRIFT_CONFIGURATION_POOLED);

    // 1st call
    testServiceClient.test();
    // only one client was created during the invocation
    assertEquals(1, TServiceClientFactoryFake.createdClientCnt);

    // 2nd call
    testServiceClient.test();
    // no new client is created during the invocation
    assertEquals(1, TServiceClientFactoryFake.createdClientCnt);
  }

  @Test
  public void newThriftClient_pooledClientBehaviour_connectionRemainOpenAfterInvocation() {
    TestServiceClient testServiceClient = testClient(THRIFT_CONFIGURATION_POOLED);

    testServiceClient.test();

    // the connection was opened when the invocation had been made
    assertEquals(true, TServiceClientFactoryFake.client.inConnOpen);
    assertEquals(true, TServiceClientFactoryFake.client.outConnOpen);

    // the connection is still open at the end of the invocation
    assertEquals(
        true, TServiceClientFactoryFake.client.getInputProtocol().getTransport().isOpen());
    assertEquals(
        true, TServiceClientFactoryFake.client.getOutputProtocol().getTransport().isOpen());
  }

}
