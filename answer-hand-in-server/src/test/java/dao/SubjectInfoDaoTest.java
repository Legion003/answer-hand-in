package dao;

import entity.SubjectInfo;
import org.junit.Test;

/**
 * @Author Legion
 * @Date 2020/12/17 10:52
 * @Description
 */
public class SubjectInfoDaoTest {
    SubjectInfoDao subjectInfoDao = SubjectInfoDao.getInstance();
    @Test
    public void searchTest(){
        SubjectInfo subjectInfo = subjectInfoDao.searchBySubjectId("S001");
        System.out.println(subjectInfo);
    }
}
