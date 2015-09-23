package com.photobox.services.foundation.server.thrift.dropwizard;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import io.dropwizard.jackson.Discoverable;

/**
 * A factory for building {@link ManagedThriftServer} instances for Dropwizard applications.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public interface ManagedThriftServerFactory extends Discoverable {
  /**
   * Build a Thrift server.
   *
   * @return a {@link ManagedThriftServer}
   */
  ManagedThriftServer build();

  /**
   * Return that will be exposed by the servers created by this factory.
   *
   * @return the server port
   */
  int getPort();
}
