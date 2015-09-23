package com.photobox.services.foundation.client.thrift;

import static com.photobox.services.foundation.client.fixtures.ClientConfigurationFixtures.PORT;

import com.photobox.services.foundation.client.ClientConfiguration;
import com.photobox.services.foundation.client.fixtures.TestServiceClient;
import com.photobox.services.foundation.testing.thrift.ThriftServerRule;

import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.apache.thrift.protocol.TProtocol;
import org.junit.ClassRule;

/**
 * Provide common logic to test different Thrift client implementations.
 */
public abstract class AbstractThriftClientTest {

  // even if the client behaviour is faked it's not possible to fake the socket connection so a
  // Thrift server has to be listening on the expected port
  @ClassRule
  public static final ThriftServerRule RULE = new ThriftServerRule(
      PORT,
      (tProtocol, tProtocol1) -> true);




  // return a test implementation of Thrift client based on the given configuration
  static TestServiceClient testClient(ThriftClientConfiguration configuration) {
    return ThriftClients.newThriftClient(
        new TServiceClientFactoryFake(), TestServiceClient.class, configuration);
  }


  // fake the behaviour of a thrift client factory adding some testing capabilities
  static class TServiceClientFactoryFake implements TServiceClientFactory {
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
    public TServiceClient getClient(TProtocol tProtocol, TProtocol tProtocol1) {
      throw new IllegalStateException("Not expected a call to this method");
    }
  }


  // fake the behaviour of a TServiceClient validating that the connection has been opened
  static class TServiceClientFake extends TServiceClient implements TestServiceClient {
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
