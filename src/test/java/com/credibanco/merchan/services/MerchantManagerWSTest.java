/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.credibanco.merchan.services;

import com.credibanco.commonsutils.constants.BusinessServicesConstants;
import java.util.ResourceBundle;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;
import org.springframework.stereotype.Component;
import static org.junit.Assert.assertNotNull;

/**
 *
 * @author jonatan.velandia
 */
@Component
@Path(BusinessServicesConstants.MERCHANT_MANAGER)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MerchantManagerWSTest extends JerseyTest {

    private ResourceBundle rb;

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        rb = ResourceBundle.getBundle("MerchantManagerWSTest");
        return new ResourceConfig().packages("com.credibanco.merchant.services");
    }

    @Test
    public void getMerchant() {
        String jsonRequest = rb.getObject(BusinessServicesConstants.GET_MERCHANT).toString();
        Entity<String> entity = Entity.entity(jsonRequest, MediaType.APPLICATION_JSON_TYPE);
        Response response = target(BusinessServicesConstants.MERCHANT_MANAGER).path(BusinessServicesConstants.GET_MERCHANT).request().post(entity);
        assertNotNull(response);
    }
}