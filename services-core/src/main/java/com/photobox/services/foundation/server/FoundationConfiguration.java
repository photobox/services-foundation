package com.photobox.services.foundation.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.photobox.services.foundation.server.thrift.dropwizard.ManagedThriftServerFactory;

import javax.validation.Valid;

import io.dropwizard.Configuration;

/**
 * Provide a common configuration structure for Foundation services.
 */
public class FoundationConfiguration extends Configuration {
  @Valid
  // expected to be set through injection - no default value
  private ManagedThriftServerFactory thriftServerFactory;

  /**
   * Return the configured factory to create a Thrift server.
   *
   * @return the thrift server factory
   */
  @JsonProperty("thrift-server")
  public final ManagedThriftServerFactory getThriftServerFactory() {
    return thriftServerFactory;
  }
}
