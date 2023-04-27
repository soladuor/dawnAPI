package com.example.servlet.api.nlp.tencent;

/*
  关键词提取
 */
// @WebServlet(name = "KeywordsExtraction", value = "/api/nlp/KeywordsExtraction")
/*
public class KeywordsExtraction extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String text = request.getParameter("Text");
        if (BaseUtil.isEmpty(text)) {
            // text = "";
            response.getWriter().write("{\"code\":400,\"message\":\"Text不能为空\"}");
            return;
        }
        try {
            NlpClient client = TencentCloudNpl.NlpClientConfig(
                    "nlp.tencentcloudapi.com",
                    "ap-guangzhou");
            // 实例化一个请求对象,每个接口都会对应一个request对象
            KeywordsExtractionRequest req = new KeywordsExtractionRequest();
            req.setText(text);
            String num = request.getParameter("Num");
            if (num != null) {
                req.setNum(Long.parseLong(num));
            }
            // 返回的resp是一个KeywordsExtractionResponse的实例，与请求对象对应
            KeywordsExtractionResponse resp = client.KeywordsExtraction(req);
            // 输出json格式的字符串回包
            // System.out.println(KeywordsExtractionResponse.toJsonString(resp));
            response.getWriter().write(KeywordsExtractionResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            ErrorLogger.logException("腾讯云SDK获取摘要报错", e);
            response.getWriter().write("{\"code\":400,\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}
*/
