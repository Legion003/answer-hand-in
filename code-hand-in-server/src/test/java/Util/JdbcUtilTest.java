package Util;

import entity.StudentInfo;
import org.junit.Test;
import util.JdbcUtil;

import java.util.List;
import java.util.Map;

/**
 * @Author Legion
 * @Date 2020/12/17 13:32
 * @Description
 */
public class JdbcUtilTest {
    @Test
    public void searchAllSingleTest(){
        String sql = "select * from students where studentId = 18372026";
        StudentInfo studentInfo = (StudentInfo) JdbcUtil.search(sql, StudentInfo.class, false);
        System.out.println(studentInfo);
    }


    @Test
    public void searchOneSingleTest(){
        String sql = "select name from students where studentId = 18372026";
        String name = (String) JdbcUtil.search(sql, String.class, false);
        System.out.println(name);
    }

    @Test
    public void searchOneListTest(){
        String sql = "select subjectId from subject_signup where studentId = 18372026";
        List<String> subjects = (List<String>) JdbcUtil.search(sql, String.class, true);
        System.out.println(subjects);
    }

    @Test
    public void searchManySingleTest(){
        String sql = "select password, personId from accounts where account = \"Legion\"";
        Map<String, String> map = (Map<String, String>) JdbcUtil.search(sql, null, false);
        System.out.println(map.get("password"));
        System.out.println(map.get("personId"));
    }

    @Test
    public void searchManyListTest(){
        String sql = "select studentId, subjectId from subject_signup where studentId = 18372026";
        List<Map<String, String>> mapList = (List<Map<String, String>>) JdbcUtil.search(sql, null, true);
        for (Map<String, String> map : mapList) {
            System.out.println(map.get("studentId"));
            System.out.println(map.get("subjectId"));
            System.out.println();
        }
    }
}
