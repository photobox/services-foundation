package com.photobox.services.foundation.server.thrift.dropwizard;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * A generic factory providing ThriftServer objects listening on the configured port.
 */
abstract class AbstractManagedThriftServerFactory implements ManagedThriftServerFactory {
  @Min(1)
  @Max(65535)
  // expected to be always set through injection - no default value
  private int port;

  @Valid
  @NotNull
  // expected to be set through injection - no default value
  private DiscoverableProcessor processor;

  @Override
  @JsonProperty
  public int getPort() {
    return port;
  }

  @JsonProperty("service")
  public DiscoverableProcessor getProcessor() {
    return processor;
  }

  /**
   * @return a new ThriftServer listening on the configured port.
   */
  public abstract ManagedThriftServer build();
}
