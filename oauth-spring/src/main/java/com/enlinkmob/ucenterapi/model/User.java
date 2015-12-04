package com.enlinkmob.ucenterapi.model;


import com.enlinkmob.ucenterapi.Enum.AccountStatus;
import com.enlinkmob.ucenterapi.Enum.SexEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
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
    private SexEnum sex;//m f
    private AccountStatus accountStatus;//当前账号状态  1正常 -1已被绑定

}
