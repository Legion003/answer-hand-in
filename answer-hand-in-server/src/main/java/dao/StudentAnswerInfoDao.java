package dao;

import entity.StudentAnswerInfo;
import util.JdbcUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * @Author Legion
 * @Date 2020/12/18 14:35
 * @Description 数据库中student_answer表相关操作
 */
public class StudentAnswerInfoDao {
    private static final StudentAnswerInfoDao studentAnswerInfoDao = new StudentAnswerInfoDao();
    private StudentAnswerInfoDao(){}
    public static StudentAnswerInfoDao getInstance(){
        return  studentAnswerInfoDao;
    }
    /**
     * 插入答案
     * @param paperId 试卷编号
     * @param questionId 题目编号
     * @param studentId 学生学号
     * @param answer 答案
     * @return 一个整型，当返回0时插入失败，当返回非0时，返回的是插入该表后的自增id
     */
    public int insertAnswer(int paperId, int questionId, String studentId, String answer) {
        Connection conn = null;
        Statement stmt = null;
        int id = 0;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "insert into student_answer (paperId, questionId, studentId, answer) values " +
                    "(" + paperId + ", " + questionId + ", \"" + studentId + "\", \"" + answer + "\");";
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

    /**
     * 更新答案
     * @param answerId 表中答案的id
     * @param answer 答案
     * @return 一个整型，当返回0时更新失败，当返回1时更新成功
     */
    public int updateAnswer(int answerId, String answer) {
        Connection conn = null;
        Statement stmt = null;
        int count = 0;
        try {
            conn = JdbcUtil.getConnection();
            stmt = conn.createStatement();
            String sql = "update student_answer set answer = \"" + answer + "\" where id = " + answerId + ";";
            count = stmt.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.close(stmt, conn);
        }
        return count;
    }

    /**
     * 根据题目编号检索出所有的答案情况
     * @param questionId 题目编号
     * @return 该试卷的所有答案情况
     */
    public List<Map<String, Object>> searchStudentScore(int paperId, int questionId) {
        String sql = "select studentId, score from student_answer where paperId = " + paperId + " and questionId = " + questionId + ";";
        return (List<Map<String, Object>>) JdbcUtil.search(sql, null, true);
    }


    /**
     * 根据试卷编号，题目编号，学生学号检索该学生的答案
     * @param paperId 试卷编号
     * @param questionId 题目编号
     * @param studentId 学生学号
     * @return 该学生在该试题上的答案，如果没有则返回null
     */
    public StudentAnswerInfo searchStudentAnswer(int paperId, int questionId, String studentId) {
        String sql = "select * from student_answer where " +
                "paperId = " + paperId + " and questionId = " + questionId + " and studentId = \"" + studentId + "\";";
        return (StudentAnswerInfo) JdbcUtil.search(sql, StudentAnswerInfo.class, false);
    }

    public int searchSingleScore(int paperId, int questionId, String studentId) {
        String sql = "select score from student_answer where " +
                "paperId = " + paperId + " and questionId = " + questionId + " and studentId = \"" + studentId + "\";";
        Object obj = JdbcUtil.search(sql, int.class, false);
        if (obj != null) {
            return (int) obj;
        } else {
            return 0;
        }
    }

    public int updateStudentScore(int paperId, int questionId, String studentId, int score) {
        Connection conn = null;
        Statement stmt = null;
        int count = 0;
        try {
            conn = JdbcUtil.getConnection();
            stmt = conn.createStatement();
            String sql = "update student_answer set score = " + score + " where " +
                    "paperId = " + paperId + " and questionId = " + questionId + " and studentId = \"" + studentId + "\";";
            count = stmt.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcUtil.close(stmt, conn);
        }
        return count;
    }


}
