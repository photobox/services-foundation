package com.photobox.services.foundation.server.thrift.dropwizard;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.photobox.services.foundation.server.thrift.MultiThreadedThriftThriftServer;

/**
 * Factory that creates new instances of ThriftServer based on the injected configuration.
 */
@JsonTypeName("thread-pool")
public final class MultiThreadedThriftServerFactory extends AbstractManagedThriftServerFactory {
  @Override
  public ManagedThriftServer build() {
    return new ManagedThriftServer(
        new MultiThreadedThriftThriftServer(getPort(), getProcessor()));
  }
}
