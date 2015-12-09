/**
 * @Title: MyFastJsonMessageConventer.java
 * @Package com.enlinkmob.uenterapi.util
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-6-6 下午4:31:34
 * @version V1.0
 */
package com.wenyu.oauth.util;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Zhaowy
 * @ClassName: MyFastJsonMessageConventer
 * @Description: (这里用一句话描述这个类的作用)
 * @date 2014-6-6 下午4:31:34
 */
public class MyFastJsonHttpMessageConverter extends FastJsonHttpMessageConverter {

    private MySimplePropertyPreFilter[] myPropfilter;

    public MySimplePropertyPreFilter[] getMyPropfilter() {
        return myPropfilter;
    }

    public void addMyPropfilter(MySimplePropertyPreFilter myPropfilter) {
        if (this.myPropfilter == null && myPropfilter != null) {
            this.myPropfilter = new MySimplePropertyPreFilter[1];
        }
        this.myPropfilter = ArrayUtils.add(this.myPropfilter, myPropfilter);
//		this.myPropfilter = myPropfilter;
    }

    public void setMyPropfilter(MySimplePropertyPreFilter[] myPropfilters) {
////		if(myPropfilter==null&&myPropfilters!=null&&myPropfilters.size()!=0){
////			myPropfilter=new MySimplePropertyPreFilter[myPropfilters.size()];
////		}
//		myPropfilters.toArray(myPropfilter);
        this.myPropfilter = myPropfilters;
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {

        OutputStream out = outputMessage.getBody();
        String text = null;
//		JsonErrorMessage jem = null;
        SerializeConfig config = null;
//        if(obj instanceof InvalidGrantException){
////        	config=new SerializeConfig();
////             config.put(InvalidGrantException.class, new MyExceptionSerializer(InvalidGrantException.class));
//			jem=new JsonErrorMessage();
//			jem.setExp_code("03");
//			jem.setError(((InvalidGrantException) obj).getOAuth2ErrorCode());
//			jem.setError_description(((InvalidGrantException) obj).getMessage());
////        	myPropfilter=new MySimplePropertyPreFilter(InvalidGrantException.class, MySimplePropertyPreFilter.JsonFitler.in, "oAuth2ErrorCode","message");
//        }else  if(obj instanceof InvalidScopeException){
////			config=new SerializeConfig();
////			config.put(InvalidScopeException.class, new MyExceptionSerializer(InvalidScopeException.class));
//			jem=new JsonErrorMessage();
//			jem.setExp_code("04");
//			jem.setError(((InvalidScopeException) obj).getOAuth2ErrorCode());
//			jem.setError_description(((InvalidScopeException) obj).getMessage());
////			myPropfilter=new MySimplePropertyPreFilter(InvalidScopeException.class,MySimplePropertyPreFilter.JsonFitler.in,"oAuth2ErrorCode","message");
//		}else if(obj instanceof UnsupportedGrantTypeException){
//			jem=new JsonErrorMessage();
//			jem.setExp_code("05");
//			jem.setError(((UnsupportedGrantTypeException) obj).getOAuth2ErrorCode());
//			jem.setError_description(((UnsupportedGrantTypeException) obj).getMessage());
//		}else if(obj instanceof InvalidTokenException){
//			jem=new JsonErrorMessage();
//			jem.setExp_code("06");
//			jem.setError(((InvalidTokenException) obj).getOAuth2ErrorCode());
//			jem.setError_description(((InvalidTokenException) obj).getMessage());
//		}

        if (myPropfilter != null && config != null) {
            text = MyJSON.toJSONString(obj, myPropfilter, config, this.getFeatures());
        } else if (myPropfilter != null && config == null) {
            text = MyJSON.toJSONString(obj, myPropfilter, this.getFeatures());
        } else if (myPropfilter == null && config != null) {
            text = MyJSON.toJSONString(obj, config, this.getFeatures());
        } else if (obj instanceof DefaultWebResponseExceptionTranslator) {
            text = obj.toString();
        } else {
            text = MyJSON.toJSONString(obj, this.getFeatures());
        }
//		else if (jem != null) {
//			text = MyJSON.toJSONString(jem, this.getFeatures());
//		}
        byte[] bytes = text.getBytes(this.getCharset());
        myPropfilter = null;
        out.write(bytes);
    }


}
