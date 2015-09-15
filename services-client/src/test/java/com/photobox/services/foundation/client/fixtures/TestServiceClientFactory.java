package com.photobox.services.foundation.client.fixtures;

import static com.photobox.services.foundation.client.fixtures.ClientConfigurationFixtures.clientConfiguration;

import com.photobox.services.foundation.client.ClientConfiguration;
import com.photobox.services.foundation.client.ServiceClientFactory;

/**
 * A simple ServiceClientFactory doing its job properly.
 */
public class TestServiceClientFactory
    implements ServiceClientFactory<TestServiceClient, ClientConfiguration> {

  public static final TestServiceClient TEST_SERVICE_CLIENT = new TestServiceClient() {
    @Override
    public Object test() {
      return null;
    }
    @Override
    public ClientConfiguration getConfiguration() {
      return clientConfiguration();
    }
  };

  @Override
  public TestServiceClient newClient(ClientConfiguration conf) {
    return TEST_SERVICE_CLIENT;
  }

  @Override
  public Class<TestServiceClient> supportedInterface() {
    return TestServiceClient.class;
  }

}
