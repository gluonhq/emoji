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
package com.gluonhq.emoji.util;

import com.gluonhq.emoji.Emoji;
import com.gluonhq.emoji.EmojiData;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class TextUtils {

    public static final double EMOJI_SIZE_FONT_FACTOR = 1.30;
    private static final double imageSize = 20;
    private static double imageOffset = -1;

    /**
     * Parses a text string and returns a list of nodes: all possible emojis found are
     * replaced with {@link ImageView} nodes based on their 20x20 images, while the rest
     * of the text is added to {@link Text} nodes.
     *
     * @param text a valid string of text, that might contain emoji unicode
     * @return a list of nodes
     */
    public static List<Node> convertToTextAndImageNodes(String text) {
        return convertToTextAndImageNodes(text, imageSize);
    }
    
    /**
     * Parses a text string and returns a list of nodes: all possible emojis found are
     * replaced with {@link ImageView} nodes based on the supplied size, while the rest
     * of the text is added to {@link Text} nodes.
     *
     * @param text a valid string of text, that might contain emoji unicode
     * @param emojiSize size of the emojis
     * @return a list of nodes
     */
    public static List<Node> convertToTextAndImageNodes(String text, double emojiSize) {
        return convertToStringAndEmojiObjects(text)
                .stream()
                .map(o -> {
                    if (o instanceof String) {
                        return getTextNode((String) o);
                    } else {
                        Emoji emoji = (Emoji) o;
                        return EmojiImageUtils.emojiView(emoji, emojiSize, getImageOffset(emojiSize));
                    }
                })
                .collect(Collectors.toList());
    }

    /**
     * Parses a string that contains the unified representation of an emoji
     * and returns an {@link ImageView} node based on its 20x20 image.
     *
     * @param unified a valid string of the unified representation of an emoji
     * @return a node with the image of the emoji if found
     */
    public static Node convertUnifiedToImageNode(String unified) {
        return convertUnifiedToImageNode(unified, imageSize);
    }

    /**
     * Parses a string that contains the unified representation of an emoji
     * and returns an {@link ImageView} node based on a size x size image.
     * @param unified a valid string of the unified representation of an emoji
     * @param size the size of the image
     * @return a node with the image of the emoji if found
     */
    public static Node convertUnifiedToImageNode(String unified, double size) {
        return EmojiData.emojiFromCodepoints(unified).map(emoji ->
                        EmojiImageUtils.emojiView(emoji, size, getImageOffset(size)))
                .orElse(new ImageView());
    }

    /**
     * Parses a text string and returns a list of objects: all possible emojis found are
     * replaced with {@link Emoji} objects, while the rest
     * of the text is added as {@link String} objects.
     *
     * @param text a valid string of text, that might contain emoji unicode
     * @return a list of objects
     */
    public static List<Object> convertToStringAndEmojiObjects(String text) {
        List<Object> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        StringBuilder sbChain = new StringBuilder();
        List<Integer> codePoints = text.codePoints().boxed().collect(Collectors.toList());
        for (int i = 0; i < codePoints.size(); i++) {
            int ch = codePoints.get(i);
            int nch = i < codePoints.size() - 1 ? codePoints.get(i + 1) : -1;
            // if current codepoint is emoji or emoji connector
            // or if current codepoint is not an emoji, but next codepoint is variant separator
            if (isEmoji(ch) || isEmojiConnector(ch) || (!isEmoji(ch) && isVariantSeparator(nch))) {
                if (sb.length() > 0) {
                    list.add(sb.toString());
                    sb.setLength(0);
                }

                if (sbChain.length() > 0) {
                    sbChain.append("-");
                }
                sbChain.append(String.format("%04X", ch));

                // stop and search emoji if:
                // current codepoint is an emoji or emoji connector and:
                // - we are at last codepoint, or
                // - current codepoint is an emoji but not a person emoji and next codepoint is skin tone, or
                // - next codepoint is neither an emoji nor a connector, or
                // - we are at a skin tone connector or at a hairstyle connector, and next codepoint is not ZWJ, or
                // - we are at a country flag codepoint and next codepoint is not country flag codepoint, or if it is, there is one "-" already, or
                // - we are not at a country flag codepoint and next codepoint is a country flag codepoint, or
                // - current codepoint is variant separator 0xFE0F and next codepoint is not ZWJ 0x200D or CombiningEnclose 0x20E3, or
                // - current codepoint is end of regional indicator, or
                // - next codepoint is an emoji, but not a country flag, and current codepoint is not a connector

                if ((i == codePoints.size() - 1) ||
                        (isEmoji(ch) && isSkinTone(nch) && !isPersonEmoji(ch)) ||
                        (!isEmoji(nch) && !isEmojiConnector(nch)) ||
                        ((isSkinTone(ch) || isHairstyle(ch)) && !isZWJ(nch)) ||
                        (isCountryFlag(ch) && (!isCountryFlag(nch) || sbChain.toString().contains("-"))) ||
                        (!isCountryFlag(ch) && isCountryFlag(nch)) ||
                        (isVariantSeparator(ch) && !isZWJ(nch) && nch != 0x20E3) ||
                        (ch == 0xE007F) ||
                        (isEmoji(nch) && !isCountryFlag(nch) && !isEmojiConnector(ch))) {
                    EmojiData.emojiFromCodepoints(sbChain.toString().toUpperCase(Locale.ROOT))
                            .ifPresent(list::add);
                    sbChain.setLength(0);
                }
            } else {
                sb.appendCodePoint(ch);
            }
        }
        if (sb.length() > 0) {
            list.add(sb.toString());
            sb.setLength(0);
        }
        return list;
    }

    // https://unicode.org/Public/emoji/15.0/emoji-test.txt

    private static boolean isEmoji(int ch) {
        // valid ranges for emojis:
        // 127744 <= ch && ch <= 129784 0x1F300-0x1FAF8, excluding the skin tone
        // 126980 <= ch && ch <= 127569 0x1F004-0x1F251
        // 8205 < ch && ch <= 12953 0x200D < ch && ch <= 0x3299
        return  (0x1F300 <= ch && ch <= 0x1FAF8 && !isSkinTone(ch)) ||
                (0x1F004 <= ch && ch <= 0x1F251) ||
                (0x200D < ch && ch <= 0x3299);
    }

    private static boolean isPersonEmoji(int ch) {
        return "People & Body".equals(EmojiData.emojiFromCodepoints(String.format("%04X", ch))
                        .map(Emoji::getCategory).orElse(""));
    }

    private static boolean isEmojiConnector(int ch) {
        // skin tone 127995 - 127999 or hairstyle 129456 - 129459 or
        // Zero width joiner 8205, or variant separator 65039,
        // or flag regional indicators
        return  isSkinTone(ch) || isHairstyle(ch) ||
                isZWJ(ch) || isVariantSeparator(ch) ||
                (ch >= 0xE0062 && ch <= 0xE007F);
    }

    private static boolean isZWJ(int ch) {
        return ch == 0x200D;
    }

    private static boolean isVariantSeparator(int ch) {
        return ch == 0xFE0F;
    }

    private static boolean isSkinTone(int ch) {
        return 0x1F3FB <= ch && ch <= 0x1F3FF;
    }

    private static boolean isHairstyle(int ch) {
        return 0x1F9B0 <= ch && ch <= 0x1F9B3;
    }

    private static boolean isCountryFlag(int ch) {
        return ch >= 0x1F1E6 && ch <= 0x1F1FF;
    }

    private static Text getTextNode(String text) {
        Text node = new Text(text);
        node.getStyleClass().add("text");
        return node;
    }

    private static double getImageOffset(double size) {
        if (imageOffset == -1) {
            Text node = getTextNode("Dummy");
            double textOffset = node.getBaselineOffset() - node.getLayoutBounds().getHeight() / 2;
            imageOffset = textOffset / size + 0.5;
        }
        return imageOffset;
    }

}
