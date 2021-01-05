package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.ConnectUtil;
import util.StageList;

import java.util.Map;

/**
 * @Author Legion
 * @Date 2020/12/28 15:15
 * @Description
 */
public class AddSubjectController {
    @FXML
    private TextField subjectName;
    @FXML
    private TextField subjectNum;
    @FXML
    private Label errorText;
    private double oldStageX, oldStageY;
    private double oldScreenX, oldScreenY;
    private Stage stage;

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
        stage.close();
        StageList.removeStage(stage);
    }

    /**
     * 提交
     */
    public void submit(){
        String num = subjectNum.getText();
        if (num.equals("")) {
            errorText.setText("please input subjectId");
            return;
        }
        String name = subjectName.getText();
        if (name.equals("")){
            errorText.setText("please input subjectName");
            return;
        }
        Map<String, Object> responseMap = ConnectUtil.addSubject(num, name);
        if (responseMap.get("code").toString().equals("200")) {
            Map<String, Object> subjectPaperResponseMap = ConnectUtil.getTeacherSubjectPaper();
            if (subjectPaperResponseMap.get("code").toString().equals("200")) {
                InterfaceController.getInstance().changeToPaperSelector();
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
