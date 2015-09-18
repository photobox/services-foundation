package com.photobox.services.foundation.client;

/**
 * Skeletal implementation of a {@link ServiceClient}.
 */
public final class SimpleServiceClient<C extends ClientConfiguration> implements ServiceClient<C> {

  private final C conf;

  public SimpleServiceClient(C conf) {
    this.conf = conf;
  }

  @Override
  public C getConfiguration() {
    return conf;
  }
}
