/**
 * @Title: ClientServiceImpl.java
 * @Package com.enlinkmob.ucenterapi.provider
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-4-24 下午2:17:23
 * @version V1.0
 */
package com.wenyu.oauth.service.impl;

import com.wenyu.oauth.dao.ClientMapper;
import com.wenyu.oauth.model.OAuthClientDetails;
import com.wenyu.oauth.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Zhaowy
 * @ClassName: ClientServiceImpl
 * @date 2014-4-24 下午2:17:23
 */
@Service("clientDetailService")
@Scope(value = "singleton")
public class ClientServiceImpl implements ClientService {

    /**
     * (非 Javadoc)
     * <p>Title: getClient</p>
     * <p>Description: 获取OAuthClientDetails</p>
     *
     * @param clientId
     * @param clientSecret
     * @return OAuthClientDetails
     * @see ClientService#getClient(java.lang.String, java.lang.String)
     */
    @Autowired
    private ClientMapper clientDetailDao;//clientdao注入


    @Override
    public OAuthClientDetails getClient(String clientId,
                                        String clientSecret) {
//		return clientDetailDao.getClient(clientId, clientSecret);
        return null;
    }

    public List<OAuthClientDetails> getClientPage(Map<String, Object> conditions) {
//		return clientDetailDao.getClientPage(pageModel, conditions);
        return null;
    }

}
