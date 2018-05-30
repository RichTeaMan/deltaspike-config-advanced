/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
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
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;

import org.apache.commons.io.IOUtils;
import org.apache.deltaspike.core.util.StringUtils;
import org.apache.deltaspike.core.util.bean.BeanBuilder;
import org.apache.deltaspike.core.util.metadata.builder.ContextualLifecycle;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.bearingsoftware.deltaspike.extension.config.yml.YmlMerger;

/**
 * YAML file config source provider.
 */
public abstract class YmlFileConfigSourceProvider implements Extension
{

    /** Logger. */
    private static final Logger LOG = Logger.getLogger(YmlFileConfigSourceProvider.class.getName());

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

    private File getOverridingFileSource()
    {
        LOG.fine("Getting config sources");

        File overridingFile = null;

        final String propertyPath = System.getProperty(getConfigurationFolderKey());
        File propertyDir = null;
        if (StringUtils.isNotEmpty(propertyPath))
        {
            propertyDir = new File(propertyPath);
        }

        if (propertyDir != null && propertyDir.exists() && propertyDir.isDirectory())
        {
            final File propertyDirFile = new File(
                    (propertyPath.endsWith("/") || propertyPath.endsWith("\\") ? propertyPath
                            : propertyPath + "/") + getPropertyFile());

            if (propertyDirFile.getName().equals(getPropertyFile())
                    && propertyDirFile.getName().endsWith(FILE_SUFFIX))
            {
                overridingFile = propertyDirFile;
            }
        }

        return overridingFile;
    }

    private class YamlInstanceFactory<FT> implements ContextualLifecycle<FT>
    {
        /**
         * {@inheritDoc}.
         */
        @Override
        public FT create(final Bean<FT> bean, final CreationalContext<FT> creationalContext)
        {
            FT factoryType = null;
            try
            {
                File coreFile = new File(getClass().getClassLoader().getResource(getPropertyFile()).getPath());
                String yaml = IOUtils.toString(new FileInputStream(
                        coreFile));
                File overridingFile = getOverridingFileSource();
                if (null != overridingFile)
                {
                    yaml = new YmlMerger()
                            .mergeToString(
                                    new String[] { coreFile.getAbsolutePath(), overridingFile.getAbsolutePath() });
                }

                factoryType = new Yaml(new Constructor(getConfiguration())).load(yaml);
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return factoryType;
        }

        /**
         * {@inheritDoc}.
         */
        @Override
        public void destroy(final Bean<FT> bean, final FT instance, final CreationalContext<FT> creationalContext)
        {
            // here you destroy the created instance if needed, can be calling close() if it is a Closeable for instance
            // or nothing if not needed
        }
    }

    public void afterBeanDiscovery(@Observes AfterBeanDiscovery after, BeanManager beanManager)
    {

        // instantiate class and inject dependencies
        final Bean<Object> bean = new BeanBuilder<Object>(beanManager)
                .beanClass(getConfiguration())
                .scope(ApplicationScoped.class) // can be configurable
                .beanLifecycle(new YamlInstanceFactory<Object>())
                .create();
        after.addBean(bean);
    }
}
