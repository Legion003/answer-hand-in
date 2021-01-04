package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import util.ConnectUtil;
import util.InfoStorage;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @Author Legion
 * @Date 2020/12/20 17:25
 * @Description 题目选项条的控制操作
 */
public class QuestionBlockController implements Initializable {
    @FXML
    private GridPane questionBlock;
    @FXML
    private Label number;
    @FXML
    private Button title;
    @FXML
    private Label score;

    private Stage stage;

    private int questionId;
    private int numberData;
    private String titleData;
    private String scoreData;
    private int paperId;

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
     * 选择题目
     */
    public void selectQuestion(){
        if (InfoStorage.getAccountInfo().isPersonType()) {
            ConnectUtil.getTeacherQuestionStudent(paperId, questionId);
            InterfaceController interfaceController = InterfaceController.getInstance();
            interfaceController.initQuestionStudent();
            interfaceController.changeToQuestionStudent();
        } else {
            ConnectUtil.getQuestionAnswer(paperId, questionId);
            InterfaceController interfaceController = InterfaceController.getInstance();
            interfaceController.changeToQuestionAnswer();
        }
    }

    /**
     * 设置数据
     */
    public void setData(int paperId, int questionId, int numberData, String titleData, String scoreData){
        this.paperId = paperId;
        this.questionId = questionId;
        this.numberData = numberData;
        this.titleData = titleData;
        this.scoreData = scoreData;
    }

    /**
     * 初始化数据，将数据放入组件中
     */
    public void initData() {
        number.setText(String.valueOf(numberData));
        title.setText(titleData);
        score.setText(scoreData);
    }



    public void titleFocus(){
        title.setStyle("-fx-text-fill: orange");
    }

    public void titleExit(){
        title.setStyle("-fx-text-fill: #252525");
    }

    public void bindUI() {
        questionBlock.prefWidthProperty().bind(stage.widthProperty().subtract(60));
    }

    public GridPane getQuestionBlock() {
        return questionBlock;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
