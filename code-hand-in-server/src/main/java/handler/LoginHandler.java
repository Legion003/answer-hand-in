package handler;

import bean.Response;
import com.alibaba.fastjson.JSONObject;
import dao.AccountInfoDao;
import entity.AccountInfo;
import service.LoginService;
import util.HandlerUtil;

/**
 * @Author Legion
 * @Date 2020/12/16 20:31
 * @Description 处理登录请求的handler
 */
public class LoginHandler implements RequestHandler{
    private static final LoginHandler loginHandler = new LoginHandler();
    private LoginHandler(){}
    public static LoginHandler getInstance() {
        return loginHandler;
    }
    @Override
    public Response handle(JSONObject requestMap) {
        Response response = new Response();
        Object account = requestMap.get("account");
        Object password = requestMap.get("password");
        if (account == null || password == null) {
            response.setCode(401);
            response.setMessage("incorrect account or password");
            return response;
        }
        AccountInfo accountInfo = AccountInfoDao.getInstance().search(account.toString());
        if (!accountInfo.getPassword().equals(password)) {
            response.setCode(401);
            response.setMessage("incorrect account or password");
            return response;
        }
        // 登陆成功
        response.setCode(200);
        response.setMessage("login success");
        response.setData(accountInfo);
        return response;
    }
}
