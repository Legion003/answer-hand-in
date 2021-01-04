package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import controller.LoginController;
import entity.AccountInfo;
import entity.PaperInfo;
import entity.QuestionInfo;
import entity.StudentAnswerInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @Author Legion
 * @Date 2020/12/19 21:53
 * @Description
 */
public class ConnectUtil {
    private static String serverIp;
    private static String serverPort;
    static {
        try {
            Properties properties = new Properties();
            ClassLoader classLoader = LoginController.class.getClassLoader();
            URL resource = classLoader.getResource("config.properties");
            String path = resource.getPath();
            properties.load(new FileReader(path));
            serverIp = properties.getProperty("server-ip");
            serverPort = properties.getProperty("server-port");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JSONObject connect(Map<String, Object> requestMap){
        Socket socket = null;
        DataOutputStream dos = null;
        DataInputStream dis = null;
        JSONObject responseMap = null;
        try {
            socket = new Socket(serverIp, Integer.parseInt(serverPort));
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            dos.writeUTF(JSON.toJSONString(requestMap));
            String response = dis.readUTF();
            responseMap = JSON.parseObject(response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dos != null){
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseMap;
    }

    public static Map<String, Object> login(String account, String password) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("account", account);
        requestMap.put("password", password);
        requestMap.put("requestType", "login");
        JSONObject responseMap = connect(requestMap);
        InfoStorage.setAccountInfo(JSON.toJavaObject((JSON) responseMap.get("data"), AccountInfo.class));
        return responseMap;
    }

    public static Map<String, Object> getSubjectPaper() {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap = setAccountPassword(requestMap);
        requestMap.put("requestType", "subjectPaper");
        JSONObject responseMap = connect(requestMap);
        InfoStorage.setSubjectPaperList(JSON.parseArray(responseMap.get("data").toString(), Map.class));
        return responseMap;
    }

    public static Map<String, Object> getPaperQuestion(Map<String, Object> requestMap) {
        requestMap = setAccountPassword(requestMap);
        requestMap.put("requestType", "paperQuestion");
        JSONObject responseMap = connect(requestMap);
        InfoStorage.setPaperQuestionMap(JSON.toJavaObject((JSON) responseMap.get("data"), Map.class));
        return responseMap;
    }

    public static Map<String, Object> getQuestionAnswer(int paperId, int questionId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap = setAccountPassword(requestMap);
        requestMap.put("requestType", "studentQuestion");
        requestMap.put("paperId", paperId);
        requestMap.put("questionId", questionId);
        JSONObject responseMap = connect(requestMap);
        Map dataMap = JSON.toJavaObject((JSON) responseMap.get("data"), Map.class);
        InfoStorage.setQuestionInfo(JSON.toJavaObject((JSON) dataMap.get("questionInfo"), QuestionInfo.class));
        InfoStorage.setStudentAnswerInfo(JSON.toJavaObject((JSON) dataMap.get("studentAnswerInfo"), StudentAnswerInfo.class));
        return responseMap;
    }

    public static Map<String, Object> writeAnswer(int paperId, int questionId, String answer) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap = setAccountPassword(requestMap);
        requestMap.put("requestType", "writeAnswer");
        requestMap.put("paperId", paperId);
        requestMap.put("questionId", questionId);
        requestMap.put("answer", answer);
        JSONObject responseMap = connect(requestMap);
        return responseMap;
    }

    public static Map<String, Object> getTeacherSubjectPaper() {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap = setAccountPassword(requestMap);
        requestMap.put("requestType", "teacherSubjectPaper");
        JSONObject responseMap = connect(requestMap);
        InfoStorage.setSubjectPaperList(JSON.parseArray(responseMap.get("data").toString(), Map.class));
        return responseMap;
    }

    public static Map<String, Object> getTeacherPaperQuestion(int paperId){
        Map<String, Object> requestMap = new HashMap<>();
        requestMap = setAccountPassword(requestMap);
        requestMap.put("requestType", "teacherPaperQuestion");
        requestMap.put("paperId", paperId);
        JSONObject responseMap = connect(requestMap);
        InfoStorage.setPaperQuestionMap(JSON.toJavaObject((JSON) responseMap.get("data"), Map.class));
        return responseMap;
    }

    public static Map<String, Object> getTeacherQuestionStudent(int paperId, int questionId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap = setAccountPassword(requestMap);
        requestMap.put("requestType", "teacherQuestionStudent");
        requestMap.put("paperId", paperId);
        requestMap.put("questionId", questionId);
        JSONObject responseMap = connect(requestMap);
        Map<String, Object> questionStudent = JSON.toJavaObject((JSON) responseMap.get("data"), Map.class);
        InfoStorage.setQuestionInfo(JSON.toJavaObject((JSON) questionStudent.get("QuestionInfo"), QuestionInfo.class));
        InfoStorage.setStudentScoreList(JSON.parseArray(questionStudent.get("StudentScoreList").toString(), Map.class));
        return responseMap;
    }

    public static Map<String, Object> getTeacherStudentAnswer(int paperId, int questionId, String studentId){
        Map<String, Object> requestMap = new HashMap<>();
        requestMap = setAccountPassword(requestMap);
        requestMap.put("requestType", "teacherStudentAnswer");
        requestMap.put("paperId", paperId);
        requestMap.put("questionId", questionId);
        requestMap.put("studentId", studentId);
        JSONObject responseMap = connect(requestMap);
        InfoStorage.setStudentAnswerInfo(JSON.toJavaObject((JSON) responseMap.get("data"), StudentAnswerInfo.class));
        return responseMap;
    }

    public static Map<String, Object> modifyScore(int paperId, int questionId, String studentId, int score) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap = setAccountPassword(requestMap);
        requestMap.put("requestType", "teacherModifyScore");
        requestMap.put("paperId", paperId);
        requestMap.put("questionId", questionId);
        requestMap.put("studentId", studentId);
        requestMap.put("score", score);
        JSONObject responseMap = connect(requestMap);
        return responseMap;
    }

    public static Map<String, Object> addSubject(String subjectId, String name) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap = setAccountPassword(requestMap);
        requestMap.put("requestType", "teacherAddSubject");
        requestMap.put("subjectId", subjectId);
        requestMap.put("teacherId", InfoStorage.getAccountInfo().getPersonId());
        requestMap.put("name", name);
        JSONObject responseMap = connect(requestMap);
        return responseMap;
    }

    public static Map<String, Object> addPaper(String subjectId, String name, String describe, String deadline){
        Map<String, Object> requestMap = new HashMap<>();
        requestMap = setAccountPassword(requestMap);
        requestMap.put("requestType", "teacherAddPaper");
        requestMap.put("subjectId", subjectId);
        requestMap.put("teacherId", InfoStorage.getAccountInfo().getPersonId());
        requestMap.put("name", name);
        requestMap.put("describe", describe);
        requestMap.put("deadline", deadline);
        JSONObject responseMap = connect(requestMap);
        return responseMap;
    }

    public static Map<String, Object> addQuestion(String title, String content, int fullScore) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap = setAccountPassword(requestMap);
        requestMap.put("requestType", "teacherAddQuestion");
        Map<String, Object> paperQuestionMap = InfoStorage.getPaperQuestionMap();
        Map paperInfoMap = JSON.toJavaObject((JSON) paperQuestionMap.get("PaperInfo"), Map.class);
        requestMap.put("paperId", Integer.parseInt(paperInfoMap.get("paperId").toString()));
        requestMap.put("title", title);
        requestMap.put("content", content);
        requestMap.put("fullScore", fullScore);
        JSONObject responseMap = connect(requestMap);
        return responseMap;
    }

    public static Map<String, Object> addStudent(String studentId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap = setAccountPassword(requestMap);
        Map<String, Object> paperQuestion = InfoStorage.getPaperQuestionMap();
        PaperInfo paperInfo = JSON.toJavaObject((JSON) paperQuestion.get("PaperInfo"), PaperInfo.class);
        requestMap.put("requestType", "teacherAddStudent");
        requestMap.put("studentId", studentId);
        requestMap.put("subjectId", paperInfo.getSubjectId());
        JSONObject responseMap = connect(requestMap);
        return responseMap;
    }

    private static Map<String, Object> setAccountPassword(Map<String, Object> requestMap) {
        AccountInfo accountInfo = InfoStorage.getAccountInfo();
        String account = accountInfo.getAccount();
        String password = accountInfo.getPassword();
        if (account != null && password != null) {
            requestMap.put("account", account);
            requestMap.put("password", password);
        }
        return requestMap;
    }

    public static Map<String, Object> signIn(String name, String personId, String account,String password, boolean personType) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("requestType", "signIn");
        requestMap.put("account", account);
        requestMap.put("password", password);
        requestMap.put("name", name);
        requestMap.put("personId", personId);
        requestMap.put("personType", personType);
        JSONObject responseMap = connect(requestMap);
        return responseMap;
    }



}
