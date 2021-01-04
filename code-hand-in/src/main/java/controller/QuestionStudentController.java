package controller;

import entity.QuestionInfo;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.InfoStorage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @Author Legion
 * @Date 2021/1/4 13:51
 * @Description
 */
public class QuestionStudentController implements Initializable {
    @FXML
    private ScrollPane backboard;
    @FXML
    private VBox container;
    @FXML
    private VBox questionContainer;
    @FXML
    private Label questionTitle;
    @FXML
    private Label questionDescribe;
    private Stage stage;
    private QuestionInfo questionInfo;
    private List<Map> studentScoreList;
    private List<GridPane> studentBlockList = new ArrayList<>();
    private int count = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initQuestionIntroduce();
                createStudentBlocks();
                initContainer();
                bindUI();
            }
        });
    }

    public void initQuestionIntroduce(){
        questionTitle.setText(questionInfo.getTitle());
        questionDescribe.setText(questionInfo.getContent());
    }

    public void createStudentBlocks(){
        for (Map studentScoreMap : studentScoreList) {
            GridPane studentBlock = createStudentBlock(studentScoreMap);
            studentBlockList.add(studentBlock);
        }
    }

    public GridPane createStudentBlock(Map studentScoreMap){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/student_block.fxml"));
        GridPane root = null;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                StudentBlockController studentBlockController = loader.getController();
                int score = 0;
                if (studentScoreMap.containsKey("score")) {
                    score = Integer.parseInt(studentScoreMap.get("score").toString());
                }
                String studentId = (String) studentScoreMap.get("studentId");
                String studentName = (String) studentScoreMap.get("studentName");
                studentBlockController.setData(studentId, studentName, score);
                studentBlockController.setStage(stage);
                if (count % 2 == 0) {
                    GridPane questionBlock = studentBlockController.getStudentBlock();
                    questionBlock.setStyle("-fx-background-color: rgb(250, 250, 250)");
                }
                count++;
            }
        });
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    /**
     * 初始化题目选项列表
     */
    public void initContainer() {
        container.getChildren().addAll(studentBlockList);
    }

    public void setData(QuestionInfo questionInfo, List<Map> studentScoreList) {
        this.questionInfo = questionInfo;
        this.studentScoreList = studentScoreList;
    }

    public void bindUI() {
        questionContainer.prefWidthProperty().bind(stage.widthProperty().subtract(20));
        backboard.prefWidthProperty().bind(stage.widthProperty());
        backboard.prefHeightProperty().bind(stage.heightProperty().subtract(110));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
