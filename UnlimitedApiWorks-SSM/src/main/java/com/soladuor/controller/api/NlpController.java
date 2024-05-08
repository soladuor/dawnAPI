package com.soladuor.controller.api;

import com.hankcs.hanlp.tokenizer.SpeedTokenizer;
import com.soladuor.utils.ErrorLogger;
import com.soladuor.utils.result.JSONResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nlp")
public class NlpController {

    /**
     * 词法分析
     */
    // HanLP官方文档 https://github.com/hankcs/HanLP
    @GetMapping("/LexicalAnalysis")
    public Object hanlp(String text) {
        try {
            return SpeedTokenizer.segment(text);
        } catch (Exception e) {
            ErrorLogger.logException("nlp获取关键词报错", e);
            // 以后不能这样用了
            return JSONResult.build(400, e.getMessage(), null);
        }
    }

}
