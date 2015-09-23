package com.photobox.services.foundation.server.thrift.dropwizard;

import com.google.common.io.Resources;

import com.photobox.services.foundation.server.thrift.dropwizard.fixtures.TestProcessor;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import javax.validation.Validation;

import io.dropwizard.configuration.ConfigurationFactory;
import io.dropwizard.jackson.Jackson;

/**
 * Test that the configuration for the Thrift server factory is loading and working properly.
 */
public class ThriftServerConfigurationITest {

  private static final int CONFIGURED_PORT = 9984;

  @Test
  public void standardMultiThreadedServerConfiguration() throws Exception {
    ManagedThriftServerFactory factory = getManagedThriftServerFactory(
        "yaml/thrift_server_standard.yml");
    Assert.assertTrue(factory instanceof MultiThreadedThriftServerFactory);
    MultiThreadedThriftServerFactory realFactory = (MultiThreadedThriftServerFactory) factory;
    Assert.assertEquals(CONFIGURED_PORT, realFactory.getPort());
    Assert.assertTrue(realFactory.getProcessor() instanceof TestProcessor);
  }

  private ManagedThriftServerFactory getManagedThriftServerFactory(String resourceName)
      throws Exception {
    return new ConfigurationFactory<>(ManagedThriftServerFactory.class,
        Validation.buildDefaultValidatorFactory().getValidator(), Jackson.newObjectMapper(), "f")
        .build(new File(Resources.getResource(resourceName).toURI()));
  }
}