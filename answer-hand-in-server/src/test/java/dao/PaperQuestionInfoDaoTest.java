package dao;

import org.junit.Test;

import java.util.List;

/**
 * @Author Legion
 * @Date 2020/12/18 11:25
 * @Description
 */
public class PaperQuestionInfoDaoTest {
    PaperQuestionInfoDao paperQuestionInfoDao = PaperQuestionInfoDao.getInstance();
    @Test
    public void searchQuestionTest(){
        List<Integer> questionIdList = paperQuestionInfoDao.searchQuestion(2);
        System.out.println(questionIdList);
    }
}
