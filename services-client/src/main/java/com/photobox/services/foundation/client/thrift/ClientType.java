package com.photobox.services.foundation.client.thrift;

/**
 * Defines the different types of supported Thrift clients.
 */
public enum ClientType {
  /**
   * A SIMPLE client starts a new connection for each call.
   */
  SIMPLE,

  /**
   * A POOLED client keeps a configurable pool of connections.
   */
  POOLED
}
