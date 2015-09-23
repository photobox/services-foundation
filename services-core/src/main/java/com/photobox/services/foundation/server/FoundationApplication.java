package com.photobox.services.foundation.server;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

/**
 * The base class for a Foundation application.
 */
public abstract class FoundationApplication
    extends Application<FoundationConfiguration> {
  @Override
  public final void run(FoundationConfiguration foundationConfiguration, Environment environment) {
    // override the Dropwizard execution providing a base one for the Foundation services
  }
}
