package dao;


import util.JdbcUtil;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

/**
 * @Author Legion
 * @Date 2020/12/17 10:56
 * @Description 课程选择信息操作
 */
public class SubjectSignUpDao {
    private static final SubjectSignUpDao subjectSignUpDao = new SubjectSignUpDao();
    private SubjectSignUpDao(){}
    public static SubjectSignUpDao getInstance(){
        return subjectSignUpDao;
    }
    /**
     * 根据学生学号检索其所选课程的课程编号
     * @param studentId 学生学号
     * @return 课程列表
     */
    public List<String> searchSubject(String studentId){
        String sql = "select subjectId from subject_signup where studentId = \"" + studentId + "\";";
        return (List<String>) JdbcUtil.search(sql, String.class, true);
    }

    /**
     * 插入一个学生登记记录
     * @param subjectId 科目编号
     * @param studentId 学生编号
     * @return 一个整型，成功返回1，否则返回0
     */
    public int insertSignUp(String subjectId, String studentId) {
        Connection conn = null;
        Statement stmt = null;
        int count = 0;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "insert into subject_signup (subjectId, studentId) values (\"" +
                    subjectId + "\", \"" + studentId + "\");";
            stmt = conn.createStatement();
            count = stmt.executeUpdate(sql);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            JdbcUtil.close(stmt, conn);
        }
        return count;
    }
}
