package com.photobox.services.foundation.client.thrift;

import org.junit.Assert;
import org.junit.Test;

public class PoolConfigUTest {

  @Test
  public void createDefaultPoolConfig() {
    PoolConfig poolConfig = PoolConfig.defaultPoolConfig();
    PoolConfig expectedPoolConfig = PoolConfig.builder().build();
    assertPoolConfigEquals(expectedPoolConfig, poolConfig);
  }

  @Test
  public void createPoolConfig_timeout() {
    int testTimeout = 123;
    PoolConfig poolConfig = PoolConfig.builder().timeout(testTimeout).build();
    Assert.assertEquals(testTimeout, poolConfig.getTimeout());
  }

  @Test
  public void createPoolConfig_maxTotal() {
    int maxTotal = 123;
    PoolConfig poolConfig = PoolConfig.builder().maxTotal(maxTotal).build();
    Assert.assertEquals(maxTotal, poolConfig.getMaxTotal());
  }

  @Test
  public void createPoolConfig_maxIdle() {
    int maxIdle = 123;
    PoolConfig poolConfig = PoolConfig.builder().maxIdle(maxIdle).build();
    Assert.assertEquals(maxIdle, poolConfig.getMaxIdle());
  }

  @Test
  public void createPoolConfig_minIdle() {
    int minIdle = 123;
    PoolConfig poolConfig = PoolConfig.builder().minIdle(minIdle).build();
    Assert.assertEquals(minIdle, poolConfig.getMinIdle());
  }

  static void assertPoolConfigEquals(PoolConfig expected, PoolConfig actual) {
    Assert.assertEquals(expected.getTimeout(), actual.getTimeout());
    Assert.assertEquals(expected.getMaxIdle(), actual.getMaxIdle());
    Assert.assertEquals(expected.getMinIdle(), actual.getMinIdle());
    Assert.assertEquals(expected.getMaxTotal(), actual.getMaxTotal());
  }
}
