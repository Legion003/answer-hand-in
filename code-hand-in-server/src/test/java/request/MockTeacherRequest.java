package request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import entity.PaperInfo;
import entity.QuestionInfo;
import entity.StudentAnswerInfo;
import entity.SubjectInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Legion
 * @Date 2020/12/23 11:44
 * @Description
 */
public class MockTeacherRequest {
    String ip = "127.0.0.1";
    int port = 8001;
    Socket socket = null;
    DataOutputStream dos = null;
    DataInputStream dis = null;
    Map<String, Object> requestMap = null;
    JSONObject responseMap = null;

    @Before
    public void init() throws Exception{
        socket = new Socket(ip, port);
        dos = new DataOutputStream(socket.getOutputStream());
        dis = new DataInputStream(socket.getInputStream());
        requestMap = new HashMap<>();
        requestMap.put("account", "XuJian");
        requestMap.put("password", "123456");
    }

    @After
    public void close() throws Exception{
        dos.close();
        dis.close();
    }

    @Test
    public void subjectPaperTest() throws Exception{
        requestMap.put("requestType", "teacherSubjectPaper");
        dos.writeUTF(JSON.toJSONString(requestMap));
        String response = dis.readUTF();
        responseMap = JSON.parseObject(response);
        List<Map> subjectPaperList = JSON.parseArray(responseMap.get("data").toString(), Map.class);
        for (Map subjectPaperMap : subjectPaperList) {
            SubjectInfo subjectInfo = JSON.toJavaObject((JSON) subjectPaperMap.get("subjectInfo"), SubjectInfo.class);
            System.out.println(subjectInfo);
            List<PaperInfo> paperInfoList = JSON.parseArray(subjectPaperMap.get("paperInfoList").toString(), PaperInfo.class);
            System.out.println(paperInfoList);
        }
    }

    @Test
    public void paperQuestionTest() throws Exception{
        requestMap.put("requestType", "teacherPaperQuestion");
        requestMap.put("paperId", 2);
        dos.writeUTF(JSON.toJSONString(requestMap));
        String response = dis.readUTF();
        responseMap = JSON.parseObject(response);
        Map<String, Object> paperQuestion = JSON.toJavaObject((JSON) responseMap.get("data"), Map.class);
        PaperInfo paperInfo = JSON.toJavaObject((JSON) paperQuestion.get("PaperInfo"), PaperInfo.class);
        System.out.println(paperInfo);
        List<QuestionInfo> questionInfoList = JSON.parseArray(paperQuestion.get("QuestionInfoList").toString(), QuestionInfo.class);
        for (QuestionInfo questionInfo : questionInfoList) {
            System.out.println(questionInfo);
        }
    }

    @Test
    public void questionStudentTest() throws Exception {
        requestMap.put("requestType", "teacherQuestionStudent");
        requestMap.put("paperId", 2);
        requestMap.put("questionId", 2);
        dos.writeUTF(JSON.toJSONString(requestMap));
        String response = dis.readUTF();
        responseMap = JSON.parseObject(response);
        Map<String, Object> questionStudent = JSON.toJavaObject((JSON) responseMap.get("data"), Map.class);
        QuestionInfo questionInfo = JSON.toJavaObject((JSON) questionStudent.get("QuestionInfo"), QuestionInfo.class);
        System.out.println(questionInfo);
        List<Map> studentScoreList = JSON.parseArray(questionStudent.get("StudentScoreList").toString(), Map.class);
        for (Map studentScoreMap : studentScoreList) {
            int score = Integer.parseInt(studentScoreMap.get("score").toString());
            String studentId = (String) studentScoreMap.get("studentId");
            String studentName = (String) studentScoreMap.get("studentName");
            System.out.println(score);
            System.out.println(studentId);
            System.out.println(studentName);
        }
    }

    @Test
    public void studentAnswerTest() throws Exception{
        requestMap.put("requestType", "teacherStudentAnswer");
        requestMap.put("paperId", 2);
        requestMap.put("questionId", 2);
        requestMap.put("studentId", "18372026");
        dos.writeUTF(JSON.toJSONString(requestMap));
        String response = dis.readUTF();
        responseMap = JSON.parseObject(response);
        StudentAnswerInfo studentAnswerInfo = JSON.toJavaObject((JSON) responseMap.get("data"), StudentAnswerInfo.class);
        System.out.println(studentAnswerInfo);
    }

    @Test
    public void modifyScoreTest() throws Exception{
        requestMap.put("requestType", "teacherModifyScore");
        requestMap.put("paperId", 2);
        requestMap.put("questionId", 2);
        requestMap.put("studentId", "18372026");
        requestMap.put("score", 20);
        dos.writeUTF(JSON.toJSONString(requestMap));
        String response = dis.readUTF();
        responseMap = JSON.parseObject(response);
        System.out.println(responseMap);
    }

    @Test
    public void addSubjectTest() throws Exception{
        requestMap.put("requestType", "teacherAddSubject");
        requestMap.put("subjectId", "S005");
        requestMap.put("teacherId", "A001");
        requestMap.put("name", "程序设计");
        dos.writeUTF(JSON.toJSONString(requestMap));
        String response = dis.readUTF();
        responseMap = JSON.parseObject(response);
        System.out.println(responseMap);
    }

    @Test
    public void addPaperTest() throws Exception{
        requestMap.put("requestType", "teacherAddPaper");
        requestMap.put("subjectId", "S001");
        requestMap.put("teacherId", "A001");
        requestMap.put("name", "期末大作业");
        requestMap.put("describe", "一个期末大作业");
        requestMap.put("deadline", "");
        dos.writeUTF(JSON.toJSONString(requestMap));
        String response = dis.readUTF();
        responseMap = JSON.parseObject(response);
        System.out.println(responseMap);
    }
}
