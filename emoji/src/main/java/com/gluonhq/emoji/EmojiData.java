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

/**
 * Processes all emojis available (including skin variations), and generates two maps:
 * one map with emoji's shortName as key, the other with emoji's unified as key,
 * providing utility methods to get collections of emojis or single emojis for a given
 * criteria.
 */
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
     * Returns Emoji from unicode string, or empty if not found.
     * For instance, a string like "\uD83D\uDC4B" will return the "wave" emoji ("1F44B")
     *
     * @param unicodeText Unicode string representation
     * @return Emoji found for the string, or empty
     */
    public static Optional<Emoji> emojiFromUnicodeString(String unicodeText) {
        return EMOJI_MAP.values().stream()
                .filter(emoji -> emoji.character().equals(unicodeText))
                .findFirst();
    }

    /**
     * Returns Emoji from its hex code point string representation.
     * For instance, for "1F44B-1F3FC" it will return the "wave" with medium light skin emoji.
     *
     * @param codePoint A string of one or more hex code points, concatenated using "-".
     * @return Emoji found for the codepoint string, or empty
     */
    public static Optional<Emoji> emojiFromCodepoints(String codePoint) {
        Emoji value = EMOJI_UNICODE_MAP.get(codePoint);
        if (value == null) {
            // try to qualify it
            value = EMOJI_UNICODE_MAP.get(codePoint + "-FE0F");
        }
        return Optional.ofNullable(value);
    }

    /**
     * Returns Emoji from shortName string, or empty if not found.
     * For instance, a string like "wave" will return the "wave" emoji ("1F44B")
     *
     * @param shortName Short name string
     * @return Emoji found for the string, or empty
     */
    public static Optional<Emoji> emojiFromShortName(String shortName) {
        return Optional.ofNullable(EMOJI_MAP.get(shortName));        
    }

    /**
     * Returns Emoji from code name string, or empty if not found.
     * For instance, a string like ":wave:" will return the "wave" emoji ("1F44B")
     *
     * @param codeName Short name string wrapped with ":"
     * @return Emoji found for the string, or empty
     */
    public static Optional<Emoji> emojiFromCodeName(String codeName) {
        if (codeName.startsWith(":") && codeName.endsWith(":")) {
            return emojiFromShortName(codeName.substring(1, codeName.length() - 1));
        }
        return Optional.empty();
    }

    /**
     * For a given shortName, returns an optional with the unicode character
     * representation of the emoji if exists, or empty otherwise
     *
     * @param shortName Short name string
     * @return an optional with the emoji's character, or empty
     */
    public static Optional<String> emojiForText(String shortName) {
        return emojiFromShortName(shortName).map(Emoji::character);
    }

    /**
     * Returns a list of emojis for a category string, sorted by emoji's sort order.
     *
     * @param category Category string
     * @return List of Emoji found for the category
     */
    public static List<Emoji> emojiFromCategory(String category) {
        return EMOJI_MAP.values().stream()
                .filter(emoji -> category.contains(emoji.getCategory()))
                .sorted(Comparator.comparingInt(Emoji::getSortOrder))
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of emojis for which their shortName contains a given text string,
     * sorted by emoji's sort order.
     *
     * @param text text string
     * @return List of Emoji found for the text string
     */
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

    /**
     * Returns a set with the shortNames of all emojis
     *
     * @return a set with the shortNames of all emojis
     */
    public static Set<String> shortNamesSet() {
        return EMOJI_MAP.keySet();
    }

    /**
     * Returns the collection of all emojis
     *
     * @return the collection of all emojis
     */
    public static Collection<Emoji> getEmojiCollection() {
        return EMOJI_UNICODE_MAP.values();
    }

    /**
     * Returns a set with the categories of all emojis
     *
     * @return a set with the categories of all emojis
     */
    public static Set<String> categories() {
        return EMOJI_MAP.values().stream()
                .map(Emoji::getCategory)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a new Emoji object, with the same most significant fields of another Emoji object
     *
     * @param other the Emoji object to copy
     * @return an Emoji object
     */
    public static Emoji copyEmoji(Emoji other) {
        Emoji emoji = new Emoji();
        emoji.setName(other.getName());
        emoji.setUnified(other.getUnified());
        emoji.setShortName(other.getShortName());
        emoji.setShortNameList(other.getShortNameList());
        emoji.setCategory(other.getCategory());
        emoji.setSheetX(other.getSheetX());
        emoji.setSheetY(other.getSheetY());
        emoji.setSortOrder(other.getSortOrder());
        emoji.setSkinVariationMap(other.getSkinVariationMap());
        return emoji;
    }

    /**
     * For a given emoji, returns a possible emoji variation with a given skin tone, or
     * the same emoji, if not found
     * For instance, for the emoji "1F44B", and tone "1F3FC", returns the emoji "1F44B-1F3FC"
     *
     * @param emoji an emoji without tone
     * @param tone the skin variation tone
     * @return an emoji with the skin variation tone, or the original emoji if not found
     */
    public static Emoji emojiWithTone(Emoji emoji, EmojiSkinTone tone) {
        return emojiWithTone(emoji, tone, tone);
    }

    /**
     * For a given emoji, returns a possible emoji variation with a given couple of skin tones, or
     * the same emoji, if not found
     * For instance, for the emoji "1F9D1-200D-1F91D-200D-1F9D1", and tones "1F3FC" and "1F3FD",
     * returns the emoji variation "1F9D1-1F3FC-200D-1F91D-200D-1F9D1-1F3FD"
     *
     * @param emoji an emoji without tone
     * @param tone1 the first skin variation tone
     * @param tone2 the second skin variation tone
     * @return an emoji with the skin variations, or the original emoji if not found
     */
    public static Emoji emojiWithTone(Emoji emoji, EmojiSkinTone tone1, EmojiSkinTone tone2) {
        if (emoji == null) {
            return null;
        }
        if (tone1 == EmojiSkinTone.NO_SKIN_TONE || tone2 == EmojiSkinTone.NO_SKIN_TONE) {
            return emoji;
        }
        Map<String, Emoji> skinVariationMap = emoji.getSkinVariationMap();
        if (skinVariationMap != null) {
            String skinTone1 = tone1.getUnicode();
            String skinTone2 = tone2.getUnicode();
            if (skinTone1.equals(skinTone2)) {
                if (skinVariationMap.containsKey(skinTone1)) {
                    // simple tone
                    return skinVariationMap.getOrDefault(skinTone1, emoji);
                }
                if (skinVariationMap.containsKey(skinTone1 + "-" + skinTone1)) {
                    // double tone, but equal tone in both sides
                    return skinVariationMap.getOrDefault(skinTone1 + "-" + skinTone1, emoji);
                }
            } else {
                if (skinVariationMap.containsKey(skinTone1 + "-" + skinTone2)) {
                    // double tone
                    return skinVariationMap.getOrDefault(skinTone1 + "-" + skinTone2, emoji);
                }
            }
        }
        return emoji;
    }

    /**
     * For a given emoji variation with a possible skin tone, finds the emoji without any skin tone
     * For instance, for the emoji "1F44B-1F3FC", returns the emoji "1F44B"
     *
     * @param emoji the emoji variation
     * @return an emoji without skin tone
     */
    public static Emoji emojiWithoutTone(Emoji emoji) {
        if (emoji == null) {
            return null;
        }
        String shortName = emoji.getShortName().split(":")[0];
        return EMOJI_MAP.getOrDefault(shortName, emoji);
    }
}
