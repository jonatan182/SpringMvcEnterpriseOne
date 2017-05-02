/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.credibanco.merchant.bussines;

import com.credibanco.commonsutils.constants.Constants;
import com.credibanco.commonsutils.constants.PropertiesConstants;
import com.credibanco.commonsutils.constants.WSOperationConstants;
import com.credibanco.commonsutils.exceptions.BusinessE;
import com.credibanco.commonsutils.generics.GenericResponse;
import com.credibanco.commonsutils.models.ResponseCoreObject;
import com.credibanco.commonsutils.properties.CodeErrorEnum;
import com.credibanco.commonsutils.properties.IErrorProperties;
import com.credibanco.commonsutils.validations.ValidationUtils;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang.NullArgumentException;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author jonatan.velandia
 */
public class ValidateRequest {

    private static final Logger LOGGER = Logger.getLogger(ValidateRequest.class.getName());

    private final ValidationUtils validationUtils;
    private final ApplicationContext context;

    public ValidateRequest() {
        this.context = new ClassPathXmlApplicationContext(PropertiesConstants.SPRINGMODULE);
        IErrorProperties errorP = (IErrorProperties) context.getBean(PropertiesConstants.ERRORS_BEAN);
        Properties errorProperties = errorP.getErrorProperties();
        this.validationUtils = new ValidationUtils(errorProperties);
    }

    public ResponseCoreObject getMerchant(final Map<String, String> request) throws BusinessE {
        try {
            validationUtils.validateParam(WSOperationConstants.NIT_LEGAL_COMMERCE, request.get(WSOperationConstants.NIT_LEGAL_COMMERCE));
            return new MerchantManagerBO(context).getMerchant(request);
        } catch (NullArgumentException n) {
            LOGGER.error(n.getMessage(), n);
            ResponseCoreObject resp = new ResponseCoreObject();
            Map<Object, Object> errorMap = new GenericResponse().genericErrors(CodeErrorEnum.ERRORREQUIREDFIELD.getCode(), Constants.ZERO, n.getMessage());
            resp.setError(errorMap);
            return resp;
        }
    }
}
