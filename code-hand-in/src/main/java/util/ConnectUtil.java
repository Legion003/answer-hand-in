package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import controller.LoginController;
import entity.AccountInfo;
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


}
