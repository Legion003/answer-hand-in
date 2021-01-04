package controller;

import com.alibaba.fastjson.JSON;
import entity.PaperInfo;
import entity.SubjectInfo;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import util.ConnectUtil;
import util.InfoStorage;
import util.StageList;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @Author Legion
 * @Date 2020/12/28 14:29
 * @Description
 */
public class AddPaperController implements Initializable {
    @FXML
    private ComboBox subjectSelector;
    @FXML
    private TextField paperName;
    @FXML
    private TextArea paperDescribe;
    @FXML
    private TextField deadline;
    @FXML
    private Label errorText;

    private Stage stage;
    private double oldStageX, oldStageY;
    private double oldScreenX, oldScreenY;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                List<Map> subjectPaperList = InfoStorage.getSubjectPaperList();
                for (Map subjectPaperMap : subjectPaperList) {
                    SubjectInfo subjectInfo = JSON.parseObject(subjectPaperMap.get("subjectInfo").toString(), SubjectInfo.class);
                    subjectSelector.getItems().add(subjectInfo.getName());
                }
            }
        });
    }

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
        int index = subjectSelector.getSelectionModel().getSelectedIndex();
        List<Map> subjectPaperList = InfoStorage.getSubjectPaperList();
        Map subjectPaperMap = subjectPaperList.get(index);
        SubjectInfo subjectInfo = JSON.parseObject(subjectPaperMap.get("subjectInfo").toString(), SubjectInfo.class);
        String subjectId = subjectInfo.getSubjectId();
        String name = paperName.getText();
        String describe = paperDescribe.getText();
        String dl = deadline.getText();
        Map<String, Object> responseMap = ConnectUtil.addPaper(subjectId, name, describe, dl);
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
