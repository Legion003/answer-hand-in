package handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Legion
 * @Date 2020/12/19 15:55
 * @Description
 */
public class LoginHandlerTest {
    LoginHandler loginHandler = LoginHandler.getInstance();

    @Test
    public void loginHandlerTest() throws IOException {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("account", "Legion");
        requestMap.put("password", "123456");
        JSONObject jsonMap = JSON.parseObject(JSON.toJSONString(requestMap));
        System.out.println(loginHandler.handle(jsonMap));
    }
}
