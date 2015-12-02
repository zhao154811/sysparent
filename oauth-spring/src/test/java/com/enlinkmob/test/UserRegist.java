/**
 * @Title: UserRegist.java
 * @Package com.enlinkmob.test
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-5-12 下午3:20:16
 * @version V1.0
 */
package com.enlinkmob.test;

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
 * @author Zhaowy
 * @ClassName: UserRegist
 * @Description: (这里用一句话描述这个类的作用)
 * @date 2014-5-12 下午3:20:16
 */
public class UserRegist {
    public static void main(String[] args) throws Exception {
//		String strurl="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        String strurl = "https://api.weixin.qq.com/cgi-bin/user/info";
//		for(int i=10000;i<10001;i++){
        DefaultHttpClient http = new DefaultHttpClient();
        HttpPost hp = new HttpPost(strurl);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//			 access_token=79203acc-0b27-42ee-aa04-13e8f3caa549&userName=test

//		         nvps.add(new BasicNameValuePair("access_token", "6111af25-b29e-4ed8-9f92-770cba5b3d4c"));  
//		         nvps.add(new BasicNameValuePair("updatemap", "{\"realName\":\"赵文宇2\"}"));  
//		         nvps.add(new BasicNameValuePair("userName", "usersys"));
//		   nvps.add(new BasicNameValuePair("client_id", "auto-china"));
//			String privateKey="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJavb+2Rr6n6+aSu0RwEeuSO2REOphLoM8TBG1pCLSqfZu3ZK6qkrGWefgrRdnDm2oLcnZ9JmzOWhHok9kdO/kmDFPOWwE8CSiW5pmvybnR7SfYRJ0gM7KfosoChxEOmStIP02wm0MKp3BD5EdzpPjLOt7CmYg+2c5/7VFUdvtX7AgMBAAECgYBdCoJf/D6tBCy1BMRVOmbvTUy2fYcJ0Zp1eI79EjN1R+t0HU4bFYblUBGfeGpbmA+AEdy5h+du2Rd/m+b9bMcRhP85ebtF9KSVmsilhwBYoG9MztAtmXty32SVBWdVgmYWXJQ5Zsv5EHbyEDKMdqL872cgzcgNSJf5fI8MKb+DcQJBANsowbB/bDYUmfY1TBTKPan/WOsmon26/G/2fOTt5LSo6p17LE999zy/pem146jSkpfpbwLyAD6KdY8pjgob4QUCQQCwA/3QtsjpN9jfU53c0eE0g3q7kAk2noSSfGUMgi/3as9Tm5yncy+oRkUIHjzIoQw9TwiBli0LzI7EU7eFiYr/AkBMnnMYwXOTdKBe80MmAY6NYDg1/cvRKQ4YFpSTdJgAkQ11ARr8r41AL8BRdMWiv/uAIJwu62wqcT2oT17ZKeYBAkBOzLqo4ev4XQQ+lxSpc1y7QdGEfuthBH6dhgtHGlGXFr5S0+vCG5NOZocpJ0BXIaJ1IBjSywTHr4CK7F/Q7M7JAkArB3FVGn87FcKe8qmKjpMl/dCqF4+KNGGAd4xMaMILRT4VLJgvgiSWongq959CBLAglH6h4k7NBcwU62Y46tDX";
//		    System.err.println("私钥加密——公钥解密");
//		    String source = "fdffdc377dfc6e155c2769ff97a74d10";
//		    byte[] data = source.getBytes();
//		    try {
//				byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);
//		   nvps.add(new BasicNameValuePair("client_secret",new BASE64Encoder().encode(encodedData)));
//		   System.out.println(new BASE64Encoder().encode(encodedData));
//			} catch (Exception e) {
//				//  Auto-generated catch block
//				e.printStackTrace();
//			}
//		    nvps.add(new BasicNameValuePair("appid","wxa2f19e3431bf13b1"));
//		    nvps.add(new BasicNameValuePair("secret","187849ad5545b627bc3fed9cfeb41773"));
//		nvps.add(new BasicNameValuePair("code","0212e6ea3692fab0a107a583e029f9db"));
//		nvps.add(new BasicNameValuePair("grant_type","authorization_code"));
        nvps.add(new BasicNameValuePair("access_token", "R9XsWHUSTistlgJJXHPYzT7KaOPg6p-_0qF2XU61AJAXnfBGTovkr5_ntdczyn_yJhiUI7y86TQn-zvY9LKKf4mwxzwnrtMVgGVYBnobGzQ"));
        nvps.add(new BasicNameValuePair("openid", "oyBuCjs8SUxFaTfcl7nl7sgU0BFk"));
        nvps.add(new BasicNameValuePair("lang", "zh_CN"));


//		         nvps.add(new BasicNameValuePair("realName", "赵文宇"));  
//		         nvps.add(new BasicNameValuePair("idNo", "123456123456789"));  
//		         nvps.add(new BasicNameValuePair("phoneNum", "18601991067"));  
//		         nvps.add(new BasicNameValuePair("email", "test@163.com"));  
//		         nvps.add(new BasicNameValuePair("address", "北京印象"));  
//		         nvps.add(new BasicNameValuePair("headIcon", "hjksadhgjksdgjksdhgasdghasdhghlk/dstgasettee"));  

        hp.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        HttpResponse rs = http.execute(hp);
        http.close();
//			 List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
//		     nvps.add(new BasicNameValuePair("opt", "uploadhi"));  
//		     nvps.add(new BasicNameValuePair("uid", "admin@bev3.enlink-mob.com"));
//		     nvps.add(new BasicNameValuePair("headicon", ImageUtils.getImageBinary()));
//		     hp.setEntity(new UrlEncodedFormEntity(nvps));  
//			HttpResponse rs=http.execute(hp);
        System.out.println(EntityUtils.toString(rs.getEntity(), "utf-8"));
//		}


    }

}
