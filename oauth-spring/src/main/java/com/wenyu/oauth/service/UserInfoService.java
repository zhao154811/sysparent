/**
 * @Title: UserInfoService.java
 * @Package com.enlinkmob.ucenterapi.provider
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-5-4 下午2:50:45
 * @version V1.0
 */
package com.wenyu.oauth.service;

import com.wenyu.oauth.model.UserInfo;

import java.util.Map;

/**
 * @author Zhaowy
 * @ClassName: UserInfoService
 * @Description: (这里用一句话描述这个类的作用)
 * @date 2014-5-4 下午2:50:45
 */
public interface UserInfoService {

    /**
     * @param @param userInfo 用户资料
     * @return ObjectId
     * @throws
     * @Title: addUserInfo
     * @Description: (这里用一句话描述这个方法的作用)
     */
    public void addUserInfo(UserInfo userInfo);

    /**
     * @param @param updateMap 更新字段和对应值
     * @param @param field 可以当做主键字段名
     * @param @param key 要更新的主键值
     * @return int
     * @throws
     * @Title: updateUserInfo
     * @Description: (这里用一句话描述这个方法的作用)
     */
    int updateUserInfo(Map<String, Object> updateMap, String field, Object key);


    /**
     * @param @param objId 用户objid
     * @return UserInfo
     * @throws
     * @Title: getUserById
     * @Description: (这里用一句话描述这个方法的作用)
     */
    public UserInfo getUserById(String objId);

}
