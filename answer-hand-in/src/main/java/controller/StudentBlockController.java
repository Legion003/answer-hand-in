package controller;

import com.alibaba.fastjson.JSON;
import entity.PaperInfo;
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
 * @Date 2021/1/4 13:53
 * @Description
 */
public class StudentBlockController implements Initializable {
    @FXML
    private GridPane studentBlock;
    @FXML
    private Label studentId;
    @FXML
    private Button studentName;
    @FXML
    private Label studentScore;

    private Stage stage;

    private String id;
    private int score;
    private String name;

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
    public void selectStudent(){
        Map<String, Object> paperQuestion = InfoStorage.getPaperQuestionMap();
        PaperInfo paperInfo = JSON.toJavaObject((JSON) paperQuestion.get("PaperInfo"), PaperInfo.class);
        ConnectUtil.getTeacherStudentAnswer(paperInfo.getPaperId(), InfoStorage.getQuestionInfo().getQuestionId(), id);
        InterfaceController interfaceController = InterfaceController.getInstance();
        interfaceController.initStudentAnswer();
        interfaceController.changeToStudentAnswer();
    }

    /**
     * 设置数据
     */
    public void setData(String id, String name, int score){
        this.id = id;
        this.name = name;
        this.score = score;
    }

    /**
     * 初始化数据，将数据放入组件中
     */
    public void initData() {
        studentId.setText(id);
        studentName.setText(name);
        studentScore.setText(String.valueOf(score));
    }

    public void nameFocus(){
        studentName.setStyle("-fx-text-fill: orange");
    }

    public void nameExit(){
        studentName.setStyle("-fx-text-fill: #252525");
    }

    public void bindUI() {
        studentBlock.prefWidthProperty().bind(stage.widthProperty().subtract(60));
    }

    public GridPane getStudentBlock() {
        return studentBlock;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
