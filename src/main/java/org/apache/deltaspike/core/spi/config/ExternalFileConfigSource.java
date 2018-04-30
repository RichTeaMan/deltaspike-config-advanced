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
package org.apache.deltaspike.core.spi.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.deltaspike.core.util.PropertyFileUtils;

/**
 * Read properties from an external file.
 */
public class ExternalFileConfigSource implements ConfigSource
{

    /** File name. */
    private String fileName;

    /** Prefix. */
    private String prefix;

    /** Key for property. */
    private String propertyKey = "$%s{%s}";

    /** Ordinal specifies configuration priority. High value is highest priority. */
    private int ordinal = 150;

    /** Properties. */
    private final Properties properties;

    /**
     * Create external file configuration.
     * 
     * @param prefix
     *            Prefix.
     * @param propertyFileUrl
     *            Property file URL.
     * @throws IOException
     *             IO Exception.
     */
    public ExternalFileConfigSource(final String prefix, final URL propertyFileUrl)
            throws IOException
    {
        this(prefix, propertyFileUrl, null);
    }

    /**
     * Create external file configuration.
     * 
     * @param prefix
     *            Prefix.
     * @param propertyFileUrl
     *            Property file URL.
     * @param file
     *            File.
     * @throws IOException
     *             IO Exception.
     */
    public ExternalFileConfigSource(final String prefix, final URL propertyFileUrl, final File file)
            throws IOException
    {

        final Properties properties = PropertyFileUtils.loadProperties(propertyFileUrl);

        final Properties overloadedProperties = new Properties();

        if (null != file)
        {
            try (final FileInputStream propertiesStream = new FileInputStream(file.getAbsolutePath()))
            {
                overloadedProperties.load(propertiesStream);
            }
        }

        final Properties prefixedProperties = new Properties();

        for (final Object keyObject : properties.keySet())
        {
            final String key = (String) keyObject;
            final Object value = properties.get(keyObject);

            prefixedProperties.put(String.format(propertyKey, prefix, key), value);
        }

        for (final Object keyObject : overloadedProperties.keySet())
        {
            final String key = (String) keyObject;
            final Object value = overloadedProperties.get(keyObject);

            prefixedProperties.put(String.format(propertyKey, prefix, key), value);
        }

        this.properties = prefixedProperties;
        fileName = propertyFileUrl.toExternalForm();
        this.prefix = prefix;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public String getPropertyValue(final String key)
    {
        return properties.getProperty(key);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Map<String, String> getProperties()
    {
        final Map<String, String> result = new HashMap<>();

        for (final String propertyName : properties.stringPropertyNames())
        {
            result.put(propertyName, properties.getProperty(propertyName));
        }

        return result;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public String getConfigName()
    {
        return fileName;
    }

    /**
     * Get the prefix.
     * 
     * @return Prefix.
     */
    public String getPrefix()
    {
        return prefix;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int getOrdinal()
    {
        return ordinal;
    }

    /**
     * Set the ordinal. Ordinal specifies the config priority, high value is the highest priority. Defaults to 150.
     * 
     * @param ordinal
     *            Ordinal.
     */
    public void setOrdinal(final int ordinal)
    {
        this.ordinal = ordinal;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean isScannable()
    {
        return true;
    }
}
