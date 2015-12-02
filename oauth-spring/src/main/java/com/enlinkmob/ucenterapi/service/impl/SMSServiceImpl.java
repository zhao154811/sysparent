package com.enlinkmob.ucenterapi.service.impl;

import com.enlinkmob.ucenterapi.dao.SMSMapper;
import com.enlinkmob.ucenterapi.model.SMSConfig;
import com.enlinkmob.ucenterapi.service.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Zhaowy on 2014/6/24.
 */
@Service("smsServiceImpl")
public class SMSServiceImpl implements SMSService {
    @Autowired
    private SMSMapper smsMapperImpl;

    @Override
    public Long addConfig(SMSConfig smsConfig) {
        return smsMapperImpl.addConfig(smsConfig);
    }


    @Override
    public void deleteConfig(SMSConfig smsConfig) {
        this.smsMapperImpl.deleteConfig(smsConfig);
    }

    @Override
    public List<SMSConfig> getSMSConfig(String client_id) {
        return smsMapperImpl.getSMSConfig(client_id);
    }
}
