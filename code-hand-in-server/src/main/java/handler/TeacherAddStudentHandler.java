package handler;

import bean.Response;
import com.alibaba.fastjson.JSONObject;
import service.TeacherService;
import util.HandlerUtil;

/**
 * @Author Legion
 * @Date 2021/1/3 16:33
 * @Description
 */
public class TeacherAddStudentHandler implements RequestHandler {
    private static TeacherAddStudentHandler teacherAddStudentHandler = new TeacherAddStudentHandler();
    private TeacherAddStudentHandler(){}
    public static TeacherAddStudentHandler getInstance(){
        return teacherAddStudentHandler;
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
        Object subjectId = requestMap.get("subjectId");
        Object studentId = requestMap.get("studentId");
        if (subjectId == null) {
            response.setCode(402);
            response.setMessage("lack of subjectId");
            return response;
        }
        if (studentId == null) {
            response.setCode(402);
            response.setMessage("lack of studentId");
            return response;
        }
        int count = TeacherService.getInstance().addStudent(subjectId.toString(), studentId.toString());
        if (count == 0) {
            response.setCode(501);
            response.setMessage("Fail to insert student");
            return response;
        } else {
            response.setCode(200);
            response.setMessage("success");
            return response;
        }
    }
}
