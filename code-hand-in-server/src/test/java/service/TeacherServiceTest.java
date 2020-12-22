package service;

import entity.StudentAnswerInfo;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @Author Legion
 * @Date 2020/12/22 19:25
 * @Description
 */
public class TeacherServiceTest {
    private TeacherService teacherService = TeacherService.getInstance();

    @Test
    public void getSubjectPaperTest(){
        List<Map<String, Object>> subjectPaper = teacherService.getSubjectPaper("A002");
        System.out.println(subjectPaper);
    }

    @Test
    public void getPaperQuestionTest(){
        Map<String, Object> paperQuestion = teacherService.getPaperQuestion(2);
        System.out.println(paperQuestion);
    }

    @Test
    public void getQuestionStudentListTest() {
        Map<String, Object> questionStudentList = teacherService.getQuestionStudentList(2, 2);
        System.out.println(questionStudentList);
    }

    @Test
    public void getStudentAnswerTest() {
        StudentAnswerInfo studentAnswer = teacherService.getStudentAnswer(2, 2, "18372026");
        System.out.println(studentAnswer);
    }

    @Test
    public void modifyScoreTest(){
        int count = teacherService.modifyScore(2, 2, "18372026", 10);
        System.out.println(count);
    }
}
