package com.photobox.services.foundation.client.thrift;

import com.photobox.services.foundation.client.ClientConfiguration;

import org.apache.commons.lang3.Validate;

/**
 * Provides specific configuration attributes for Thrift clients.
 */
public class ThriftClientConfiguration extends ClientConfiguration {

  public static final ClientType DEFAULT_CLIENT_TYPE = ClientType.POOLED;

  private final ClientType clientType;

  /**
   * Creates a new Thrift specific configuration starting from a generic one.
   * ClientType will be set using the default value 'POOLED'.
   *
   * @param conf a generic configuration
   * @throws NullPointerException if any of the parameters is null
   */
  public ThriftClientConfiguration(ClientConfiguration conf) {
    this(conf.getHost(), conf.getPort());
  }

  /**
   * Creates a new Thrift configuration with default clientType 'POOLED'.
   *
   * @param host the host of the remote server
   * @param port the port of the remote server
   * @throws NullPointerException if any of the parameters is null
   * @throws IllegalArgumentException if the host is an empty String
   */
  public ThriftClientConfiguration(String host, int port) {
    this(DEFAULT_CLIENT_TYPE, host, port);
  }

  /**
   * Creates a new Thrift configuration with the specified attributes.
   *
   * @see ClientType
   *
   * @param clientType the type of Thrift client
   * @param host the host of the remote server
   * @param port the port of the remote server
   */
  public ThriftClientConfiguration(ClientType clientType, String host, int port) {
    super(host, port);
    Validate.notNull(clientType, "clientType can't be null");
    this.clientType = clientType;
  }

  public final ClientType getClientType() {
    return clientType;
  }
}
