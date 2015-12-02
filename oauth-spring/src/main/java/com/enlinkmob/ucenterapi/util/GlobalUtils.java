package com.enlinkmob.ucenterapi.util;

import java.util.ResourceBundle;

/**
 * Created by zhaowy on 15/6/2.
 */
public class GlobalUtils {
    public static String cliCheckSign;


    static {

        try {
            ResourceBundle rb = ResourceBundle.getBundle("config");
//			PropertyResourceBundle props = new PropertyResourceBundle(fis);
            cliCheckSign = rb.getString("golbal.cliCheckSign");
//
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
