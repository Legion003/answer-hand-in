package handler;

import bean.Response;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.LoginService;
import service.StudentService;
import util.HandlerUtil;
import util.ThreadPool;

import java.io.IOException;
import java.util.Map;

/**
 * @Author Legion
 * @Date 2020/12/18 14:17
 * @Description 处理试卷和对应题目相关请求
 */
public class PaperQuestionHandler implements RequestHandler {
    private static final PaperQuestionHandler paperQuestionHandler = new PaperQuestionHandler();

    private PaperQuestionHandler(){}
    public static PaperQuestionHandler getInstance(){
        return paperQuestionHandler;
    }

    @Override
    public Response handle(JSONObject requestMap) {
        Response response = new Response();
        String personId = HandlerUtil.verify(requestMap);
        if (personId == null) {
            response.setCode(401);
            response.setMessage("account verification fail");
            return response;
        }
        Object paperId = requestMap.get("paperId");
        // 判断一下有没有paperId这个参数，如果没有就回传错误代号和提示信息
        if (paperId == null) {
            response.setCode(402);
            response.setMessage("lack of paperId");
            return response;
        }
        // 数据齐全，正常检索
        Map<String, Object> paperQuestionInfo = StudentService.getInstance().getPaperQuestion(
                Integer.parseInt(paperId.toString()), personId);
        response.setCode(200);
        response.setMessage("search success");
        response.setData(paperQuestionInfo);
        return response;
    }
}
