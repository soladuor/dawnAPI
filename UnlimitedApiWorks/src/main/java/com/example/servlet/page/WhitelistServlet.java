package com.example.servlet.page;

import com.example.bean.ErrorLog;
import com.example.bean.Identifier;
import com.example.bean.Whitelist;
import com.example.dao.impl.ErrorLogsDAOImpl;
import com.example.dao.impl.WhitelistDAOImpl;
import com.example.servlet.BaseServlet;
import com.example.servlet.api.zydsoft.utils.DialecticalCloud;
import com.example.singleton.IdentifierSingleton;
import com.example.singleton.WhitelistSingleton;
import com.example.utils.BaseUtil;
import com.example.utils.IPUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

// WhitelistServlet通过MD5加密9901次的结果
@WebServlet(name = "WhitelistServlet", value = "/page/white/2214086D382FE0823550CAD6B19EA205")
public class WhitelistServlet extends BaseServlet {
    WhitelistDAOImpl whitelistDAO = new WhitelistDAOImpl();

    public void getAllList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 白名单
        List<Whitelist> whitelist = WhitelistSingleton.getInstance().getWhiteList();
        request.setAttribute("whitelist", whitelist);
        // 标识表
        List<Identifier> identifierList = IdentifierSingleton.getInstance().getIdentifierList();
        request.setAttribute("identifierList", identifierList);
        // 错误表
        ErrorLogsDAOImpl errorLogsDAO = new ErrorLogsDAOImpl();
        List<ErrorLog> errorList = errorLogsDAO.getErrorList();
        request.setAttribute("errorList", errorList);
        // 响应
        request.setAttribute("result", "刷新成功");
        request.getRequestDispatcher("/WEB-INF/admin/index.jsp").forward(request, response);
    }

    public void addIP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ip = request.getParameter("ip");
        String description = request.getParameter("description");
        if (BaseUtil.stringsIsEmpty(ip, description)) {
            response.sendRedirect(request.getContextPath() + "/page/white/2214086D382FE0823550CAD6B19EA205?flag=getAllList");
            return;
        }
        // 正则表达式验证IP地址
        String regex = "^(?:(?:1\\d{2}|2[0-4]\\d|25[0-5]|\\d\\d?)\\.){3}(?:1\\d{2}|2[0-4]\\d|25[0-5]|\\d\\d?)$";
        if (Pattern.matches(regex, ip)) {
            String city = IPUtil.getCityByIP(ip);
            Whitelist whitelist = new Whitelist(ip, description, city);
            whitelistDAO.addWhitelist(whitelist);
            WhitelistSingleton.getInstance().updateWhitelist(); // 刷新ip白名单
        }
        response.sendRedirect(request.getContextPath() + "/page/white/2214086D382FE0823550CAD6B19EA205?flag=getAllList");
    }

    public void deleteIP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (BaseUtil.isEmpty(id)) {
            response.getWriter().write("删除失败，参数为空");
            return;
        }
        whitelistDAO.deleteWhitelistById(id);
        WhitelistSingleton.getInstance().updateWhitelist(); // 刷新ip白名单
        response.sendRedirect(request.getContextPath() + "/page/white/2214086D382FE0823550CAD6B19EA205?flag=getAllList");
    }

    // 手动重新加载标识和ip白名单数据库
    public void refresh(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IdentifierSingleton.getInstance().updateIdentifier(); // 刷新标识
        WhitelistSingleton.getInstance().updateWhitelist(); // 手动刷新ip白名单
        response.sendRedirect(request.getContextPath() + "/page/white/2214086D382FE0823550CAD6B19EA205?flag=getAllList");
    }

    // 手动刷新 Token
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取新token
        DialecticalCloud.getTokenFromApi();
        response.sendRedirect(request.getContextPath() + "/page/white/2214086D382FE0823550CAD6B19EA205?flag=getAllList");
    }

    // 删除全部错误日志
    public void deleteAllErrLog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ErrorLogsDAOImpl errorLogsDAO = new ErrorLogsDAOImpl();
        errorLogsDAO.deleteAllErrorLog();
        response.sendRedirect(request.getContextPath() + "/page/white/2214086D382FE0823550CAD6B19EA205?flag=getAllList");
    }

    // 删除单个错误日志
    public void deleteErrLog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (BaseUtil.isEmpty(id)) {
            response.getWriter().write("删除失败，参数为空");
            return;
        }
        ErrorLogsDAOImpl errorLogsDAO = new ErrorLogsDAOImpl();
        errorLogsDAO.deleteErrorLogById(Integer.valueOf(id));
        response.sendRedirect(request.getContextPath() + "/page/white/2214086D382FE0823550CAD6B19EA205?flag=getAllList");
    }
}
