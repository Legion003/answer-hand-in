package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import util.ConnectUtil;
import util.InfoStorage;
import util.StageList;
import java.net.URL;
import java.util.*;

/**
 * @Author Legion
 * @Date 2020/12/13 21:58
 * @Description 登录界面
 */
public class LoginController implements Initializable {
    @FXML
    TextField accountField;
    @FXML
    TextField passwordField;
    @FXML
    Label errorPrompt;
    Stage stage;
    double oldStageX, oldStageY;
    double oldScreenX, oldScreenY;
    InterfaceController interfaceController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * 设置stage，主要用于stage的控制
     */
    public void setStage(Stage stage){
        this.stage = stage;
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
        stage.close();
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

    /**
     * 登录
     */
    public void login(){
        // 获取输入的账号的密码
        String account = accountField.getText().trim();
        String password = passwordField.getText().trim();
        // 当账号密码填写不正确时做出提醒
        if (account == null && password == null){
            errorPrompt.setText("Account and password is empty.");
        } else if (account == null){
            errorPrompt.setText("Account is empty.");
        } else if (password == null){
            errorPrompt.setText("Password is empty.");
        } else {
            // 像服务端发送请求，验证身份
            Map<String, Object> loginResponseMap = ConnectUtil.login(account, password);
            if (loginResponseMap.get("code").toString().equals("200")) {
                errorPrompt.setText("");
                Map<String, Object> subjectPaperResponseMap = null;
                // 学生类型
                if (!InfoStorage.getAccountInfo().isPersonType()) {
                    subjectPaperResponseMap = ConnectUtil.getSubjectPaper();
                }
                // 教师类型
                else {
                    subjectPaperResponseMap = ConnectUtil.getTeacherSubjectPaper();

                }
                if (subjectPaperResponseMap.get("code").toString().equals("200")) {
                    stage.hide();
                    Stage newStage = new Stage();
                    StageList.addStage(newStage);
                    InterfaceController interfaceController = InterfaceController.getInstance();
                    Pane root = interfaceController.initStudentPaperInterface(newStage);
                    Scene scene = new Scene(root, 1300, 800);
                    newStage.setScene(scene);
                    newStage.show();
                } else {
                    errorPrompt.setText("Incorrect account or password.");
                }
            } else {
                errorPrompt.setText("Incorrect account or password.");
            }

        }
    }
}
