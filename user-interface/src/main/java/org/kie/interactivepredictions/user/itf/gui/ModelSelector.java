package org.kie.interactivepredictions.user.itf.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import org.kie.interactivepredictions.api.models.IPModelFileTupla;
import org.kie.interactivepredictions.user.itf.utils.GUIUtils;

import static org.kie.interactivepredictions.user.itf.utils.GUIUtils.getGoToMainButton;
import static org.kie.interactivepredictions.user.itf.utils.GUIUtils.showVBox;

public class ModelSelector {

    private ModelSelector() {
    }

    public static void showModelSelector(List<IPModelFileTupla> ipModelFileTuplas,
                                         Consumer<IPModelFileTupla> consumer, Stage primaryStage) {
        TilePane modelPane = createModelsBox(ipModelFileTuplas, consumer, primaryStage);
        showVBox(modelPane, "Select Model", primaryStage);
    }

    private static TilePane createModelsBox(List<IPModelFileTupla> ipModelFileTuplas,
                                            Consumer<IPModelFileTupla> consumer, Stage primaryStage) {
        List<Region> regions = getModelElements(ipModelFileTuplas, consumer);
        regions.add(getGoToMainButton(primaryStage));
        return GUIUtils.getVerticalTilePane(regions);
    }

    private static List<Region> getModelElements(List<IPModelFileTupla> ipModelFileTuplas,
                                                 Consumer<IPModelFileTupla> consumer) {
        return ipModelFileTuplas.stream()
                .map(ipModelFileTupla -> createTilePane(ipModelFileTupla, consumer))
                .collect(Collectors.toList());
    }

    private static TilePane createTilePane(IPModelFileTupla ipModelFileTupla, Consumer<IPModelFileTupla> consumer) {
        List<Region> regions = new ArrayList<>();
        regions.add(getButton(ipModelFileTupla, consumer));
        regions.add(getLabel(ipModelFileTupla));
        return GUIUtils.getHorizontalTilePane(regions);
    }

    private static Button getButton(IPModelFileTupla ipModelFileTupla, Consumer<IPModelFileTupla> consumer) {
        return GUIUtils.getButton(ipModelFileTupla.getModelName(), event -> consumer.accept(ipModelFileTupla));
    }

    private static Label getLabel(IPModelFileTupla ipModelFileTupla) {
        return GUIUtils.getLabel(ipModelFileTupla.getFileName());
    }
}