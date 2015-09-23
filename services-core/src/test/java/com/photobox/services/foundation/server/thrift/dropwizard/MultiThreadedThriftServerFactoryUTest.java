package com.photobox.services.foundation.server.thrift.dropwizard;

import com.photobox.services.foundation.server.thrift.dropwizard.fixtures.TestProcessor;

import org.junit.After;
import org.junit.Before;

public class MultiThreadedThriftServerFactoryUTest {

  private static final int PORT = 9984;

  private MultiThreadedThriftServerFactory factory;
  private ManagedThriftServer server;

  @Before
  public void init() {
    factory = new MultiThreadedThriftServerFactory();
    factory.setPort(PORT);
    factory.setProcessor(new TestProcessor());
  }

  @After
  public void tearDown() {
    // stop the server
//    server.stop();
  }

  // TODO test multi-threaded server connection - this require a Thrift client available on the Foundation project
}
