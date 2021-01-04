package dao;

import entity.AccountInfo;
import util.JdbcUtil;

import java.sql.Connection;
import java.sql.Statement;


/**
 * @Author Legion
 * @Date 2020/12/16 21:09
 * @Description 账户信息操作
 */
public class AccountInfoDao {
    private static final AccountInfoDao accountInfoDao = new AccountInfoDao();
    private AccountInfoDao(){}
    public static AccountInfoDao getInstance() {
        return accountInfoDao;
    }

    /**
     * 使用账户名进行检索
     * @param account 账户名
     * @return 账户信息
     */
    public AccountInfo search(String account){
        String sql = "select * from accounts where account = \"" + account + "\";";
        return (AccountInfo) JdbcUtil.search(sql, AccountInfo.class, false);
    }
}
