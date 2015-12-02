/**
 * @Title: UserInfoMapper.java
 * @Package com.enlinkmob.ucenterapi.dao
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-5-4 下午2:09:46
 * @version V1.0
 */
package com.enlinkmob.ucenterapi.dao;

import com.enlinkmob.ucenterapi.model.UserInfo;
import org.springframework.stereotype.Repository;

/**
 * @author Zhaowy
 * @ClassName: UserInfoMapper
 * @date 2014-5-4 下午2:09:46
 */
@Repository(value = "userInfoMapper")
public interface UserInfoMapper {

    /**
     * @param @param userName 用户名
     * @return UserInfo
     * @throws
     * @Title: getUserByName
     */
    public void addUserInfo(UserInfo userInfo);


    /**
     * @param @param objId 用户objid
     * @return UserInfo
     * @throws
     * @Title: getUserById
     */
    public com.enlinkmob.ucenterapi.model.UserInfo getUserById(String objId);
}
