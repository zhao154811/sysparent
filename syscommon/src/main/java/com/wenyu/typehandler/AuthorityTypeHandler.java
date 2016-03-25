package com.wenyu.typehandler;


import com.wenyu.Enum.AuthorityType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 */
public class AuthorityTypeHandler extends BaseTypeHandler<AuthorityType> {
    private Class<AuthorityType> type;

    private final AuthorityType[] enums;

    public AuthorityTypeHandler(Class<AuthorityType> type) {
        if (type == null)
            throw new IllegalArgumentException("Type argument cannot be null");
        this.type = type;
        this.enums = type.getEnumConstants();
        if (this.enums == null)
            throw new IllegalArgumentException(type.getSimpleName()
                    + " does not represent an enum type.");
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, AuthorityType parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getEnString());
    }

    @Override
    public AuthorityType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String status = rs.getString(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return locateStatusEnum(status);
        }
    }

    @Override
    public AuthorityType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String status = rs.getString(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return locateStatusEnum(status);
        }
    }

    @Override
    public AuthorityType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        // 根据数据库存储类型决定获取类型，本例子中数据库中存放INT类型
        String status = cs.getString(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return locateStatusEnum(status);
        }
    }


    /**
     * 枚举类型转换，由于构造函数获取了枚举的子类enums，让遍历更加高效快捷
     *
     * @param status 数据库中存的Status字段
     * @return status 对应的枚举类
     */
    private AuthorityType locateStatusEnum(String status) {
        for (AuthorityType stu : enums) {
            if (stu.getEnString().equals(status)) {
                return stu;
            }
        }
        throw new IllegalArgumentException("未知的枚举类型：" + status + ",请核对" + type.getSimpleName());
    }
}
