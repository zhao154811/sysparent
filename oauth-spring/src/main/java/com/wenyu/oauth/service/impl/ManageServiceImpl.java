package com.wenyu.oauth.service.impl;

import com.wenyu.oauth.dao.ClientMapper;
import com.wenyu.oauth.model.Client;
import com.wenyu.oauth.service.ManageService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zhaowy on 15/5/19.
 */
@Service("manageService")
public class ManageServiceImpl implements ManageService {

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private Client suclient;


    public Client addClient(Client client) {
        //生成随机key
        String appkey = DigestUtils.md5Hex(UUID.randomUUID().toString());
        client.setClientKey(appkey);
//        return this.clientMapper.saveObject(client);
        return null;
    }

    @Override
    public List<Client> getClients(boolean status) {
        return null;
//        return this.clientMapper.getAllObjects(status);
    }

    public Client getClientByClientId(String clientId) {
//		if(clientId.equals("enlink_su_admin")){
//			return suclient;
//		}else{
        return this.clientMapper.getClientByQuery(clientId);
//		}
    }

    @Override
    public Client getClientByConditon(Map<String, Object> condition) {
        return null;
//        return this.clientMapper.getObjectByCondition(new Query(criteria));
    }

    @Override
    public void updateClient(Client client) {

//        clientMapper.updateObj(client);
    }

    @Override
    public boolean updateClients(List<Integer> ids, Client client) {
        if (client != null && client.getClientType() != 0) {
            return false;
//            return this.clientMapper.updateMulti(new Query(Criteria.where("_id").in(ids)), Update.update("clientType", client.getClientType()));
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteClients(List<Integer> ids) {
        return false;
//        return this.clientMapper.updateMulti(new Query(Criteria.where("_id").in(ids)), Update.update("status", 0));
    }
}
