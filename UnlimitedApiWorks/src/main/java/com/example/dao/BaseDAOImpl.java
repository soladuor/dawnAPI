package com.example.dao;


import com.example.utils.JDBCTools;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;


/**
 * @version 2.0
 */
public abstract class BaseDAOImpl {
    // 可以new的时候参数直接带上DataSource类型
    // 也可以调用函数时带上连接
    private final QueryRunner queryRunner = new QueryRunner();

    // 通用的增删改操作, 只让继承的子类使用
    protected int update(String sql, Object... objs) throws SQLException {
        return queryRunner.update(JDBCTools.getConnection(), sql, objs); // 返回受影响的行数
    }

    // 查询多个对象
    protected <T> List<T> getList(Class<T> tClass, String sql, Object... objs) throws SQLException {
        return queryRunner.query(JDBCTools.getConnection(), sql, new BeanListHandler<>(tClass), objs);
    }

    // 查询单个对象
    protected <T> T getBean(Class<T> tClass, String sql, Object... objs) throws SQLException {
        return queryRunner.query(JDBCTools.getConnection(), sql, new BeanHandler<>(tClass), objs);
    }

    // 查询单个值（例如查询总人数）
    protected Object getValue(String sql, Object... objs) throws SQLException {
        return queryRunner.query(JDBCTools.getConnection(), sql, new ScalarHandler<>(), objs);
    }

    // 批处理
    protected void batch(String sql, Object[][] objs) throws SQLException {
        queryRunner.batch(JDBCTools.getConnection(), sql, objs);
    }
}
