package com.enlinkmob.ucenterapi.util;

import com.alibaba.fastjson.JSON;
import com.enlinkmob.ucenterapi.exception.ParamException;
import com.enlinkmob.ucenterapi.model.SMSConfig;
import com.enlinkmob.ucenterapi.model.SMSSendLog;
import com.enlinkmob.ucenterapi.service.SMSService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.context.ContextLoader;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Zhaowy on 2014/6/24.
 */
public class SMSSendTask implements Runnable {
    protected final Log logger = LogFactory.getLog(getClass());

    private String phoneNum;
    private SMSConfig smsConfig;
    private DefaultHttpClient http = new DefaultHttpClient();
    private SMSService smsServiceImpl;
    private String paramString;

    public SMSSendTask(String phoneNum, SMSConfig smsConfig, String paramString) {
        this.phoneNum = phoneNum;
        this.smsConfig = smsConfig;
        this.paramString = paramString;
        smsServiceImpl = (SMSService) ContextLoader.getCurrentWebApplicationContext().getBean("smsServiceImpl");
    }

    @Override
    public void run() {
        Date now = new Date();
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(now);
        rightNow.add(Calendar.MINUTE, -2);
        now = rightNow.getTime();
        if (now.before(smsConfig.getTokenExpireIn())) {
            //直接使用保存的token，然后发送短信，并log
            boolean issend = this.sendVerifySMS();
        } else {

            try {
                //使用获取的token直到产生新的token 并入库，然后发送短信，并log
                HttpPost tokenpost = new HttpPost(smsConfig.getTokenUrl());
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();

                nvps.add(new BasicNameValuePair("grant_type", smsConfig.getGrantType()));
                nvps.add(new BasicNameValuePair("app_id", smsConfig.getApiId()));
                nvps.add(new BasicNameValuePair("app_secret", smsConfig.getApiSecret()));
                tokenpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
                HttpResponse rs = http.execute(tokenpost);
                String tokenString = EntityUtils.toString(rs.getEntity());
                String access_token = (String) JSON.parseObject(tokenString).get("access_token");
                if (access_token.equals(smsConfig.getAccessToken())) {
                    boolean issend = this.sendVerifySMS();
                } else {
                    this.getToken();
                    boolean issend = this.sendVerifySMS();
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }


        }
    }


    private boolean sendVerifySMS() {
        HttpPost smspost = new HttpPost(smsConfig.getSendSmsUrl());
        List<NameValuePair> smsparams = new ArrayList<NameValuePair>();
        smsparams.add(new BasicNameValuePair("app_id", smsConfig.getApiId()));
        smsparams.add(new BasicNameValuePair("app_secret", smsConfig.getApiSecret()));
        smsparams.add(new BasicNameValuePair("access_token", smsConfig.getAccessToken()));
        smsparams.add(new BasicNameValuePair("acceptor_tel", this.phoneNum));
        smsparams.add(new BasicNameValuePair("template_id", smsConfig.getTemplate_id()));
        smsparams.add(new BasicNameValuePair("template_param", paramString));
        smsparams.add(new BasicNameValuePair("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
        try {
            smspost.setEntity(new UrlEncodedFormEntity(smsparams, "UTF-8"));
            HttpResponse smsrs = http.execute(smspost);
            String reponse = EntityUtils.toString(smsrs.getEntity());
            String res_code = (String) JSON.parseObject(reponse).get("res_code");
//			System.out.println(res_code);
            if (res_code == null || res_code.length() == 0) {
                throw new ParamException("api error", reponse);
            }

            if (res_code.equals("0")) {
                SMSSendLog log = new SMSSendLog();
                log.setPhoneNum(this.phoneNum);
                log.setJsonparams(this.paramString);
                log.setCreateTime(new Date());
                log.setClient_id(smsConfig.getClient_id());
                log.setSendStatu(Integer.parseInt(res_code));
//                log.setStatus(1);
//				this.smsLogServiceImpl.addLog(log);
            } else {
                SMSSendLog log = new SMSSendLog();
                log.setPhoneNum(this.phoneNum);
                log.setJsonparams(this.paramString);
                log.setCreateTime(new Date());
                log.setClient_id(smsConfig.getClient_id());
                log.setSendStatu(Integer.parseInt(res_code));
//                log.setStatus(1);
//				this.smsLogServiceImpl.addLog(log);
            }
//			res_code
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ParamException("api error", e.getMessage());
        }
        return false;
    }

    private void getToken() {
        String access_token = null;
        try {
            //使用获取的token直到产生新的token 并入库，然后发送短信，并log
            HttpPost tokenpost = new HttpPost(smsConfig.getTokenUrl());
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();

            nvps.add(new BasicNameValuePair("grant_type", smsConfig.getGrantType()));
            nvps.add(new BasicNameValuePair("app_id", smsConfig.getApiId()));
            nvps.add(new BasicNameValuePair("app_secret", smsConfig.getApiSecret()));
            tokenpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            HttpResponse rs = http.execute(tokenpost);
            String tokenString = EntityUtils.toString(rs.getEntity());
            access_token = (String) JSON.parseObject(tokenString).get("access_token");
            if (access_token != null && !access_token.equals(smsConfig.getAccessToken())) {
                Integer expires_in = (Integer) JSON.parseObject(tokenString).get("expires_in");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.SECOND, expires_in);
                smsConfig.setTokenExpireIn(calendar.getTime());
                smsConfig.setAccessToken(access_token);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("tokenExpireIn", calendar.getTime());
                map.put("accessToken", access_token);
//				smsServiceImpl.updateConfig(map,smsConfig.get_id());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }


    }
}
