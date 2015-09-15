package com.photobox.services.foundation.client.thrift;

import com.photobox.services.foundation.client.ServiceClient;

import org.apache.commons.lang3.Validate;
import org.apache.thrift.TServiceClientFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * This is a factory to create different types of Thrift based {@link ServiceClient}.
 *
 * This class provides only static factories and is not instantiable.
 */
public final class ThriftServiceClients {

  private ThriftServiceClients() {
    // suppress default constructor to prevent instantiation
  }

  /**
   * Creates a new client exposing the given interface and that delegates to the Thrift server
   * specified in the configuration for the actual execution of the service.
   *
   * The client produced by this method will be a proxy adding some features around the real
   * Thrift client that will be generated through the provided factory.
   *
   * The behaviour of the client will depend on the {@code clientType} specified
   * in the configuration.
   *
   * @see ClientType
   *
   * @param thriftClientFactory the factory that will be used to generate the actual Thrift client
   * @param clientInterface the interface exposed by the client
   * @param conf the client's configuration
   * @return the client
   * @throws IllegalArgumentException if clientType is not supported
   * @throws NullPointerException if any of the parameters is null
   */
  public static <T extends ServiceClient> T newThriftClient(
      TServiceClientFactory thriftClientFactory,
      Class<T> clientInterface,
      ThriftClientConfiguration conf) {

    Validate.notNull(thriftClientFactory, "thriftClientFactory can't be null");
    Validate.notNull(clientInterface, "clientInterface can't be null");
    Validate.notNull(conf, "conf can't be null");

    InvocationHandler invocationHandler;
    switch (conf.getClientType()) {
      case SIMPLE:
        invocationHandler = new SimpleThriftServiceClient(thriftClientFactory, conf);
        break;
      case POOLED:
        invocationHandler = new PooledThriftServiceClient(thriftClientFactory, conf);
        break;
      default:
        throw new IllegalArgumentException("Unsupported type: " + conf.getClientType());
    }

    @SuppressWarnings("unchecked") // clientInterface has been checked being of type T
    T clientProxy = (T) Proxy.newProxyInstance(
        clientInterface.getClassLoader(),
        new Class[]{clientInterface},
        invocationHandler);

    return clientProxy;
  }
}
