package handler;

import bean.Response;
import com.alibaba.fastjson.JSONObject;

/**
 * @Author Legion
 * @Date 2020/12/19 18:00
 * @Description
 */
public interface RequestHandler {
    /**
     * 处理请求的方法
     * @param requestMap 封装了客户端传过来的内容
     * @return 一个回传封装对象
     */
    Response handle(JSONObject requestMap);
}
