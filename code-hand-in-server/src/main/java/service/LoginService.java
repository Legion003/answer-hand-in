package service;

import bean.Response;
import dao.AccountInfoDao;
import entity.AccountInfo;

/**
 * @Author Legion
 * @Date 2020/12/16 22:25
 * @Description 登陆相关操作
 */
public class LoginService {
    private static final LoginService loginService = new LoginService();
    private AccountInfoDao accountInfoDao = AccountInfoDao.getInstance();

    private LoginService(){}
    public static LoginService getInstance(){
        return loginService;
    }
    /**
     * 检测登录
     * @param account 账号
     * @param password 密码
     * @return 对应的人员编号，如果没有就返回null
     */
    public String checkAccount(String account, String password) {
        AccountInfo accountInfo = accountInfoDao.search(account);
        if (accountInfo != null){
            if (password.equals(accountInfo.getPassword())){
                return accountInfo.getPersonId();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


}
