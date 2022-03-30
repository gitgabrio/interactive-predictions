package org.kie.interactivepredictions.user.itf.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import org.kie.interactivepredictions.api.models.IPInputPrediction;
import org.kie.interactivepredictions.api.models.IPModelFileTupla;
import org.kie.interactivepredictions.api.models.IPOutputPrediction;

import static org.kie.interactivepredictions.user.itf.Main.PREDICTION_SERVICE;
import static org.kie.interactivepredictions.user.itf.utils.GUIUtils.getHorizontalTilePane;
import static org.kie.interactivepredictions.user.itf.utils.GUIUtils.getLabel;
import static org.kie.interactivepredictions.user.itf.utils.GUIUtils.getVerticalTilePane;
import static org.kie.interactivepredictions.user.itf.utils.GUIUtils.showVBox;

public class PredictionGUI {

    private PredictionGUI() {
    }

    public static void showPredictionGUI(IPModelFileTupla ipModelFileTupla, Stage primaryStage) {
        Consumer<Map<String, Object>> consumer = inputData -> showResult(ipModelFileTupla, inputData, primaryStage);
        InputGUI.showInputGUI(ipModelFileTupla, "Prediction", consumer, primaryStage);
    }

    private static void showResult(IPModelFileTupla model, Map<String, Object> inputData, Stage primaryStage) {
        IPOutputPrediction retrieved = PREDICTION_SERVICE.predict(new IPInputPrediction(model.getFileName(),
                                                                                        model.getModelName(),
                                                                                        inputData));
        TilePane resultPane = createResultBox(retrieved);
        showVBox(resultPane, "Prediction Result", primaryStage);
    }

    private static TilePane createResultBox(IPOutputPrediction retrieved) {
        List<Region> regions = new ArrayList<>();
        String resultObjectName = retrieved.getResultObjectName();
        regions.add(getHorizontalTilePane("RESULT CODE", retrieved.getResultCode()));
        regions.add(getHorizontalTilePane(resultObjectName,
                                          retrieved.getResultVariables().get(resultObjectName).toString()));
        regions.addAll(getResultElements(retrieved.getResultVariables()));
        return getVerticalTilePane(regions);
    }

    private static List<TilePane> getResultElements(Map<String, Object> resultsMap) {
        return resultsMap.entrySet()
                .stream()
                .map(PredictionGUI::createResultPane)
                .collect(Collectors.toList());
    }

    private static TilePane createResultPane(Map.Entry<String, Object> entry) {
        List<Region> regions = new ArrayList<>();
        regions.add(getLabel(entry.getKey()));
        regions.add(getLabel(entry.getValue().toString()));
        regions.add(getLabel(entry.getValue().getClass().toString()));
        return getHorizontalTilePane(regions);
    }
}