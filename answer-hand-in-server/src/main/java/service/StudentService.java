package service;

import dao.*;
import entity.PaperInfo;
import entity.QuestionInfo;
import entity.StudentAnswerInfo;
import entity.TeacherInfo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Legion
 * @Date 2020/12/19 14:18
 * @Description 学生相关操作
 */
public class StudentService {
    private static final StudentService studentService = new StudentService();
    private SubjectSignUpDao subjectSignUpDao = SubjectSignUpDao.getInstance();
    private SubjectInfoDao subjectInfoDao = SubjectInfoDao.getInstance();
    private PaperInfoDao paperInfoDao = PaperInfoDao.getInstance();
    private TeacherInfoDao teacherInfoDao = TeacherInfoDao.getInstance();
    private PaperQuestionInfoDao paperQuestionInfoDao = PaperQuestionInfoDao.getInstance();
    private QuestionInfoDao questionInfoDao = QuestionInfoDao.getInstance();
    private StudentAnswerInfoDao studentAnswerInfoDao = StudentAnswerInfoDao.getInstance();
    private StudentService(){}
    public static StudentService getInstance(){
        return studentService;
    }

    /**
     * 根据account获取其所拥有的科目和试卷信息
     * @param personId 人员编号
     * @return 与该人对应的科目以及这些科目对应的试卷
     */
    public List<Map<String, Object>> getSubjectPaper(String personId) {
        List<Map<String, Object>> subjectPaperInfoList = new ArrayList<>();
        List<String> subjectIdList = subjectSignUpDao.searchSubject(personId);
        if(subjectIdList != null) {
            for (String subjectId : subjectIdList) {
                Map<String, Object> subjectPaperInfo = new HashMap<>();
                subjectPaperInfo.put("subjectInfo", subjectInfoDao.searchBySubjectId(subjectId));
                subjectPaperInfo.put("paperInfoList", paperInfoDao.searchBySubjectId(subjectId));
                subjectPaperInfoList.add(subjectPaperInfo);
            }
        }
        return subjectPaperInfoList;
    }

    /**
     * 根据试卷编号获取试卷信息和其对应的题目信息
     * @param paperId 试卷编号
     * @param studentId 学生学号
     * @return 试卷信息和相应的题目信息
     */
    public Map<String, Object> getPaperQuestion(int paperId, String studentId) {
        Map<String, Object> paperQuestionInfo = new HashMap<>();
        PaperInfo paperInfo = paperInfoDao.searchByPaperId(paperId);
        paperQuestionInfo.put("PaperInfo", paperInfo);
        TeacherInfo teacherInfo = teacherInfoDao.search(paperInfo.getTeacherId());
        paperQuestionInfo.put("teacherInfo", teacherInfo);
        List<Integer> questionIdList = paperQuestionInfoDao.searchQuestion(paperId);
        Map<Integer, Map<String, Object>> questionSimpleInfoMap = new HashMap<>();
        for (Integer questionId : questionIdList) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", questionInfoDao.searchForTitle(questionId));
            map.put("score", studentAnswerInfoDao.searchSingleScore(paperId, questionId, studentId));
            map.put("fullScore", paperQuestionInfoDao.searchFullScore(paperId, questionId));
            questionSimpleInfoMap.put(questionId, map);
        }
        paperQuestionInfo.put("questionSimpleInfoMap", questionSimpleInfoMap);
        return paperQuestionInfo;
    }

    /**
     * 获取单个题目信息以及之前的答题情况
     * @param paperId 试卷编号
     * @param questionId 题目编号
     * @param studentId 学生学号
     * @return 题目信息和答题情况
     */
    public Map<String, Object> getQuestionAndPreviousAnswer(int paperId, int questionId, String studentId) {
        Map<String, Object> questionAndAnswerMap = new HashMap<>();
        // 获取题目信息
        QuestionInfo questionInfo = questionInfoDao.search(questionId);
        questionAndAnswerMap.put("questionInfo", questionInfo);
        // 获取之前的答案信息
        StudentAnswerInfo studentAnswerInfo = studentAnswerInfoDao.searchStudentAnswer(paperId, questionId, studentId);
        questionAndAnswerMap.put("studentAnswerInfo", studentAnswerInfo);
        return questionAndAnswerMap;
    }

    /**
     * 写答案，如果之前写过，就更新答案记录；如果之前没有写过，就增添答案记录
     * @param paperId 试卷编号
     * @param questionId 目编号
     * @param studentId 学生学号
     * @param answer 答案
     * @return 一个整型，当返回0时失败，当返回n正整数时成功
     */
    public int writeAnswer(int paperId, int questionId, String studentId, String answer) {
        // 检测是否已经到了deadline
        Date deadline = paperInfoDao.searchDate(paperId);
        if (deadline == null){
            StudentAnswerInfo studentAnswerInfo = studentAnswerInfoDao.searchStudentAnswer(paperId, questionId, studentId);
            // 该学生之前提交过答案
            if (studentAnswerInfo != null) {
                // 获取这个答案对应的id
                int answerId = studentAnswerInfo.getId();
                // 根据id更新答案
                return studentAnswerInfoDao.updateAnswer(answerId, answer);
            } else {
                return studentAnswerInfoDao.insertAnswer(paperId, questionId, studentId, answer);
            }
        }
        Date today = new Date(System.currentTimeMillis());
        // 如果已经超时，则无法写答案
        if (deadline.before(today)) {
            return 0;
        }
        StudentAnswerInfo studentAnswerInfo = studentAnswerInfoDao.searchStudentAnswer(paperId, questionId, studentId);
        // 该学生之前提交过答案
        if (studentAnswerInfo != null) {
            // 获取这个答案对应的id
            int answerId = studentAnswerInfo.getId();
            // 根据id更新答案
            return studentAnswerInfoDao.updateAnswer(answerId, answer);
        } else {
            return studentAnswerInfoDao.insertAnswer(paperId, questionId, studentId, answer);
        }
    }




}
