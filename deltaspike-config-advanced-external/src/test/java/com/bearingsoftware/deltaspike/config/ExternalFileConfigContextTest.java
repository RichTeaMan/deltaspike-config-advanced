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

import javax.inject.Inject;

import org.apache.deltaspike.core.api.config.ConfigProperty;
import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for {@link ExternalFileConfigSource}.
 */
@RunWith(CdiTestRunner.class)
public class ExternalFileConfigContextTest {

  /** The upload directory. */
  @Inject
  @ConfigProperty(name = "$test{upload.directory}")
  private String uploadDirectory;

  /** The upload file. */
  @Inject
  @ConfigProperty(name = "test${upload.file}")
  private String uploadFile;

  /**
   * Check values injected.
   */
  @Test
  public void checkValuesInjected() {
    Assert.assertEquals("directory\\original\\test", uploadDirectory);
    Assert.assertNull(uploadFile);
  }
}
