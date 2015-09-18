package com.photobox.services.foundation.client.thrift;

/**
 * Contains the configuration of a Thrift client pool.
 */
public class PoolConfig {

  private final int timeout;
  private final int maxTotal;
  private final int maxIdle;
  private final int minIdle;

  private PoolConfig(PoolConfigBuilder builder) {
    this.timeout = builder.timeout;
    this.maxTotal = builder.maxTotal;
    this.maxIdle = builder.maxIdle;
    this.minIdle = builder.minIdle;
  }

  /**
   * Get connection socket timeout (default 0, means not timeout).
   *
   * @return The current setting of {@code timeout} for this
   *         configuration instance
   */
  public int getTimeout() {
    return timeout;
  }

  /**
   * Get the value for the {@code maxTotal} configuration attribute
   * for pools created with this configuration instance.
   *
   * @return  The current setting of {@code maxTotal} for this
   *          configuration instance
   */
  public int getMaxTotal() {
    return maxTotal;
  }

  /**
   * Get the value for the {@code maxIdle} configuration attribute
   * for pools created with this configuration instance.
   *
   * @return  The current setting of {@code maxIdle} for this
   *          configuration instance
   */
  public int getMaxIdle() {
    return maxIdle;
  }

  /**
   * Get the value for the {@code minIdle} configuration attribute
   * for pools created with this configuration instance.
   *
   * @return  The current setting of {@code minIdle} for this
   *          configuration instance
   */
  public int getMinIdle() {
    return minIdle;
  }

  /**
   * @return a builder for PoolConfig
   */
  public static PoolConfigBuilder builder() {
    return new PoolConfigBuilder();
  }

  /**
   * @return a pool configuration with default parameters
   */
  public static PoolConfig defaultPoolConfig() {
    return builder().build();
  }

  /**
   * A builder for PoolConfig.
   */
  public static class PoolConfigBuilder {

    private int timeout = 1000;
    private int maxTotal = 8;
    private int maxIdle = 8;
    private int minIdle = 0;

    private PoolConfigBuilder() {}

    /**
     * Set default connection socket timeout (default 0, means not timeout).
     *
     * @param timeout timeout timeout millis
     * @return the current builder
     */
    public PoolConfigBuilder timeout(int timeout) {
      this.timeout = timeout;
      return this;
    }

    /**
     * Set the value for the {@code maxTotal} configuration attribute for
     * pools created with this configuration instance.
     *
     * @param maxTotal The new setting of {@code maxTotal}
     *        for this configuration instance
     * @return the current builder
     */
    public PoolConfigBuilder maxTotal(int maxTotal) {
      this.maxTotal = maxTotal;
      return this;
    }

    /**
     * Set the value for the {@code maxIdle} configuration attribute for
     * pools created with this configuration instance.
     *
     * @param maxIdle The new setting of {@code maxIdle}
     *        for this configuration instance
     * @return the current builder
     */
    public PoolConfigBuilder maxIdle(int maxIdle) {
      this.maxIdle = maxIdle;
      return this;
    }

    /**
     * Set the value for the {@code minIdle} configuration attribute for
     * pools created with this configuration instance.
     *
     * @param minIdle The new setting of {@code minIdle}
     *        for this configuration instance
     * @return the current builder
     */
    public PoolConfigBuilder minIdle(int minIdle) {
      this.minIdle = minIdle;
      return this;
    }

    /**
     * Create a {@link PoolConfig} based on the current builder configuration.
     * @return
     */
    public PoolConfig build() {
      return new PoolConfig(this);
    }
  }

}
