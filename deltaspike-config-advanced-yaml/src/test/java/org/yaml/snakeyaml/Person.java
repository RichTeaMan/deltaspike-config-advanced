/**
 * Copyright (c) 2008, http://www.snakeyaml.org
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
package org.yaml.snakeyaml;

/**
 * The Person.
 */
public class Person {

  /** The given. */
  private String given;

  /** The family. */
  private String family;

  /** The address. */
  private Address address;

  /**
   * Get the given.
   *
   * @return given.
   */
  public String getGiven() {
    return given;
  }

  /**
   * Set the given.
   *
   * @param given given.
   */
  public void setGiven(final String given) {
    this.given = given;
  }

  /**
   * Get the family.
   *
   * @return family.
   */
  public String getFamily() {
    return family;
  }

  /**
   * Set the family.
   *
   * @param family family.
   */
  public void setFamily(final String family) {
    this.family = family;
  }

  /**
   * Get the address.
   *
   * @return address.
   */
  public Address getAddress() {
    return address;
  }

  /**
   * Set the address.
   *
   * @param address address.
   */
  public void setAddress(final Address address) {
    this.address = address;
  }
}
