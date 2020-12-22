package dao;

import entity.QuestionInfo;
import util.JdbcUtil;

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

}
