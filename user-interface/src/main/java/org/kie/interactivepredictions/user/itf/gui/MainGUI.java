package org.kie.interactivepredictions.user.itf.gui;

import java.util.List;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kie.interactivepredictions.api.models.IPModelFileTupla;

import static org.kie.interactivepredictions.user.itf.Main.PREDICTION_SERVICE;
import static org.kie.interactivepredictions.user.itf.gui.OperationsSelector.createVBox;

public class MainGUI {


    private static final List<IPModelFileTupla> AVAILABLE_MODELS;

    static {
        AVAILABLE_MODELS = PREDICTION_SERVICE.availableModels();
    }

    private MainGUI() {
    }

    public static void showMainGUI(Stage primaryStage) {
        primaryStage.setTitle("Select operation");
        VBox layout = createVBox(AVAILABLE_MODELS, primaryStage);
        primaryStage.setScene(new Scene(layout));
        primaryStage.show();
    }
}