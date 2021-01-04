package dao;

import entity.TeacherInfo;
import util.JdbcUtil;

import java.sql.Connection;
import java.sql.Statement;

/**
 * @Author Legion
 * @Date 2020/12/16 21:48
 * @Description 教师信息操作
 */
public class TeacherInfoDao {
    private static final TeacherInfoDao teacherInfoDao = new TeacherInfoDao();
    private TeacherInfoDao(){}
    public static TeacherInfoDao getInstance(){
        return teacherInfoDao;
    }

    /**
     * 使用教师编号进行检索
     * @param teacherId 教师编号
     * @return 教师信息
     */
    public TeacherInfo search(String teacherId){
        String sql = "select * from teachers where teacherId = \"" + teacherId + "\";";
        return (TeacherInfo) JdbcUtil.search(sql, TeacherInfo.class, false);
    }
}
