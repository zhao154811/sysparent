package com.wenyu.dao;


import com.wenyu.model.SMSConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Zhaowy on 2014/6/23.
 */
@Repository(value = "smsMapper")
public interface SMSMapper {
    public Long addConfig(SMSConfig smsConfig);

    public void deleteConfig(SMSConfig smsConfig);

    public List<SMSConfig> getSMSConfig(String client_id);
}
