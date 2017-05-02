/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.credibanco.merchant.bussines;

import com.credibanco.commonsutils.constants.Constants;
import com.credibanco.commonsutils.constants.PropertiesConstants;
import com.credibanco.commonsutils.constants.StringConstants;
import com.credibanco.commonsutils.constants.WSOperationConstants;
import com.credibanco.commonsutils.exceptions.BusinessE;
import com.credibanco.commonsutils.models.ResponseCoreObject;
import com.credibanco.commonsutils.properties.CodeErrorEnum;
import com.credibanco.commonsutils.properties.IErrorProperties;
import com.credibanco.commonsutils.validations.ValidationUtils;
import com.credibanco.merchant.constants.MerchantConstants;
import com.credibanco.merchant.constants.QueryKeyConstants;
import com.credibanco.merchant.interfaces.IAccesData;
import com.credibanco.merchant.services.MerchantManagerWS;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang.NullArgumentException;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author jonatan.velandia
 */
public class MerchantManagerBO {

    private static final Logger LOGGER = Logger.getLogger(MerchantManagerBO.class.getName());

    private final ValidationUtils validationUtils;
    private final Properties errorProperties;
    private final ApplicationContext context;

    Map<Object, Object> errorMap = new HashMap<>();
    ResponseCoreObject response = new ResponseCoreObject();

    public MerchantManagerBO(ApplicationContext context) {
        this.context = context;
        IErrorProperties errorP;
        errorP = (IErrorProperties) context.getBean(PropertiesConstants.ERRORS_BEAN);
        errorProperties = errorP.getErrorProperties();
        this.validationUtils = new ValidationUtils(errorProperties);

    }

    public ResponseCoreObject getMerchant(final Map<String, String> request) throws BusinessE {
        LOGGER.info(StringConstants.STARTINGMETHOD + MerchantManagerWS.class.getName());
        String nitComercioJuridico = request.get(WSOperationConstants.NIT_LEGAL_COMMERCE);
        List<Map<String, Object>> result;
        try {
            IAccesData info = (IAccesData) this.context.getBean(MerchantConstants.BEAN_DATA_ACCESS);
            Object[] args = {nitComercioJuridico};
            result = info.queryForList(QueryKeyConstants.GET_LEGAL_COMMERCE_Q1, args);
            response = validationUtils.validateQueryResult(result);
            Map<String, Object> dataResponseMap = new HashMap<>();
            if (response.getError().get(Constants.ERROR_CODE).equals(CodeErrorEnum.SUCCESSFULL.getCode())) {
                dataResponseMap.put(WSOperationConstants.GENERAL_INFORMATION_COMMERCE, result);
                String numeroSolicitud = result.get(new Integer(Constants.ZERO)).get(MerchantConstants.SLCTUD_ID).toString();
                Object[] args2 = {numeroSolicitud};
                result = info.queryForList(QueryKeyConstants.GET_LEGAL_COMMERCE_Q2, args2);
                dataResponseMap.put(WSOperationConstants.FRANCHISES_REQUEST, result);
                result = info.queryForList(QueryKeyConstants.GET_LEGAL_COMMERCE_Q3, args2);
                dataResponseMap.put(WSOperationConstants.TECHNOLOGIES_REQUEST, result);
                result = info.queryForList(QueryKeyConstants.GET_LEGAL_COMMERCE_Q4, args2);
                dataResponseMap.put(WSOperationConstants.PARTNERS, result);
                response.setData(dataResponseMap);
            }
            return response;
        } catch (NullArgumentException n) {
            throw new BusinessE(n);
        }
    }
}
