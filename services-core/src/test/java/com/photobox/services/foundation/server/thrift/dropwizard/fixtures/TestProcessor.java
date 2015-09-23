package com.photobox.services.foundation.server.thrift.dropwizard.fixtures;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.photobox.services.foundation.server.thrift.dropwizard.DiscoverableProcessor;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;

/**
 * A processor used to test discoverability and configuration in a Dropwizard application.
 */
@JsonTypeName("test-service")
public class TestProcessor implements DiscoverableProcessor {
  @Override
  public boolean process(TProtocol p1, TProtocol p2) throws TException {
    // a stubbed processor method
    return true;
  }
}
