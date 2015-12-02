package com.enlinkmob.test;


import com.enlinkmob.ucenterapi.util.RSA.RSAUtils;

import java.util.Map;

public class RSATester {

    static String publicKey;
    static String privateKey;

    static {
        try {
            Map<String, Object> keyMap = RSAUtils.genKeyPair();
            publicKey = RSAUtils.getPublicKey(keyMap);
            privateKey = RSAUtils.getPrivateKey(keyMap);


            System.err.println("公钥: \n\r" + publicKey);
            System.err.println("私钥： \n\r" + privateKey);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) throws Exception {
////        test();
//        testSign();
//    }

    static void test() throws Exception {
        System.err.println("公钥加密——私钥解密");
        String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";
        System.out.println("\r加密前文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKey);
        System.out.println("加密后文字：\r\n" + new String(encodedData));
        byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData, privateKey);
        String target = new String(decodedData);
        System.out.println("解密后文字: \r\n" + target);
    }

    static void testSign() throws Exception {
        privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJW8s4aVYictauwFcJ24UNBQCZfg1kF/iwubxzjWIkd9wqiV0/0ckTkbsq/LjB2n0SRT4IM7++N9/h0y3ZTxnbD1mTPLhOTU4uJs2p9JRO7yl4kdRXbf8aBeRA6ONTtiiAgb4QyqBzYdjsjGF3XDwbzrAqqDqnOq3If0GPLNUeUnAgMBAAECgYAFuu7z24uiDC7nUV356y/MiQOU+Fm6r/LWQuPg3PD642kronA2Y317PgAckiGOkPmD9mHFI+qDzjKHU4b/SC/Moo+JfTlyAi7kPi3vf4+IRWl3wm45vcwadbO2/DlYjOZccmlsVtO7PC4bGzsLOa0lXsiCPZjHz2NvegWokhbz4QJBAO9c1X3ILXlj9FSFzpJVMtSilkiYCZR00bcqCOVqCF7ZwU81/X2DSGfvBXALgiPP9XlasIN7M41nIL4FC7I58eUCQQCgJRcu0M0I7bz9keNX5y2GtfOoYQ76DIATfFnGKDYNLsBkXMtfIvPXyKpLNinas2hRnhKxE1fHYrzN2NgGUbobAkBFGh/oOCooqVqYLCj+xenuySFpw7rASRliL5hx4rKX73WHUKbl3UjB+JcxSaFAhYDwj+X1j1PKtsMVGI/P3DqhAkB3jLjNvhoR/EgFk47/5BhcZCBNJoejqwlBgqcSApq3JJuDULKRhuo5wBdApmKAXUYXB8Y0Ypg4xEDl+mbp8MDZAkEAyOwJ84XWQyYEKS+3uk2th3soyCpJjviq3BBqAH3ISO/a5EkJSbJ/xQMbRov6+MCneQm7V4fbRzeW3a+w7JjUiw==";
        System.err.println("私钥加密——公钥解密");
        String source = "ucenter-sys";
        System.out.println("原文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);
        System.out.println("加密后：\r\n" + new String(encodedData));
        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
        String target = new String(decodedData);
        System.out.println("解密后: \r\n" + target);
        System.err.println("私钥签名——公钥验证签名");
        String sign = RSAUtils.sign(encodedData, privateKey);
        System.err.println("签名:\r" + sign);
        boolean status = RSAUtils.verify(encodedData, publicKey, sign);
        System.err.println("验证结果:\r" + status);
    }

    public static void main(String[] args) throws Exception {
//		String str="SfiurOPIoyXH8m+6F1TNXwgkgV+GyfdwsMGEa0AEN+PmCs/T69gHmSKMIAvIdyb0JBX/4D4W4Ixk";
//		privateKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMZ+q0pG+iCm4xhGppb1aJvCYBCm" +
//				"nceMUicImijiqEZRJgGXYATCQI+p97Us/Hk0kvD1axadwGaEJ0HDSbchl5mAfUsOn8qjcdUI+WBa" +
//				"4ezDc0jgbhDuUfd9TpY3C2My0w9Tte6mXlrfBaRVZ/9uitVAAAMihUOdJ+yNGmdpOfylAgMBAAEC" +
//				"gYAxPcc8e/MdGomHwi8dj+/NhCDUwKCvmi3fJREpR2E7YHIvPuBBDfd6LE4ZEfnoD5TMVfWk/Kl4" +
//				"RRyAAfRNi/XRXOilg2bRpRcpuNR5juolvme1YGkYUq4/sG7gmkpqLJcA8Z+GC8jYu1LfxZT0ZM7T" +
//				"oferf8DaYzOqZen4H7P62QJBAOHtUSTNKpHW5TWOE3iYTyp+khBKj3O0ohQ59+cDZdNyJRu+HsVK" +
//				"L0qsnVWOI2us5e+G3ZIjyILQnSP+mSqCpAMCQQDg6pKWNKzrRDndNcn7A3jeb7HNzQRFAYEkczxy" +
//				"sCaUfMLoEKIRqD0GAmAFSag11WS8eI6JWjxGbeV6PA2UuEA3AkEAqPzVgO1lNgjDETfgIIfsTyC1" +
//				"KDJc8nLd+LsTTlwgQfRTBVlDstY2YWiTlnbmz5dXtb3u5WyWgiKAJ3g8IKvu+QJAHhH+iyp7u/Ch" +
//				"M9wPJwotzL9uLC+u1HMBCxiFCmhWoB+WB2TB5k5x6i3DHLHeVWQ0VaZgmCiCSoBC3/bLcgi4PQJB" +
//				"AIEhF3ef/XASmKWMzFJ76yJ8Gy00kGMj8s1jr8GHV1aEECX8znOYxSkHFDBhPN4+/UzPNR7PjMga" +
//				"V3claUK9BZQ=";
        privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJavb+2Rr6n6+aSu0RwEeuSO2REO" +
                "phLoM8TBG1pCLSqfZu3ZK6qkrGWefgrRdnDm2oLcnZ9JmzOWhHok9kdO/kmDFPOWwE8CSiW5pmvy" +
                "bnR7SfYRJ0gM7KfosoChxEOmStIP02wm0MKp3BD5EdzpPjLOt7CmYg+2c5/7VFUdvtX7AgMBAAEC" +
                "gYBdCoJf/D6tBCy1BMRVOmbvTUy2fYcJ0Zp1eI79EjN1R+t0HU4bFYblUBGfeGpbmA+AEdy5h+du" +
                "2Rd/m+b9bMcRhP85ebtF9KSVmsilhwBYoG9MztAtmXty32SVBWdVgmYWXJQ5Zsv5EHbyEDKMdqL8" +
                "72cgzcgNSJf5fI8MKb+DcQJBANsowbB/bDYUmfY1TBTKPan/WOsmon26/G/2fOTt5LSo6p17LE99" +
                "9zy/pem146jSkpfpbwLyAD6KdY8pjgob4QUCQQCwA/3QtsjpN9jfU53c0eE0g3q7kAk2noSSfGUM" +
                "gi/3as9Tm5yncy+oRkUIHjzIoQw9TwiBli0LzI7EU7eFiYr/AkBMnnMYwXOTdKBe80MmAY6NYDg1" +
                "/cvRKQ4YFpSTdJgAkQ11ARr8r41AL8BRdMWiv/uAIJwu62wqcT2oT17ZKeYBAkBOzLqo4ev4XQQ+" +
                "lxSpc1y7QdGEfuthBH6dhgtHGlGXFr5S0+vCG5NOZocpJ0BXIaJ1IBjSywTHr4CK7F/Q7M7JAkAr" +
                "B3FVGn87FcKe8qmKjpMl/dCqF4+KNGGAd4xMaMILRT4VLJgvgiSWongq959CBLAglH6h4k7NBcwU" +
                "62Y46tDX";
        publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWr2/tka+p+vmkrtEcBHrkjtkRDqYS6DPEwRtaQi0qn2bt2SuqpKxlnn4K0XZw5tqC3J2fSZszloR6JPZHTv5JgxTzlsBPAkoluaZr8m50e0n2ESdIDOyn6LKAocRDpkrSD9NsJtDCqdwQ+RHc6T4yzrewpmIPtnOf+1RVHb7V+wIDAQAB";
//		publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCCRStxLgGp9tixpcUkyM9NjjzTJTH4P+jqApUlAVWVnNz0I0FsF9M7KMxXFX2Gfx34iaTAlqt0aw16I2ncdpZMvurY9leeFYW8D4njiuVOurvNs4kF4p1bFV0N1AGw6R35tu/YA4GfpnZm462e9DaKiYa87cjANWGYB+IDy7FoAwIDAQAB";
//        privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALgUFkwFnDfPoO+5pNtfeGknA7yj" +
//                "Phbwuf15VZqjZO1KhER9G8BH4WXSh/DIbmU7ZmrOvzBSsEneOMGousRxrhOH6Qt5BdV16JvjEuOt" +
//                "u78m9/gD1YddV++PMlwB0SSA6l9n7RyXXBEuQdTqiL+lhBB0FxCrryjKNx+OYsnzeWwLAgMBAAEC" +
//                "gYBylXZnrhpXF3SrpNi9AYm3kuSdmNP+fayY3t1tnAYoO3loFKSgpV3l/w5F0M3/T+Fp3PZgAyjf" +
//                "YmfcDMbtn4Hmb5ML/17VyqSuR+JosrTh0Tf47LcmforpVa44PvpHmnh8AhMG2wRC52oU3uSF1OUh" +
//                "p3GTC3qb8RZEP8L9K71PSQJBAPmBu7xzEt+OLcZyJkJeq5LJ5fjqX8y1H7p1gUyMisCb2CAatjgK" +
//                "sRr4gRfY1ftgaFusKAbhBBDAi3/FZj6xq58CQQC83nTj0eakXodCdQw62h7kfGw2zrHhXWoPo+ka" +
//                "Mj3jksGsvzkbxSEsicqlQ6QM4YsJh/3al8P5YCeYUWpD+qgVAkEAo1Q2pf0aTLeQ5YgR3X6FiBic" +
//                "b55VSfcDJxcGpiqWJdGe4B08TWFKYGJud0LdiKmqhuAib6Za9/BjISgCb2X09wJASKMxx3CxyKOG" +
//                "V7vWcUf0O6HSqJ2D8g3QPaWnf+i0vrJxhDOjDhgZsxxncHn8POv9YjQIIKrftygQ8lbVjMF5JQJA" +
//                "Qa1giX5cVITpKYSHiEnz74q+cIOGALuRw2o4zCNwsv/+OklRFraSOM3Vik6zDEQjP7wO36TW+QkN" +
//                "46LZ/iez/w==";
//		privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIJFK3EuAan22LGlxSTIz02OPNMl" +
//				"Mfg/6OoClSUBVZWc3PQjQWwX0zsozFcVfYZ/HfiJpMCWq3RrDXojadx2lky+6tj2V54VhbwPieOK" +
//				"5U66u82ziQXinVsVXQ3UAbDpHfm279gDgZ+mdmbjrZ70NoqJhrztyMA1YZgH4gPLsWgDAgMBAAEC" +
//				"gYBAJnHTJVW6bg2dxcVEbQLw7Td0vt9RoZ9ABY+Ojo6nw3xnWSkxYrYpbtjZlYKgBd8rchTIpim+" +
//				"ha04VVIfN+KhKo9kWmVhFsLQF0/gtFG0r8VGPrCOUB7B93/e1Hu7bpbJnElIyCgHpowYwXcnbHxm" +
//				"lhgdXNoLlTFZVjkGqPDgwQJBALj9Kr8ps56J/GOglSbI0fT/H7CAAtpvgDQQeOq3fJ8zfpSa85BH" +
//				"csZe6X2OmeYMg5j1dTnN86xhTjKmxPgp0uMCQQC0Rs0IyQlgPgAtHQRVUnmKSNgFkTOzCHABe10r" +
//				"Az4I9tydKBcOC0HR7z8qU/gKjm0Oe5DQyWKSwCCm1RX9rYBhAkEArZi+zafXrJZ26BNhBIV+Kjhk" +
//				"R6DJjK0IFVPD9Rf9dfS/+7qxiJrUBX46BR6WI8FomVj3CijQW9rwpC3GN9Af2wJADheWwYANlpNi" +
//				"KiShiRXoYFHvaQf/tZgMQWFmWA1p1QOPHU3b94ITxBhipKlps1GM3tvz5w5HAVe5dbEkhe8BIQJB" +
//				"AJyLQhyb6BUfhvoMp5JufYALaeMoOx5dynUBQ1zWSrc2/8eqceS3KMzS8SLbfddYzINizGTX9gId" +
//				"5eSezn8liPA=";
//        String estr="LeXoG73Lsh4lK75vlP1OYkaRZots/1aoQ+wUE9zC1HR3/GXBmz+Yqln0hrzNRPPpP1WfebbXC5WP" +
//                "IrMCJf4BZCAtBxY6Wg+9lUjXIS4JjQnHXmJFoZ1q655q0sx4SYLjzj8g0H0Hnn/rIIrqGuYzcejq" +
//                "f3qLCHnjVsz0dPeoOGI=";

//		byte[] bytearray = RSAUtils.decryptByPrivateKey(new BASE64Decoder().decodeBuffer(estr), privateKey);
//		System.out.println( "***a*dasd"+new String(bytearray,"UTF-8"));
//		String publicKye="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmmqcfgzyeOHkF55E9tSZjBXqXjVSGFVHQSCxMokEaeR+FjdrwOREliom5iBNRO6xk0ID9IabecbKXZDPbfDNSPBpusuAwJ2T9w2Sa5s1b7Sa+JgzM2YCheobfKvgbB6txTaPN6NH5b9pmZXvZEwYuGaLlMgQ8APLbrA2Zcl5aQQIDAQAB";
//String ed=new BASE64Encoder().encode(RSAUtils.encryptByPublicKey("qq".getBytes(), publicKye));
//		System.out.println("加密:"+ed);

//		bytearray = RSAUtils.decryptByPrivateKey(new BASE64Decoder().decodeBuffer(value), rsaKey.getPrivateKey());
//		byte[] keyBytes = Base64Utils.decode(privateKey);
//		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
//		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
//		StringWriter stringWriter = new StringWriter();
//		PEMWriter pemWriter = new PEMWriter(stringWriter);
//		pemWriter.writeObject(privateK);
//		pemWriter.close();
//		String privateKeyString = stringWriter.toString();
//		System.err.print("pem syao******\n\r" + privateKeyString);
//        byte[] ss = RSAUtils.decryptByPrivateKey(new BASE64Decoder().decodeBuffer("T3rXOm8E8CvcjKUopcSpFNaJNo08Viw7pwq/N/pFLeZFBRxxQmbP259c06EDMY/QjMs+KhDn9GWvPhqYd11jQiTIGYnAWbq4bZrI1C/VBupYHO1uwRrS5onjJ0i61L58znnupbwLy30B1G4f+/+YynzgKuxo8EKIxpa8bJ4DNB8="), privateKey);
//        System.out.println("解密:" + new String(ss, "UTF-8"));


        System.out.println("私钥签名——公钥验证签名");
        String sign = RSAUtils.sign("test".getBytes("UTF-8"), privateKey);
        System.out.println("签名:");

        System.out.println(sign);
        boolean status = RSAUtils.verify("test".getBytes("UTF-8"), publicKey, sign);
        System.out.println("验证结果:\r" + status);
//		byte[] keyBytes = Base64Utils.decode(publicKye);
//		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
//		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//		Key publicK = keyFactory.generatePublic(x509KeySpec);
//		System.out.println(new BigInteger(publicK.getEncoded()).toString());


    }

}
