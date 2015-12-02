package com.enlinkmob.ucenterapi.model;

/**
 * Created by zhaowy on 15/5/13.
 */
public class Client extends BaseLongEntity {
    private static final long serialVersionUID = 7398504721768796821L;
    private String clientId;
    private String clientKey;
    private int clientType;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    public int getClientType() {
        return clientType;
    }

    public void setClientType(int clientType) {
        this.clientType = clientType;
    }
}
