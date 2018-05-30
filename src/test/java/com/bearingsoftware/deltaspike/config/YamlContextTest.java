package com.bearingsoftware.deltaspike.config;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.yaml.snakeyaml.Invoice;

@RunWith(CdiTestRunner.class)
public class YamlContextTest
{

    @Inject
    private Invoice invoice;

    @Test
    public void blah()
    {
        System.out.println(invoice.getInvoice());
    }
}
