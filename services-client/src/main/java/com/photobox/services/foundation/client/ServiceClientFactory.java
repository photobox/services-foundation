package com.photobox.services.foundation.client;

/**
 * Defines a factory that produces {@link ServiceClient}.
 *
 * ServiceFactory implementations have to be configured in the provider-configuration file:
 * {@code META-INF/services/ServiceClientFactory}
 *
 * This file has to be provided in each artifact with implementations of
 * ServiceClientFactory and has to contain the fully qualified names of the these classes.
 *
 * @param <T> the interface exposed by the clients produced by this factory
 * @param <C> the type of the object with the client's configuration
 */
public interface ServiceClientFactory<T extends ServiceClient, C extends ClientConfiguration> {
  /**
   * Creates a new client exposing the given interface and that delegates to the server
   * specified in the configuration for the actual execution of the service.
   *
   * @param conf the client's configuration
   * @return the client
   */
  T newClient(C conf);

  /**
   * Provides the interface exposed by the clients generated by the factory.
   * This is useful when loading the factory to check if is supporting the expected interface.
   *
   * @return the interface of the clients generated by this factory
   */
  Class<T> supportedInterface();
}