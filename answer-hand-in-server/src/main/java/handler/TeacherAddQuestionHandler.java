package handler;

import bean.Response;
import com.alibaba.fastjson.JSONObject;
import service.TeacherService;
import util.HandlerUtil;

/**
 * @Author Legion
 * @Date 2021/1/3 16:28
 * @Description
 */
public class TeacherAddQuestionHandler implements RequestHandler {
    private static TeacherAddQuestionHandler teacherAddQuestionHandler = new TeacherAddQuestionHandler();
    private TeacherAddQuestionHandler(){}
    public static TeacherAddQuestionHandler getInstance(){
        return teacherAddQuestionHandler;
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
        Object title = requestMap.get("title");
        Object content = requestMap.get("content");
        Object fullScore = requestMap.get("fullScore");
        if (paperId == null) {
            response.setCode(402);
            response.setMessage("lack of paperId");
            return response;
        }
        if (title == null) {
            response.setCode(402);
            response.setMessage("lack of title");
            return response;
        }
        if (content == null) {
            response.setCode(402);
            response.setMessage("lack of content");
            return response;
        }
        if (fullScore == null) {
            response.setCode(402);
            response.setMessage("lack of fullScore");
            return response;
        }
        int count = TeacherService.getInstance().addQuestion(
                Integer.parseInt(paperId.toString()),
                title.toString(),
                content.toString(),
                Integer.parseInt(fullScore.toString())
        );
        if (count == 0) {
            response.setCode(501);
            response.setMessage("Fail to insert question");
            return response;
        } else {
            response.setCode(200);
            response.setMessage("success");
            return response;
        }
    }
}
