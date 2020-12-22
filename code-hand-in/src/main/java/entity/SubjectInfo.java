package entity;

/**
 * @Author Legion
 * @Date 2020/12/17 10:29
 * @Description 课程信息
 */
public class SubjectInfo {
    private String subjectId;
    private String name;
    private String teacherId;

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "SubjectInfo{" +
                "subjectId='" + subjectId + '\'' +
                ", name='" + name + '\'' +
                ", teacherId='" + teacherId + '\'' +
                '}';
    }
}
