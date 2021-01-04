package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import util.InfoStorage;
import util.InterfacePhase;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author Legion
 * @Date 2020/12/15 13:44
 * @Description 基础界面
 */
public class BackBoardController implements Initializable {
    @FXML
    Pane root;
    @FXML
    BorderPane backBoard;
    @FXML
    BorderPane topController;
    @FXML
    Label account;
    @FXML
    Pane topLine;
    @FXML
    ChoiceBox<Object> accountBtn;
    @FXML
    Button backBtn;
    @FXML
    BorderPane bottomController;


    Stage stage;


    double tempX, tempY;
    double tempWidth, tempHeight;
    double oldScreenX, oldScreenY;


    @Override
    public void initialize(URL location, ResourceBundle resources){
        bindUI();
    }

    /**
     * 最小化窗口
     */
    public void minimizeWindow(){
        stage.setIconified(true);
    }

    /**
     * 最大化窗口
     */
    public void maximizeWindow(){
        stage.setMaximized(!stage.isMaximized());
    }

    /**
     * 关闭窗口
     */
    public void closeWindow(){
        stage.close();
    }

    /**
     * UI的宽高绑定
     */
    public void bindUI(){
        Platform.runLater(() -> {
            backBoard.prefWidthProperty().bind(stage.widthProperty());
            backBoard.prefHeightProperty().bind(stage.heightProperty());
            topController.prefWidthProperty().bind(stage.widthProperty());
            topLine.prefWidthProperty().bind(stage.widthProperty());
            bottomController.prefWidthProperty().bind(stage.widthProperty());
        });

    }

    /**
     * 调整窗口大小用，记录鼠标的位置和窗口现在的大小
     */
    public void resizePress(MouseEvent event){
        tempX = event.getScreenX();
        tempY = event.getScreenY();
        tempWidth = stage.getWidth();
        tempHeight = stage.getHeight();
    }

    /**
     * 调整窗口大小用，使用之前的鼠标位置和窗口大小来更新窗口
     * 其中500是最小宽度，410是最小高度
     */
    public void resizeDrag(MouseEvent event){
        double newWidth = tempWidth + (event.getScreenX() - tempX);
        double newHeight = tempHeight + (event.getSceneY() - tempY + 80);
        if (newWidth >= 500 && newHeight >= 410){
            stage.setWidth(newWidth);
            stage.setHeight(newHeight);
        }
    }

    /**
     * 拖动窗口用，按下鼠标时记录窗口的位置和鼠标的位置
     */
    public void dragPressWindow(MouseEvent event){
        tempX = stage.getX();
        tempY = stage.getY();
        oldScreenX = event.getScreenX();
        oldScreenY = event.getScreenY();
    }

    /**
     * 拖动窗口用，拖动窗口时更新窗口的位置
     */
    public void dragWindow(MouseEvent event){
        stage.setX(event.getScreenX() - oldScreenX + tempX);
        stage.setY(event.getScreenY() - oldScreenY + tempY);
    }

    public void back() {
        InterfaceController interfaceController = InterfaceController.getInstance();
        InterfacePhase phase = interfaceController.getPhase();
        if (phase.equals(InterfacePhase.STUDENT_QUESTION_SELECTOR)){
            interfaceController.changeToPaperSelector();
        }
        if (phase.equals(InterfacePhase.STUDENT_QUESTION_ANSWER) && !InfoStorage.getAccountInfo().isPersonType()) {
            interfaceController.changeToQuestionSelector();
        }
        if (phase.equals(InterfacePhase.TEACHER_QUESTION_STUDENT)) {
            interfaceController.changeToQuestionSelector();
        }
    }


    public ChoiceBox<Object> getAccountBtn() {
        return accountBtn;
    }

    public Label getAccount() {
        return account;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public BorderPane getBackBoard() {
        return backBoard;
    }

    public Button getBackBtn() {
        return backBtn;
    }
}
