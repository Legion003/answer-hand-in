package service;

import dao.*;
import entity.*;

import java.sql.Date;
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
    private TeacherInfoDao teacherInfoDao = TeacherInfoDao.getInstance();
    private SubjectSignUpDao subjectSignUpDao = SubjectSignUpDao.getInstance();


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
            subjectPaperMap.put("subjectInfo", subjectInfo);
            subjectPaperMap.put("paperInfoList", paperInfoDao.searchBySubjectId(subjectInfo.getSubjectId()));
            subjectPaperList.add(subjectPaperMap);
        }
        return subjectPaperList;
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

    /**
     * 插入一个科目信息
     * @param subjectId 科目编号
     * @param name 科目名称
     * @param teacherId 教师编号
     * @return 一个整型，如果插入成功则返回1，否则返回0
     */
    public int addSubject(String subjectId, String name, String teacherId) {
        return subjectInfoDao.insertSubject(subjectId, name, teacherId);
    }

    /**
     * 插入一个试卷信息
     * @param subjectId 科目编号
     * @param name 试卷名称
     * @param describe 试卷描述
     * @param teacherId 教师编号
     * @return 一个整型，成功返回1，否则返回0
     */
    public int addPaper(String subjectId, String name, String describe, String deadline, String teacherId){
        return paperInfoDao.insertPaper(subjectId, name, describe, deadline, teacherId);
    }

    /**
     * 根据试卷编号获取试卷信息和其对应的题目信息
     * @param paperId 试卷编号
     * @return 试卷信息和相应的题目信息
     */
    public Map<String, Object> getPaperQuestion(int paperId) {
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
            map.put("fullScore", paperQuestionInfoDao.searchFullScore(paperId, questionId));
            questionSimpleInfoMap.put(questionId, map);
        }
        paperQuestionInfo.put("questionSimpleInfoMap", questionSimpleInfoMap);
        return paperQuestionInfo;
    }

    /**
     * 增加一个题目
     * @param paperId 试卷编号
     * @param title 题目标题
     * @param content 题目内容
     * @param fullScore 满分分数
     * @return 一个整型，成功返回1，否则返回0
     */
    public int addQuestion(int paperId, String title, String content, int fullScore) {
        int id = questionInfoDao.insertQuestion(title, content);
        if (id == 0){
            return 0;
        }
        int count = paperQuestionInfoDao.insertPaperQuestion(paperId, id, fullScore);
        return count;
    }

    /**
     * 增加科目对应的学生
     * @param subjectId 科目编号
     * @param studentId 学生学号
     * @return
     */
    public int addStudent(String subjectId, String studentId){
        StudentInfo studentInfo = StudentInfoDao.getInstance().search(studentId);
        // 如果没有这个学生登记在册，则增加失败
        if (studentInfo == null) {
            return  0;
        }
        return subjectSignUpDao.insertSignUp(subjectId, studentId);
    }

}
