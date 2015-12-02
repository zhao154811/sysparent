package com.enlinkmob.ucenterapi.dao;

import com.enlinkmob.ucenterapi.Enum.StatusEnum;
import com.enlinkmob.ucenterapi.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;

@Repository(value = "userMapper")
public interface UserMapper {
    void insert(User user);

    int update(User user);

    User selectById(Number id);

    int delete(Number id);

    void insertBatch(Collection<User> collection);

    int selectCountByConditions(User user);

    Collection<User> selectByConditions(User user);

    int selectCountByMap(Map<String, Object> map);

    Collection<User> selectByMap(Map<String, Object> map);

    User selectByName(@Param("name") String name);

    int updateUserByName(User user);

    @Update("update user set status=#{status} where id = #{id}")
    int updateUserStatus(@Param("id") Number id, @Param("status") StatusEnum status);
}
