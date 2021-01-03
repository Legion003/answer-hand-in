package util;

import entity.AccountInfo;
import entity.PaperInfo;
import entity.QuestionInfo;
import entity.StudentAnswerInfo;

import java.util.List;
import java.util.Map;

/**
 * @Author Legion
 * @Date 2020/12/21 13:09
 * @Description
 */
public class InfoStorage {
    private static AccountInfo accountInfo;
    private static List<Map> subjectPaperList;
    private static Map<String, Object> paperQuestionMap;
    private static QuestionInfo questionInfo;
    private static StudentAnswerInfo studentAnswerInfo;
    private static PaperInfo paperInfo;
    private static List<Map> studentScoreList;

    public static AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public static void setAccountInfo(AccountInfo accountInfo) {
        InfoStorage.accountInfo = accountInfo;
    }

    public static List<Map> getSubjectPaperList() {
        return subjectPaperList;
    }

    public static void setSubjectPaperList(List<Map> subjectPaperList) {
        InfoStorage.subjectPaperList = subjectPaperList;
    }

    public static Map<String, Object> getPaperQuestionMap() {
        return paperQuestionMap;
    }

    public static void setPaperQuestionMap(Map<String, Object> paperQuestionMap) {
        InfoStorage.paperQuestionMap = paperQuestionMap;
    }

    public static QuestionInfo getQuestionInfo() {
        return questionInfo;
    }

    public static void setQuestionInfo(QuestionInfo questionInfo) {
        InfoStorage.questionInfo = questionInfo;
    }

    public static StudentAnswerInfo getStudentAnswerInfo() {
        return studentAnswerInfo;
    }

    public static void setStudentAnswerInfo(StudentAnswerInfo studentAnswerInfo) {
        InfoStorage.studentAnswerInfo = studentAnswerInfo;
    }

    public static PaperInfo getPaperInfo() {
        return paperInfo;
    }

    public static void setPaperInfo(PaperInfo paperInfo) {
        InfoStorage.paperInfo = paperInfo;
    }

    public static List<Map> getStudentScoreList() {
        return studentScoreList;
    }

    public static void setStudentScoreList(List<Map> studentScoreList) {
        InfoStorage.studentScoreList = studentScoreList;
    }
}
