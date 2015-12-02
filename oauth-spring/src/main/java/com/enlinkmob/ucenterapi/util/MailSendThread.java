package com.enlinkmob.ucenterapi.util;

import com.enlinkmob.ucenterapi.model.SMSConfig;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Zhaowy on 2014/7/24.
 */
public class MailSendThread {

    ExecutorService pool = this.getInstance();

    private ExecutorService getInstance() {
        return Executors.newCachedThreadPool();
    }

    public void sendmail(String phoneNum, SMSConfig smsConfig, String params) {
        pool.execute(new SMSSendTask(phoneNum, smsConfig, params));
    }
}
