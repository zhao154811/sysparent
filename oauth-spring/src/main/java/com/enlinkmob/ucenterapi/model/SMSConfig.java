package com.enlinkmob.ucenterapi.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by Zhaowy on 2014/6/23.
 */
@Document(collection = "sms_config")
public class SMSConfig extends BaseLongEntity {
    private static final long serialVersionUID = -5707483387013924165L;
    private String client_id;//client_id 用来确定哪个客户端使用哪个账号
    private String apiId;//321253400000035816
    private String apiSecret;//25a1d3561de7af59b10d45c04aecd24d
    private String grantType;//使用client_credentials
    private String tokenUrl;//获取tokenurl
    private String sendSmsUrl;//发送短信url
    private String accessToken;//access_token
    private String template_id;//模板id
    private Date tokenExpireIn; //利用token返回的expires_in字段计算得出过期时间，将该时间减少5分钟以保证token 一直处于可用状态
    private String[] params;//参数名array
    private String serviceSupport;//短信服务商名称


    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public String getSendSmsUrl() {
        return sendSmsUrl;
    }

    public void setSendSmsUrl(String sendSmsUrl) {
        this.sendSmsUrl = sendSmsUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public Date getTokenExpireIn() {
        return tokenExpireIn;
    }

    public void setTokenExpireIn(Date tokenExpireIn) {
        this.tokenExpireIn = tokenExpireIn;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public String getServiceSupport() {
        return serviceSupport;
    }

    public void setServiceSupport(String serviceSupport) {
        this.serviceSupport = serviceSupport;
    }
}
