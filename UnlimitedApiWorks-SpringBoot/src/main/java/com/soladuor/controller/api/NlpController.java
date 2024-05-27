package com.soladuor.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hankcs.hanlp.tokenizer.SpeedTokenizer;
import com.soladuor.exception.GraceException;
import com.soladuor.utils.BaseUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/nlp")
public class NlpController {

    static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 词法分析
     */
    // HanLP 1.x Portable版官方文档 https://github.com/hankcs/HanLP/tree/1.x
    @GetMapping("/LexicalAnalysis")
    public void hanlp(String text, HttpServletResponse response) throws IOException {
        if (BaseUtils.isEmpty(text)) {
            response.getWriter().write("{\"code\":400,\"message\":\"text不能为空\"}");
            return;
        }
        try {
            response.getWriter().write(objectMapper.writeValueAsString(SpeedTokenizer.segment(text)));
        } catch (Exception e) {
            e.printStackTrace();
            GraceException.display("nlp获取关键词报错");
            response.getWriter().write("{\"code\":400,\"message\":\"" + e.getMessage() + "\"}");
        }
    }

}
