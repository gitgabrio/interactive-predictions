package org.kie.interactivepredictions.user.itf.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import org.kie.interactivepredictions.api.models.IPInputExplainability;
import org.kie.interactivepredictions.api.models.IPModelFileTupla;
import org.kie.interactivepredictions.api.models.IPOutputExplainability;

import static org.kie.interactivepredictions.user.itf.Main.EXPLAINABILITY_SERVICE;
import static org.kie.interactivepredictions.user.itf.utils.GUIUtils.getGoToMainButton;
import static org.kie.interactivepredictions.user.itf.utils.GUIUtils.getHorizontalTilePane;
import static org.kie.interactivepredictions.user.itf.utils.GUIUtils.getLabel;
import static org.kie.interactivepredictions.user.itf.utils.GUIUtils.getTextArea;
import static org.kie.interactivepredictions.user.itf.utils.GUIUtils.getVerticalTilePane;
import static org.kie.interactivepredictions.user.itf.utils.GUIUtils.showVBox;

public class ExplanationGUI {

    private ExplanationGUI() {
    }

    public static void showExplanationGUI(IPModelFileTupla ipModelFileTupla, Stage primaryStage) {
        Consumer<Map<String, Object>> consumer = inputData -> showExplanation(ipModelFileTupla, inputData,
                                                                              primaryStage);
        InputGUI.showInputGUI(ipModelFileTupla, "Explanation", consumer, primaryStage);
    }

    private static void showExplanation(IPModelFileTupla model, Map<String, Object> inputData, Stage primaryStage) {
        IPOutputExplainability retrieved =
                EXPLAINABILITY_SERVICE.explain(new IPInputExplainability(model.getFileName(),
                                                                         model.getModelName(),
                                                                         inputData));
        TilePane explanation = createExplanationBox(retrieved, primaryStage);
        showVBox(explanation, "Explanation Result", primaryStage);
    }

    private static TilePane createExplanationBox(IPOutputExplainability retrieved, Stage primaryStage) {
        List<Region> regions = getExplanationElements(retrieved.getResult());
        regions.add(getGoToMainButton(primaryStage));
        return getVerticalTilePane(regions);
    }

    private static List<Region> getExplanationElements(Map<String, String> resultsMap) {
        return resultsMap
                .entrySet()
                .stream()
                .map(ExplanationGUI::createExplanationPane)
                .collect(Collectors.toList());
    }

    private static TilePane createExplanationPane(Map.Entry<String, String> entry) {
        List<Region> regions = new ArrayList<>();
        regions.add(getLabel(entry.getKey()));
        regions.add(getTextArea(entry.getValue()));
        return getHorizontalTilePane(regions);
    }
}