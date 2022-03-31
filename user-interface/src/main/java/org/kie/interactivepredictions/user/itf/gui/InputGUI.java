package org.kie.interactivepredictions.user.itf.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import org.kie.interactivepredictions.api.models.IPAvailableInputs;
import org.kie.interactivepredictions.api.models.IPModelFileTupla;
import org.kie.interactivepredictions.user.itf.utils.GUIUtils;

import static org.kie.interactivepredictions.user.itf.Main.PREDICTION_SERVICE;
import static org.kie.interactivepredictions.user.itf.utils.ConversionUtils.convertFromString;
import static org.kie.interactivepredictions.user.itf.utils.ConversionUtils.getMappedClass;
import static org.kie.interactivepredictions.user.itf.utils.GUIUtils.getGoToMainButton;
import static org.kie.interactivepredictions.user.itf.utils.GUIUtils.getHorizontalTilePane;
import static org.kie.interactivepredictions.user.itf.utils.GUIUtils.getLabel;
import static org.kie.interactivepredictions.user.itf.utils.GUIUtils.getTextField;
import static org.kie.interactivepredictions.user.itf.utils.GUIUtils.getVerticalTilePane;
import static org.kie.interactivepredictions.user.itf.utils.GUIUtils.showVBox;

public class InputGUI {

    private InputGUI() {
    }

    public static void showInputGUI(IPModelFileTupla ipModelFileTupla, String callerOperation, Consumer<Map<String,
            Object>> consumer, Stage primaryStage) {
        IPAvailableInputs availableInputs = PREDICTION_SERVICE.availableInput(ipModelFileTupla.getModelName(),
                                                                              ipModelFileTupla.getFileName());
        TilePane inputTile = createInputBox(availableInputs, consumer, primaryStage);
        showVBox(inputTile, "Insert Data for " + callerOperation, primaryStage);
    }

    private static TilePane createInputBox(IPAvailableInputs availableInputs, Consumer<Map<String, Object>> consumer,
                                           Stage primaryStage) {
        List<TilePane> tilePanes = getInputElements(availableInputs.getInputMap());
        List<Region> regions = new ArrayList<>(tilePanes);
        regions.add(getButton(tilePanes, consumer));
        regions.add(getGoToMainButton(primaryStage));
        return getVerticalTilePane(regions);
    }

    private static List<TilePane> getInputElements(Map<String, String> inputMap) {
        return inputMap.entrySet()
                .stream()
                .map(InputGUI::createInputPane)
                .collect(Collectors.toList());
    }

    private static TilePane createInputPane(Map.Entry<String, String> entry) {
        List<Region> tilePanes = new ArrayList<>();
        tilePanes.add(getLabel(entry.getKey()));
        tilePanes.add(getTextField());
        tilePanes.add(getLabel(entry.getValue()));
        return getHorizontalTilePane(tilePanes);
    }

    private static Button getButton(List<TilePane> tilePanes, Consumer<Map<String, Object>> consumer) {
        return GUIUtils.getButton("EXECUTE", event -> buttonAction(tilePanes, consumer));
    }

    private static void buttonAction(List<TilePane> tilePanes, Consumer<Map<String, Object>> consumer) {
        Map<String, Object> inputData = tilePanes.stream()
                .collect(Collectors.toMap(tilePane -> ((Label) tilePane.getChildren().get(0)).getText(),
                                          tilePane -> {
                                              String insertedValue =
                                                      ((TextField) tilePane.getChildren().get(1)).getText();
                                              String variableClass = ((Label) tilePane.getChildren().get(2)).getText();
                                              Object toReturn = null;
                                              if (insertedValue != null && !insertedValue.isEmpty()) {
                                                  Class<?> expectedClass = getMappedClass(variableClass);
                                                  if (expectedClass != null) {
                                                      toReturn = convertFromString(expectedClass, insertedValue);
                                                  }
                                              }
                                              return toReturn;
                                          }));
        consumer.accept(inputData);
    }
}