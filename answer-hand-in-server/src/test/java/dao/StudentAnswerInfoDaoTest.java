package dao;

import entity.StudentAnswerInfo;
import org.junit.Test;

import java.util.List;

/**
 * @Author Legion
 * @Date 2020/12/18 16:56
 * @Description
 */
public class StudentAnswerInfoDaoTest {
    StudentAnswerInfoDao studentAnswerInfoDao = StudentAnswerInfoDao.getInstance();
    @Test
    public void insertAnswerTest() {
        int id = studentAnswerInfoDao.insertAnswer(2, 2, "18372026", "一个随便写的答案，用于测试");
        System.out.println(id);
    }

    @Test
    public void updateAnswerTest() {
        int count = studentAnswerInfoDao.updateAnswer(8, "这是修改过的答案");
        System.out.println(count);
    }


    @Test
    public void searchStudentAnswerTest() {
        StudentAnswerInfo studentAnswerInfo = studentAnswerInfoDao.searchStudentAnswer(2, 2, "18372026");
        System.out.println(studentAnswerInfo);
    }

    @Test
    public void searchSingleScoreTest() {
        int score = studentAnswerInfoDao.searchSingleScore(2, 2, "18372026");
        System.out.println(score);
    }
}
