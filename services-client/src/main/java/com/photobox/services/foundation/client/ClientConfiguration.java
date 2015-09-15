package com.photobox.services.foundation.client;

import org.apache.commons.lang3.Validate;

/**
 * Provides the configuration attributes for a generic service client.
 *
 * Open to be extended by more specific classes.
 */
public class ClientConfiguration {

  private final String host;
  private final int port;

  /**
   * Creates a generic client configuration.
   *
   * @param host the host of the remote server
   * @param port the port of the remote server
   * @throws NullPointerException if any of the parameters is null
   * @throws IllegalArgumentException if the host is an empty String
   */
  public ClientConfiguration(String host, int port) {
    Validate.notEmpty(host, "host can't be empty");
    this.host = host;
    this.port = port;
  }

  public final String getHost() {
    return host;
  }

  public final int getPort() {
    return port;
  }
}
