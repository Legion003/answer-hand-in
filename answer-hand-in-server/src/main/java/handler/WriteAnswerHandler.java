package handler;

import bean.Response;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.StudentService;
import util.HandlerUtil;
import util.ThreadPool;

import java.io.IOException;

/**
 * @Author Legion
 * @Date 2020/12/19 15:04
 * @Description 学生提交答案请求处理
 */
public class WriteAnswerHandler implements RequestHandler {
    private static WriteAnswerHandler writeAnswerHandler = new WriteAnswerHandler();
    private WriteAnswerHandler(){}
    public static WriteAnswerHandler getInstance() {
        return writeAnswerHandler;
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
        Object questionId = requestMap.get("questionId");
        Object answer = requestMap.get("answer");
        if (paperId == null) {
            response.setCode(402);
            response.setMessage("lack of paperId");
            return response;
        }
        if (questionId == null){
            response.setCode(402);
            response.setMessage("lack of questionId");
            return response;
        }
        if (answer == null) {
            response.setCode(402);
            response.setMessage("lack of answer");
            return response;
        }
        int count = StudentService.getInstance().writeAnswer(
                Integer.parseInt(paperId.toString()), Integer.parseInt(questionId.toString()),
                personId.toString(), answer.toString());
        if (count == 0) {
            response.setCode(501);
            response.setMessage("Fail to insert or update the answer.");
            return response;
        }
        response.setCode(200);
        response.setMessage("Submitted.");
        return response;
    }

}
