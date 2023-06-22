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
package com.gluonhq.emoji;

import com.gluonhq.emoji.util.TextUtils;
import javafx.scene.Node;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum EmojiSkinTone {

    NO_SKIN_TONE("\u270b", ""),
    LIGHT_SKIN_TONE("\u270b\ud83c\udffb", "1F3FB"),
    MEDIUM_LIGHT_SKIN_TONE("\u270b\ud83c\udffc", "1F3FC"),
    MEDIUM_SKIN_TONE("\u270b\ud83c\udffd", "1F3FD"),
    MEDIUM_DARK_SKIN_TONE("\u270b\ud83c\udffe", "1F3FE"),
    DARK_SKIN_TONE("\u270b\ud83c\udfff", "1F3FF");

    private final String text;
    private final String unicode;

    EmojiSkinTone(String text, String unicode) {
        this.text = text;
        this.unicode = unicode;
    }

    public String getText() {
        return text;
    }

    public String getUnicode() {
        return unicode;
    }

    public Node getImageView() {
        return TextUtils.convertToTextAndImageNodes(text).get(0);
    }

    public static EmojiSkinTone fromUnicode(String unicode) {
        return Stream.of(values())
                .filter(emojiSkinTone -> emojiSkinTone.unicode.equals(unicode))
                .findFirst()
                .orElse(NO_SKIN_TONE);
    }

    public static String getSkinVariationName(String tone) {
        return Stream.of(tone.split("-"))
                .map(t -> EmojiSkinTone.fromUnicode(t).name().replaceAll("_", " "))
                .collect(Collectors.joining(", "));
    }
}
