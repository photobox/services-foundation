package com.photobox.services.foundation.client;

import static com.photobox.services.foundation.client.fixtures.ClientConfigurationFixtures.clientConfiguration;
import static org.junit.Assert.assertEquals;

import com.photobox.services.foundation.client.fixtures.TestServiceClient;
import com.photobox.services.foundation.client.fixtures.TestServiceClientFactory;
import com.photobox.services.foundation.client.fixtures.TestServiceClientFactoryFaulty.TestServiceClientNotSupportedByFactory;

import org.junit.Test;

public class ServiceClientsUTest {

  private static final ClientConfiguration TEST_CONFIGURATION = clientConfiguration();

  @Test
  public void newClient_createdFromFactory() {
    // tests that a new client for the expected interface is provided by the factory
    TestServiceClient client =
        ServiceClients.newClient(TestServiceClient.class, TEST_CONFIGURATION);
    assertEquals(TestServiceClientFactory.TEST_SERVICE_CLIENT, client);
  }

  @Test(expected = IllegalArgumentException.class)
  public void newClient_interfaceNotSupported() {
    ServiceClients.newClient(TestServiceClientNotSupportedByFactory.class, TEST_CONFIGURATION);
  }

  @Test(expected = IllegalArgumentException.class)
  public void newClient_factoryNotSupportingInterfaceProperly() {
    ServiceClients.newClient(TestServiceClientWithNoConfiguredFactory.class, TEST_CONFIGURATION);
  }

  @Test(expected = NullPointerException.class)
  public void newClient_nullInterface() {
    ServiceClients.newClient(null, TEST_CONFIGURATION);
  }

  @Test(expected = NullPointerException.class)
  public void newClient_nullConfiguration() {
    ServiceClients.newClient(TestServiceClient.class, null);
  }

  private interface TestServiceClientWithNoConfiguredFactory extends ServiceClient {}

}