package controller;

import com.alibaba.fastjson.JSON;
import entity.PaperInfo;
import entity.QuestionInfo;
import entity.StudentAnswerInfo;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import util.ConnectUtil;
import util.InfoStorage;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @Author Legion
 * @Date 2020/12/21 21:49
 * @Description
 */
public class QuestionAnswerController implements Initializable {
    @FXML
    private TextArea code;
    @FXML
    private Button submit;
    @FXML
    private BorderPane question;
    @FXML
    private Label questionTitle;
    @FXML
    private BorderPane questionAnswer;
    @FXML
    private Label questionDescribe;
    @FXML
    private Label message;

    private Stage stage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initData();
                bindUI();
            }
        });
    }

    /**
     * 初始化页面数据
     */
    public void initData() {
        QuestionInfo questionInfo = InfoStorage.getQuestionInfo();
        questionTitle.setText(questionInfo.getTitle());
        questionDescribe.setText(questionInfo.getContent());
        StudentAnswerInfo studentAnswerInfo = InfoStorage.getStudentAnswerInfo();
        if (studentAnswerInfo != null) {
            code.setText(studentAnswerInfo.getAnswer());
        }
    }

    /**
     * 提交答案数据
     */
    public void submit() {
        String answerData = code.getText();
        Map<String, Object> paperQuestionMap = InfoStorage.getPaperQuestionMap();
        PaperInfo paperInfo = JSON.toJavaObject((JSON) paperQuestionMap.get("PaperInfo"), PaperInfo.class);
        QuestionInfo questionInfo = InfoStorage.getQuestionInfo();
        Map<String, Object> responseMap = ConnectUtil.writeAnswer(paperInfo.getPaperId(), questionInfo.getQuestionId(), answerData);
        message.setText(responseMap.get("message").toString());
        message.setVisible(true);
    }

    public void bindUI() {
        question.prefWidthProperty().bind(stage.widthProperty());
        code.prefWidthProperty().bind(stage.widthProperty());
        code.prefHeightProperty().bind(stage.heightProperty().subtract(question.heightProperty()).subtract(175));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
