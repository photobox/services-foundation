package com.photobox.services.foundation.client.thrift;

import static com.photobox.services.foundation.client.fixtures.ClientConfigurationFixtures.PORT;
import static com.photobox.services.foundation.client.fixtures.ClientConfigurationFixtures.TEST_POOLED_CONFIGURATION;
import static com.photobox.services.foundation.client.fixtures.ClientConfigurationFixtures.TEST_SIMPLE_CONFIGURATION;
import static org.junit.Assert.assertEquals;

import com.photobox.services.foundation.client.ClientConfiguration;
import com.photobox.services.foundation.client.fixtures.TestServiceClient;
import com.photobox.services.foundation.testing.thrift.ThriftServerRule;

import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.apache.thrift.protocol.TProtocol;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Tests the behaviour of the different Thrift-based service clients.
 */
public class ThriftServiceClientsITest {

  // even if the client behaviour is faked it's not possible to fake the socket connection so a
  // Thrift server has to be listening on the expected port
  @ClassRule
  public static final ThriftServerRule RULE = new ThriftServerRule(
      PORT,
      (tProtocol, tProtocol1) -> true);

  @Test
  public void newThriftClient_simpleClientBehaviour_resultReturnedFromTheClient() {
    TestServiceClient testServiceClient = testClient(TEST_SIMPLE_CONFIGURATION);
    Object result = testServiceClient.test();
    // the returned object is the same obtained from the client
    assertEquals(TServiceClientFake.CLIENT_RESULT, result);
  }

  @Test
  public void newThriftClient_simpleClientBehaviour_oneNewClientEveryCall() {
    TestServiceClient testServiceClient = testClient(TEST_SIMPLE_CONFIGURATION);

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
    TestServiceClient testServiceClient = testClient(TEST_SIMPLE_CONFIGURATION);

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

  @Test
  public void newThriftClient_pooledClientBehaviour_resultReturnedFromTheClient() {
    TestServiceClient testServiceClient = testClient(TEST_POOLED_CONFIGURATION);
    Object result = testServiceClient.test();
    // the returned object is the same obtained from the client
    assertEquals(TServiceClientFake.CLIENT_RESULT, result);
  }

  @Test
  public void newThriftClient_pooledClientBehaviour_usesPooledConnection() {
    TestServiceClient testServiceClient = testClient(TEST_POOLED_CONFIGURATION);

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
    TestServiceClient testServiceClient = testClient(TEST_POOLED_CONFIGURATION);

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

  private TestServiceClient testClient(ThriftClientConfiguration configuration) {
    return ThriftServiceClients.newThriftClient(
        new TServiceClientFactoryFake(), TestServiceClient.class, configuration);
  }


  // fake the behaviour of a thrift client factory adding some testing capabilities
  private static class TServiceClientFactoryFake implements TServiceClientFactory {
    static TServiceClientFake client;
    static int createdClientCnt;

    TServiceClientFactoryFake() {
      client = null;
      createdClientCnt = 0;
    }

    @Override
    public TServiceClient getClient(TProtocol tProtocol) {
      createdClientCnt++;
      client = new TServiceClientFake(tProtocol);
      return client;
    }

    @Override
    public TServiceClient getClient(TProtocol tProtocol, org.apache.thrift.protocol.TProtocol tProtocol1) {
      throw new IllegalStateException("Not expected a call to this method");
    }
  }

  // fake the behaviour of a TServiceClient validating that the connection has been opened
  private static class TServiceClientFake extends TServiceClient implements TestServiceClient {
    static final Object CLIENT_RESULT = new Object();
    boolean inConnOpen = false;
    boolean outConnOpen = false;

    public TServiceClientFake(TProtocol prot) {
      super(prot);
    }

    @Override
    public Object test() {
      inConnOpen = getInputProtocol().getTransport().isOpen();
      outConnOpen = getOutputProtocol().getTransport().isOpen();

      // we don't want to test the complete call to the server so returns a stubbed object
      return CLIENT_RESULT;
    }

    @Override
    public ClientConfiguration getConfiguration() {
      // this is part of ServiceClient, no need to test it here
      return null;
    }
  }

}
