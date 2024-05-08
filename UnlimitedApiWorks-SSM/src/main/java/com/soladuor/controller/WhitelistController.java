package com.soladuor.controller;

import com.soladuor.pojo.ErrorLog;
import com.soladuor.pojo.Identifier;
import com.soladuor.pojo.Whitelist;
import com.soladuor.service.ErrorLogService;
import com.soladuor.service.IdentifierService;
import com.soladuor.service.WhitelistService;
import com.soladuor.utils.BaseUtil;
import com.soladuor.utils.IPUtil;
import com.soladuor.utils.zydsoft.DialecticalCloud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@Transactional
// WhitelistServlet通过MD5加密9901次的结果
@RequestMapping("/page/white/2214086D382FE0823550CAD6B19EA205")
public class WhitelistController {

    // 重定向到 getAllList 页面
    public static String REDIRECT_WHITELIST_GET_ALL_LIST = "redirect:/page/white/2214086D382FE0823550CAD6B19EA205/getAllList";

    @Autowired
    ErrorLogService errorLogService;

    @Autowired
    IdentifierService identifierService;

    @Autowired
    WhitelistService whitelistService;

    @GetMapping("/getAllList")
    public String getAllList(Map<String, Object> map) {
        // 白名单
        List<Whitelist> whitelist = whitelistService.getWhiteList();
        map.put("whitelist", whitelist);
        // 标识表
        List<Identifier> identifierList = identifierService.getIdentifierList();
        map.put("identifierList", identifierList);
        // 错误表
        List<ErrorLog> errorList = errorLogService.getErrorList();
        map.put("errorList", errorList);
        // 响应
        map.put("result", "刷新成功");
        return "admin/index";
    }

    @GetMapping("/addIP")
    public String addIP(@RequestParam String ip, @RequestParam String description) {
        if (BaseUtil.stringsIsEmpty(ip, description)) {
            return REDIRECT_WHITELIST_GET_ALL_LIST;
        }
        // 正则表达式验证IP地址
        if (IPUtil.isIp(ip)) {
            whitelistService.addWhitelist(ip, description);
        }
        return REDIRECT_WHITELIST_GET_ALL_LIST;
    }

    @GetMapping("/deleteIP")
    public String deleteIP(@RequestParam String id) {
        if (!BaseUtil.isEmpty(id)) {
            whitelistService.deleteWhitelistById(id);
        }
        return REDIRECT_WHITELIST_GET_ALL_LIST;
    }

    // 手动刷新 Token
    @GetMapping("/refreshToken")
    public String refreshToken() {
        // 获取新token
        DialecticalCloud.getTokenFromApi();
        return REDIRECT_WHITELIST_GET_ALL_LIST;
    }

    // 删除全部错误日志
    @GetMapping("/deleteAllErrLog")
    public String deleteAllErrLog() {
        errorLogService.deleteAllErrorLog();
        return REDIRECT_WHITELIST_GET_ALL_LIST;
    }

    // 删除单个错误日志
    @GetMapping("/deleteErrLog")
    public String deleteErrLog(@RequestParam String id) {
        if (!BaseUtil.isEmpty(id)) {
            errorLogService.deleteErrorLogById(Integer.valueOf(id));
        }
        // response.getWriter().write("删除失败，参数为空");
        return REDIRECT_WHITELIST_GET_ALL_LIST;
    }
}