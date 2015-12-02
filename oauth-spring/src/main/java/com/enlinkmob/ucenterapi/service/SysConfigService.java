package com.enlinkmob.ucenterapi.service;

/**
 * Created by Zhaowy on 2014/8/1.
 */
public interface SysConfigService<SysConfig, ObjectId> {
    public SysConfig getSysConfigByName(String propName);

    public ObjectId addSysConfig(SysConfig sysConfig);

}
