package controller;

import com.alibaba.fastjson.JSON;
import entity.TeacherInfo;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @Author Legion
 * @Date 2020/12/20 17:25
 * @Description 题目选择页面
 */
public class QuestionSelectorController implements Initializable {
    @FXML
    private VBox container;
    @FXML
    private BorderPane paperIntroduce;
    @FXML
    private Label subjectName;
    @FXML
    private Label paperName;
    @FXML
    private Label describe;
    @FXML
    private Label teacher;
    @FXML
    private Label deadline;
    @FXML
    private ScrollPane slider;

    private Stage stage;
    private Map<String, Object> paperQuestionMap;
    private List<GridPane> questionBlockList = new ArrayList<>();
    private String subjectData;
    private String teacherData;
    private Date deadlineData;
    private String describeData;
    private String paperNameData;
    private int paperId;

    private int count = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initPaperIntroduce();
                createQuestionBlocks();
                initContainer();
                bindUI();
            }
        });
    }

    public void initPaperIntroduce() {
        Map paperInfoMap = JSON.toJavaObject((JSON) paperQuestionMap.get("PaperInfo"), Map.class);
        this.deadlineData = new Date(Long.parseLong(paperInfoMap.get("deadline").toString()));
        this.describeData = paperInfoMap.get("describe").toString();
        this.paperNameData = paperInfoMap.get("name").toString();
        this.paperId = Integer.parseInt(paperInfoMap.get("paperId").toString());
        TeacherInfo teacherInfo = JSON.toJavaObject((JSON) paperQuestionMap.get("teacherInfo"), TeacherInfo.class);
        this.teacherData = teacherInfo.getName();

        this.subjectName.setText(this.subjectData);
        this.paperName.setText(this.paperNameData);
        this.describe.setText(this.describeData);
        this.teacher.setText(this.teacherData);
        this.deadline.setText(this.deadlineData.toString());
    }

    /**
     * 创建单个题目选项条
     */
    public GridPane createQuestionBlock(int questionId, Map questionMap) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/question_block.fxml"));
        GridPane root = null;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                QuestionBlockController questionBlockController = loader.getController();
                String score = questionMap.get("score").toString();
                String fullScore = questionMap.get("fullScore").toString();
                String scoreShow = score + "/" + fullScore;
                questionBlockController.setData(paperId, questionId, count, questionMap.get("title").toString(), scoreShow);
                questionBlockController.setStage(stage);
                if (count % 2 == 0) {
                    GridPane questionBlock = questionBlockController.getQuestionBlock();
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
     * 创建多个题目选项条
     */
    public void createQuestionBlocks() {
        Map questionSimpleInfoMap = JSON.toJavaObject((JSON) paperQuestionMap.get("questionSimpleInfoMap"), Map.class);
        for (Object questionId : questionSimpleInfoMap.keySet()) {
            GridPane questionBlock = createQuestionBlock(Integer.parseInt(questionId.toString()),
                    JSON.toJavaObject((JSON) questionSimpleInfoMap.get(questionId), Map.class));
            questionBlockList.add(questionBlock);
        }
    }

    /**
     * 初始化题目选项列表
     */
    public void initContainer() {
        container.getChildren().addAll(questionBlockList);
    }

    public void setData(Map<String, Object> paperQuestionMap, String subject) {
        this.paperQuestionMap = paperQuestionMap;
        this.subjectData = subject;
    }

    public void bindUI() {
        slider.prefWidthProperty().bind(stage.widthProperty());
        paperIntroduce.prefWidthProperty().bind(stage.widthProperty());
        slider.prefHeightProperty().bind(stage.heightProperty().subtract(paperIntroduce.heightProperty()).subtract(160));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
