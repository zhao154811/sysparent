/**
 * @Title: ResourceServiceImpl.java
 * @Package com.enlinkmob.ucenterapi.mongooauth.provider
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-4-21 上午11:53:46
 * @version V1.0
 */
package com.enlinkmob.ucenterapi.service;

import com.enlinkmob.ucenterapi.model.OauthResource;

import java.util.List;

/**
 * @author Zhaowy
 * @ClassName: ResourceServiceImpl
 * @Description: (这里用一句话描述这个类的作用)
 * @date 2014-4-21 上午11:53:46
 */
public interface ResourceService {

    public void addResource(OauthResource resource);

    public List<OauthResource> getResources();
}
