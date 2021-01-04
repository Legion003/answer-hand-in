package entity;

/**
 * @Author Legion
 * @Date 2020/12/17 23:59
 * @Description
 */
public class QuestionInfo {
    private int questionId;
    private String title;
    private String content;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "QuestionInfo{" +
                "questionId=" + questionId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
