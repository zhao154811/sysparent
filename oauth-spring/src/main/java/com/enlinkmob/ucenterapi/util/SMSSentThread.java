package com.enlinkmob.ucenterapi.util;

import com.enlinkmob.ucenterapi.model.SMSConfig;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Zhaowy on 2014/6/24.
 */
@Component(value = "smsSentThread")
@Scope(value = "singleton")
public class SMSSentThread {

    ExecutorService pool = this.getInstance();

    private ExecutorService getInstance() {
        return Executors.newCachedThreadPool();
    }

    public void sendsms(String phoneNum, SMSConfig smsConfig, String params) {
        pool.execute(new SMSSendTask(phoneNum, smsConfig, params));
    }

}
