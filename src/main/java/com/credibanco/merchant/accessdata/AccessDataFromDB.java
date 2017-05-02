package com.credibanco.merchant.accessdata;

import com.credibanco.commonsutils.constants.StringConstants;
import com.credibanco.commonsutils.queryloader.XmlProperty;
import com.credibanco.merchant.constants.MerchantConstants;
import com.credibanco.merchant.interfaces.IAccesData;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author jonatan.velandia
 */
public class AccessDataFromDB implements IAccesData {

    private static final Logger LOGGER = Logger.getLogger(AccessDataFromDB.class.getName());

    private static final XmlProperty XML = new XmlProperty(MerchantConstants.QUERIES_OF_MERCHAN);

    private final DataSource dataSourceAfiliaciones;

    public AccessDataFromDB(DataSource dataSourceAfiliaciones) {
        this.dataSourceAfiliaciones = dataSourceAfiliaciones;
    }

    @Override
    public List<Map<String, Object>> queryForList(String nameQuery, Object[] args) {
        String query = XML.getQueryFromMap(nameQuery);
        LOGGER.debug(StringConstants.SERVICES_PARAMETERS + Arrays.toString(args));
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceAfiliaciones);
        return jdbcTemplate.queryForList(query, args);
    }

}
