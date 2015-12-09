/**
 * @Title: ClientService.java
 * @Package com.enlinkmob.ucenterapi.provider
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-4-24 下午2:16:11
 * @version V1.0
 */
package com.wenyu.oauth.service;


import com.wenyu.oauth.model.OAuthClientDetails;

import java.util.List;
import java.util.Map;

/**
 * @author Zhaowy
 * @ClassName: ClientService
 * @Description: (这里用一句话描述这个类的作用)
 * @date 2014-4-24 下午2:16:11
 */
public interface ClientService {
    public OAuthClientDetails getClient(String clientId, String clientSecret);

    public List<OAuthClientDetails> getClientPage(Map<String, Object> conditions);
}
