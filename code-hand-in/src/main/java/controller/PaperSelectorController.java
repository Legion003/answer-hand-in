package controller;

import com.alibaba.fastjson.JSON;
import entity.PaperInfo;
import entity.SubjectInfo;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
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
 * @Date 2020/12/16 10:45
 * @Description
 */
public class PaperSelectorController implements Initializable {
    @FXML
    VBox container;
    @FXML
    ScrollPane slider;
    @FXML
    ComboBox subjectSelector;

    Stage stage;
    List<GridPane> paperBlockList;
    int count = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.paperBlockList = new ArrayList<>();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                List<Map> subjectPaperList = InfoStorage.getSubjectPaperList();
                for (Map subjectPaperMap : subjectPaperList) {
                    List<PaperInfo> paperInfoList = JSON.parseArray(subjectPaperMap.get("paperInfoList").toString(), PaperInfo.class);
                    SubjectInfo subjectInfo = JSON.parseObject(subjectPaperMap.get("subjectInfo").toString(), SubjectInfo.class);
                    subjectSelector.getItems().add(subjectInfo.getName());
                    for (PaperInfo paperInfo : paperInfoList) {
                        GridPane paperBlock = createPaperBlock(paperInfo, subjectInfo);
                        paperBlockList.add(paperBlock);
                    }
                }
                subjectSelector.getItems().add("全部");
                initContainer();
                setSubjectSelectorListener();
                bindUI();
            }
        });

    }



    public GridPane createPaperBlock(PaperInfo paperInfo, SubjectInfo subjectInfo){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/paper_block.fxml"));
        GridPane root = null;
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    PaperBlockController paperBlockController = loader.getController();
                    paperBlockController.setStage(stage);
                    paperBlockController.setPaperInfo(paperInfo);
                    paperBlockController.setSubjectInfo(subjectInfo);
                    if (count % 2 == 1){
                        GridPane paperBlock = paperBlockController.getPaperBlock();
                        paperBlock.setStyle("-fx-background-color: rgb(250, 250, 250)");
                    }
                    count++;
                }
            });
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    public void chooseSubject(Map subjectPaperMap) {
        List<PaperInfo> paperInfoList = JSON.parseArray(subjectPaperMap.get("paperInfoList").toString(), PaperInfo.class);
        SubjectInfo subjectInfo = JSON.parseObject(subjectPaperMap.get("subjectInfo").toString(), SubjectInfo.class);
        for (PaperInfo paperInfo : paperInfoList) {
            GridPane paperBlock = createPaperBlock(paperInfo, subjectInfo);
            paperBlockList.add(paperBlock);
        }
    }

    public void setSubjectSelectorListener() {
        subjectSelector.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                List<Map> subjectPaperList = InfoStorage.getSubjectPaperList();
                container.getChildren().clear();
                paperBlockList.clear();
                count = 0;
                if ((Integer)newValue == subjectSelector.getItems().size()-1) {
                    for (Map subjectPaperMap : subjectPaperList) {
                        chooseSubject(subjectPaperMap);
                    }
                    initContainer();
                    return;
                }
                Map subjectPaperMap = subjectPaperList.get((Integer) newValue);
                chooseSubject(subjectPaperMap);
                initContainer();
            }
        });
    }

    public void initContainer(){
        for (GridPane paperBlock : paperBlockList) {
            container.getChildren().add(paperBlock);
        }
    }

    public void bindUI(){
        slider.prefWidthProperty().bind(stage.widthProperty());
        slider.prefHeightProperty().bind(stage.heightProperty().subtract(170));
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public List<GridPane> getPaperBlockList() {
        return paperBlockList;
    }
}
