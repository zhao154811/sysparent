/**
 * @Title: ResourceDao.java
 * @Package com.enlinkmob.ucenterapi.mongooauth.dao
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-4-21 上午11:46:06
 * @version V1.0
 */
package com.enlinkmob.ucenterapi.dao;

import com.enlinkmob.ucenterapi.model.OauthResource;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author Zhaowy
 * @ClassName: OauthResourceMapper
 * @Description: resource持久层
 * @date 2014-4-21 上午11:46:06
 */
@Repository(value = "resourceMapper")
public interface OauthResourceMapper {
    public void addResource(OauthResource resource);

    public Collection<OauthResource> getResources();

}
