package handler;

import bean.Response;
import com.alibaba.fastjson.JSONObject;
import service.TeacherService;
import util.HandlerUtil;

/**
 * @Author Legion
 * @Date 2021/1/3 13:57
 * @Description
 */
public class TeacherAddSubejctHandler implements RequestHandler {
    private static TeacherAddSubejctHandler teacherAddSubejctHandler = new TeacherAddSubejctHandler();
    private TeacherAddSubejctHandler(){}
    public static TeacherAddSubejctHandler getInstance(){
        return teacherAddSubejctHandler;
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
        Object name = requestMap.get("name");
        Object teacherId = requestMap.get("teacherId");
        if (subjectId == null) {
            response.setCode(402);
            response.setMessage("lack of subjectId");
            return response;
        }
        if (name == null) {
            response.setCode(402);
            response.setMessage("lack of name");
            return response;
        }
        if (teacherId == null) {
            response.setCode(402);
            response.setMessage("lack of teacherId");
            return response;
        }
        int count = TeacherService.getInstance()
                .addSubject(subjectId.toString(), name.toString(), teacherId.toString());
        if (count == 0) {
            response.setCode(501);
            response.setMessage("Fail to insert subject");
            return response;
        } else {
            response.setCode(200);
            response.setMessage("success");
            return response;
        }
    }
}
