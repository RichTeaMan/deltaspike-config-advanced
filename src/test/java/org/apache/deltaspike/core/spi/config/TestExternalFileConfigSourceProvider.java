/**
 * Copyright Edinburgh Napier University 2018.
 */
package org.apache.deltaspike.core.spi.config;

/**
 * Test class extending {@link ExternalFileConfigSourceProvider}.
 */
public class TestExternalFileConfigSourceProvider extends ExternalFileConfigSourceProvider {

  /** Prefix. */
  private static final String PREFIX = "test";

  /** Property file name. */
  private static final String PROPERTY_FILE = "test.properties";

  /**
   * {@inheritDoc}.
   */
  @Override
  public String getPrefix() {
    return PREFIX;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public String getConfigurationFolderKey() {
    return "test.properties.folder";
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public String getPropertryFile() {
    return PROPERTY_FILE;
  }

}
