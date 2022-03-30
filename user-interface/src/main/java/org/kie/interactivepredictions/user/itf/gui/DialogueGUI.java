package org.kie.interactivepredictions.user.itf.gui;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import org.kie.interactivepredictions.api.models.IPInputDialogue;
import org.kie.interactivepredictions.api.models.IPModelFileTupla;
import org.kie.interactivepredictions.api.models.IPOutputDialogue;

import static org.kie.interactivepredictions.user.itf.Main.DIALOGUE_SERVICE;
import static org.kie.interactivepredictions.user.itf.utils.GUIUtils.getLabel;
import static org.kie.interactivepredictions.user.itf.utils.GUIUtils.getVerticalTilePane;
import static org.kie.interactivepredictions.user.itf.utils.GUIUtils.showVBox;

public class DialogueGUI {

    private DialogueGUI() {
    }

    public static void showDialogueGUI(IPModelFileTupla ipModelFileTupla, Stage primaryStage) {
        Consumer<Map<String, Object>> consumer = inputData -> showDialogue(ipModelFileTupla, inputData, primaryStage);
        InputGUI.showInputGUI(ipModelFileTupla, "Dialogue", consumer, primaryStage);
    }

    private static void showDialogue(IPModelFileTupla model, Map<String, Object> inputData, Stage primaryStage) {
        IPOutputDialogue retrieved = DIALOGUE_SERVICE.dialogue(new IPInputDialogue());
        TilePane explanation = createDialogueBox(retrieved);
        showVBox(explanation, "Dialogue Result", primaryStage);
    }

    private static TilePane createDialogueBox(IPOutputDialogue retrieved) {
        List<Region> regions = Arrays.asList(getLabel(retrieved.toString()));
        return getVerticalTilePane(regions);
    }
}