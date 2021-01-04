package application;

import bean.Response;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpServer;
import handler.*;
import util.JdbcUtil;
import util.ThreadPool;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @Author Legion
 * @Date 2020/12/16 20:29
 * @Description 服务器启动入口，主要使用了socket
 */
public class Main {
    static Map<String, RequestHandler> requestTypeMap = null;
    static String port;
    static {
        // 读取配置文件中的值
        try {
            Properties properties = new Properties();
            ClassLoader classLoader = JdbcUtil.class.getClassLoader();
            URL resource = classLoader.getResource("server-config.properties");
            String path = resource.getPath();
            properties.load(new FileReader(path));
            port = properties.getProperty("port");
        } catch (Exception e){
            e.printStackTrace();
        }
        // 构建一个map，key是请求类型，value是handler对象，不同请求对应不同的handler对象
        requestTypeMap = new HashMap<>();
        requestTypeMap.put("login", LoginHandler.getInstance());
        requestTypeMap.put("paperQuestion", PaperQuestionHandler.getInstance());
        requestTypeMap.put("studentQuestion", StudentQuestionHandler.getInstance());
        requestTypeMap.put("subjectPaper", SubjectPaperHandler.getInstance());
        requestTypeMap.put("writeAnswer", WriteAnswerHandler.getInstance());
        requestTypeMap.put("teacherSubjectPaper", TeacherSubjectPaperHandler.getInstance());
        requestTypeMap.put("teacherPaperQuestion", TeacherPaperQuestionHandler.getInstance());
        requestTypeMap.put("teacherQuestionStudent", TeacherQuestionStudentHandler.getInstance());
        requestTypeMap.put("teacherStudentAnswer", TeacherStudentAnswerHandler.getInstance());
        requestTypeMap.put("teacherModifyScore", TeacherModifyScoreHandler.getInstance());
        requestTypeMap.put("teacherAddSubject", TeacherAddSubejctHandler.getInstance());
        requestTypeMap.put("teacherAddPaper", TeacherAddPaperHandler.getInstance());
        requestTypeMap.put("teacherAddQuestion", TeacherAddQuestionHandler.getInstance());
        requestTypeMap.put("teacherAddStudent", TeacherAddStudentHandler.getInstance());
        requestTypeMap.put("signIn", SignInHandler.getInstance());
    }
    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        serverSocket = new ServerSocket(Integer.parseInt(port));
        // 一直监听端口
        while (true) {
            clientSocket = serverSocket.accept();
            // 获取客户端传过来的信息，并将其转为一个JSONObject对象，通过map的形式获取相关的值
            dis = new DataInputStream(clientSocket.getInputStream());
            String request = dis.readUTF();
            JSONObject requestMap = JSON.parseObject(request);
            Response response = new Response();
            // 根据请求类型，利用requestTypeMap获取对应的handler对象，并调用其中的handle方法
            String requestType = (String) requestMap.get("requestType");
            RequestHandler requestHandler = requestTypeMap.get(requestType);
            if (requestHandler != null) {
                response = requestHandler.handle(requestMap);
            } else {
                // 当找不到这个请求类型，证明并不支持这个请求，返回错误代码和提示信息
                response.setCode(402);
                response.setMessage("request type not found");
            }
            // 将结果写回客户端
            dos = new DataOutputStream(clientSocket.getOutputStream());
            dos.writeUTF(JSON.toJSONString(response));
            // 关闭资源
            dis.close();
            dos.close();
            clientSocket.close();
        }
    }


}
