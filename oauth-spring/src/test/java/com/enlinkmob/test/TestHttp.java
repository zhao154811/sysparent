package com.enlinkmob.test;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaowy on 15/7/2.
 */
public class TestHttp {
    public static void main(String[] args) throws Exception {
        DefaultHttpClient http = new DefaultHttpClient();
        String strurl = "http://notifycenter.cnautoshows.com/newnotify/sendsms";
        HttpPost hp = new HttpPost(strurl);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        String clientid = "exhibitionOL";
        String clientkey = DigestUtils.md5Hex("exhibitionOL" + "6408ef76522f0695466ad43da90bbb19");
        String verifycode = ((int) ((Math.random() * 9 + 1) * 100000)) + "";
        nvps.add(new BasicNameValuePair("client_id", clientid));
        nvps.add(new BasicNameValuePair("clientSign", clientkey));
        nvps.add(new BasicNameValuePair("mobile", "13466641530"));
        nvps.add(new BasicNameValuePair("content", verifycode));
        hp.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        HttpResponse rs = http.execute(hp);
        System.out.print("end");
        System.out.println(rs.getStatusLine());
        System.out.println(EntityUtils.toString(rs.getEntity()));
    }
}
