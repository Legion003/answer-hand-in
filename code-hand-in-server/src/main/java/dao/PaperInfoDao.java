package dao;

import entity.PaperInfo;
import util.JdbcUtil;

import java.sql.Date;
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
}
