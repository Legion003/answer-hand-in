package dao;

import entity.PaperInfo;
import util.JdbcUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.util.List;

/**
 * @Author Legion
 * @Date 2020/12/17 17:42
 * @Description 试卷信息操作
 */
public class PaperInfoDao {
    private static final PaperInfoDao paperInfoDao = new PaperInfoDao();
    private PaperInfoDao(){}
    public static PaperInfoDao  getInstance(){
        return paperInfoDao;
    }
    /**
     * 根据课程编号检索相关的试卷信息
     * @param subjectId 课程编号
     * @return 试卷列表
     */
    public List<PaperInfo> searchBySubjectId(String subjectId){
        String sql = "select * from paper where subjectId = \"" + subjectId + "\";";
        return (List<PaperInfo>) JdbcUtil.search(sql, PaperInfo.class, true);
    }

    /**
     * 根据试卷编号检索相关的试卷信息
     * @param paperId 试卷编号
     * @return 试卷信息
     */
    public PaperInfo searchByPaperId(int paperId) {
        String sql = "select * from paper where paperId = " + paperId + ";";
        return (PaperInfo) JdbcUtil.search(sql, PaperInfo.class, false);
    }

    /**
     * 根据教师编号检索老师负责的试卷
     * @param teacherId 教师编号
     * @return 该老师负责的试卷信息列表
     */
    public List<PaperInfo> searchByTeacherId(String teacherId) {
        String sql = "select * from paper where teacherId = \"" + teacherId + "\";";
        return (List<PaperInfo>) JdbcUtil.search(sql, PaperInfo.class, true);
    }

    /**
     * 根据试卷编号检索该试卷的完成截止日期
     * @param paperId 试卷编号
     * @return 截止日期
     */
    public Date searchDate(int paperId) {
        String sql = "select deadline from paper where paperId = " + paperId + ";";
        return (Date) JdbcUtil.search(sql, Date.class, false);
    }

    /**
     * 插入一个试卷信息
     * @param subjectId 科目编号
     * @param name 试卷名称
     * @param describe 试卷描述
     * @param teacherId 教师编号
     * @return 一个整型，成功返回1，否则返回0
     */
    public int insertPaper(String subjectId, String name, String describe, String deadline, String teacherId) {
        Connection conn = null;
        Statement stmt = null;
        int count = 0;
        try {
            conn = JdbcUtil.getConnection();
            String sql = null;
            if (deadline.equals("")) {
                sql = "insert into paper (subjectId, name, `describe`, deadline, teacherId) values (\"" +
                        subjectId + "\", \"" + name + "\", \"" + describe + "\", null , \"" + teacherId + "\");";
            } else {
                sql = "insert into paper (subjectId, name, `describe`, deadline, teacherId) values (\"" +
                        subjectId + "\", \"" + name + "\", \"" + describe + "\", \"" + deadline + "\", \"" + teacherId + "\");";
            }
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
