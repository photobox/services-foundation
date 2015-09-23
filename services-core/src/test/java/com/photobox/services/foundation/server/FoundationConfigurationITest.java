package com.photobox.services.foundation.server;

import com.photobox.services.foundation.server.thrift.dropwizard.ManagedThriftServer;
import com.photobox.services.foundation.server.thrift.dropwizard.ManagedThriftServerFactory;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;

/**
 * Test the startup of a generic Foundation application and the loading of the configuration.
 */
public class FoundationConfigurationITest {

  @ClassRule
  public static final DropwizardAppRule<FoundationConfiguration> RULE =
      new DropwizardAppRule<>(
          FakeDropwizardApp.class,
          ResourceHelpers.resourceFilePath("configuration/test.yml"));

  public static class FakeDropwizardApp extends FoundationApplication {
    public static void main(String[] args) throws Exception {
      new FakeDropwizardApp().run(args);
    }
  }

  @Test
  public void applicationStartAndConfigurationLoaded() {
    ManagedThriftServerFactory factory = RULE.getConfiguration().getThriftServerFactory();
    Assert.assertNotNull(factory);

    ManagedThriftServer server = factory.build();
    Assert.assertNotNull(server);
  }

}