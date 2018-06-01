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

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.yaml.snakeyaml.Invoice;

/**
 * Tests for {@link YmlFileConfigSourceProvider}.
 */
@RunWith(CdiTestRunner.class)
public class YamlContextTest {

  /** Test YAML populated object. */
  @Inject
  private Invoice invoice;

  /**
   * Check whether the injected object has been populated appropriately. Not this doesn't test the
   * overriding behaviour as difficult to get working in a unit test.
   */
  @Test
  public void checkPopulation() {

    Assert.assertEquals("The invoice number has not been set correctly", Integer.valueOf(34843),
        invoice.getInvoice());
  }
}
