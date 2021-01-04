package entity;

/**
 * @Author Legion
 * @Date 2020/12/16 21:50
 * @Description 学生信息
 */
public class StudentInfo {
    private String studentId;
    private String name;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
                "studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
