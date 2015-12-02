package com.enlinkmob.test;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Zhaowy on 2014/6/20.
 */
public class VerifyCodeTest {

    static DefaultHttpClient http = new DefaultHttpClient();

    public static void main(String[] args) {
        String strurl = "https://oauth.api.189.cn/emp/oauth2/v3/access_token";

        HttpPost post = new HttpPost(strurl);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        nvps.add(new BasicNameValuePair("grant_type", "client_credentials"));
        nvps.add(new BasicNameValuePair("app_id", "321253400000035816"));
        nvps.add(new BasicNameValuePair("app_secret", "25a1d3561de7af59b10d45c04aecd24d"));
        try {
            post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            HttpResponse rs = http.execute(post);
            String tokenString = EntityUtils.toString(rs.getEntity());
            System.out.println(tokenString);
            String access_token = (String) JSON.parseObject(tokenString).get("access_token");
            int expires_in = (Integer) JSON.parseObject(tokenString).get("expires_in");
            System.out.println(expires_in);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.SECOND, expires_in);
            System.out.println(calendar.getTime());


            String smsurl = "http://api.189.cn/v2/emp/templateSms/sendSms";

            HttpPost smspost = new HttpPost(smsurl);
            List<NameValuePair> smsparams = new ArrayList<NameValuePair>();
            smsparams.add(new BasicNameValuePair("app_id", "321253400000035816"));
            smsparams.add(new BasicNameValuePair("app_secret", "25a1d3561de7af59b10d45c04aecd24d"));
            smsparams.add(new BasicNameValuePair("access_token", access_token));
            smsparams.add(new BasicNameValuePair("acceptor_tel", "18601991067"));
            smsparams.add(new BasicNameValuePair("template_id", "91001409"));
            smsparams.add(new BasicNameValuePair("template_param", "{}"));
            smsparams.add(new BasicNameValuePair("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
            smspost.setEntity(new UrlEncodedFormEntity(smsparams, "UTF-8"));

            HttpResponse smsrs = http.execute(smspost);
            System.out.println(EntityUtils.toString(smsrs.getEntity()));


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
