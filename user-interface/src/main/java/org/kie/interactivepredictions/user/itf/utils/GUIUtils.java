/*
 * Copyright 2022 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.interactivepredictions.user.itf.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIUtils {

    private GUIUtils() {
    }

    public static void showVBox(TilePane contentPane, String title, Stage primaryStage) {
        VBox layout = new VBox(10, contentPane);
        layout.setPadding(new Insets(10));
        primaryStage.setTitle(title);
        primaryStage.setScene(new Scene(layout));
    }

    public static Label getLabel(String text) {
        Label toReturn = new Label();
        toReturn.setText(text);
        return toReturn;
    }

    public static TextArea getTextArea(String text) {
        TextArea toReturn = new TextArea();
        toReturn.setText(text);
        return toReturn;
    }

    public static Button getButton(String text, EventHandler<ActionEvent> eventHandler) {
        Button toReturn = new Button();
        toReturn.setText(text);
        toReturn.setOnAction(eventHandler);
        return toReturn;
    }

    public static TextField getTextField() {
        return new TextField();
    }

    public static TilePane getVerticalTilePane(List<Region> regions) {
        Pos alignment = Pos.CENTER;
        double spacing = 10;
        TilePane toReturn = new TilePane(
                Orientation.VERTICAL,
                spacing,
                spacing,
                regions.toArray(new Region[0]));
        toReturn.setMinWidth(TilePane.USE_PREF_SIZE);
        toReturn.setPrefRows(regions.size());
        toReturn.setAlignment(alignment);

        regions.forEach(b -> {
            b.setMinWidth(Button.USE_PREF_SIZE);
            b.setMaxWidth(Double.MAX_VALUE);
        });
        return toReturn;
    }

    public static TilePane getHorizontalTilePane(List<Region> regions) {
        Pos alignment = Pos.CENTER;
        double spacing = 10;
        TilePane toReturn = new TilePane(
                Orientation.HORIZONTAL,
                spacing,
                spacing,
                regions.toArray(new Region[0]));
        toReturn.setMinWidth(TilePane.USE_PREF_SIZE);
        toReturn.setPrefRows(1);
        toReturn.setAlignment(alignment);

        regions.forEach(b -> {
            b.setMinWidth(Button.USE_PREF_SIZE);
            b.setMaxWidth(Double.MAX_VALUE);
        });
        return toReturn;
    }

    public static TilePane getHorizontalTilePane(String... toShow) {
        List<Region> regions = Arrays.stream(toShow).map(GUIUtils::getLabel).collect(Collectors.toList());
        return getHorizontalTilePane(regions);
    }
}
