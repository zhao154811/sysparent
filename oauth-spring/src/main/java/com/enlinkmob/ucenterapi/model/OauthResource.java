/**
 * @Title: Resource.java
 * @Package com.enlinkmob.ucenterapi.mongooauth.model
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-4-21 上午11:20:58
 * @version V1.0
 */
package com.enlinkmob.ucenterapi.model;

import com.enlinkmob.ucenterapi.Enum.OauthResourceEnum;

import java.util.List;

/**
 * @author Zhaowy
 * @ClassName: Resource
 * @Description: url资源控制
 * @date 2014-4-21 上午11:20:58
 */
public class OauthResource extends BaseLongEntity {
    private static final long serialVersionUID = 3442274789908507720L;


    //	@Id
//	private ObjectId _id;
    private String resource_name;
    private OauthResourceEnum resource_type;
    private String resource_string;
    private String resource_desc;
    private List<String> authories;
    private String scope;

    //	public ObjectId get_id() {
//		return _id;
//	}
//	public void set_id(ObjectId _id) {
//		this._id = _id;
//	}
    public String getResource_name() {
        return resource_name;
    }

    public void setResource_name(String resource_name) {
        this.resource_name = resource_name;
    }

    public OauthResourceEnum getResource_type() {
        return resource_type;
    }

    public void setResource_type(OauthResourceEnum resource_type) {
        this.resource_type = resource_type;
    }

    public String getResource_string() {
        return resource_string;
    }

    public void setResource_string(String resource_string) {
        this.resource_string = resource_string;
    }

    public String getResource_desc() {
        return resource_desc;
    }

    public void setResource_desc(String resource_desc) {
        this.resource_desc = resource_desc;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<String> getAuthories() {
        return authories;
    }

    public void setAuthories(List<String> authories) {
        this.authories = authories;
    }


}
