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

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;

import org.apache.deltaspike.core.util.StringUtils;
import org.apache.deltaspike.core.util.bean.BeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * YAML file config source provider.
 */
public abstract class YmlFileConfigSourceProvider implements Extension {

  /** Logger. */
  private static final Logger LOG = LoggerFactory.getLogger(YmlFileConfigSourceProvider.class);

  /** The file extension a properties file requires. */
  private static final String FILE_SUFFIX = ".yaml";

  /**
   * Get the configuration class.
   *
   * @return The configuration.
   */
  public abstract Class<?> getConfiguration();

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
   * Get the overriding file source if it exists.
   *
   * @return File.
   */
  public File getOverridingFileSource() {

    LOG.debug("Getting config sources");

    File overridingFile = null;

    final String propertyPath = System.getProperty(getConfigurationFolderKey());
    File propertyDir = null;
    if (StringUtils.isNotEmpty(propertyPath)) {
      propertyDir = new File(propertyPath);
    }

    if (propertyDir != null && propertyDir.exists() && propertyDir.isDirectory()) {

      LOG.debug("The property file override directory exixts, directory is: {}", propertyPath);

      final File propertyDirFile =
          new File((propertyPath.endsWith("/") || propertyPath.endsWith("\\") ? propertyPath
              : propertyPath + "/") + getPropertyFile());

      if (propertyDirFile.getName().equals(getPropertyFile())
          && propertyDirFile.getName().endsWith(FILE_SUFFIX)) {

        LOG.debug("Found overriding file: {}", getPropertyFile());

        overridingFile = propertyDirFile;
      }
    }

    return overridingFile;
  }

  /**
   * Hooks into the bean creation to add a YAML injected bean in at startup.
   *
   * @param afterBeanDiscovery After bean discovery event.
   * @param beanManager Bean manager.
   */
  public void afterBeanDiscovery(@Observes final AfterBeanDiscovery afterBeanDiscovery,
      final BeanManager beanManager) {

    LOG.debug(
        "Creating YAML injected bean for class: {}, configured from folder with key:{} and file: {}",
        getConfiguration(), getConfigurationFolderKey(), getPropertyFile());

    // instantiate class and inject dependencies
    final Bean<Object> bean = new BeanBuilder<Object>(beanManager).beanClass(getConfiguration())
        .scope(ApplicationScoped.class) // can be configurable
        .beanLifecycle(new YamlInstanceFactory<Object>(this)).create();

    LOG.trace("Adding bean: {}", bean.toString());

    afterBeanDiscovery.addBean(bean);

    LOG.debug("Bean susccessfully added for class: {}", getConfiguration());
  }
}
