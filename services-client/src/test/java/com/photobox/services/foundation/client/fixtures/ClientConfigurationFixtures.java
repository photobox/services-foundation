package com.photobox.services.foundation.client.fixtures;

import com.photobox.services.foundation.client.ClientConfiguration;
import com.photobox.services.foundation.client.thrift.ClientType;
import com.photobox.services.foundation.client.thrift.ThriftClientConfiguration;

public class ClientConfigurationFixtures {

  public static final String HOST = "localhost";
  public static final int PORT = 10084;

  public static final ThriftClientConfiguration TEST_POOLED_CONFIGURATION =
      new ThriftClientConfiguration(ClientType.POOLED, HOST, PORT);

  public static final ThriftClientConfiguration TEST_SIMPLE_CONFIGURATION =
      new ThriftClientConfiguration(ClientType.SIMPLE, HOST, PORT);

  public static ClientConfiguration clientConfiguration() {
    return new ClientConfiguration(HOST, PORT);
  }
}
