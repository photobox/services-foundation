package com.photobox.services.foundation.client.fixtures;

import com.photobox.services.foundation.client.ServiceClient;

/**
 * This interface has multiple supporting factories and this should bring to an error.
 */
public interface TestServiceClientMultiple extends ServiceClient {
  // just a test method
  Object test();
}
