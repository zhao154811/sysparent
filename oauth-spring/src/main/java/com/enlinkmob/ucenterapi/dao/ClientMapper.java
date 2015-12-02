package com.enlinkmob.ucenterapi.dao;


import com.enlinkmob.ucenterapi.model.Client;
import org.springframework.stereotype.Repository;

/**
 * Created by zhaowy on 15/5/19.
 */
@Repository(value = "clientMapper")
public interface ClientMapper {
    public Client getClientByQuery(String clientId);
}
