package com.gluonhq.sample;

import com.gluonhq.emoji.Emoji;
import com.gluonhq.emoji.EmojiData;
import com.gluonhq.emoji.util.TextUtils;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        VBox root = createRoot();
        root.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(root, 300, 200);
        scene.getStylesheets().add(Main.class.getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private VBox createRoot() {
        TextArea textArea = new TextArea();
        textArea.setPrefHeight(150);
        HBox hBox = new HBox();

        textArea.textProperty().addListener((o, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                String unicodeText = createUnicodeText(newValue);
                List<Node> nodes = TextUtils.convertToTextAndImageNodes(unicodeText);
                hBox.getChildren().setAll(nodes);
            }
        });
        return new VBox(20, textArea, hBox);
    }

    private String createUnicodeText(String nv) {
        StringBuilder unicodeText = new StringBuilder();
        String[] words = nv.split(" ");
        for (String word : words) {
            Optional<Emoji> optionalEmoji = EmojiData.emojiFromShortName(word);
            unicodeText.append(optionalEmoji.isPresent() ? optionalEmoji.get().character() : word);
            unicodeText.append(" ");
        }
        return unicodeText.toString();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
