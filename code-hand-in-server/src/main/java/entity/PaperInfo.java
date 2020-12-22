package entity;

import java.sql.Date;

/**
 * @Author Legion
 * @Date 2020/12/17 17:41
 * @Description
 */
public class PaperInfo {
    private int paperId;
    private String subjectId;
    private String name;
    private String describe;
    private Date deadline;
    private String teacherId;

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }


    @Override
    public String toString() {
        return "PaperInfo{" +
                "paperId=" + paperId +
                ", subjectId='" + subjectId + '\'' +
                ", name='" + name + '\'' +
                ", describe='" + describe + '\'' +
                ", deadline=" + deadline +
                ", teacherId='" + teacherId + '\'' +
                '}';
    }
}
