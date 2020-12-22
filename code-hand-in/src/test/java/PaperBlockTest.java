import controller.PaperBlockController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @Author Legion
 * @Date 2020/12/15 17:31
 * @Description
 */
public class PaperBlockTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/paper_block.fxml"));
        Pane root = loader.load();
        Scene scene = new Scene(root, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
