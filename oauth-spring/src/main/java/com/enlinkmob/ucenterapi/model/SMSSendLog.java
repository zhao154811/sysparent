package com.enlinkmob.ucenterapi.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Zhaowy on 2014/6/23.
 */
@Document(collection = "sms_send_log")
public class SMSSendLog extends BaseLongEntity {
    private static final long serialVersionUID = -5989152905011672856L;
    //	@Id
//	private ObjectId _id;
    private String phoneNum;
    private String jsonparams;
    private String expireIn;
    private int sendStatu;
    private String client_id;

//	public ObjectId get_id() {
//		return _id;
//	}
//
//	public void set_id(ObjectId _id) {
//		this._id = _id;
//	}

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(String expireIn) {
        this.expireIn = expireIn;
    }

    public int getSendStatu() {
        return sendStatu;
    }

    public void setSendStatu(int sendStatu) {
        this.sendStatu = sendStatu;
    }

    public String getJsonparams() {
        return jsonparams;
    }

    public void setJsonparams(String jsonparams) {
        this.jsonparams = jsonparams;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
