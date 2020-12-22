package dao;

import entity.PaperInfo;
import org.junit.Test;

import java.sql.Date;
import java.util.List;

/**
 * @Author Legion
 * @Date 2020/12/17 22:48
 * @Description
 */
public class PaperInfoDaoTest {
    PaperInfoDao paperInfoDao = PaperInfoDao.getInstance();
    @Test
    public void searchBySubjectIdTest() {
        List<PaperInfo> paperInfoList = paperInfoDao.searchBySubjectId("S002");
        System.out.println(paperInfoList);
    }

    @Test
    public void searchByPaperIdTesd() {
        PaperInfo paperInfo = paperInfoDao.searchByPaperId(2);
        System.out.println(paperInfo);
    }

    @Test
    public void searchByTeacherIdTest() {
        List<PaperInfo> paperInfoList = paperInfoDao.searchByTeacherId("A002");
        System.out.println(paperInfoList);
    }

    @Test
    public void searchDateTest() {
        Date deadline = paperInfoDao.searchDate(2);
        System.out.println(deadline);
    }
}
