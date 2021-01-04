package handler;

import bean.Response;
import com.alibaba.fastjson.JSONObject;
import service.TeacherService;
import util.HandlerUtil;

import java.util.List;
import java.util.Map;

/**
 * @Author Legion
 * @Date 2020/12/22 19:44
 * @Description
 */
public class TeacherSubjectPaperHandler implements RequestHandler{
    private static final TeacherSubjectPaperHandler teacherSubjectPaperHandler = new TeacherSubjectPaperHandler();
    private TeacherSubjectPaperHandler(){}
    public static TeacherSubjectPaperHandler getInstance() {
        return teacherSubjectPaperHandler;
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
        List<Map<String, Object>> subjectPaper = TeacherService.getInstance().getSubjectPaper(personId);
        response.setCode(200);
        response.setMessage("Success.");
        response.setData(subjectPaper);
        return response;
    }
}
