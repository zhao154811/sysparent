package com.wenyu.oauth.service;


import com.wenyu.oauth.model.Client;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaowy on 15/5/19.
 */
public interface ManageService {
    public Client addClient(Client client);

    public List<Client> getClients(boolean status);

    public Client getClientByClientId(String clientId);

    public Client getClientByConditon(Map<String, Object> Condition);


    public void updateClient(Client client);

    public boolean updateClients(List<Integer> ids, Client client);

    /**
     * 删除client功能，逻辑删除
     *
     * @param ids 为id属性
     * @return boolean 删除是否成功
     */
    public boolean deleteClients(List<Integer> ids);


}
