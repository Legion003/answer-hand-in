package handler;

import bean.Response;
import com.alibaba.fastjson.JSONObject;
import service.StudentService;
import util.HandlerUtil;

import java.util.List;
import java.util.Map;

/**
 * @Author Legion
 * @Date 2020/12/18 0:14
 * @Description 处理科目和试卷信息的请求
 */
public class SubjectPaperHandler implements RequestHandler {
    private static final SubjectPaperHandler subjectPaperHandler = new SubjectPaperHandler();
    private SubjectPaperHandler(){}
    public static SubjectPaperHandler getInstance(){
        return subjectPaperHandler;
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
        List<Map<String, Object>> subjectPaper = StudentService.getInstance().getSubjectPaper(personId);
        response.setCode(200);
        response.setMessage("search success");
        response.setData(subjectPaper);
        return response;

    }
}
