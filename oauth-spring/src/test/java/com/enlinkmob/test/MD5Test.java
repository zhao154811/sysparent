/**
 * @Title: MD5Test.java
 * @Package com.enlinkmob.test
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-5-12 下午1:57:13
 * @version V1.0
 */
package com.enlinkmob.test;

import com.wenyu.oauth.util.StringUtils;

/**
 * @author Zhaowy
 * @ClassName: MD5Test
 * @Description: (这里用一句话描述这个类的作用)
 * @date 2014-5-12 下午1:57:13
 */
public class MD5Test {
    public static void main(String[] args) {
        System.out.println(StringUtils.hash("ucenter", "MD5"));
    }
}
