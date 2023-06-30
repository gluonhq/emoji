/*
 * Copyright (c) 2023, Gluon
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
package com.gluonhq.emoji.samples;

import com.gluonhq.emoji.Emoji;
import com.gluonhq.emoji.EmojiData;
import com.gluonhq.emoji.util.EmojiImageUtils;
import com.gluonhq.emoji.util.TextUtils;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class Demo extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Get a single text string with all unicode characters for all emojis
        String text = getAllEmojisAsUnicodeString();

        // Convert text to Text (if delimiter is not empty) and Image nodes (all emojis)
        List<Node> emojiNodes = TextUtils.convertToTextAndImageNodes(text);
        emojiNodes.stream()
                .filter(ImageView.class::isInstance)
                .forEach(node -> {
                    String unified = (String) node.getProperties().get(EmojiImageUtils.IMAGE_VIEW_EMOJI_PROPERTY);
                    EmojiData.emojiFromCodepoints(unified).ifPresent(emoji ->
                            Tooltip.install(node, new Tooltip("Emoji: " + emoji.getName() + "\n" + unified)));
                });
        // Add nodes to textFlow
        TextFlow textFlow = new TextFlow();
        textFlow.getChildren().addAll(emojiNodes);
        ScrollPane pane = new ScrollPane(textFlow);
        Scene scene = new Scene(pane, 1230, 800);
        textFlow.prefWidthProperty().bind(scene.widthProperty().subtract(30));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Emoji nodes: " +
                textFlow.getChildren().stream().filter(ImageView.class::isInstance).count());
        primaryStage.show();
    }

    /**
     * Get a text string with the unicode characters of all emojis, in the same
     * order as the sheet image
     * https://github.com/iamcal/emoji-data/blob/master/sheet_apple_64.png
     *
     * @return a text string
     */
    private static String getAllEmojisAsUnicodeString() {
        return EmojiData.getEmojiCollection().stream()
                .sorted((e1, e2) -> {
                    if (e1.getSheetY() == e2.getSheetY()) {
                        return Integer.compare(e1.getSheetX(), e2.getSheetX());
                    }
                    return Integer.compare(e1.getSheetY(), e2.getSheetY());
                })
                .map(Emoji::character)
                // optional: add a delimiter like `|` to separate emojis
                .collect(Collectors.joining(""));
    }
}
