package entity;

/**
 * @Author Legion
 * @Date 2020/12/16 21:50
 * @Description
 */
public class TeacherInfo {
    private String teacherId;
    private String name;

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TeacherInfo{" +
                "teacherId='" + teacherId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
