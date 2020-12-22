package entity;

/**
 * @Author Legion
 * @Date 2020/12/21 22:29
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
}
