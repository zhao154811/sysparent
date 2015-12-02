/**
 * @Title: Test.java
 * @Package com.enlinkmob.test
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-5-15 下午5:55:05
 * @version V1.0
 */
package com.enlinkmob.test;

import sun.misc.BASE64Decoder;

import java.io.IOException;

/**
 * @author Zhaowy
 * @ClassName: Test
 * @Description: (这里用一句话描述这个类的作用)
 * @date 2014-5-15 下午5:55:05
 */
public class Test {

    public static void main(String[] args) throws IOException {

        String sss = "eyJuaWNrTmFtZSI6IumXu+mlreWQpiJ9";
        String mm = new String(new BASE64Decoder().decodeBuffer(sss), "UTF-8");
//		ObjectId ss=new ObjectId("5339388436a2d527545085d7");
//		System.out.println(JSON.toJSONString(ss));
        System.out.println(mm);
        System.out.println(11);
    }
}
