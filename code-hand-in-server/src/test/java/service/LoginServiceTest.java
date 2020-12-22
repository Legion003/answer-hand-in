package service;

import org.junit.Test;

/**
 * @Author Legion
 * @Date 2020/12/16 22:36
 * @Description
 */
public class LoginServiceTest {
    LoginService loginService = LoginService.getInstance();
    @Test
    public void checkAccountTest(){
        String personId = loginService.checkAccount("Legion", "123456");
        System.out.println(personId);
        System.out.println();
        personId = loginService.checkAccount("Le", "123456");
        System.out.println(personId);
        System.out.println();
        personId = loginService.checkAccount("Legion", "111");
        System.out.println(personId);
    }
}
