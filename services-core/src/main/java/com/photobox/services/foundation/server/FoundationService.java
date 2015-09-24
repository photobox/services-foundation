package com.photobox.services.foundation.server;

import com.photobox.services.foundation.server.thrift.dropwizard.ManagedThriftServerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * The base class for a Foundation application.
 */
public abstract class FoundationService {

  private static final Logger LOG = LoggerFactory.getLogger(FoundationService.class);

  private Application<FoundationConfiguration> launcher = new DropWizardLauncher();

  /**
   * Start the Foundation service.
   *
   * @param args the command-line arguments
   * @throws Exception if something goes wrong
   */
  public final void boot(String... args) throws Exception {
    launcher.run(args);
  }

  // Launch the Foundation service as a Dropwizard application
  // FIXME this class shouldn't be exposed as public - it's needed to run the service with a JUnit rule
  public final static class DropWizardLauncher extends Application<FoundationConfiguration> {
    @Override
    public void initialize(Bootstrap<FoundationConfiguration> bootstrap) {
      // nothing to do yet
    }

    @Override
    // override the Dropwizard execution providing a base one for the Foundation services
    public void run(FoundationConfiguration configuration, Environment environment)
        throws Exception {
      // start the Thrift server if any is configured
      ManagedThriftServerFactory thriftFactory = configuration.getThriftServerFactory();
      if (thriftFactory != null) {
        environment.lifecycle().manage(thriftFactory.build());
      } else {
        LOG.warn("No Thrift server configuration has been found");
      }
    }
  }
}
