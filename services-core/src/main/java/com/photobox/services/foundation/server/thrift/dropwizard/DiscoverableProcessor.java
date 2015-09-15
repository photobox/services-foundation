package com.photobox.services.foundation.server.thrift.dropwizard;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import org.apache.thrift.TProcessor;

import io.dropwizard.jackson.Discoverable;

/**
 * Defines Thrift {@link TProcessor} that can be configured in a Dropwizard application.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "name")
public interface DiscoverableProcessor extends Discoverable, TProcessor {
  // it's used just to combine Discoverable and TProcessor - doesn't provide any additional method
}
