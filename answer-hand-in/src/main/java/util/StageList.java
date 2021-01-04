package util;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Legion
 * @Date 2020/12/16 15:26
 * @Description
 */
public class StageList {
    private static List<Stage> stageList = new ArrayList<>();
    public static void addStage(Stage stage){
        stageList.add(stage);
    }
    public static void removeStage(Stage stage){
        stageList.remove(stage);
    }
    public static Stage getStage(int index){
        return stageList.get(index);
    }
}
