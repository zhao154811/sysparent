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

public class HttpTest {


    public static void main(String[] args) throws Exception {
        DefaultHttpClient http = new DefaultHttpClient();
//		DefaultHttpClient http = new DefaultHttpClient();
//	用户资料
//	HttpPost hp=new HttpPost("http://localhost:9090/plugins/eimstructure/eimstructureservice/api.serv?opt=userinfo&uid=testzz@zhaowy.com");
        //企业架构
//	HttpPost hp=new HttpPost("http://localhost:9090/plugins/eimstructure/eimstructureservice/api.serv?opt=eimstructure&uid=testzz@zhaowy.com");
        //查询子部门信息和用户
//	HttpPost hp=new HttpPost("http://localhost:9090/plugins/eimstructure/eimstructureservice/api.serv?opt=departmentusers&uid=testzz@zhaowy.com&deptid=2");
//密码更新
//	HttpPost hp=new HttpPost("http://localhost:9090/plugins/eimstructure/eimstructureservice/api.serv?opt=updatepwd&uid=testzz@zhaowy.com&pwd=654321");
//email更新
//	HttpPost hp=new HttpPost("http://localhost:9090/plugins/eimstructure/eimstructureservice/api.serv?opt=updateemail&uid=testzz@zhaowy.com&email=qiannian_zwy@163.com");
//updatetelephone
//	HttpPost hp=new HttpPost("http://localhost:9090/plugins/eimstructure/eimstructureservice/api.serv?opt=updatetelephone&uid=testzz@zhaowy.com&tel=18600956543");
//cfmpwd
//	HttpPost hp=new HttpPost("http://localhost:9090/plugins/eimstructure/eimstructureservice/api.serv?opt=cfmpwd&uid=testzz@zhaowy.com&pwd=654321");
//search name
//	String name="赵文宇";
//	name=new String(name.getBytes("utf-8"),"iso-8859-1");
//	HttpGet hp=new HttpGet("http://localhost:9090/plugins/eimstructure/eimstructureservice/api.serv?opt=searchuser&uname="+URLEncoder.encode(name,"utf-8"));
//update source  (ios/android)
//	HttpGet hp=new HttpGet("http://localhost:9090/plugins/eimstructure/eimstructureservice/api.serv?opt=updatesource&uid=testzz@zhaowy.com&source=5");

//	
//	
////	System.out.println(EntityUtils.toString(rs.getEntity()));
//	用户头像
//	String im=URLEncoder.encode(,"utf-8");
//	String strurl="http://tech.enlink-mob.com:9090/ucenterapi/cltAPI/cltAPIGet";
//	String strurl="http://localhost/ucenterapi/user/common/bindCustomerID";
//	String strurl="http://localhost/ucenterapi/user/regist/addCustomerID";
//	String strurl="http://localhost/ucenterapi/user/common/addUserInfo";
//	String strurl="http://localhost/ucenterapi/user/common/getUser";
//	String strurl="http://10.0.2.130/ucenterapi/user/common/updateUserHeadIcon";
//	String strurl="http://ucenter.cnautoshows.com/user/regist/getUserById";

//	865ed4c4-69cb-4014-a135-47fcf0abc97b
//	String strurl="http://localhost/ucenterapi/user/regist/getNotice";

//	String strurl="http://localhost/ucenterapi/user/regist/getVerifyCode";
//	String strurl="http://localhost/ucenterapi/user/regist/client/userRegistToken";
//	String strurl="http://localhost/ucenterapi/userBehavior/getUserBehavior";
//	String strurl="http://localhost/ucenterapi/ticket/userBehavior";
//	String strurl="http://tech.enlink-mob.com:9292/ucenterapi/ticket/fastUserBehavior";
//		String strurl = "http://tech.enlink-mob.com:9191/bbs/api/uc.php?time=1421393904&code=8f30cHthXd%2FNzuMCAlDANjfkkHgdQo1EEFX1CA7aRJznCCa3%2B%2BOQ%2F6PGuvBptIGdzqH0LgsvquoeyLoioKaUSO94xi%2BDhOPVoS%2Fdy3d1GCgqxim0I14FDfsqWaVd0XaSRBTFlGdn3BPqqC%2BFTcqdJO%2FBSJo3v02RZqYXCA";
//		String strurl = "http://10.0.2.40:9090/ucenterapi/uc/gateway";
        String strurl = "http://localhost:8080/uc/gateway";


//	String strurl="http://localhost/ucenterapi/user/common/bindNewLogName";
//	URL url = new URL(strurl);
//	URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
        HttpPost hp = new HttpPost(strurl);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//	 access_token=79203acc-0b27-42ee-aa04-13e8f3caa549&userName=test

//         nvps.add(new BasicNameValuePair("access_token", "6093212f-f674-4004-836f-ce58598ddc6c"));
//         nvps.add(new BasicNameValuePair("userName", "idtest"));
//       nvps.add(new BasicNameValuePair("password", "111111"));

//         nvps.add(new BasicNameValuePair("updatemap", "{\"idNo\":\"98874561321341654651561\",\"realName\":\"赵文宇3\"}"));
//         nvps.add(new BasicNameValuePair("userName", "caonimei4"));
        nvps.add(new BasicNameValuePair("client_id", "auto-china"));
        nvps.add(new BasicNameValuePair("method", "customerLogin"));
        nvps.add(new BasicNameValuePair("sourceApp", "wechat"));
        nvps.add(new BasicNameValuePair("clientSign", DigestUtils.md5Hex("auto-china" + "6408ef76522f0695466ad43da90bbb19" + 0)));
//		nvps.add(new BasicNameValuePair("clientSign", DigestUtils.md5Hex("enlink_activity_manage" + "F2E6E8E658A8DC341CDD728FF702FE24" + 1)));

//		System.out.println(DigestUtils.md5Hex("auto-china" + "6408ef76522f0695466ad43da90bbb19" + 0));
        nvps.add(new BasicNameValuePair("infoJson", "{\"openid\":\"oPMbzjnTbETqlcz3222Cs5mr1KFQ\",\"nickname\":\"Keith\",\"sex\":1,\"language\":\"zh_CN\",\"city\":\"常德\",\"province\":\"湖南\",\"country\":\"中国\",\"headimgurl\":\"http://wx.qlogo.cn/mmopen/Q3auHgzwzM5siaZH8ibc0NakgwG0UPK95QFDicMA0ZEibZk319uV9slon4fU5Z58kQGtiadz2gSBH5CE2gYwmibsLyKg/0\",\"privilege\":[],\"unionid\":\"oVdS7t_TXfHvb222hfoqF5sztR9M\"}"));
//		nvps.add(new BasicNameValuePair("phoneNum", "18601991067"));
//		nvps.add(new BasicNameValuePair("password", "123456"));
//		nvps.add(new BasicNameValuePair("birth", "20130214"));
//		nvps.add(new BasicNameValuePair("nickName", "撸啊撸"));
//		nvps.add(new BasicNameValuePair("userId", "556d7041e4b0a4050349ff0b"));
//		nvps.add(new BasicNameValuePair("userSign", "2AC559AF99E70C28A447035BA824F3FC"));
//		Map<String, Object> map = new HashMap<>();
//		map.put("nickName","555644545564");
//		map.put("password", "654321");
//		map.put("birth","20150302");
//
//		nvps.add(new BasicNameValuePair("infoJson", JSON.toJSONString(map)));


//		nvps.add(new BasicNameValuePair("version", "v2"));


////	String autochinaprivateKey="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJavb+2Rr6n6+aSu0RwEeuSO2REOphLoM8TBG1pCLSqfZu3ZK6qkrGWefgrRdnDm2oLcnZ9JmzOWhHok9kdO/kmDFPOWwE8CSiW5pmvybnR7SfYRJ0gM7KfosoChxEOmStIP02wm0MKp3BD5EdzpPjLOt7CmYg+2c5/7VFUdvtX7AgMBAAECgYBdCoJf/D6tBCy1BMRVOmbvTUy2fYcJ0Zp1eI79EjN1R+t0HU4bFYblUBGfeGpbmA+AEdy5h+du2Rd/m+b9bMcRhP85ebtF9KSVmsilhwBYoG9MztAtmXty32SVBWdVgmYWXJQ5Zsv5EHbyEDKMdqL872cgzcgNSJf5fI8MKb+DcQJBANsowbB/bDYUmfY1TBTKPan/WOsmon26/G/2fOTt5LSo6p17LE999zy/pem146jSkpfpbwLyAD6KdY8pjgob4QUCQQCwA/3QtsjpN9jfU53c0eE0g3q7kAk2noSSfGUMgi/3as9Tm5yncy+oRkUIHjzIoQw9TwiBli0LzI7EU7eFiYr/AkBMnnMYwXOTdKBe80MmAY6NYDg1/cvRKQ4YFpSTdJgAkQ11ARr8r41AL8BRdMWiv/uAIJwu62wqcT2oT17ZKeYBAkBOzLqo4ev4XQQ+lxSpc1y7QdGEfuthBH6dhgtHGlGXFr5S0+vCG5NOZocpJ0BXIaJ1IBjSywTHr4CK7F/Q7M7JAkArB3FVGn87FcKe8qmKjpMl/dCqF4+KNGGAd4xMaMILRT4VLJgvgiSWongq959CBLAglH6h4k7NBcwU62Y46tDX";
//	String autochinapublicKye="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWr2/tka+p+vmkrtEcBHrkjtkRDqYS6DPEwRtaQi0qn2bt2SuqpKxlnn4K0XZw5tqC3J2fSZszloR6JPZHTv5JgxTzlsBPAkoluaZr8m50e0n2ESdIDOyn6LKAocRDpkrSD9NsJtDCqdwQ+RHc6T4yzrewpmIPtnOf+1RVHb7V+wIDAQAB";
//		String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKaapx+DPJ44eQXnkT21JmMFepeNVIYVUdBILEyiQRp5H4WN2vA5ESWKibmIE1E7rGTQgP0hpt5xspdkM9t8M1I8Gm6y4DAnZP3DZJrmzVvtJr4mDMzZgKF6ht8q+BsHq3FNo83o0flv2mZle9kTBi4ZouUyBDwA8tusDZlyXlpBAgMBAAECgYBCYLWbMY9yPm/BcXyFiGoN473vKcj2TwI0qLy1n6Oup3lunZEQSrxRJ7wtplCD+fF6jTQHJmQ0ljogZTTgCsDrJ4KcxDO24zBXcDOm/aocF0Dpyyd9Tp6GbwWmiot9CDvViup0Pt/3Fo5FOStPj5ch0iUy9bLRDk7GjAftXdS9AQJBAP62TaDG7nqt+q5Y5cj4AYSg2joZaohMWzeCTjsbTAW0sjeBy/WZjdfj/TsDvy5G0Oj5y66zToYVYDA9vQQ9WrECQQCnck2xHqDgFlA/GvOHD9uYkGPoBDQlz+DApjbBT7qbjy0QTyKdYbdisTlbF91kZwh36wDMHX8pKGX+mLzGRLyRAkB2AconUeMNDWvVuLQFDvnNE6TnQ/06Vn3wUruh4Lyw7A2hb84p5FF2E1ewLsU1UHPbbFe0gbhAoMimsQPQYgiRAkEAo/V8ta40ALhYozIUu29/qwfXi9xx44SFop4eh3M2WhP8IDgcwNZp5y156ASaODHbTPDDuxo25Wo1VPLiBZJQwQJBAKhnUcSlaV3XnNX/Dpiu6n3STNT4weJhwZpHAevoglUWrmTR7f3TQmje2pPNQwOaUOQDy8GJhFeXCt+kVBowwVw=";
//		String publicKye = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCCRStxLgGp9tixpcUkyM9NjjzTJTH4P+jqApUlAVWVnNz0I0FsF9M7KMxXFX2Gfx34iaTAlqt0aw16I2ncdpZMvurY9leeFYW8D4njiuVOurvNs4kF4p1bFV0N1AGw6R35tu/YA4GfpnZm462e9DaKiYa87cjANWGYB+IDy7FoAwIDAQAB";
//		String publicKye = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmmqcfgzyeOHkF55E9tSZjBXqXjVSGFVHQSCxMokEaeR+FjdrwOREliom5iBNRO6xk0ID9IabecbKXZDPbfDNSPBpusuAwJ2T9w2Sa5s1b7Sa+JgzM2YCheobfKvgbB6txTaPN6NH5b9pmZXvZEwYuGaLlMgQ8APLbrA2Zcl5aQQIDAQAB";
//		String publicKye = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4FBZMBZw3z6DvuaTbX3hpJwO8oz4W8Ln9eVWa" +
//				"o2TtSoREfRvAR+Fl0ofwyG5lO2Zqzr8wUrBJ3jjBqLrEca4Th+kLeQXVdeib4xLjrbu/Jvf4A9WH" +
//				"XVfvjzJcAdEkgOpfZ+0cl1wRLkHU6oi/pYQQdBcQq68oyjcfjmLJ83lsCwIDAQAB";
//
//		System.err.println("公钥加密——私钥解密");
//		String source = "fdffdc377dfc6e155c27ss6997a74d10";
//		byte[] data = source.getBytes();
//		try {
//			byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKye);
//			Map<String, String[]> param = new HashMap<String, String[]>();
////			param.put("password", new String[]{"654321"});
//			param.put("userId", new String[]{"551a7ba9e4b0f2986b7a00cf"});
////			param.put("userName", new String[]{"13552317505"});
////			param.put("nickName",new String[]{"wulalawulala"});
//
//			param.put("method", new String[]{"loginById"});
////			param.put("sourceApp", new String[]{"weichat"});
////			System.out.println(JSON.toJSONString(param));
//			System.out.println(JSON.toJSONString(param));

//			nvps.add(new BasicNameValuePair("sign", new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(JSON.toJSONString(param).getBytes(), publicKye))));
//			nvps.add(new BasicNameValuePair("sign","gcHN+kBH5Ww1yM5ry\\/yu4C62WNoyEUQppzMu8X4bxjos+suMR54DtZNFdSE7BzzULkJnIEggkyCO\\/DPK3n8Mn2ijGBk2vq6ifISmgclE0btXyMjSgnwDYqSdcNUYNd2HKfbg+tzKi+fKMA\\/Zf2aZPf+2dbz9NZFu7kWuHjR9Q3I="));
//			nvps.add(new BasicNameValuePair("infoJson","eyJzdWJzY3JpYmUiOjEsIm9wZW5pZCI6Im9QTWJ6am1UTTJ5M3ZrVVYwcmFVaGVxQ3pmcU0iLCJuaWNrbmFtZSI6Ind5emhhbyIsInNleCI6MSwibGFuZ3VhZ2UiOiJ6aF9DTiIsImNpdHkiOiLmtbfmt4AiLCJwcm92aW5jZSI6IuWMl+S6rCIsImNvdW50cnkiOiLkuK3lm70iLCJoZWFkaW1ndXJsIjoiaHR0cDpcL1wvd3gucWxvZ28uY25cL21tb3BlblwvRkd6WXlhYVE3M1cwRTFoS3RwT091bUptUVdaTFdFa1lKYkgzUG9BaWJ2cHBpY3J4NjJuaWF0aFEySWJNT09nYjd5QjFKN3pqMDBtQVZzNnYzYnY0M0phb1RKdzB3TG5HVG53XC8wIiwic3Vic2NyaWJlX3RpbWUiOjE0MjI4NTg0NDUsInVuaW9uaWQiOiJvVmRTN3Q4bFEzU0VjYUhsNDE4LU9Hc0NqV3FNIiwicmVtYXJrIjoiIiwidG9rZW4iOiJnaF82NjFmYzViNjViMDUifQ=="));
//		nvps.add(new BasicNameValuePair("userName", new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(new String("13466641530").getBytes(), publicKye))) );
//		nvps.add(new BasicNameValuePair("projectId", new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(new String("1").getBytes(), publicKye))) );
//		nvps.add(new BasicNameValuePair("password", new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(new String("111111").getBytes(), publicKye))) );
//		nvps.add(new BasicNameValuePair("realName", new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(new String("阿德三天啊").getBytes(), publicKye))) );
//		nvps.add(new BasicNameValuePair("appUniqueId", new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(new String("damai").getBytes(), publicKye))) );
//		nvps.add(new BasicNameValuePair("sourceApp", new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(new String("DAMAI").getBytes(), publicKye))) );
//		nvps.add(new BasicNameValuePair("behavior", new BASE64Encoder().encode("DAMAI".getBytes())) );

//			nvps.add(new BasicNameValuePair("toAddresses", "zhaowy@enlink-mob.com"));
//			nvps.add(new BasicNameValuePair("projectName", "广州车展test"));
//			nvps.add(new BasicNameValuePair("projectId", "3"));

//		nvps.add(new BasicNameValuePair("client_secret",source));

//			nvps.add(new BasicNameValuePair("client_secret", new BASE64Encoder().encode(encodedData)));
//			nvps.add(new BasicNameValuePair("appUniqueId", new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(new String("97775").getBytes(), publicKye))));
//		nvps.add(new BasicNameValuePair("channelId",new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(new String("11").getBytes(), publicKye))));

//		nvps.add(new BasicNameValuePair("objId",new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(new String("53f15634e4b04d5f283ce603").getBytes(), publicKye))));
//			nvps.add(new BasicNameValuePair("infoJson", new BASE64Encoder().encode("{\"subscribe\":1,\"openid\":\"oyBuCjjlrBZ0zmICloKqxntestqE\",\"nickname\":\"河鸥鸡爷\",\"sex\":1,\"language\":\"zh_CN\",\"city\":\"东城\",\"province\":\"北京\",\"country\":\"中国\",\"headimgurl\":\"http:\\/\\/wx.qlogo.cn\\/mmopen\\/FrdAUicrPIibcsasRg5pp66yIbtkbmEwowdRtuuMicIfAeN31W7l7bLQXGEwnwa6Oe9RKY6Qjl4jeRiaUEJYVd2exg\\/0\",\"subscribe_time\":1417736030,\"unionid\":\"oyBuCjjlrBZ0zmICloKqxntestee\",\"remark\":\"\"}".getBytes("UTF-8"))));


//			nvps.add(new BasicNameValuePair("sourceApp", new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(new String("weiChat").getBytes(), publicKye))));


//		nvps.add(new BasicNameValuePair("phoneNum", new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(new String("16666666611").getBytes(), publicKye))) );
//		nvps.add(new BasicNameValuePair("email", new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(new String("12346@qq.com").getBytes(), publicKye))) );
//		nvps.add(new BasicNameValuePair("nickUserName", new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(new String("sssbbb").getBytes(), publicKye))) );
//			nvps.add(new BasicNameValuePair("userName", new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(new String("16666666611").getBytes(), publicKye))));

//		nvps.add(new BasicNameValuePair("headIcon", "test19/photo"));
//		nvps.add(new BasicNameValuePair("realName", new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(new String("oooo啊").getBytes(), publicKye))) );
//		nvps.add(new BasicNameValuePair("phoneNum", new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(new String("01472583690").getBytes(), publicKye))) );
//		nvps.add(new BasicNameValuePair("behavior", new BASE64Encoder().encode("{\"idNo\":\"98874561321341654651561\",\"realName\":\"behavior\"}".getBytes("UTF-8")) ));
//		nvps.add(new BasicNameValuePair("projectId", new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(new String("1").getBytes(), publicKye))) );
//		nvps.add(new BasicNameValuePair("pageTag", new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(new String("hongbao").getBytes(), publicKye))) );

//		nvps.add(new BasicNameValuePair("goodsId", new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(new String("11").getBytes(), publicKye))) );
//
//		nvps.add(new BasicNameValuePair("password", new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(new String("111111").getBytes(), publicKye))) );
//		nvps.add(new BasicNameValuePair("access_token", new BASE64Encoder().encode(RSAUtils.encryptByPublicKey("0be70e34-0c69-49bf-b60d-be1389878260".getBytes(), publicKye))));
//		kkony37moFF/s13B9Oa6+7kChv0Duyx63CE64gnPWrLtQGHPybpYqWCpqK7amQF05GW+fgobD81SCr/Q+fiE0uAZfxcDITUr7kxOMzVGMrBK2Ykp5KY4Sat8A75NlZuHIHO2CAxV/RvsykB9AEgjIdys905ljbsvI+h0QGiQl4U=
//		nvps.add(new BasicNameValuePair("realName", new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(new String("爱吃小豆芽").getBytes(), publicKye))) );
//		nvps.add(new BasicNameValuePair("sex", new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(new String("male").getBytes(), publicKye))) );
//		System.out.println(new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(new String("爱吃小豆芽").getBytes(), publicKye)));
//		System.out.println(new BASE64Encoder().encode(RSAUtils.encryptByPublicKey("17b492bf-12ff-471b-adc1-db93d02fb80f".getBytes(), publicKye)));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}


//	String s="0nS/gr4PyWJOVXAex4zBNbqrIRiagZ2uSUNIVEkbWWsU9aXOiDoCwRxUm6WeR7KFYKayACjXpPbCejCo+saFtk9GGPxfToefpoh4OVc3EgiOcJu4lF3d1WW+fFJnDET2NKJP1IvGDlyuyGF44/ouRvK34uLBs69t4kCuPKj5sgAFylskJvRNmC5wvAoeWaPhPWo2a2BZWOkqyHV3ohVH5ATjcGP/1GHDNRUgs/dZhcwNq8BtNeslVP8OcjvjAnEqiJj6nz7TI/l6/khxYN5ZsnRYg8fWWXL8n39GwaUR+LoV/MFUASvMuiTJMq8eiKc8JJKpDp45OkAwA6TKMw==";
//		byte[] bytearray=null;
//	try {
//		bytearray = RSAUtils.decryptByPrivateKey(new BASE64Decoder().decodeBuffer(s), privateKey);
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//	System.out.println(new String(bytearray,"UTF-8"));

//         nvps.add(new BasicNameValuePair("realName", "赵文宇"));
//         nvps.add(new BasicNameValuePair("idNo", "123456123456789"));
//         nvps.add(new BasicNameValuePair("phoneNum", "18601991067"));
//         nvps.add(new BasicNameValuePair("email", "test@163.com"));
//         nvps.add(new BasicNameValuePair("address", "北京印象"));
//         nvps.add(new BasicNameValuePair("headIcon", "hjksadhgjksdgjksdhgasdghasdhghlk/dstgasettee"));
//		File fi=new File("C:\\Users\\Zhaowy\\Desktop\\451af7d5025a48e17096a5b9fbfc2e6c.png");
//		Map<String, String[]> param = new HashMap<String, String[]>();
//		param.put("userId", new String[]{"54d0830b1ec8ef9a8020dc0b"});
////			param.put("nickName",new String[]{"wulalawulala"});
////
//		param.put("method", new String[]{"uploadHeadIcon"});
//		param.put("storeChannel",new String[]{"localStore"});
////			param.put("nickName",new String[]{"哦哦哦"});
//		System.out.println(JSON.toJSONString(param));
//		String sign=new BASE64Encoder().encode(RSAUtils.encryptByPublicKey(JSON.toJSONString(param).getBytes(), publicKye));
//		HttpEntity httpEntity = MultipartEntityBuilder.create().addBinaryBody("filedatas", fi).addTextBody("sign", sign).build();
        hp.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

//		hp.setEntity(httpEntity);
        HttpResponse rs = http.execute(hp);
        DigestUtils.md5Hex("auto-china" + "6408ef76522f0695466ad43da90bbb19" + 0);
        System.out.println(rs.getStatusLine());

//	 List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//     nvps.add(new BasicNameValuePair("opt", "uploadhi"));
//     nvps.add(new BasicNameValuePair("uid", "admin@bev3.enlink-mob.com"));
//     nvps.add(new BasicNameValuePair("headicon", ImageUtils.getImageBinary()));
//     hp.setEntity(new UrlEncodedFormEntity(nvps));
//	HttpResponse rs=http.execute(hp);
        System.out.println(EntityUtils.toString(rs.getEntity()));


//	System.out.println(StringUtils.hash("admin","md5"));
//	StringUtils.
    }
}
