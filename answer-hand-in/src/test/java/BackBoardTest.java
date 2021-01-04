import controller.BackBoardController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @Author Legion
 * @Date 2020/12/15 14:05
 * @Description
 */
public class BackBoardTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/back_board.fxml"));
        Pane root = loader.load();
        BackBoardController controller = loader.getController();
        controller.setStage(primaryStage);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root, 1300, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
