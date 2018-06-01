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
 * The Address.
 */
public class Address {

  /** The lines. */
  private String lines;

  /** The city. */
  private String city;

  /** The state. */
  private String state;

  /** The postal. */
  private String postal;

  /**
   * Gets the lines.
   *
   * @return the lines
   */
  public String getLines() {
    return lines;
  }

  /**
   * Sets the lines.
   *
   * @param lines the new lines
   */
  public void setLines(final String lines) {
    this.lines = lines;
  }

  /**
   * Gets the city.
   *
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * Sets the city.
   *
   * @param city the new city
   */
  public void setCity(final String city) {
    this.city = city;
  }

  /**
   * Gets the state.
   *
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * Sets the state.
   *
   * @param state the new state
   */
  public void setState(final String state) {
    this.state = state;
  }

  /**
   * Gets the postal.
   *
   * @return the postal
   */
  public String getPostal() {
    return postal;
  }

  /**
   * Sets the postal.
   *
   * @param postal the new postal
   */
  public void setPostal(final String postal) {
    this.postal = postal;
  }
}
