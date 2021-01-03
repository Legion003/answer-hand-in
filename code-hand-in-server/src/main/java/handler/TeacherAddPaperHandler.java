package handler;

import bean.Response;
import com.alibaba.fastjson.JSONObject;
import service.TeacherService;
import util.HandlerUtil;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * @Author Legion
 * @Date 2021/1/3 14:02
 * @Description
 */
public class TeacherAddPaperHandler implements RequestHandler {
    private static TeacherAddPaperHandler teacherAddPaperHandler= new TeacherAddPaperHandler();
    private TeacherAddPaperHandler(){}
    public static TeacherAddPaperHandler getInstance() {
        return teacherAddPaperHandler;
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
        Object describe = requestMap.get("describe");
        Object teacherId = requestMap.get("teacherId");
        Object deadline = requestMap.get("deadline");
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
        if (describe == null){
            response.setCode(402);
            response.setMessage("lack of describe");
            return response;
        }
        if (teacherId == null) {
            response.setCode(402);
            response.setMessage("lack of teacherId");
            return response;
        }
        if (deadline == null) {
            response.setCode(402);
            response.setMessage("lack of deadline");
            return response;
        }
        int count = TeacherService.getInstance()
                .addPaper(subjectId.toString(), name.toString(), describe.toString(), deadline.toString(), teacherId.toString());
        if (count == 0) {
            response.setCode(501);
            response.setMessage("Fail to insert paper");
            return response;
        } else {
            response.setCode(200);
            response.setMessage("success");
            return response;
        }
    }
}
