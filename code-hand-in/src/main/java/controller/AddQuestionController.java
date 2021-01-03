package controller;

import com.alibaba.fastjson.JSON;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.ConnectUtil;
import util.InfoStorage;
import util.StageList;

import java.util.Map;

/**
 * @Author Legion
 * @Date 2021/1/3 16:47
 * @Description
 */
public class AddQuestionController {
    @FXML
    private TextField questionTitle;
    @FXML
    private TextArea questionContent;
    @FXML
    private TextField questionFullScore;
    @FXML
    private Label errorText;

    private Stage stage;
    private double oldStageX, oldStageY;
    private double oldScreenX, oldScreenY;

    /**
     * 最小化窗口
     */
    public void minimizeWindow(){
        stage.setIconified(true);
    }

    /**
     * 关闭窗口
     */
    public void closeWindow(){
        StageList.removeStage(stage);
        stage.close();
    }

    /**
     * 提交
     */
    public void submit(){
        String title = questionTitle.getText();
        String content = questionContent.getText();
        int fullScore = 0;
        try{
            fullScore = Integer.parseInt(questionFullScore.getText());
        } catch (Exception e) {
            errorText.setText("wrong fullScore");
            return;
        }
        Map<String, Object> responseMap = ConnectUtil.addQuestion(title, content, fullScore);
        if (responseMap.get("code").toString().equals("200")) {
            Map<String, Object> paperQuestionMap = InfoStorage.getPaperQuestionMap();
            Map paperInfoMap = JSON.toJavaObject((JSON) paperQuestionMap.get("PaperInfo"), Map.class);
            Map<String, Object> responseMap2 = ConnectUtil.getTeacherPaperQuestion(Integer.parseInt(paperInfoMap.get("paperId").toString()));
            if (responseMap2.get("code").toString().equals("200")) {
                InterfaceController.getInstance().changeToQuestionSelector(InfoStorage.getSubjectInfo().getName());
                StageList.removeStage(stage);
                stage.close();
            } else {
                errorText.setText(responseMap.get("message").toString());
            }
        } else {
            errorText.setText(responseMap.get("message").toString());
        }

    }

    /**
     * 拖动窗口用，按下鼠标时记录窗口的位置和鼠标的位置
     */
    public void dragPressWindow(MouseEvent event){
        oldStageX = stage.getX();
        oldStageY = stage.getY();
        oldScreenX = event.getScreenX();
        oldScreenY = event.getScreenY();
    }

    /**
     * 拖动窗口用，拖动窗口时更新窗口的位置
     */
    public void dragWindow(MouseEvent event){
        stage.setX(event.getScreenX() - oldScreenX + oldStageX);
        stage.setY(event.getScreenY() - oldScreenY + oldStageY);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
