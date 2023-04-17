package com.example.filter.all;


import com.example.utils.JDBCTools;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 事务处理拦截器
 *
 * @version 1.0
 */
@WebFilter(filterName = "TransactionFilter", urlPatterns = "/*")
public class TransactionFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        Connection connection = JDBCTools.getConnection(); // 获得数据库连接
        try {
            connection.setAutoCommit(false); // 设置手动提交
            chain.doFilter(request, response); // 放行
            connection.commit(); // 正常运行到此处没有异常产生，则提交
        } catch (Exception e) { // 注：捕获类型一定要设置为最大的(什么异常都可以捕获)
            try {
                connection.rollback(); // 走到这里表示有异常产生，则回滚
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } finally {
            JDBCTools.freeConnection(); // 无论成功与否，释放链接
        }
    }
}
