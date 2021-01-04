package dao;

import entity.AccountInfo;
import org.junit.Test;

import java.sql.Time;

/**
 * @Author Legion
 * @Date 2020/12/16 22:02
 * @Description
 */

public class AccountInfoDaoTest {
    AccountInfoDao accountInfoDao = AccountInfoDao.getInstance();
    @Test
    public void searchTest(){
        long start = System.currentTimeMillis();
        AccountInfo accountInfo = accountInfoDao.search("Legion");
        System.out.println(accountInfo);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
