package org.yaml.snakeyaml;
/**
 * Copyright (c) 2008, http://www.snakeyaml.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.List;

public class Invoice
{
    private Integer invoice; // invoice
    private String date; // date
    private Person billTo;// bill-to
    private Person shipTo;// ship-to
    private List<Product> product;
    private Float tax;
    private Float total;
    private String comments;

    public Integer getInvoice()
    {
        return invoice;
    }

    public void setInvoice(Integer invoice)
    {
        this.invoice = invoice;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public Person getBillTo()
    {
        return billTo;
    }

    public void setBillTo(Person billTo)
    {
        this.billTo = billTo;
    }

    public Person getShipTo()
    {
        return shipTo;
    }

    public void setShipTo(Person shipTo)
    {
        this.shipTo = shipTo;
    }

    public List<Product> getProduct()
    {
        return product;
    }

    public void setProduct(List<Product> product)
    {
        this.product = product;
    }

    public Float getTax()
    {
        return tax;
    }

    public void setTax(Float tax)
    {
        this.tax = tax;
    }

    public Float getTotal()
    {
        return total;
    }

    public void setTotal(Float total)
    {
        this.total = total;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

}
