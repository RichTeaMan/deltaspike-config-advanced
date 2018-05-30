/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.bearingsoftware.deltaspike.config;

import java.util.List;
import java.util.Map;

import org.apache.deltaspike.core.spi.config.ConfigSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for {@link ExternalFileConfigSourceProvider}.
 */
public class ExternalFileConfigSourceTest
{

    /** Class under test. */
    private TestExternalFileConfigSourceProvider externalFileConfigSourceProvider;

    /** Command line flag for properties folder. */
    private static final String COMMAND_LINE_FLAG = "test.properties.folder";

    /**
     * Setup.
     */
    @Before
    public void setup()
    {
        System.setProperty(COMMAND_LINE_FLAG, "src/test/resources/properties.folder");
        externalFileConfigSourceProvider = new TestExternalFileConfigSourceProvider();
    }

    /**
     * Check external file loader.
     */
    @Test
    public void checkExternalFileConfigSourceProvider()
    {
        // Test
        final List<ConfigSource> configSourceList = externalFileConfigSourceProvider.getConfigSources();

        // Asserts
        final Map<String, String> properties = configSourceList.get(0).getProperties();

        Assert.assertEquals(2, properties.size());
        Assert.assertTrue(properties.containsKey("$test{upload.directory}"));
        Assert.assertEquals("directory\\test", properties.get("$test{upload.directory}"));
    }
}
