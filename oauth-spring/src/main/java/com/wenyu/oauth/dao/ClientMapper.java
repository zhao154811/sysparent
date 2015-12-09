package com.wenyu.oauth.dao;


import com.wenyu.oauth.model.Client;
import org.springframework.stereotype.Repository;

/**
 * Created by zhaowy on 15/5/19.
 */
@Repository(value = "clientMapper")
public interface ClientMapper {
    public Client getClientByQuery(String clientId);
}
