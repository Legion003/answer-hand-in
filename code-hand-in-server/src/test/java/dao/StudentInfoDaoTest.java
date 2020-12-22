package dao;

import entity.StudentInfo;
import org.junit.Test;

/**
 * @Author Legion
 * @Date 2020/12/16 22:19
 * @Description
 */
public class StudentInfoDaoTest {
    StudentInfoDao studentInfoDao = StudentInfoDao.getInstance();
    @Test
    public void testSearch(){
        StudentInfo studentInfo = studentInfoDao.search("18372026");
        System.out.println(studentInfo);
    }
}
