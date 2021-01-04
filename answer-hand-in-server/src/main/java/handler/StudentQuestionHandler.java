package handler;

import bean.Response;
import com.alibaba.fastjson.JSONObject;
import service.StudentService;
import util.HandlerUtil;

import java.util.Map;

/**
 * @Author Legion
 * @Date 2020/12/18 21:30
 * @Description 学生获取题目请求处理
 */
public class StudentQuestionHandler implements RequestHandler {
    private static final StudentQuestionHandler studentQuestionHandler = new StudentQuestionHandler();
    private StudentQuestionHandler(){}
    public static StudentQuestionHandler getInstance(){
        return studentQuestionHandler;
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
        // 判断是否缺少了paperId或questionId参数，如果缺少则返回错误信息
        if (paperId == null || questionId == null) {
            response.setCode(402);
            response.setMessage("lack of paperId or questionId");
            return response;
        }
        Map<String, Object> questionAndPreviousAnswer = StudentService.getInstance().getQuestionAndPreviousAnswer(
                Integer.parseInt(paperId.toString()), Integer.parseInt(questionId.toString()), personId);
        response.setCode(200);
        response.setMessage("search success");
        response.setData(questionAndPreviousAnswer);
        return response;
    }
}
