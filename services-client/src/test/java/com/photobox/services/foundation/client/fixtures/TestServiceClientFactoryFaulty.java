package com.photobox.services.foundation.client.fixtures;

import com.photobox.services.foundation.client.ClientConfiguration;
import com.photobox.services.foundation.client.ServiceClientFactory;
import com.photobox.services.foundation.client.ServiceClient;

/**
 * A ServiceClientFactory that doesn't implement correctly the supportedInterface method.
 */
public class TestServiceClientFactoryFaulty
    implements ServiceClientFactory<TestServiceClientFactoryFaulty.TestServiceClientNotSupportedByFactory, ClientConfiguration> {

  @Override
  public TestServiceClientNotSupportedByFactory newClient(ClientConfiguration conf) {
    return null;
  }

  @Override
  public Class<TestServiceClientNotSupportedByFactory> supportedInterface() {
    // instead of returning the supported interface it just returns null
    return null;
  }

  public interface TestServiceClientNotSupportedByFactory extends ServiceClient {}
}
