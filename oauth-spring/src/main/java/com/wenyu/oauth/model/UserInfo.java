/**
 * @Title: UserInfo.java
 * @Package com.enlinkmob.ucenterapi.model
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-5-4 下午1:51:58
 * @version V1.0
 */
package com.wenyu.oauth.model;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Zhaowy
 * @ClassName: UserInfo
 * @date 2014-5-4 下午1:51:58
 */
@Data
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -8089479822996683718L;
    private Long _id;
    private String objId;//用于显示objid;
    private String userName;
    private String realName;//真实姓名
    private String idNo;//身份证号
    private String phoneNum;//电话号
    private String email;//电子邮箱
    private String address;//地址
    private String headIcon;//头像
    private Date modifyTime;//修改时间
    private Date createTime;//创建时间
    private String sex;//性别  male female secret




}
