package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.InfoStorage;
import util.StageList;
import util.InterfacePhase;

import java.io.IOException;
import java.util.Map;

/**
 * @Author Legion
 * @Date 2020/12/15 19:43
 * @Description 主界面入口：试卷选择页面
 */
public class InterfaceController {
    private Pane root;
    private PaperSelectorController paperSelectorController;
    private VBox paperSelector;
    private BorderPane questionSelector;
    private ScrollPane questionStudentSelector;
    private QuestionSelectorController questionSelectorController;
    private QuestionAnswerController questionAnswerController;
    private BackBoardController backBoardController;
    private QuestionStudentController questionStudentController;
    private Stage stage;
    private InterfacePhase phase = InterfacePhase.STUDENT_PAPER_SELECTOR;

    private static final InterfaceController INTERFACE_CONTROLLER = new InterfaceController();
    private InterfaceController(){}
    public static InterfaceController getInstance(){
        return INTERFACE_CONTROLLER;
    }



    /**
     * 初始化试卷选择栏目
     * @param stage
     * @return
     */
    public VBox initPaperSelector(Stage stage){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/paper_selector.fxml"));
        VBox selector = null;
        try {
            selector = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        paperSelectorController = loader.getController();
        paperSelectorController.setStage(stage);
        paperSelector = selector;
        return selector;
    }

    public BorderPane initQuestionSelector(String subject) {
        Map<String, Object> paperQuestionMap = InfoStorage.getPaperQuestionMap();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/question_selector.fxml"));
        BorderPane selector = null;
        try {
            selector = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        questionSelectorController = loader.getController();
        questionSelectorController.setStage(stage);
        questionSelectorController.setData(paperQuestionMap, subject);
        return selector;
    }

    public BorderPane initQuestionAnswer() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/question_answer.fxml"));
        BorderPane questionAnswer = null;
        try {
            questionAnswer = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        questionAnswerController = loader.getController();
        questionAnswerController.setStage(stage);
        return questionAnswer;
    }

    public void changeToQuestionSelector(String subject) {
        questionSelector = initQuestionSelector(subject);
        BorderPane backBoard = backBoardController.getBackBoard();
        backBoard.setLeft(questionSelector);
        Button backBtn = backBoardController.getBackBtn();
        backBtn.setVisible(true);
        phase = InterfacePhase.STUDENT_QUESTION_SELECTOR;
    }

    public void changeToQuestionSelector() {
        questionAnswerController = null;
        BorderPane backBoard = backBoardController.getBackBoard();
        backBoard.setLeft(questionSelector);
        phase = InterfacePhase.STUDENT_QUESTION_SELECTOR;
    }

    public void changeToPaperSelector() {
        questionSelectorController = null;
        paperSelector = initPaperSelector(stage);
        BorderPane backBoard = backBoardController.getBackBoard();
        backBoard.setLeft(paperSelector);
        Button backBtn = backBoardController.getBackBtn();
        backBtn.setVisible(false);
        phase = InterfacePhase.STUDENT_PAPER_SELECTOR;
    }

    public void changeToQuestionAnswer() {
        BorderPane questionAnswer = initQuestionAnswer();
        BorderPane backBoard = backBoardController.getBackBoard();
        backBoard.setLeft(questionAnswer);
        phase = InterfacePhase.STUDENT_QUESTION_ANSWER;
    }

    public void changeToQuestionStudent(){
        BorderPane backBoard = backBoardController.getBackBoard();
        backBoard.setLeft(questionStudentSelector);
        phase = InterfacePhase.TEACHER_QUESTION_STUDENT;
    }

    public void initQuestionStudent(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/question_student.fxml"));
        questionStudentSelector = null;
        try {
            questionStudentSelector = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        questionStudentController = loader.getController();
        questionStudentController.setStage(stage);
        questionStudentController.setData(InfoStorage.getQuestionInfo(), InfoStorage.getStudentScoreList());
    }


    public Pane initStudentPaperInterface(Stage stage){
        this.stage = stage;
        // 读取backboard
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/back_board.fxml"));
        try {
            this.root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        backBoardController = loader.getController();
        backBoardController.setStage(stage);
        // 设置登出
        setLogout();

        // 设置左上角的用户栏
        Label account = backBoardController.getAccount();
        account.setText(InfoStorage.getAccountInfo().getAccount());

        // 读取中间的试卷选择框
        VBox selector = initPaperSelector(stage);

        // 设置中间的试卷选择框
        BorderPane backBoard = backBoardController.getBackBoard();
        backBoard.setLeft(selector);


        stage.initStyle(StageStyle.TRANSPARENT);
        return root;
    }

    public void setLogout(){
        ChoiceBox<Object> accountBtn = backBoardController.getAccountBtn();
        accountBtn.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->{
            switch (newv.intValue()){
                case 0:
                    StageList.removeStage(stage);
                    destroy();
                    Stage primaryStage = StageList.getStage(0);
                    primaryStage.show();
                    stage.close();
                    break;
                default:
                    System.out.println("none");
            }
        });
    }

    public void destroy(){
        root = null;
        paperSelectorController = null;
        backBoardController = null;
        InfoStorage.setPaperQuestionMap(null);
        InfoStorage.setSubjectPaperList(null);
        InfoStorage.setAccountInfo(null);
    }

    public InterfacePhase getPhase() {
        return phase;
    }

}
