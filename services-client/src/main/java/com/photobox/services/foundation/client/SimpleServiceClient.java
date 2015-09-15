package com.photobox.services.foundation.client;

/**
 * Skeletal implementation of a {@link ServiceClient}.
 */
public final class SimpleServiceClient implements ServiceClient {

  private final ClientConfiguration conf;

  public SimpleServiceClient(ClientConfiguration conf) {
    this.conf = conf;
  }

  @Override
  public ClientConfiguration getConfiguration() {
    return conf;
  }
}
