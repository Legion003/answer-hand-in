package dao;

import entity.SubjectInfo;
import util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Legion
 * @Date 2020/12/17 10:36
 * @Description 课程信息相关操作
 */
public class SubjectInfoDao {
    private static final SubjectInfoDao subjectInfoDao = new SubjectInfoDao();
    private SubjectInfoDao(){}
    public static SubjectInfoDao getInstance(){
        return subjectInfoDao;
    }
    /**
     * 根据课程号检索课程信息
     * @param subjectId 课程编号
     * @return 课程信息
     */
    public SubjectInfo searchBySubjectId(String subjectId) {
        String sql = "select * from `subject` where subjectId = \"" + subjectId + "\";";
        return (SubjectInfo) JdbcUtil.search(sql, SubjectInfo.class, false);
    }

    /**
     * 根据教师编号检索课程信息
     * @param teacherId 教师编号
     * @return 课程信息列表
     */
    public List<SubjectInfo> searchByTeacherId(String teacherId) {
        String sql = "select * from `subject` where teacherId = \"" + teacherId + "\"";
        return (List<SubjectInfo>) JdbcUtil.search(sql, SubjectInfo.class, true);
    }
}
