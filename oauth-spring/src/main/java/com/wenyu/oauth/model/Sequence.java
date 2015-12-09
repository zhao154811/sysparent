package com.wenyu.oauth.model;


import lombok.Data;

/**
 * Created by zhaowy on 15/5/19.
 */
@Data
public class Sequence {

    private Long _id;

    private Long seq;
    private String className;


    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}