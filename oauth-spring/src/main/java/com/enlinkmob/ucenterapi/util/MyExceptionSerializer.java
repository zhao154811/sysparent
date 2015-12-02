/**
 * @Title: MyExceptionSerializer.java
 * @Package com.enlinkmob.uenterapi.util
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-6-10 上午11:34:31
 * @version V1.0
 */
package com.enlinkmob.ucenterapi.util;

import com.alibaba.fastjson.serializer.ExceptionSerializer;
import com.alibaba.fastjson.serializer.JSONSerializer;

import java.lang.reflect.Type;

/**
 * @author Zhaowy
 * @ClassName: MyExceptionSerializer
 * @Description: (这里用一句话描述这个类的作用)
 * @date 2014-6-10 上午11:34:31
 */
public class MyExceptionSerializer extends ExceptionSerializer {

    public MyExceptionSerializer(Class<?> clazz) {
        super(clazz);
    }

    @Override
    protected boolean isWriteClassName(JSONSerializer serializer, Object obj,
                                       Type fieldType, Object fieldName) {
        return false;
    }


}
