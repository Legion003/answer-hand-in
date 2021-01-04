package request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Legion
 * @Date 2021/1/4 17:50
 * @Description
 */
public class MockAccountRequest {
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
    }

    @After
    public void close() throws Exception{
        dos.close();
        dis.close();
    }

    @Test
    public void signInTest() throws Exception {
        requestMap.put("requestType", "signIn");
        requestMap.put("account", "XiaoHong");
        requestMap.put("password", "123456");
        requestMap.put("name", "小红");
        requestMap.put("personId", "18000000");
        requestMap.put("personType", false);
        dos.writeUTF(JSON.toJSONString(requestMap));
        String response = dis.readUTF();
        responseMap = JSON.parseObject(response);
        System.out.println(responseMap);
    }
}
