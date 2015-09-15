package com.photobox.services.foundation.client.fixtures;

import com.photobox.services.foundation.client.ServiceClient;

/**
 * An interface to test ServiceClient factories.
 */
public interface TestServiceClient extends ServiceClient {
  // just a test method
  Object test();
}
