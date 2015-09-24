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

  /**
   * Build a Thrift server.
   *
   * @return a {@link ManagedThriftServer} listening on the configured port.
   */
  @Override
  public abstract ManagedThriftServer build();

  /**
   * Return that will be exposed by the servers created by this factory.
   *
   * @return the server port
   */
  @Override
  @JsonProperty
  public final int getPort() {
    return port;
  }

  // for test purpose only
  void setPort(int port) {
    this.port = port;
  }

  /**
   * Return the service processor that will execute the service logic.
   *
   * @return the service processor
   */
  @JsonProperty("service")
  final DiscoverableProcessor getProcessor() {
    return processor;
  }

  // for test purpose only
  void setProcessor(DiscoverableProcessor processor) {
    this.processor = processor;
  }
}
