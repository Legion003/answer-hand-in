package dao;

import org.junit.Test;

import java.util.List;

/**
 * @Author Legion
 * @Date 2020/12/17 17:38
 * @Description
 */
public class SubjectSignUpDaoTest {
    SubjectSignUpDao subjectSignUpDao = SubjectSignUpDao.getInstance();
    @Test
    public void searchSubjectTest(){
        List<String> subjects = subjectSignUpDao.searchSubject("18372026");
        System.out.println(subjects);
    }
}
