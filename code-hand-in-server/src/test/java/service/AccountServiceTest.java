package service;

import org.junit.Test;

/**
 * @Author Legion
 * @Date 2020/12/16 22:36
 * @Description
 */
public class AccountServiceTest {
    AccountService accountService = AccountService.getInstance();
    @Test
    public void checkAccountTest(){
        String personId = accountService.checkAccount("Legion", "123456");
        System.out.println(personId);
        System.out.println();
        personId = accountService.checkAccount("Le", "123456");
        System.out.println(personId);
        System.out.println();
        personId = accountService.checkAccount("Legion", "111");
        System.out.println(personId);
    }
}
