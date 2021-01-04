package dao;

import entity.TeacherInfo;
import org.junit.Test;

/**
 * @Author Legion
 * @Date 2020/12/16 22:23
 * @Description
 */
public class TeacherInfoDaoTest {
    TeacherInfoDao teacherInfoDao = TeacherInfoDao.getInstance();
    @Test
    public void testSearch(){
        TeacherInfo teacherInfo = teacherInfoDao.search("A001");
        System.out.println(teacherInfo);
    }
}
