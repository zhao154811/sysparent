package com.wenyu.oauth.security.provider;

import com.wenyu.oauth.model.OAuthClientDetails;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class MongoClientDetailService implements ClientDetailsService,
        ClientRegistrationService {

    private static final Log logger = LogFactory.getLog(MongoClientDetailService.class);
    @Autowired
    private com.wenyu.oauth.dao.OauthClientDetailMapper OauthClientDetailMapper;

    private ObjectMapper mapper = new ObjectMapper();

    private static final String CLIENT_FIELDS_FOR_UPDATE = "resource_ids, scope, "
            + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
            + "refresh_token_validity, additional_information";

    private static final String CLIENT_FIELDS = "client_secret, " + CLIENT_FIELDS_FOR_UPDATE;

    private static final String BASE_FIND_STATEMENT = "select client_id, " + CLIENT_FIELDS
            + " from oauth_client_details";

    private static final String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";

    private static final String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";

    private static final String DEFAULT_INSERT_STATEMENT = "insert into oauth_client_details (" + CLIENT_FIELDS
            + ", client_id) values (?,?,?,?,?,?,?,?,?,?)";

    private static final String DEFAULT_UPDATE_STATEMENT = "update oauth_client_details " + "set "
            + CLIENT_FIELDS_FOR_UPDATE.replaceAll(", ", "=?, ") + "=? where client_id = ?";

    private static final String DEFAULT_UPDATE_SECRET_STATEMENT = "update oauth_client_details "
            + "set client_secret = ? where client_id = ?";

    private static final String DEFAULT_DELETE_STATEMENT = "delete from oauth_client_details where client_id = ?";

//	private RowMapper<ClientDetails> rowMapper = new ClientDetailsRowMapper();

    private String deleteClientDetailsSql = DEFAULT_DELETE_STATEMENT;

    private String findClientDetailsSql = DEFAULT_FIND_STATEMENT;

    private String updateClientDetailsSql = DEFAULT_UPDATE_STATEMENT;

    private String updateClientSecretSql = DEFAULT_UPDATE_SECRET_STATEMENT;

    private String insertClientDetailsSql = DEFAULT_INSERT_STATEMENT;

    private String selectClientDetailsSql = DEFAULT_SELECT_STATEMENT;

    private PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();

//	private final JdbcTemplate jdbcTemplate;
//
//	private JdbcListFactory listFactory;
//
//	public JdbcClientDetailsService(DataSource dataSource) {
//		Assert.notNull(dataSource, "DataSource required");
//		this.jdbcTemplate = new JdbcTemplate(dataSource);
//		this.listFactory = new DefaultJdbcListFactory(new NamedParameterJdbcTemplate(jdbcTemplate));
//	}

    /**
     * @param passwordEncoder the password encoder to set
     */
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        ClientDetails clientDetails = null;
        OAuthClientDetails mocd = OauthClientDetailMapper.getByClientId(clientId);
        if (mocd != null) {
            clientDetails = this.mongo2CD(mocd);
        } else {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }
        return clientDetails;
    }

    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
        OAuthClientDetails mocd = new OAuthClientDetails();
        Object[] objs = getFields(clientDetails);
        mocd.setClientSecret(com.wenyu.oauth.util.StringUtils.hash((String) objs[0], "md5"));
        mocd.setResourceIds((String) objs[1]);
        mocd.setScope((String) objs[2]);
        mocd.setAuthorizedGrantTypes((String) objs[3]);
        mocd.setWebServerRedirectUri((String) objs[4]);
        mocd.setAuthorities((String) objs[5]);
        mocd.setAccessTokenValidity((Integer) objs[6]);
        mocd.setRefreshTokenValidity((Integer) objs[7]);
        mocd.setAdditionalInformation((String) objs[8]);
        mocd.setClientId((String) objs[9]);
        Integer insertflag = OauthClientDetailMapper.ifexist(mocd.getClientId());
        if (insertflag != 0) {
            OauthClientDetailMapper.addClientDetail(mocd);
        } else {
            throw new ClientAlreadyExistsException("Client already exists: " + clientDetails.getClientId(), new RuntimeException());
        }
    }

    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
//		resource_ids, scope, "
//				+ "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
//				+ "refresh_token_validity, additional_information
        OAuthClientDetails oAuthClientDetails = new OAuthClientDetails();
        Object[] objs = getFieldsForUpdate(clientDetails);
        oAuthClientDetails.setResourceIds((String) objs[0]);
        oAuthClientDetails.setScope((String) objs[1]);
        oAuthClientDetails.setAuthorizedGrantTypes((String) objs[2]);
        oAuthClientDetails.setWebServerRedirectUri((String) objs[3]);
        oAuthClientDetails.setAuthorities((String) objs[4]);
        oAuthClientDetails.setAccessTokenValidity((Integer) objs[5]);
        oAuthClientDetails.setRefreshTokenValidity((Integer) objs[6]);
        oAuthClientDetails.setAdditionalInformation((String) objs[7]);
        int count = OauthClientDetailMapper.updateClientDetailByClientId(oAuthClientDetails);
        if (count != 1) {
            throw new NoSuchClientException("No client found with id = " + clientDetails.getClientId());
        }
    }

    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
        Map<String, Object> updatemap = new HashMap<String, Object>();
        OAuthClientDetails oAuthClientDetails = new OAuthClientDetails();
        oAuthClientDetails.setClientId(clientId);
        oAuthClientDetails.setClientSecret(com.wenyu.oauth.util.StringUtils.hash(secret, "md5"));
        int count = OauthClientDetailMapper.updateClientDetailByClientId(oAuthClientDetails);
        if (count != 1) {
            throw new NoSuchClientException("No client found with id = " + clientId);
        }
    }

    public void removeClientDetails(String clientId) throws NoSuchClientException {
        int insertflag = OauthClientDetailMapper.ifexist(clientId);
        if (insertflag == 1) {
            OauthClientDetailMapper.deleteClientDetail(clientId);
        } else {
            throw new NoSuchClientException("No client found with id = " + clientId);
        }
    }

    public List<ClientDetails> listClientDetails() {
//		client_id,client_secret,resource_ids, scope, "
//				+ "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
//				+ "refresh_token_validity, additional_information
        List<ClientDetails> cdlist = new ArrayList<ClientDetails>();
        List<OAuthClientDetails> mocdlist = (List<OAuthClientDetails>) OauthClientDetailMapper.getClientListByClientId("client_id");
        if (mocdlist != null && mocdlist.size() != 0) {
            for (OAuthClientDetails OAuthClientDetails : mocdlist) {
                cdlist.add(this.mongo2CD(OAuthClientDetails));
            }
        }
        return cdlist;
    }

    private Object[] getFields(ClientDetails clientDetails) {
        Object[] fieldsForUpdate = getFieldsForUpdate(clientDetails);
        Object[] fields = new Object[fieldsForUpdate.length + 1];
        System.arraycopy(fieldsForUpdate, 0, fields, 1, fieldsForUpdate.length);
        fields[0] = clientDetails.getClientSecret() != null ? com.wenyu.oauth.util.StringUtils.hash(clientDetails.getClientSecret(), "md5")
                : null;
        return fields;
    }

    private Object[] getFieldsForUpdate(ClientDetails clientDetails) {
        String json = null;
        try {
            json = mapper.writeValueAsString(clientDetails.getAdditionalInformation());
        } catch (Exception e) {
            logger.warn("Could not serialize additional information: " + clientDetails, e);
        }
        return new Object[]{
                clientDetails.getResourceIds() != null ? StringUtils.collectionToCommaDelimitedString(clientDetails
                        .getResourceIds()) : null,
                clientDetails.getScope() != null ? StringUtils.collectionToCommaDelimitedString(clientDetails
                        .getScope()) : null,
                clientDetails.getAuthorizedGrantTypes() != null ? StringUtils
                        .collectionToCommaDelimitedString(clientDetails.getAuthorizedGrantTypes()) : null,
                clientDetails.getRegisteredRedirectUri() != null ? StringUtils
                        .collectionToCommaDelimitedString(clientDetails.getRegisteredRedirectUri()) : null,
                clientDetails.getAuthorities() != null ? StringUtils.collectionToCommaDelimitedString(clientDetails
                        .getAuthorities()) : null, clientDetails.getAccessTokenValiditySeconds(),
                clientDetails.getRefreshTokenValiditySeconds(), json, clientDetails.getClientId()};
    }

    public void setSelectClientDetailsSql(String selectClientDetailsSql) {
        this.selectClientDetailsSql = selectClientDetailsSql;
    }

    public void setDeleteClientDetailsSql(String deleteClientDetailsSql) {
        this.deleteClientDetailsSql = deleteClientDetailsSql;
    }

    public void setUpdateClientDetailsSql(String updateClientDetailsSql) {
        this.updateClientDetailsSql = updateClientDetailsSql;
    }

    public void setUpdateClientSecretSql(String updateClientSecretSql) {
        this.updateClientSecretSql = updateClientSecretSql;
    }

    public void setInsertClientDetailsSql(String insertClientDetailsSql) {
        this.insertClientDetailsSql = insertClientDetailsSql;
    }

    public void setFindClientDetailsSql(String findClientDetailsSql) {
        this.findClientDetailsSql = findClientDetailsSql;
    }

//	/**
//	 * @param listFactory the list factory to set
//	 */
//	public void setListFactory(JdbcListFactory listFactory) {
//		this.listFactory = listFactory;
//	}
//
//	/**
//	 * @param rowMapper the rowMapper to set
//	 */
//	public void setRowMapper(RowMapper<ClientDetails> rowMapper) {
//		this.rowMapper = rowMapper;
//	}

    /**
     * Row mapper for ClientDetails.
     *
     * @author Dave Syer
     */

    public ClientDetails mongo2CD(OAuthClientDetails mocd) {
        BaseClientDetails details = null;
        if (mocd != null) {
            details = new BaseClientDetails(mocd.getClientId(), mocd.getResourceIds(), mocd.getScope(),
                    mocd.getAuthorizedGrantTypes(), mocd.getAuthorities(), mocd.getWebServerRedirectUri());
            details.setClientSecret(mocd.getClientSecret());
            details.setAccessTokenValiditySeconds(mocd.getAccessTokenValidity());
            details.setRefreshTokenValiditySeconds(mocd.getRefreshTokenValidity());
            String[] redirectUris = org.apache.commons.lang3.StringUtils.isEmpty(mocd.getWebServerRedirectUri()) ? null : mocd.getWebServerRedirectUri().split(",");
            if (ArrayUtils.isNotEmpty(redirectUris)) {
                details.setRegisteredRedirectUri(new HashSet<>(Arrays.asList(redirectUris)));
            }
            String json = mocd.getAdditionalInformation();
            if (json != null) {
                try {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> additionalInformation = mapper.readValue(json, Map.class);
                    details.setAdditionalInformation(additionalInformation);
                } catch (Exception e) {
                    logger.warn("Could not decode JSON for additional information: " + details, e);
                }
            }
        }
        return details;
    }

    private static class ClientDetailsRowMapper implements RowMapper<ClientDetails> {
        private ObjectMapper mapper = new ObjectMapper();

        public ClientDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
            BaseClientDetails details = new BaseClientDetails(rs.getString(1), rs.getString(3), rs.getString(4),
                    rs.getString(5), rs.getString(7), rs.getString(6));
            details.setClientSecret(rs.getString(2));
            if (rs.getObject(8) != null) {
                details.setAccessTokenValiditySeconds(rs.getInt(8));
            }
            if (rs.getObject(9) != null) {
                details.setRefreshTokenValiditySeconds(rs.getInt(9));
            }
            String json = rs.getString(10);
            if (json != null) {
                try {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> additionalInformation = mapper.readValue(json, Map.class);
                    details.setAdditionalInformation(additionalInformation);
                } catch (Exception e) {
                    logger.warn("Could not decode JSON for additional information: " + details, e);
                }
            }
            return details;
        }
    }
}
