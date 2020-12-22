package service;

import dao.*;
import entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Legion
 * @Date 2020/12/19 14:18
 * @Description 教师相关操作
 */
public class TeacherService {
    private static final TeacherService teacherService = new TeacherService();
    private TeacherService(){}
    public static TeacherService getInstance(){
        return teacherService;
    }

    private PaperInfoDao paperInfoDao = PaperInfoDao.getInstance();
    private SubjectInfoDao subjectInfoDao = SubjectInfoDao.getInstance();
    private PaperQuestionInfoDao paperQuestionInfoDao = PaperQuestionInfoDao.getInstance();
    private QuestionInfoDao questionInfoDao = QuestionInfoDao.getInstance();
    private StudentAnswerInfoDao studentAnswerInfoDao = StudentAnswerInfoDao.getInstance();
    private StudentInfoDao studentInfoDao = StudentInfoDao.getInstance();


    /**
     * 获取课程和试卷信息
     * @param teacherId 教师编号
     * @return 课程和试卷信息
     */
    public List<Map<String, Object>> getSubjectPaper(String teacherId) {
        List<Map<String, Object>> subjectPaperList = new ArrayList<>();
        List<SubjectInfo> subjectInfoList = subjectInfoDao.searchByTeacherId(teacherId);
        for (SubjectInfo subjectInfo : subjectInfoList) {
            Map<String, Object> subjectPaperMap = new HashMap<>();
            subjectPaperMap.put("SubjectInfo", subjectInfo);
            subjectPaperMap.put("PaperInfoList", paperInfoDao.searchBySubjectId(subjectInfo.getSubjectId()));
            subjectPaperList.add(subjectPaperMap);
        }
        return subjectPaperList;
    }

    /**
     * 根据试卷编号检索相关的试卷信息和题目信息列表
     * @param paperId 试卷编号
     * @return 试卷信息和题目基础信息列表
     */
    public Map<String, Object> getPaperQuestion(int paperId) {
        Map<String, Object> paperQuestionInfo = new HashMap<>();
        PaperInfo paperInfo = paperInfoDao.searchByPaperId(paperId);
        paperQuestionInfo.put("PaperInfo", paperInfo);
        List<Map<String, Object>> questionInfoList = paperQuestionInfoDao.searchQuestionScore(paperId);
        for (Map<String, Object> questionInfoMap : questionInfoList) {
            int questionId = Integer.parseInt(questionInfoMap.get("questionId").toString());
            questionInfoMap.put("title", questionInfoDao.searchForTitle(questionId));
        }
        paperQuestionInfo.put("QuestionInfoList", questionInfoList);
        return paperQuestionInfo;
    }

    /**
     * 根据试卷编号和问题编号检索出学生们的答案
     * @param paperId 试卷编号
     * @param questionId 题目编号
     * @return 学生答案信息列表
     */
    public Map<String, Object> getQuestionStudentList(int paperId, int questionId) {
        Map<String, Object> questionStudent = new HashMap<>();
        QuestionInfo questionInfo = questionInfoDao.search(questionId);
        questionStudent.put("QuestionInfo", questionInfo);
        List<Map<String, Object>> studentScoreList = studentAnswerInfoDao.searchStudentScore(paperId, questionId);
        for (Map<String, Object> studentScoreMap : studentScoreList) {
            StudentInfo studentInfo = studentInfoDao.search((String) studentScoreMap.get("studentId"));
            studentScoreMap.put("studentName", studentInfo.getName());
        }
        questionStudent.put("StudentScoreList", studentScoreList);
        return questionStudent;
    }

    /**
     * 检索单个学生的答案情况
     * @param paperId 试卷编号
     * @param questionId 题目编号
     * @param studentId 学生学号
     * @return 学生答案信息
     */
    public StudentAnswerInfo getStudentAnswer(int paperId, int questionId, String studentId) {
        return studentAnswerInfoDao.searchStudentAnswer(paperId, questionId, studentId);
    }

    /**
     * 修改学生得分
     * @param paperId 试卷编号
     * @param questionId 题目编号
     * @param studentId 学生学号
     * @param score 分数
     * @return 一个整型，当返回0时修改失败，当返回1时修改成功
     */
    public int modifyScore(int paperId, int questionId, String studentId, int score) {
        int fullScore = paperQuestionInfoDao.searchFullScore(paperId, questionId);
        if (score > fullScore) {
            return 0;
        }
        int count = studentAnswerInfoDao.updateStudentScore(paperId, questionId, studentId, score);
        return count;
    }
}
