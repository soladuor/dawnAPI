package com.soladuor.controller;

import com.soladuor.pojo.Identifier;
import com.soladuor.pojo.Whitelist;
import com.soladuor.service.IdentifierService;
import com.soladuor.service.WhitelistService;
import com.soladuor.utils.BaseUtils;
import com.soladuor.utils.IPUtils;
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
        // 响应
        map.put("result", "刷新成功");
        return "admin/index";
    }

    @GetMapping("/addIP")
    public String addIP(@RequestParam String ip, @RequestParam String description) {
        if (BaseUtils.stringsIsEmpty(ip, description)) {
            return REDIRECT_WHITELIST_GET_ALL_LIST;
        }
        // 正则表达式验证IP地址
        if (IPUtils.isIp(ip)) {
            whitelistService.addWhitelist(ip, description);
        }
        return REDIRECT_WHITELIST_GET_ALL_LIST;
    }

    @GetMapping("/deleteIP")
    public String deleteIP(@RequestParam String id) {
        if (!BaseUtils.isEmpty(id)) {
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

}
