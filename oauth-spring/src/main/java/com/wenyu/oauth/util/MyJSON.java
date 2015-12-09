/**
 * @Title: MyJSON.java
 * @Package com.enlinkmob.uenterapi.util
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-6-10 上午11:32:45
 * @version V1.0
 */
package com.wenyu.oauth.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.*;

/**
 * @author Zhaowy
 * @ClassName: MyJSON
 * @Description: (这里用一句话描述这个类的作用)
 * @date 2014-6-10 上午11:32:45
 */
public class MyJSON extends JSON {

    public static final String toJSONString(Object object, SerializeFilter[] filters, SerializeConfig config, SerializerFeature... features) {
        SerializeWriter out = new SerializeWriter();

        try {
            JSONSerializer serializer = new JSONSerializer(out, config);
            for (SerializerFeature feature : features) {
                serializer.config(feature, true);
            }

            serializer.config(SerializerFeature.WriteDateUseDateFormat, true);
            for (SerializeFilter filter : filters) {
                if (filter instanceof PropertyPreFilter) {
                    serializer.getPropertyPreFilters().add((PropertyPreFilter) filter);
                }

                if (filter instanceof NameFilter) {
                    serializer.getNameFilters().add((NameFilter) filter);
                }

                if (filter instanceof ValueFilter) {
                    serializer.getValueFilters().add((ValueFilter) filter);
                }

                if (filter instanceof PropertyFilter) {
                    serializer.getPropertyFilters().add((PropertyFilter) filter);
                }

                if (filter instanceof BeforeFilter) {
                    serializer.getBeforeFilters().add((BeforeFilter) filter);
                }

                if (filter instanceof AfterFilter) {
                    serializer.getAfterFilters().add((AfterFilter) filter);
                }
            }


            serializer.write(object);

            return out.toString();
        } finally {
            out.close();
        }
    }


}
