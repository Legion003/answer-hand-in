package entity;

/**
 * @Author Legion
 * @Date 2020/12/18 14:48
 * @Description 学生试题答案信息
 */
public class StudentAnswerInfo {
    private int id;
    private int paperId;
    private int questionId;
    private String studentId;
    private String answer;
    private int score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "StudentAnswerInfo{" +
                "id=" + id +
                ", paperId=" + paperId +
                ", questionId=" + questionId +
                ", studentId='" + studentId + '\'' +
                ", answer='" + answer + '\'' +
                ", score=" + score +
                '}';
    }
}
