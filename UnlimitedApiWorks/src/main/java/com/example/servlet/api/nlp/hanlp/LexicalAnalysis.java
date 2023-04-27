package com.example.servlet.api.nlp.hanlp;

import com.example.utils.BaseUtil;
import com.example.utils.ErrorLogger;
import com.google.gson.Gson;
import com.hankcs.hanlp.tokenizer.SpeedTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 词法分析
 */
// HanLP官方文档 https://github.com/hankcs/HanLP
@WebServlet(name = "LexicalAnalysis", value = "/api/nlp/LexicalAnalysis")
public class LexicalAnalysis extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String text = request.getParameter("text");
        if (BaseUtil.isEmpty(text)) {
            response.getWriter().write("{\"code\":400,\"message\":\"text不能为空\"}");
            return;
        }
        try {
            response.getWriter().write(new Gson().toJson(SpeedTokenizer.segment(text)));
        } catch (Exception e) {
            ErrorLogger.logException("nlp获取关键词报错", e);
            response.getWriter().write("{\"code\":400,\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}
