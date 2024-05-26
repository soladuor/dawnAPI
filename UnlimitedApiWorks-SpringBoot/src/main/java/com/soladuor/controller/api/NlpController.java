package com.soladuor.controller.api;

import com.hankcs.hanlp.tokenizer.SpeedTokenizer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nlp")
public class NlpController {

    /**
     * 词法分析
     */
    // HanLP 1.x Portable版官方文档 https://github.com/hankcs/HanLP/tree/1.x
    @GetMapping("/LexicalAnalysis")
    public Object hanlp(String text) {
        return SpeedTokenizer.segment(text);
    }

}
