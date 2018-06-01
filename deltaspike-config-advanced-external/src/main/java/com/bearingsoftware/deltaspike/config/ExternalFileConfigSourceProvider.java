/**
 * Copyright 2018 Craig Hattersley
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.bearingsoftware.deltaspike.config;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.deltaspike.core.spi.config.ConfigSource;
import org.apache.deltaspike.core.spi.config.ConfigSourceProvider;
import org.apache.deltaspike.core.util.PropertyFileUtils;
import org.apache.deltaspike.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * External file config source provider.
 */
public abstract class ExternalFileConfigSourceProvider implements ConfigSourceProvider {

  /** Standard logger. */
  private static final Logger LOG = LoggerFactory.getLogger(ExternalFileConfigSourceProvider.class);

  /** The file extension a properties file requires. */
  private static final String FILE_SUFFIX = ".properties";

  /**
   * Gets a prefix used for both the property and property file names.
   *
   * A property file will only be read if it's name begins with this prefix. All properties in that
   * file will have the same prefix added to the front of their key with a dot.
   *
   * Classes implementing this method SHOULD NOT end the prefix with a '.', this wil lbe added
   * later.
   *
   * @return The property prefix.
   */
  public abstract String getPrefix();

  /**
   * Get the configuration folder key.
   *
   * @return Configuration folder key.
   */
  public abstract String getConfigurationFolderKey();

  /**
   * Get the name of the property file.
   *
   * @return Property file name.
   */
  public abstract String getPropertyFile();

  /**
   * {@inheritDoc}.
   */
  @Override
  public List<ConfigSource> getConfigSources() {

    LOG.debug("Getting config sources");

    if (getPrefix() == null) {
      throw new IllegalStateException("Prefix must be set");
    }

    final List<ConfigSource> configSourceList = new ArrayList<>();

    try {
      final Enumeration<URL> propertyFileUrls =
          PropertyFileUtils.resolvePropertyFiles(getPropertyFile());

      final String propertyPath = System.getProperty(getConfigurationFolderKey());

      File propertyDir = null;
      if (StringUtils.isNotEmpty(propertyPath)) {
        propertyDir = new File(propertyPath);
      }

      final String prefix = getPrefix();

      while (propertyFileUrls.hasMoreElements()) {
        final URL propertyFileUrl = propertyFileUrls.nextElement();

        LOG.debug(String.format("Custom config found: name: '%s', URL: '%s', prefix: '%s'",
            getPropertyFile(), propertyFileUrl, getPrefix()));

        ExternalFileConfigSource configSource = null;

        if (propertyDir != null && propertyDir.exists() && propertyDir.isDirectory()) {
          final File propertyDirFile =
              new File((propertyPath.endsWith("/") || propertyPath.endsWith("\\") ? propertyPath
                  : propertyPath + "/") + getPropertyFile());

          if (propertyDirFile.getName().equals(getPropertyFile())
              && propertyDirFile.getName().endsWith(FILE_SUFFIX)) {
            LOG.debug("Adding file config source from directory");
            configSource = new ExternalFileConfigSource(prefix, propertyFileUrl, propertyDirFile);
          }
        } else {
          LOG.debug("Adding file config source without directory");
          configSource = new ExternalFileConfigSource(prefix, propertyFileUrl);
        }

        if (configSource != null) {
          configSourceList.add(configSource);
        }
      }
    } catch (

    IOException ioe) {
      LOG.warn(String.format("Could not read from properties file", getPropertyFile()), ioe);
    }

    return configSourceList;
  }

}
