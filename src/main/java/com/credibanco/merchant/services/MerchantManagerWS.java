/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.credibanco.merchant.services;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.credibanco.commonsutils.constants.BusinessServicesConstants;
import com.credibanco.commonsutils.constants.StringConstants;
import com.credibanco.commonsutils.models.ResponseCoreObject;
import com.credibanco.commonsutils.properties.CodeErrorEnum;
import com.credibanco.merchant.bussines.ValidateRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
@Path(BusinessServicesConstants.MERCHANT_MANAGER)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MerchantManagerWS {

    private static final Gson g = new Gson();
    private static final Logger LOGGER = Logger.getLogger(MerchantManagerWS.class.getName());

    /**
     *
     * Metodo que tiene todos los datos capturados de los formularios de
     * afiliación
     *
     * @author IMIX CONSULTING SAS <br>
     * Jonatan Velandia<br>
     * email: jonatan.velandia@imix.com.co>
     * @date 27/04/2017
     *
     * @version 1.0
     *
     * @param request {@link RequestDTOPsl} objeto con los datos de usuario
     *
     * @return {@link ResponseCoreObject} objeto con los datos de respuesta
     */
    @POST
    @Path(BusinessServicesConstants.GET_MERCHANT)
    @Consumes({MediaType.APPLICATION_XML, MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMerchant(final String request) {
        LOGGER.info(StringConstants.STARTINGMETHOD + BusinessServicesConstants.GET_MERCHANT);
        LOGGER.debug(StringConstants.SERVICES_PARAMETERS + request);
        ResponseCoreObject response = new ResponseCoreObject();

        try {
            Map<String, String> retMap = new Gson().fromJson(request, new TypeToken<HashMap<String, String>>() {
            }.getType());

            response = new ValidateRequest().getMerchant(retMap);
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(MerchantManagerWS.class.getName()).log(Level.SEVERE, null, e);
        }

        String json = g.toJson(response);

        LOGGER.info(StringConstants.ENDINGMETHOD + BusinessServicesConstants.MERCHANT_MANAGER);
        LOGGER.debug(StringConstants.MICRO_RESPONSE_MESSAGE + json);
        return Response.status(Integer.valueOf(CodeErrorEnum.STATUS200.getCode())).entity(json).build();
    }

}
