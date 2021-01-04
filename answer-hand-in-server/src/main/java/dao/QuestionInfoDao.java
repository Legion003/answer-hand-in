package dao;

import entity.QuestionInfo;
import util.JdbcUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * @Author Legion
 * @Date 2020/12/18 10:39
 * @Description 数据库中Question表的相关操作
 */
public class QuestionInfoDao {
    private static final QuestionInfoDao questionInfoDao = new QuestionInfoDao();
    private QuestionInfoDao(){}
    public static QuestionInfoDao getInstance(){
        return questionInfoDao;
    }

    /**
     * 根据题目编号检索题目信息
     * @param questionId 题目编号
     * @return 题目信息
     */
    public QuestionInfo search(int questionId) {
        String sql = "select * from question where questionId = " + questionId + ";";
        return (QuestionInfo) JdbcUtil.search(sql, QuestionInfo.class, false);
    }

    /**
     * 根据题目编号检索题目标题
     * @param questionId 题目编号
     * @return 题目标题
     */
    public String searchForTitle(int questionId) {
        String sql = "select title from question where questionId = " + questionId + ";";
        return (String) JdbcUtil.search(sql, String.class, false);
    }

    /**
     * 插入一个题目
     * @param title 题目标题
     * @param content 题目内容
     * @return 若插入成功返回这个题目的id，否则返回0
     */
    public int insertQuestion(String title, String content) {
        Connection conn = null;
        Statement stmt = null;
        int id = 0;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "insert into question (title, content) values " +
                    "(\"" + title + "\", \"" + content + "\");";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.close(stmt, conn);
        }
        return id;
    }
}
