package com.example.servlet.api.nlp.tencent;


/*
  词法分析
 */
// @WebServlet(name = "LexicalAnalysis", value = "/api/nlp/LexicalAnalysis")
/*
public class LexicalAnalysis extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String text = request.getParameter("Text");
        if (BaseUtil.isEmpty(text)) {
            response.getWriter().write("{\"code\":400,\"message\":\"Text不能为空\"}");
            return;
        }
        try {
            NlpClient client = TencentCloudNpl.NlpClientConfig(
                    "nlp.tencentcloudapi.com",
                    "ap-guangzhou");
            // 实例化一个请求对象,每个接口都会对应一个request对象
            LexicalAnalysisRequest req = new LexicalAnalysisRequest();
            String dictId = request.getParameter("DictId");
            if (dictId != null) {
                req.setDictId(dictId);
            }
            String flag = request.getParameter("Flag");
            if (flag != null) {
                req.setFlag(Long.parseLong(flag));
            }
            req.setText(text);
            // 返回的resp是一个LexicalAnalysisResponse的实例，与请求对象对应
            LexicalAnalysisResponse resp = client.LexicalAnalysis(req);
            // 输出json格式的字符串回包
            // System.out.println(LexicalAnalysisResponse.toJsonString(resp));
            response.getWriter().write(LexicalAnalysisResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            ErrorLogger.logException("腾讯云SDK获取摘要报错", e);
            response.getWriter().write("{\"code\":400,\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}
*/
