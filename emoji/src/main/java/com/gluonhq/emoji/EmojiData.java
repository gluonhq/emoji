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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class EmojiData {

    private static final Logger LOG = Logger.getLogger(EmojiData.class.getName());

    /**
     * Map that stores emojis with their shortName as key.
     */
    private static final Map<String, Emoji> EMOJI_MAP = new HashMap<>();

    /**
     * Map that stores emojis with their unified field as key
     */
    private static final Map<String, Emoji> EMOJI_UNICODE_MAP = new HashMap<>();

    private static final String COMMA_DELIMITER = "#";

    static {
        // See emoji-updater module on how to get and update this csv file
        try (final InputStream emojiStream = EmojiData.class.getResourceAsStream("emoji.csv");
             Scanner scanner = new Scanner(Objects.requireNonNull(emojiStream))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Scanner rowScanner = new Scanner(line);
                rowScanner.useDelimiter(COMMA_DELIMITER);
                List<String> values = new ArrayList<>();
                while (rowScanner.hasNext()) {
                    values.add(rowScanner.next());
                }
                try {
                    Emoji e = Emoji.parseEmojiFromCSVList(values);
                    EMOJI_UNICODE_MAP.put(e.getUnified(), e);
                    EMOJI_MAP.put(e.getShortName(), e);
                    if (e.getSkinVariationMap() != null) {
                        e.getSkinVariationMap().values()
                                .forEach(v -> {
                                    EMOJI_UNICODE_MAP.put(v.getUnified(), v);
                                    EMOJI_MAP.put(v.getShortName(), v);
                                });
                    }
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, "Error parsing line: " + line + ", " + ex.getMessage(), ex);
                }
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error parsing emoji csv file: " + ex.getMessage(), ex);
        }
    }

    /**
     * Returns Emoji from unicode string
     * @param unicodeText Unicode string representation
     * @return Emoji found for the string
     */
    public static Optional<Emoji> emojiFromUnicodeString(String unicodeText) {
        return EMOJI_MAP.values().stream()
                .filter(emoji -> emoji.character().equals(unicodeText))
                .findFirst();
    }

    /**
     * Returns Emoji from its hex code point string representation.
     * @param codePoint A string of one or more hex code points, concatenated using "-".
     * @return Emoji found for the codepoint string
     */
    public static Optional<Emoji> emojiFromCodepoints(String codePoint) {
        Emoji value = EMOJI_UNICODE_MAP.get(codePoint);
        if (value == null) {
            // try to qualify it
            value = EMOJI_UNICODE_MAP.get(codePoint + "-FE0F");
        }
        return Optional.ofNullable(value);
    }

    public static Optional<Emoji> emojiFromShortName(String shortName) {
        return Optional.ofNullable(EMOJI_MAP.get(shortName));        
    }

    public static Optional<Emoji> emojiFromCodeName(String text) {
        if (text.startsWith(":") && text.endsWith(":")) {
            return emojiFromShortName(text.substring(1, text.length() - 1));
        }
        return Optional.empty();
    }

    public static List<Emoji> emojiFromCategory(String category) {
        return EMOJI_MAP.values().stream()
                .filter(emoji -> category.contains(emoji.getCategory()))
                .sorted(Comparator.comparingInt(Emoji::getSortOrder))
                .collect(Collectors.toList());
    }
    
    public static List<Emoji> search(String text) {
        List<Emoji> emojis = new ArrayList<>();
        for (String s : text.split(" ")) {
            emojis.addAll(EMOJI_MAP.entrySet().stream()
                    .filter(es -> es.getKey().contains(s))
                    .map(Map.Entry::getValue)
                    .sorted(Comparator.comparingInt(Emoji::getSortOrder))
                    .collect(Collectors.toList()));
        }
        return emojis;
    }
    
    public static Set<String> shortNames() {
        return EMOJI_MAP.keySet();
    }
    
    public static Collection<Emoji> emojiValues() {
        return EMOJI_UNICODE_MAP.values();
    }

    public static Set<String> categories() {
        return EMOJI_MAP.values().stream()
                .map(Emoji::getCategory)
                .collect(Collectors.toSet());
    }
    
    public static String emojiForText(String shortName) {
        return emojiForText(shortName, false);
    }

    public static String emojiForText(String shortName, boolean strip) {
        final Emoji emoji = EMOJI_MAP.get(shortName);
        if (emoji == null) {
            return strip ? "" : shortName;
        }
        else return emoji.character();
    }

    public static Emoji copyEmoji(Emoji other) {
        Emoji emoji = new Emoji();
        emoji.setUnified(other.getUnified());
        emoji.setShortName(other.getShortName());
        emoji.setShortNameList(other.getShortNameList());
        emoji.setCategory(other.getCategory());
        emoji.setSheetX(other.getSheetX());
        emoji.setSheetY(other.getSheetY());
        emoji.setSkinVariationMap(other.getSkinVariationMap());
        return emoji;
    }

    public static Emoji emojiWithTone(Emoji emoji, EmojiSkinTone tone) {
        return (emoji != null && emoji.getSkinVariationMap() != null && tone != EmojiSkinTone.NO_SKIN_TONE) ?
                emoji.getSkinVariationMap().getOrDefault(tone.getUnicode(), emoji) :
                emoji;
    }

    public static Emoji emojiWithoutDefinedTone(Emoji emoji) {
        for (EmojiSkinTone tone : EmojiSkinTone.values()) {
            if (tone != EmojiSkinTone.NO_SKIN_TONE) {
                Emoji e = emojiWithoutTone(emoji, tone);
                if (e != null) {
                    return e;
                }
            }
        }
        return emoji;
    }

    public static Emoji emojiWithoutTone(Emoji emoji, EmojiSkinTone tone) {
        if (emoji != null && emoji.getSkinVariationMap() != null && tone != EmojiSkinTone.NO_SKIN_TONE) {
            if (emoji.getUnified().endsWith("-" + tone.getUnicode())) {
                String noTone = emoji.getUnified().substring(0, emoji.getUnified().length() - tone.getUnicode().length() - 1);
                return emojiFromCodepoints(noTone).orElse(emoji);
            } else if (emoji.getUnified().contains("-" + tone.getUnicode() + "-")) {
                String noToneNonQualified = emoji.getUnified().replace("-" + tone.getUnicode() + "-", "-");
                return emojiFromCodepoints(noToneNonQualified).orElseGet(() -> {
                    String noToneQualified = emoji.getUnified().replace("-" + tone.getUnicode() + "-", "-FE0F-");
                    return emojiFromCodepoints(noToneQualified).orElse(emoji);
                });
            }
        }
        return emoji;
    }
}
