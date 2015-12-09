package com.wenyu.oauth.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OauthApprovals implements Serializable {

    private static final long serialVersionUID = -1353201047519569044L;

    private Long _id;
    private String userId;
    private String clientId;
    private String scope;
    private String status;
    private Date expiresAt;
    private Date lastModifiedAt;



}
