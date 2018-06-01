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

import java.util.List;

/**
 * Invoice.
 */
public class Invoice {

  /** The invoice. */
  private Integer invoice;

  /** The date. */
  private String date;

  /** The bill to. */
  private Person billTo;

  /** The ship to. */
  private Person shipTo;

  /** The product. */
  private List<Product> product;

  /** The tax. */
  private Float tax;

  /** The total. */
  private Float total;

  /** The comments. */
  private String comments;

  /**
   * Gets the invoice.
   *
   * @return The invoice.
   */
  public Integer getInvoice() {
    return invoice;
  }

  /**
   * Sets the invoice.
   *
   * @param invoice The new invoice.
   */
  public void setInvoice(final Integer invoice) {
    this.invoice = invoice;
  }

  /**
   * Gets the date.
   *
   * @return The date.
   */
  public String getDate() {
    return date;
  }

  /**
   * Sets the date.
   *
   * @param date The new date.
   */
  public void setDate(final String date) {
    this.date = date;
  }

  /**
   * Gets the bill to.
   *
   * @return The bill to.
   */
  public Person getBillTo() {
    return billTo;
  }

  /**
   * Sets the bill to.
   *
   * @param billTo The new bill to.
   */
  public void setBillTo(final Person billTo) {
    this.billTo = billTo;
  }

  /**
   * Gets the ship to.
   *
   * @return The ship to.
   */
  public Person getShipTo() {
    return shipTo;
  }

  /**
   * Sets the ship to.
   *
   * @param shipTo The new ship to.
   */
  public void setShipTo(final Person shipTo) {
    this.shipTo = shipTo;
  }

  /**
   * Gets the product.
   *
   * @return The product.
   */
  public List<Product> getProduct() {
    return product;
  }

  /**
   * Sets the product.
   *
   * @param product The new product.
   */
  public void setProduct(final List<Product> product) {
    this.product = product;
  }

  /**
   * Gets the tax.
   *
   * @return The tax.
   */
  public Float getTax() {
    return tax;
  }

  /**
   * Sets the tax.
   *
   * @param tax The new tax.
   */
  public void setTax(final Float tax) {
    this.tax = tax;
  }

  /**
   * Gets the total.
   *
   * @return The total.
   */
  public Float getTotal() {
    return total;
  }

  /**
   * Sets the total.
   *
   * @param total The new total.
   */
  public void setTotal(final Float total) {
    this.total = total;
  }

  /**
   * Gets the comments.
   *
   * @return The comments.
   */
  public String getComments() {
    return comments;
  }

  /**
   * Sets the comments.
   *
   * @param comments The new comments.
   */
  public void setComments(final String comments) {
    this.comments = comments;
  }

}
