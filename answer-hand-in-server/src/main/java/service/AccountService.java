package service;

import dao.AccountInfoDao;
import entity.AccountInfo;
import util.JdbcUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Author Legion
 * @Date 2020/12/16 22:25
 * @Description 登陆相关操作
 */
public class AccountService {
    private static final AccountService ACCOUNT_SERVICE = new AccountService();
    private AccountInfoDao accountInfoDao = AccountInfoDao.getInstance();

    private AccountService(){}
    public static AccountService getInstance(){
        return ACCOUNT_SERVICE;
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

    /**
     * 注册
     * @param name 姓名
     * @param personId 人员编号
     * @param account 账号
     * @param password 密码
     * @param personType 是否是教师，是则为true，否则为false
     * @return 返回一个整型，成功返回1，否则返回0
     */
    public int signIn(String name, String personId, String account,String password, boolean personType) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = JdbcUtil.getConnection();
            conn.setAutoCommit(false);
            String sql1 = null;
            if (personType) {
                sql1 = "insert into teachers (teacherId, name) values (\"" +
                        personId + "\", \"" + name + "\");";
            } else {
                sql1 = "insert into students (studentId, name) values (\"" +
                        personId + "\", \"" + name + "\");";
            }
            stmt = conn.createStatement();
            stmt.executeUpdate(sql1);
            String sql2 = "insert into accounts (account, password, personId, personType) values (\"" +
                    account + "\", \"" + password + "\", \"" + personId + "\", " + personType + ");";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql2);
            conn.commit();
        } catch (Exception e){
            //事务回滚操作
            try {
                if(conn!=null) {
                    //在catch中回滚
                    conn.rollback();
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            e.printStackTrace();
            return 0;
        } finally {
            JdbcUtil.close(stmt, conn);
        }
        return 1;
    }


}
