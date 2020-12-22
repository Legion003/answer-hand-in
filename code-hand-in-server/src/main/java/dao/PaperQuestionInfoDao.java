package dao;

import util.JdbcUtil;

import java.util.List;
import java.util.Map;

/**
 * @Author Legion
 * @Date 2020/12/18 11:21
 * @Description 数据库paper_question表的相关操作
 */
public class PaperQuestionInfoDao {
    private static final PaperQuestionInfoDao paperQuestionInfoDao = new PaperQuestionInfoDao();
    private PaperQuestionInfoDao(){}
    public static PaperQuestionInfoDao getInstance() {
        return paperQuestionInfoDao;
    }
    /**
     * 通过试卷编号检索该试卷所拥有的题目的题目编号
     * @param paperId 试卷编号
     * @return 题目编号列表
     */
    public List<Integer> searchQuestion(int paperId) {
        String sql = "select questionId from paper_question where paperId = " + paperId + ";";
        return (List<Integer>) JdbcUtil.search(sql, int.class, true);
    }

    /**
     * 检索单个题目的满分分数
     * @param paperId 试卷编号
     * @param questionId 题目编号
     * @return 满分分数
     */
    public int searchFullScore(int paperId, int questionId) {
        String sql = "select fullScore from paper_question where paperId = " + paperId + " and questionId = " + questionId + ";";
        return (int) JdbcUtil.search(sql, int.class, false);
    }

    public List<Map<String, Object>> searchQuestionScore(int paperId) {
        String sql = "select questionId, fullScore from paper_question where paperId = " + paperId + ";";
        return (List<Map<String, Object>>) JdbcUtil.search(sql, null, true);
    }
}
