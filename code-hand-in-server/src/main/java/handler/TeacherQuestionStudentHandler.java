package handler;

import bean.Response;
import com.alibaba.fastjson.JSONObject;
import service.TeacherService;
import util.HandlerUtil;

import java.util.Map;

/**
 * @Author Legion
 * @Date 2020/12/22 19:53
 * @Description
 */
public class TeacherQuestionStudentHandler implements RequestHandler {
    private static final TeacherQuestionStudentHandler teacherQuestionStudentHandler = new TeacherQuestionStudentHandler();
    private TeacherQuestionStudentHandler(){}
    public static TeacherQuestionStudentHandler getInstance(){
        return teacherQuestionStudentHandler;
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
        if (paperId == null) {
            response.setCode(402);
            response.setMessage("lack of paperId");
            return response;
        }
        if (questionId == null) {
            response.setCode(402);
            response.setMessage("lack of questionId");
            return response;
        }
        Map<String, Object> questionStudentList = TeacherService.getInstance().getQuestionStudentList(
                Integer.parseInt(paperId.toString()),
                Integer.parseInt(questionId.toString())
        );
        response.setCode(200);
        response.setMessage("success");
        response.setData(questionStudentList);
        return response;
    }
}
