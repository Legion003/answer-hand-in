package controller;

import entity.QuestionInfo;
import entity.StudentAnswerInfo;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.ConnectUtil;
import util.InfoStorage;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @Author Legion
 * @Date 2021/1/4 15:01
 * @Description
 */
public class StudentAnswerController implements Initializable {
    @FXML
    private VBox questionContainer;
    @FXML
    private HBox bottomContainer;
    @FXML
    private Label answer;
    @FXML
    private VBox answerContainer;
    @FXML
    private ScrollPane backboard;
    @FXML
    private Label questionTitle;
    @FXML
    private Label questionDescribe;
    @FXML
    private TextField score;
    @FXML
    private Label errorText;
    private QuestionInfo questionInfo;
    private StudentAnswerInfo studentAnswerInfo;
    private Stage stage;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initInterface();
                bindUI();
            }
        });
    }

    public void initInterface(){
        questionTitle.setText(questionInfo.getTitle());
        questionDescribe.setText(questionInfo.getContent());
        answer.setText(studentAnswerInfo.getAnswer());
        score.setText(String.valueOf(studentAnswerInfo.getScore()));
    }

    public void submit(){
        int s = 0;
        try {
            s = Integer.parseInt(score.getText());
        } catch (Exception e) {
            errorText.setText("please input integer");
            return;
        }
        Map<String, Object> responseMap = ConnectUtil.modifyScore(
                studentAnswerInfo.getPaperId(),
                studentAnswerInfo.getQuestionId(),
                studentAnswerInfo.getStudentId(),
                s);
        if (responseMap.get("code").toString().equals("200")) {
            errorText.setText("success");
        } else {
            errorText.setText(responseMap.get("message").toString());
        }
    }

    public void setData(StudentAnswerInfo studentAnswerInfo){
        this.studentAnswerInfo = studentAnswerInfo;
        this.questionInfo = InfoStorage.getQuestionInfo();
    }

    public void bindUI(){
        questionContainer.prefWidthProperty().bind(stage.widthProperty().subtract(50));
        answerContainer.prefWidthProperty().bind(stage.widthProperty().subtract(50));
        bottomContainer.prefWidthProperty().bind(stage.widthProperty().subtract(50));
        backboard.prefWidthProperty().bind(stage.widthProperty());
        backboard.prefHeightProperty().bind(stage.heightProperty().subtract(110));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
