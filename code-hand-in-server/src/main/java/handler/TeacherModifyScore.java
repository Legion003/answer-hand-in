package handler;

import bean.Response;
import com.alibaba.fastjson.JSONObject;
import service.TeacherService;
import util.HandlerUtil;

/**
 * @Author Legion
 * @Date 2020/12/22 20:00
 * @Description
 */
public class TeacherModifyScore implements RequestHandler {
    private static final TeacherModifyScore teacherModifyScore = new TeacherModifyScore();
    private TeacherModifyScore(){}
    public static TeacherModifyScore getInstance(){
        return teacherModifyScore;
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
        Object score = requestMap.get("score");
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
        if (score == null) {
            response.setCode(402);
            response.setMessage("lack of score");
            return response;
        }
        int count = TeacherService.getInstance().modifyScore(
                Integer.parseInt(paperId.toString()),
                Integer.parseInt(questionId.toString()),
                studentId.toString(),
                Integer.parseInt(score.toString())
        );
        if (count == 0) {
            response.setCode(501);
            response.setMessage("Fail to update the score");
            return response;
        } else {
            response.setCode(200);
            response.setMessage("success");
            return response;
        }
    }
}
