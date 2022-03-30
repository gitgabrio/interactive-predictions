package org.kie.interactivepredictions.user.itf.gui;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kie.interactivepredictions.api.models.IPModelFileTupla;

import static org.kie.interactivepredictions.user.itf.Main.PREDICTION_SERVICE;
import static org.kie.interactivepredictions.user.itf.gui.OperationsSelector.createVBox;

public class MainGUI extends Application {

    private static final List<IPModelFileTupla> AVAILABLE_MODELS;
    private static final Map<Integer, IPModelFileTupla> MODEL_INDEX_MAP;

    static {
        AVAILABLE_MODELS = PREDICTION_SERVICE.availableModels();
        MODEL_INDEX_MAP = IntStream.range(0, AVAILABLE_MODELS.size())
                .boxed()
                .collect(Collectors.toMap(integer -> integer + 1,
                                          AVAILABLE_MODELS::get));
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Select operation");
        VBox layout = createVBox(AVAILABLE_MODELS, primaryStage);
        primaryStage.setScene(new Scene(layout));
        primaryStage.show();
    }
}