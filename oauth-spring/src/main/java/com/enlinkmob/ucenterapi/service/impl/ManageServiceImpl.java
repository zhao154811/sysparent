package com.enlinkmob.ucenterapi.service.impl;

import com.enlinkmob.ucenterapi.dao.ClientMapper;
import com.enlinkmob.ucenterapi.model.Client;
import com.enlinkmob.ucenterapi.service.ManageService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
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
        Criteria criteria = new Criteria();

        for (Map.Entry<String, Object> entry : condition.entrySet()) {
            criteria.andOperator(Criteria.where(entry.getKey()).is(entry.getValue()));
        }
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
