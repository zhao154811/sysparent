package com.enlinkmob.ucenterapi.dao;

import com.enlinkmob.ucenterapi.model.Authority;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;

/**
 * Created by zhaowenyu@ucredit.com on 2015/11/9.
 */
@Repository(value = "authorityMapper")
public interface AuthorityMapper {
    void insert(Authority authority);

    int update(Authority authority);

    Authority selectById(Number id);

    int delete(Number id);

    void insertBatch(Collection<Authority> collection);

    int selectCountByConditions(Authority authority);

    Collection<Authority> selectByConditions(Authority authority);

    int selectCountByMap(Map<String, Object> map);

    Collection<Authority> selectByMap();
}
