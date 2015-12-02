package com.enlinkmob.ucenterapi.model;

import com.enlinkmob.ucenterapi.Enum.StatusEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhaowy on 15/5/19.
 */
public class BaseLongEntity implements Serializable {
    private static final long serialVersionUID = 4960622808020244402L;
    protected Long id;
    protected StatusEnum status;
    protected Date createTime;
    protected Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
