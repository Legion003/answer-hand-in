package service;

import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @Author Legion
 * @Date 2020/12/19 14:56
 * @Description
 */
public class StudentServiceTest {
    StudentService studentService = StudentService.getInstance();
    @Test
    public void getPaperQuestionTest() {
        Map<String, Object> paperQuestionInfo = studentService.getPaperQuestion(2, "18372026");
        System.out.println(paperQuestionInfo);
    }

    @Test
    public void writeAnswerTest() {
        System.out.println(studentService.writeAnswer(2, 2, "18372026", "测试writeAnswer"));
        System.out.println(studentService.writeAnswer(1, 1, "18372026", "测试writeAnswer"));

    }

    @Test
    public void getSubjectPaperTest(){
        List<Map<String, Object>> subjectPaperInfoList = studentService.getSubjectPaper("18372026");
        System.out.println(subjectPaperInfoList);
    }

    @Test
    public void getQuestionAndPreviousAnswerTest() {
        Map<String, Object> questionAndPreviousAnswer = studentService.getQuestionAndPreviousAnswer(2, 2, "18372026");
        System.out.println(questionAndPreviousAnswer);
    }
}
