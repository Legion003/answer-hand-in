package handler;

import bean.Response;
import com.alibaba.fastjson.JSONObject;
import service.AccountService;

/**
 * @Author Legion
 * @Date 2021/1/4 17:43
 * @Description
 */
public class SignInHandler implements RequestHandler {
    private static final SignInHandler signInHandler = new SignInHandler();
    private SignInHandler(){}
    public static SignInHandler getInstance(){
        return signInHandler;
    }
    @Override
    public Response handle(JSONObject requestMap) {
        Response response = new Response();
        Object name = requestMap.get("name");
        Object personId = requestMap.get("personId");
        Object account = requestMap.get("account");
        Object password = requestMap.get("password");
        Object personType = requestMap.get("personType");
        if (name == null) {
            response.setCode(401);
            response.setMessage("lack of name");
            return response;
        }
        if (personId == null) {
            response.setCode(401);
            response.setMessage("lack of personId");
            return response;
        }
        if (account == null) {
            response.setCode(401);
            response.setMessage("lack of account");
            return response;
        }
        if (password == null) {
            response.setCode(401);
            response.setMessage("lack of password");
            return response;
        }
        if (personType == null) {
            response.setCode(401);
            response.setMessage("lack of personType");
            return response;
        }
        boolean type;
        try {
            type = (boolean) personType;
        } catch (Exception e) {
            response.setCode(401);
            response.setMessage("wrong personType");
            return response;
        }
        int count = AccountService.getInstance().signIn(
                name.toString(),
                personId.toString(),
                account.toString(),
                password.toString(),
                type
        );
        if (count == 0) {
            response.setCode(501);
            response.setMessage("Fail to insert account");
            return response;
        } else {
            response.setCode(200);
            response.setMessage("success");
            return response;
        }
    }
}
