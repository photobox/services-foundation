package com.photobox.services.foundation.client.thrift;

import static com.photobox.services.foundation.client.fixtures.ClientConfigurationFixtures.THRIFT_CONFIGURATION_SIMPLE;
import static org.junit.Assert.assertEquals;

import com.photobox.services.foundation.client.fixtures.TestServiceClient;

import org.junit.Test;

/**
 * Tests the behaviour of the different Thrift-based service clients.
 */
public class SimpleThriftClientsUTest extends AbstractThriftClientTest {

  @Test
  public void newThriftClient_simpleClientBehaviour_resultReturnedFromTheClient() {
    TestServiceClient testServiceClient = testClient(THRIFT_CONFIGURATION_SIMPLE);
    Object result = testServiceClient.test();
    // the returned object is the same obtained from the client
    assertEquals(TServiceClientFake.CLIENT_RESULT, result);
  }

  @Test
  public void newThriftClient_simpleClientBehaviour_oneNewClientEveryCall() {
    TestServiceClient testServiceClient = testClient(THRIFT_CONFIGURATION_SIMPLE);

    // 1st call
    testServiceClient.test();
    // only one client was created during the invocation
    assertEquals(1, TServiceClientFactoryFake.createdClientCnt);

    // 2nd call
    testServiceClient.test();
    // a new client was created during the invocation
    assertEquals(2, TServiceClientFactoryFake.createdClientCnt);
  }

  @Test
  public void newThriftClient_simpleClientBehaviour_connectionLifeLimitedToInvocation() {
    TestServiceClient testServiceClient = testClient(THRIFT_CONFIGURATION_SIMPLE);

    testServiceClient.test();

    // the connection was opened when the invocation had been made
    assertEquals(true, TServiceClientFactoryFake.client.inConnOpen);
    assertEquals(true, TServiceClientFactoryFake.client.outConnOpen);

    // the connection has been closed at the end of the invocation
    assertEquals(
        false, TServiceClientFactoryFake.client.getInputProtocol().getTransport().isOpen());
    assertEquals(
        false, TServiceClientFactoryFake.client.getOutputProtocol().getTransport().isOpen());
  }

}
