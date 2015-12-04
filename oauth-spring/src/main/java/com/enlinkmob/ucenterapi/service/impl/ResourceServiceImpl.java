/**
 * @Title: ResourceServiceImpl.java
 * @Package com.enlinkmob.ucenterapi.mongooauth.provider
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-4-21 上午11:53:46
 * @version V1.0
 */
package com.enlinkmob.ucenterapi.service.impl;

import com.enlinkmob.ucenterapi.dao.OauthResourceMapper;
import com.enlinkmob.ucenterapi.model.OauthResource;
import com.enlinkmob.ucenterapi.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zhaowy
 * @ClassName: ResourceServiceImpl
 * @Description: (这里用一句话描述这个类的作用)
 * @date 2014-4-21 上午11:53:46
 */
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private OauthResourceMapper oauthResourceMapper;

    public void addResource(OauthResource resource) {
        this.oauthResourceMapper.addResource(resource);
    }

    public List<OauthResource> getResources() {
        return (List<OauthResource>) this.oauthResourceMapper.getResources();
    }
}
