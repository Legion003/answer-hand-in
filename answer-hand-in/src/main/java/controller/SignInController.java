package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.ConnectUtil;
import util.StageList;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @Author Legion
 * @Date 2021/1/4 17:12
 * @Description
 */
public class SignInController implements Initializable {
    @FXML
    private Label errorText;
    @FXML
    private ComboBox typeSelector;
    @FXML
    private TextField personName;
    @FXML
    private TextField personId;
    @FXML
    private TextField personAccount;
    @FXML
    private TextField personPassword;

    private Stage stage;
    private double oldStageX, oldStageY;
    private double oldScreenX, oldScreenY;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeSelector.getItems().addAll("教师", "学生");
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
        int index = typeSelector.getSelectionModel().getSelectedIndex();
        boolean personType;
        if (index == 0) {
            personType = true;
        } else if (index == 1) {
            personType = false;
        } else {
            errorText.setText("please choose personType");
            return;
        }
        String name = personName.getText();
        if (name.equals("")){
            errorText.setText("please input your name");
            return;
        }
        String id = personId.getText();
        if (id.equals("")){
            errorText.setText("please input your id");
            return;
        }
        String account = personAccount.getText();
        if (account.equals("")) {
            errorText.setText("please input your account");
            return;
        }
        String password = personPassword.getText();
        if (password.equals("")) {
            errorText.setText("please input your password");
            return;
        }
        Map<String, Object> responseMap = ConnectUtil.signIn(name, id, account, password, personType);
        if (responseMap.get("code").toString().equals("200")){
            StageList.removeStage(stage);
            stage.close();
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
