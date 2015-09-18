package com.photobox.services.foundation.client.thrift;

import com.photobox.services.foundation.client.ClientConfiguration;

import org.apache.commons.lang3.Validate;

/**
 * Provides specific configuration attributes for Thrift clients.
 */
public class ThriftClientConfiguration extends ClientConfiguration {

  static final ClientType DEFAULT_CLIENT_TYPE = ClientType.POOLED;

  private final ClientType clientType;
  private final PoolConfig poolConfig;

  /**
   * Creates a Thrift client configuration with default parameters.
   *
   * @param host the host of the remote server
   * @param port the port of the remote server
   * @return a default Thrift client configuration
   */
  public static ThriftClientConfiguration defaultConfiguration(String host, int port) {
    return new ThriftClientConfiguration(DEFAULT_CLIENT_TYPE, host, port);
  }

  /**
   * Creates the configuration for a simple Thrift client.
   * Simple Thrift clients use a new connection for each invocation.
   *
   * @param host the host of the remote server
   * @param port the port of the remote server
   * @return the configuration for a simple Thrift client
   */
  public static ThriftClientConfiguration simpleClientConfiguration(String host, int port) {
    return new ThriftClientConfiguration(ClientType.SIMPLE, host, port);
  }

  /**
   * Creates the configuration for a pooled Thrift client using the default pool configuration.
   * Pooled Thrift clients use a pool of connections to the specified server.
   *
   * @param host the host of the remote server
   * @param port the port of the remote server
   * @return the configuration for a pooled Thrift client
   */
  public static ThriftClientConfiguration pooledClientConfiguration(String host, int port) {
    return new ThriftClientConfiguration(ClientType.POOLED, host, port);
  }

  /**
   * Creates the configuration for a pooled Thrift client using the given
   * configuration for the pool. If the pool configuration is {@code null}
   * the default one will be used.
   * Pooled Thrift clients use a pool of connections to the specified server.
   *
   * @param host the host of the remote server
   * @param port the port of the remote server
   * @param poolConfig the configuration for the connection pool
   * @return the configuration for a pooled Thrift client
   */
  public static ThriftClientConfiguration pooledClientConfiguration(
      String host, int port, PoolConfig poolConfig) {
    return new ThriftClientConfiguration(ClientType.POOLED, host, port, poolConfig);
  }

  // can only be instantiated through internal factories
  private ThriftClientConfiguration(ClientType clientType, String host, int port) {
    this(clientType, host, port, null);
  }

  // can only be instantiated through internal factories
  private ThriftClientConfiguration(ClientType clientType, String host, int port, PoolConfig poolConfig) {
    super(host, port);
    Validate.notNull(clientType, "clientType can't be null");
    this.clientType = clientType;
    if (poolConfig == null) {
      this.poolConfig =  PoolConfig.defaultPoolConfig();
    } else {
      this.poolConfig = poolConfig;
    }
  }

  public final ClientType getClientType() {
    return clientType;
  }

  public final PoolConfig getPoolConfig() {
    return poolConfig;
  }

}
