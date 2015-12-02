package com.enlinkmob.ucenterapi.model;


import com.enlinkmob.ucenterapi.Enum.AccountStatus;

import java.util.Date;
import java.util.List;

public class User extends BaseLongEntity {

    private static final long serialVersionUID = -3814199231759885239L;
    //	@Id
//	@JsonIgnore
//	private ObjectId _id;
    private String userName;
    private String password;
    private String salt;
    private List<OauthAuthorities> authorities;
    private String clientId;//来源client
    private String realName;//真实姓名
    private String idNo;//身份证号
    private String phoneNum;//电话号
    private String email;//电子邮箱
    private String address;//地址
    private Date birthday;//生日
    private String headIcon;//头像
    private String sex;//m f
    private AccountStatus accountStatus;//当前账号状态  1正常 -1已被绑定


    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public List<OauthAuthorities> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<OauthAuthorities> authorities) {
        this.authorities = authorities;
    }

    //	public ObjectId get_id() {
//		return _id;
//	}
//	public void set_id(ObjectId _id) {
//		this._id = _id;
//	}
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }


    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }


}
