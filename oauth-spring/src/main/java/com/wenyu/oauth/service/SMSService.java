package com.wenyu.oauth.service;

import com.wenyu.model.SMSConfig;

import java.util.List;

/**
 * Created by Zhaowy on 2014/6/24.
 */
public interface SMSService {
    public Long addConfig(SMSConfig smsConfig);

    public void deleteConfig(SMSConfig smsConfig);

    public List<SMSConfig> getSMSConfig(String client_id);
}
