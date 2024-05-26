package com.soladuor.controller.page;

import com.soladuor.pojo.Identifier;
import com.soladuor.pojo.Whitelist;
import com.soladuor.service.IdentifierService;
import com.soladuor.service.WhitelistService;
import com.soladuor.utils.BaseUtils;
import com.soladuor.utils.IPUtils;
import com.soladuor.utils.zydsoft.DialecticalCloud;
import com.soladuor.utils.zydsoft.ZydsoftWhitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

@Controller
@Transactional
// WhitelistServlet通过MD5加密9901次的结果
@RequestMapping("/page/white/2214086D382FE0823550CAD6B19EA205")
public class WhitelistController {
    private static final Logger log = LoggerFactory.getLogger(WhitelistController.class);
    // 重定向到 getAllList 页面
    public static String REDIRECT_GET_ALL_LIST = "redirect:/page/white/2214086D382FE0823550CAD6B19EA205/getAllList";
    // 首页
    public static String ADMIN_INDEX = "admin/index";

    @Autowired
    IdentifierService identifierService;

    @Autowired
    WhitelistService whitelistService;

    // 固定获取的数据
    private void putData(Map<String, Object> map) {
        // 白名单
        List<Whitelist> whitelist = whitelistService.getWhiteList();
        map.put("whitelist", whitelist);
        // 标识表
        List<Identifier> identifierList = identifierService.getIdentifierList();
        map.put("identifierList", identifierList);
        // 面板白名单
        LinkedHashSet<String> boardWhiteList = ZydsoftWhitelist.getWhitelist();
        map.put("boardWhiteList", boardWhiteList);
    }

    @GetMapping("/getAllList")
    public String getAllList(Map<String, Object> map) {
        putData(map);
        // 弹框的消息
        map.put("msg", "刷新成功");
        return ADMIN_INDEX;
    }

    @GetMapping("/addIP")
    public String addIP(@RequestParam String ip, @RequestParam String description) {
        if (BaseUtils.stringsIsEmpty(ip, description)) {
            return REDIRECT_GET_ALL_LIST;
        }
        // 正则表达式验证IP地址
        if (IPUtils.isIp(ip)) {
            whitelistService.addWhitelist(ip, description);
        }
        return REDIRECT_GET_ALL_LIST;
    }

    @GetMapping("/deleteIP")
    public String deleteIP(@RequestParam String id) {
        if (!BaseUtils.isEmpty(id)) {
            whitelistService.deleteWhitelistById(id);
        }
        return REDIRECT_GET_ALL_LIST;
    }

    @GetMapping("/updateDesc")
    public String updateDesc(@RequestParam String id, @RequestParam String description) {
        if (!BaseUtils.isEmpty(id)) {
            whitelistService.updateDescById(id, description);
        }
        return REDIRECT_GET_ALL_LIST;
    }

    @GetMapping("/updateValue")
    public String updateValue(@RequestParam String key, @RequestParam String value) {
        if (!BaseUtils.isEmpty(key)) {
            log.info("key: " + key + ", 新 value: " + value);
            identifierService.updateValueByKey(key, value);
        }
        return REDIRECT_GET_ALL_LIST;
    }

    // 手动刷新 Token
    @GetMapping("/refreshToken")
    public String refreshToken() {
        // 获取新token
        DialecticalCloud.getTokenFromApi();
        return REDIRECT_GET_ALL_LIST;
    }

    /*
    /**********************************************************
    /* 工作室面板白名单
    /**********************************************************
     */

    @PostMapping("/setBoardIP")
    public String setBoardIP(@RequestBody List<String> ips) {
        if (ips.size() > 30 || ips.size() == 0) {
            return REDIRECT_GET_ALL_LIST;
        }
        // 利用 LinkedHashSet 的特性去重，且可以保持原有顺序
        LinkedHashSet<String> list = ZydsoftWhitelist.getWhitelist();
        list.clear();
        list.addAll(ips);
        ZydsoftWhitelist.setWhitelist(list);
        return REDIRECT_GET_ALL_LIST;
    }

    @GetMapping("/delBoardIP")
    public String delBoardIP(@RequestParam String ip) {
        if (IPUtils.isIp(ip)) {
            // 利用 LinkedHashSet 的特性去重，且可以保持原有顺序
            LinkedHashSet<String> list = ZydsoftWhitelist.getWhitelist();
            list.remove(ip);
            ZydsoftWhitelist.setWhitelist(list);
        }
        return REDIRECT_GET_ALL_LIST;
    }

    @GetMapping("/addNativeIpToBoardIP")
    public String addNativeIpToBoardIP() {
        try {
            ZydsoftWhitelist.addNativeIpToWhitelist();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return REDIRECT_GET_ALL_LIST;
    }

}
