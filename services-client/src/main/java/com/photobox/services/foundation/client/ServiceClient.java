package com.photobox.services.foundation.client;

/**
 * Defines the common client-side functionalities that a client implements in addition to the
 * usual service interface.
 */
public interface ServiceClient<C extends ClientConfiguration> {
  /**
   * @return the client's configuration
   */
  C getConfiguration();
}
