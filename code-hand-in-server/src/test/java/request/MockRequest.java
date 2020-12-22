package request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import entity.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.print.Paper;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Legion
 * @Date 2020/12/19 17:38
 * @Description
 */
public class MockRequest {
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
        requestMap.put("account", "Legion");
        requestMap.put("password", "123456");
    }

    @After
    public void close() throws Exception{
        dos.close();
        dis.close();
    }

    @Test
    public void loginTest() throws IOException {
        requestMap.put("requestType", "login");
        dos.writeUTF(JSON.toJSONString(requestMap));
        String response = dis.readUTF();
        responseMap = JSON.parseObject(response);
        AccountInfo accountInfo = JSON.toJavaObject((JSON) responseMap.get("data"), AccountInfo.class);
        System.out.println(accountInfo);

    }

    @Test
    public void PaperQuestionTest() throws IOException{
        requestMap.put("requestType", "paperQuestion");
        requestMap.put("paperId", 2);
        dos.writeUTF(JSON.toJSONString(requestMap));
        String response = dis.readUTF();
        responseMap = JSON.parseObject(response);
        System.out.println(responseMap);
        Map paperQuestionInfoMap = JSON.toJavaObject((JSON) responseMap.get("data"), Map.class);
        Map paperInfoMap = JSON.toJavaObject((JSON) paperQuestionInfoMap.get("PaperInfo"), Map.class);

        Date deadline = new Date(Long.parseLong(paperInfoMap.get("deadline").toString()));
        String describe = paperInfoMap.get("describe").toString();
        String name = paperInfoMap.get("name").toString();
        PaperInfo paperInfo = new PaperInfo();
        paperInfo.setDeadline(deadline);
        paperInfo.setDescribe(describe);
        paperInfo.setName(name);
        System.out.println(paperInfo);

        Map questionSimpleInfoMap = JSON.toJavaObject((JSON) paperQuestionInfoMap.get("questionSimpleInfoMap"), Map.class);
        for (Object questionId : questionSimpleInfoMap.keySet()) {
            System.out.println(questionId);
            Map scoreTitle = JSON.toJavaObject((JSON) questionSimpleInfoMap.get(questionId), Map.class);
            System.out.println(scoreTitle.get("score"));
            System.out.println(scoreTitle.get("title"));

        }

    }

    @Test
    public void SubjectPaperTest() throws IOException{
        requestMap.put("requestType", "subjectPaper");
        dos.writeUTF(JSON.toJSONString(requestMap));
        String response = dis.readUTF();
        responseMap = JSON.parseObject(response);
        List<Map> SubjectPaperList = JSON.parseArray(responseMap.get("data").toString(), Map.class);
        for (Map subjectPaperMap : SubjectPaperList) {
            List<PaperInfo> paperInfoList = JSON.parseArray(subjectPaperMap.get("paperInfoList").toString(), PaperInfo.class);
            SubjectInfo subjectInfo = JSON.parseObject(subjectPaperMap.get("subjectInfo").toString(), SubjectInfo.class);
            System.out.println(paperInfoList);
            System.out.println(subjectInfo);
        }
    }

    @Test
    public void studentQuestionTest() throws Exception{
        requestMap.put("requestType", "studentQuestion");
        requestMap.put("paperId", 1);
        requestMap.put("questionId", 1);
        dos.writeUTF(JSON.toJSONString(requestMap));
        String response = dis.readUTF();
        responseMap = JSON.parseObject(response);
        Map dataMap = JSON.toJavaObject((JSON) responseMap.get("data"), Map.class);
        QuestionInfo questionInfo = JSON.toJavaObject((JSON) dataMap.get("questionInfo"), QuestionInfo.class);
        StudentAnswerInfo studentAnswerInfo = JSON.toJavaObject((JSON) dataMap.get("studentAnswerInfo"), StudentAnswerInfo.class);
        System.out.println(questionInfo);
        System.out.println(studentAnswerInfo);
    }

    @Test
    public void writeAnswerTest() throws Exception{
        requestMap.put("requestType", "writeAnswer");
        requestMap.put("paperId", 1);
        requestMap.put("questionId", 1);
        requestMap.put("answer", "修改一下修改一下");
        dos.writeUTF(JSON.toJSONString(requestMap));
        String response = dis.readUTF();
        responseMap = JSON.parseObject(response);
        System.out.println(responseMap);
    }

}
