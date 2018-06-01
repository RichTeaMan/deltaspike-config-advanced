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
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;

import org.apache.commons.io.IOUtils;
import org.apache.deltaspike.core.util.metadata.builder.ContextualLifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.bearingsoftware.deltaspike.extension.config.yml.YmlMerger;

/**
 * YAML creation factory.
 *
 * @param <FT> File type.
 */
class YamlInstanceFactory<FT> implements ContextualLifecycle<FT> {

  /** Standard logger. */
  private static final Logger LOG = LoggerFactory.getLogger(YamlInstanceFactory.class);

  /** The yml file config source provider. */
  private final YmlFileConfigSourceProvider ymlFileConfigSourceProvider;

  /**
   * Instantiates a new yaml instance factory.
   *
   * @param ymlFileConfigSourceProvider the yml file config source provider
   */
  public YamlInstanceFactory(final YmlFileConfigSourceProvider ymlFileConfigSourceProvider) {
    this.ymlFileConfigSourceProvider = ymlFileConfigSourceProvider;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public FT create(final Bean<FT> bean, final CreationalContext<FT> creationalContext) {

    LOG.debug("Creating bean");

    FT factoryType = null;
    try {
      final File coreFile = new File(getClass().getClassLoader()
          .getResource(ymlFileConfigSourceProvider.getPropertyFile()).getPath());
      String yaml = IOUtils.toString(new FileInputStream(coreFile), Charset.defaultCharset());
      final File overridingFile = ymlFileConfigSourceProvider.getOverridingFileSource();
      if (null != overridingFile) {
        yaml = new YmlMerger().mergeToString(
            new String[] {coreFile.getAbsolutePath(), overridingFile.getAbsolutePath()});
      }

      factoryType =
          new Yaml(new Constructor(ymlFileConfigSourceProvider.getConfiguration())).load(yaml);
    } catch (final IOException e) {
      LOG.error("There was a problem when creating the bean", e);
      throw new IllegalStateException("Unable to create YAML bean", e);
    }

    return factoryType;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public void destroy(final Bean<FT> bean, final FT instance,
      final CreationalContext<FT> creationalContext) {}
}
