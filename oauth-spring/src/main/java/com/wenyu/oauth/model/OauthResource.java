/**
 * @Title: Resource.java
 * @Package com.enlinkmob.ucenterapi.mongooauth.model
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-4-21 上午11:20:58
 * @version V1.0
 */
package com.wenyu.oauth.model;


import com.wenyu.Enum.OauthResourceEnum;
import com.wenyu.model.BaseLongEntity;

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
    private String resource_url;
    private OauthResourceEnum resource_type;
    private String resource_cn_name;
    private String resource_desc;
    private String authories;
    private String scope;

    public String getResource_url() {
        return resource_url;
    }

    public void setResource_url(String resource_url) {
        this.resource_url = resource_url;
    }

    public OauthResourceEnum getResource_type() {
        return resource_type;
    }

    public void setResource_type(OauthResourceEnum resource_type) {
        this.resource_type = resource_type;
    }

    public String getResource_cn_name() {
        return resource_cn_name;
    }

    public void setResource_cn_name(String resource_cn_name) {
        this.resource_cn_name = resource_cn_name;
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

    public String getAuthories() {
        return authories;
    }

    public void setAuthories(String authories) {
        this.authories = authories;
    }


}
