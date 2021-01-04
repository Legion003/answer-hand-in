package handler;

import bean.Response;
import com.alibaba.fastjson.JSONObject;
import service.TeacherService;
import util.HandlerUtil;

import java.util.Map;

/**
 * @Author Legion
 * @Date 2020/12/22 19:47
 * @Description
 */
public class TeacherPaperQuestionHandler implements RequestHandler {
    private static final TeacherPaperQuestionHandler teacherPaperQuestionHandler = new TeacherPaperQuestionHandler();
    private TeacherPaperQuestionHandler(){}
    public static TeacherPaperQuestionHandler getInstance(){
        return teacherPaperQuestionHandler;
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
        if (paperId == null) {
            response.setCode(402);
            response.setMessage("lack of paperId");
            return response;
        }
        Map<String, Object> paperQuestion = TeacherService.getInstance().getPaperQuestion(Integer.parseInt(paperId.toString()));
        response.setCode(200);
        response.setMessage("success");
        response.setData(paperQuestion);
        return response;
    }
}
