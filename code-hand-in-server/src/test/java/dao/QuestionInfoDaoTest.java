package dao;

import entity.QuestionInfo;
import org.junit.Test;

/**
 * @Author Legion
 * @Date 2020/12/18 11:19
 * @Description
 */
public class QuestionInfoDaoTest {
    QuestionInfoDao questionInfoDao = QuestionInfoDao.getInstance();
    @Test
    public void searchTest() {
        QuestionInfo questionInfo = questionInfoDao.search(1);
        System.out.println(questionInfo);
    }

    @Test
    public void searchForTitleTest() {
        String title = questionInfoDao.searchForTitle(2);
        System.out.println(title);
    }
}
