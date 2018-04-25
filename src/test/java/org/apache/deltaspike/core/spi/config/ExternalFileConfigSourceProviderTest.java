/**
 * Copyright Edinburgh Napier University 2018.
 */
package org.apache.deltaspike.core.spi.config;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

/**
 * Test for {@link ExternalFileConfigSourceProvider}.
 */
public class ExternalFileConfigSourceProviderTest {

  /** Class under test. */
  private TestExternalFileConfigSourceProvider externalFileConfigSourceProvider;

  /** Command line flag for properties folder. */
  private static final String COMMAND_LINE_FLAG = "test.properties.folder";

  /**
   * Setup.
   */
  @Before
  public void setup() {
    System.setProperty(COMMAND_LINE_FLAG, "src/test/resources/properties.folder");
    externalFileConfigSourceProvider = new TestExternalFileConfigSourceProvider();
  }

  /**
   * Check external file loader.
   */
  @Test
  public void checkExternalFileConfigSourceProvider() {

    // Test
    final List<ConfigSource> configSourceList = externalFileConfigSourceProvider.getConfigSources();

    // Asserts
    final Map<String, String> properties = configSourceList.get(0).getProperties();

    Assert.assertEquals(2, properties.size());
    Assert.assertTrue(properties.containsKey("$test{upload.directory}"));
    Assert.assertEquals("directory\\test", properties.get("$test{upload.directory}"));
  }
}
