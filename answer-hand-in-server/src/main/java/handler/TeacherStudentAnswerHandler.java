package handler;

import bean.Response;
import com.alibaba.fastjson.JSONObject;
import entity.StudentAnswerInfo;
import service.TeacherService;
import util.HandlerUtil;

/**
 * @Author Legion
 * @Date 2020/12/22 19:57
 * @Description
 */
public class TeacherStudentAnswerHandler implements RequestHandler {
    private static final TeacherStudentAnswerHandler teacherStudentAnswerHandler = new TeacherStudentAnswerHandler();
    private TeacherStudentAnswerHandler(){}
    public static TeacherStudentAnswerHandler getInstance(){
        return teacherStudentAnswerHandler;
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
        Object studentId = requestMap.get("studentId");
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
        if (studentId == null) {
            response.setCode(402);
            response.setMessage("lack of studentId");
            return response;
        }
        StudentAnswerInfo studentAnswer = TeacherService.getInstance().getStudentAnswer(
                Integer.parseInt(paperId.toString()),
                Integer.parseInt(questionId.toString()),
                studentId.toString()
        );
        response.setCode(200);
        response.setMessage("success");
        response.setData(studentAnswer);
        return response;
    }
}
