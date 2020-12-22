package util;

import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import service.LoginService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/**
 * @Author Legion
 * @Date 2020/12/17 22:05
 * @Description 在Handler中可能使用到的一些重用代码
 */
public class HandlerUtil {
    /**
     * 验证用户身份
     * @param requestMap 客户端请求数据
     * @return 当验证失败时返回null，验证成功时返回人员编号
     */
    public static String verify(JSONObject requestMap){
        LoginService loginService = LoginService.getInstance();
        Object account = requestMap.get("account");
        Object password = requestMap.get("password");
        // 缺少account或password参数
        if (account == null || password == null) {
            return null;
        }
        String personId = loginService.checkAccount(account.toString(), password.toString());
        return personId;
    }
}
