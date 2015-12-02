/**
 * @Title: UserInfo.java
 * @Package com.enlinkmob.ucenterapi.model
 * @author A18ccms A18ccms_gmail_com
 * @date 2014-5-4 下午1:51:58
 * @version V1.0
 */
package com.enlinkmob.ucenterapi.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Zhaowy
 * @ClassName: UserInfo
 * @date 2014-5-4 下午1:51:58
 */
@Document(collection = "user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -8089479822996683718L;
    @Id
    private ObjectId _id;
    @Transient
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


    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getObjId() {
        return _id.toString();
    }


}
