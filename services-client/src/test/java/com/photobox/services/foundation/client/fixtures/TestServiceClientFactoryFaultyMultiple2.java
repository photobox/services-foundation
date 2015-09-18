package com.photobox.services.foundation.client.fixtures;

import com.photobox.services.foundation.client.ClientConfiguration;
import com.photobox.services.foundation.client.ServiceClientFactory;

/**
 * The interface supported by this ServiceClientFactory has multiple supporting factories and this
 * should produce an error.
 */
public class TestServiceClientFactoryFaultyMultiple2
    implements ServiceClientFactory<TestServiceClientMultiple, ClientConfiguration> {

  @Override
  public TestServiceClientMultiple newClient(ClientConfiguration conf) {
    return null;
  }

  @Override
  public Class<TestServiceClientMultiple> supportedInterface() {
    return TestServiceClientMultiple.class;
  }

}
