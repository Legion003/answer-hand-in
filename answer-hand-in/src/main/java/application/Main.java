package application;

import controller.BackBoardController;
import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.StageList;

//-Dglass.win.uiScale=100%

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        StageList.addStage(primaryStage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Pane root = loader.load();
        LoginController controller = loader.getController();
        controller.setStage(primaryStage);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root, 500, 750);
        scene.setFill(null);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
