/*
 * Copyright (c) 2023, 2024, Gluon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL GLUON BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.gluonhq.sample;

import com.gluonhq.emoji.Emoji;
import com.gluonhq.emoji.EmojiData;
import com.gluonhq.emoji.EmojiLoaderFactory;
import com.gluonhq.emoji.util.TextUtils;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        TextArea textArea = new TextArea();
        textArea.setPrefHeight(150);
        HBox hBox = new HBox();
        VBox root = new VBox(20, textArea, hBox);
        root.setAlignment(Pos.TOP_CENTER);
        root.setDisable(true);

        textArea.textProperty().addListener((o, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                String unicodeText = createUnicodeText(newValue);
                List<Node> nodes = TextUtils.convertToTextAndImageNodes(unicodeText);
                hBox.getChildren().setAll(nodes);
            }
        });

        EmojiLoaderFactory.getEmojiImageLoader().initialize().thenAccept(aBoolean -> {
            root.setDisable(false);
        });

        Scene scene = new Scene(root, 300, 200);
        scene.getStylesheets().add(Main.class.getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
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
