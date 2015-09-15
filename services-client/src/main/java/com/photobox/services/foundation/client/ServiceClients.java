package com.photobox.services.foundation.client;

import org.apache.commons.lang3.Validate;

import java.util.ServiceLoader;

/**
 * Provides access to the service clients.
 *
 * This class uses the {@link ServiceClientFactory} implementations to generate the clients. These
 * factories are retrieved through the Java service-provider loading facility and have to be
 * configured in the provider-configuration file:
 * {@code META-INF/services/ServiceClientFactory}
 *
 * This class provides only static methods and is not instantiable.
 *
 * @see ServiceClientFactory
 * @see ServiceLoader
 */
public final class ServiceClients {

  private ServiceClients() {
    // suppress default constructor to prevent instantiation
  }

  /**
   * Creates a new client exposing the given interface and that delegates to the server
   * specified in the configuration for the actual execution of the service.
   *
   * @param clientInterface the interface exposed by the client
   * @param conf the client's configuration
   * @param <T> the interface exposed by the client
   * @return the client
   * @throws IllegalArgumentException if the specified interface is not configured
   * @throws NullPointerException if any of the parameters is null
   */
  public static <T extends ServiceClient, C extends ClientConfiguration> T newClient(Class<T> clientInterface, C conf) {
    Validate.notNull(clientInterface, "clientInterface can't be null");
    Validate.notNull(conf, "conf can't be null");
    ServiceClientFactory<T,C> clientFactory = loadFactory(clientInterface);
    return clientFactory.newClient(conf);
  }

  private static <T extends ServiceClient, C extends ClientConfiguration> ServiceClientFactory<T,C> loadFactory(Class<T> clientInterface) {
    // loads the configured factories from the provider-configuration file in META-INF/services
    ServiceLoader<ServiceClientFactory> clientFactories =
        ServiceLoader.load(ServiceClientFactory.class);

    // search for a ServiceClientFactory supporting the requested clientInterface
    for (ServiceClientFactory clientFactory : clientFactories) {
      if (clientInterface.equals(clientFactory.supportedInterface())) {
        @SuppressWarnings("unchecked") // the previous if statement validates the clientFactory type
        ServiceClientFactory<T,C> result = (ServiceClientFactory<T,C>) clientFactory;
        return result;
      }
    }

    throw new IllegalArgumentException(
        "Unable to find a ServiceClientFactory for client interface: "
            + clientInterface.toString()
            + " (is your factory configured in "
            + "META-INF/services/ServiceClientFactory and "
            + "is the supportedInterface method implemented properly?)");
  }
}
