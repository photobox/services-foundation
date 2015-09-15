package com.photobox.services.foundation.client;

import org.junit.Assert;
import org.junit.Test;

public class ClientConfigurationUTest {

  @Test
  public void withValidAttributes() {
    ClientConfiguration conf = new ClientConfiguration("localhost", 123);
    Assert.assertEquals("localhost", conf.getHost());
    Assert.assertEquals(123, conf.getPort());
  }

  @Test(expected = NullPointerException.class)
  public void nullHost() {
    new ClientConfiguration(null, 123);
  }

  @Test(expected = IllegalArgumentException.class)
  public void emptyHost() {
    new ClientConfiguration("", 123);
  }

}
