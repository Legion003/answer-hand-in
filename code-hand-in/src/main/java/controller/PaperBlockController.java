package controller;

import com.alibaba.fastjson.JSONObject;
import entity.AccountInfo;
import entity.PaperInfo;
import entity.SubjectInfo;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import util.ConnectUtil;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * @Author Legion
 * @Date 2020/12/15 16:43
 * @Description 试卷选择栏
 */
public class PaperBlockController implements Initializable {
    @FXML
    Label subject;
    @FXML
    Button name;
    @FXML
    Label describe;
    @FXML
    Label deadline;
    @FXML
    GridPane paperBlock;

    Stage stage;

    private PaperInfo paperInfo;
    private SubjectInfo subjectInfo;

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

    public void initData() {
        subject.setText(subjectInfo.getName());
        name.setText(paperInfo.getName());
        describe.setText(paperInfo.getDescribe());
        Date deadlineData = paperInfo.getDeadline();
        if (deadlineData != null) {
            deadline.setText(deadlineData.toString());
        }
    }

    public PaperInfo getPaperInfo() {
        return paperInfo;
    }

    public void setPaperInfo(PaperInfo paperInfo) {
        this.paperInfo = paperInfo;
    }

    public SubjectInfo getSubjectInfo() {
        return subjectInfo;
    }

    public void setSubjectInfo(SubjectInfo subjectInfo) {
        this.subjectInfo = subjectInfo;
    }

    public void nameFocus(){
        name.setStyle("-fx-text-fill: orange");
    }

    public void nameExit(){
        name.setStyle("-fx-text-fill: #252525");
    }

    public void selectPaper() {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("paperId", paperInfo.getPaperId());
        ConnectUtil.getPaperQuestion(requestMap);
        StudentInterfaceController studentInterfaceController = StudentInterfaceController.getInstance();
        studentInterfaceController.changeToQuestionSelector(subjectInfo.getName());
    }

    public GridPane getPaperBlock() {
        return paperBlock;
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void bindUI(){
        paperBlock.prefWidthProperty().bind(stage.widthProperty().subtract(40));
    }
}
