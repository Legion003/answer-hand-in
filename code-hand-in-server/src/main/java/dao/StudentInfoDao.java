package dao;

import entity.StudentInfo;
import util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author Legion
 * @Date 2020/12/16 21:48
 * @Description 学生信息操作
 */
public class StudentInfoDao {
    private static final StudentInfoDao studentInfoDao = new StudentInfoDao();
    private StudentInfoDao(){}
    public static StudentInfoDao getInstance(){
        return studentInfoDao;
    }
    /**
     * 根据学生学号进行检索
     * @param studentId 学生学号
     * @return 学生信息
     */
    public StudentInfo search(String studentId){
        String sql = "select * from students where studentId = \"" + studentId + "\";";
        return (StudentInfo) JdbcUtil.search(sql, StudentInfo.class, false);
    }

}
