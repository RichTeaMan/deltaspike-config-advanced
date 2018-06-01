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
 * The Product.
 */
public class Product {

  /** The sku. */
  private String sku;

  /** The quantity. */
  private Integer quantity;

  /** The description. */
  private String description;

  /** The price. */
  private Float price;

  /**
   * Get the sku.
   *
   * @return sku.
   */
  public String getSku() {
    return sku;
  }

  /**
   * Set the sku.
   *
   * @param sku sku.
   */
  public void setSku(final String sku) {
    this.sku = sku;
  }

  /**
   * Get the quantity.
   *
   * @return quantity.
   */
  public Integer getQuantity() {
    return quantity;
  }

  /**
   * Set the quantity.
   *
   * @param quantity quantity.
   */
  public void setQuantity(final Integer quantity) {
    this.quantity = quantity;
  }

  /**
   * Get the description.
   *
   * @return description.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Set the description.
   *
   * @param description description.
   */
  public void setDescription(final String description) {
    this.description = description;
  }

  /**
   * Get the price.
   *
   * @return price.
   */
  public Float getPrice() {
    return price;
  }

  /**
   * Set the price.
   *
   * @param price price.
   */
  public void setPrice(final Float price) {
    this.price = price;
  }
}
