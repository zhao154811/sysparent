package com.wenyu.oauth.dao;

import com.wenyu.oauth.model.Authority;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;

/**
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
