package org.kie.interactivepredictions.user.itf.gui;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kie.interactivepredictions.api.models.IPModelFileTupla;

import static java.lang.System.exit;
import static org.kie.interactivepredictions.user.itf.utils.GUIUtils.getButton;

public class OperationsSelector {

    private OperationsSelector() {
    }

    public static VBox createVBox(List<IPModelFileTupla> availableModels, Stage primaryStage) {
        Pane pane = createButtonBox(availableModels, primaryStage);
        VBox toReturn = new VBox(10, pane);
        toReturn.setPadding(new Insets(10));
        return toReturn;
    }

    private static Pane createButtonBox(List<IPModelFileTupla> availableModels, Stage primaryStage) {
        Pos alignment = Pos.CENTER;
        double spacing = 10;
        List<Button> buttons = getButtons(availableModels, primaryStage);
        TilePane tiles = new TilePane(
                Orientation.VERTICAL,
                spacing,
                spacing,
                buttons.toArray(new Button[0]));
        tiles.setMinWidth(TilePane.USE_PREF_SIZE);
        tiles.setPrefRows(buttons.size());
        tiles.setAlignment(alignment);

        buttons.forEach(b -> {
            b.setMinWidth(Button.USE_PREF_SIZE);
            b.setMaxWidth(Double.MAX_VALUE);
        });

        return tiles;
    }

    private static List<Button> getButtons(List<IPModelFileTupla> availableModels, Stage primaryStage) {
        Button btnPrediction = getButton("Prediction", btnEvent -> predictionAction(availableModels, primaryStage));
        Button btnExplanation = getButton("Explanation", btnEvent -> explanationAction(availableModels, primaryStage));
        Button btnDialogue = getButton("Dialogue", btnEvent -> dialogueAction(availableModels, primaryStage));
        Button btnExit = getButton("Exit", OperationsSelector::exitAction);
        return Arrays.asList(btnPrediction, btnExplanation, btnDialogue, btnExit);
    }

    private static void predictionAction(List<IPModelFileTupla> availableModels, Stage primaryStage) {
        Consumer<IPModelFileTupla> operationConsumer =
                ipModelFileTupla -> PredictionGUI.showPredictionGUI(ipModelFileTupla, primaryStage);
        ModelSelector.showModelSelector(availableModels, operationConsumer, primaryStage);
    }

    private static void explanationAction(List<IPModelFileTupla> availableModels, Stage primaryStage) {
        Consumer<IPModelFileTupla> operationConsumer =
                ipModelFileTupla -> ExplanationGUI.showExplanationGUI(ipModelFileTupla, primaryStage);
        ModelSelector.showModelSelector(availableModels, operationConsumer, primaryStage);
    }

    private static void dialogueAction(List<IPModelFileTupla> availableModels, Stage primaryStage) {
        Consumer<IPModelFileTupla> operationConsumer =
                ipModelFileTupla -> DialogueGUI.showDialogueGUI(ipModelFileTupla, primaryStage);
        ModelSelector.showModelSelector(availableModels, operationConsumer, primaryStage);
    }

    private static void exitAction(Event btnEvent) {
        exit(0);
    }
}