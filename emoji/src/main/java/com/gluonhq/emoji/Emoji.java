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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Emoji {

    private static final String ITEMS_LIST_DELIMITER = "!";
    private static final String FIELDS_SKIN_DELIMITER = ",";

    /**
     * The official Unicode name, like "SMILING FACE WITH OPEN MOUTH AND SMILING EYES".
     * Not null
     */
    private String name;

    /**
     * The Unicode codepoint, like "1F604", without the 0x prefix.
     * It can have one or more codepoints, separated by a dash character, and include
     * a variant selector "FE0F", a zero width joiner "-200D",
     * skin tone ("1F3FB" to "1F3FB") or hairstyle modifiers ("1F9B0" to "1F9B3").
     * Not null
     */
    private String unified;

    /**
     * The emojis that use a variation selector ("FE0F") can also be used without it, otherwise is null.
     * For instance, for WORLD MAP ("1F5FA-FE0F") the non-qualified version is "1F5FA"
     */
    private String non_qualified;

    /**
     * The legacy Unicode codepoints used by NTT Docomo (previously styled as DoCoMo), a Japanese phone carrier.
     * For instance,for THUMBS UP SIGN ("1F44D"), the docomo version is "E727".
     * It can be null
     */
    private String docomo;

    /**
     * The legacy Unicode codepoints used by au by KDDI, a Japanese phone carrier.
     * For instance,for THUMBS UP SIGN ("1F44D"), the au version is "E4F9".
     * It can be null
     */
    private String au;

    /**
     * The legacy Unicode codepoints used by SoftBank, a Japanese phone carrier.
     * For instance,for THUMBS UP SIGN ("1F44D"), the softbank version is "E00E".
     * It can be null
     */
    private String softbank;

    /**
     * The legacy Unicode codepoints used by Google on Android devices.
     * For instance,for THUMBS UP SIGN ("1F44D"), the google version is "FEB97".
     * It can be null
     */
    private String google;

    /**
     * The name of the image file, like "1f604.png".
     * Not null
     */
    private String image;

    /**
     * The x position of the image in the sprite sheets.
     * For instance, for "1F604", the x position is 32
     */
    private int sheet_x;

    /**
     * The y position of the image in the sprite sheets.
     * For instance, for "1F604", the x position is 25
     */
    private int sheet_y;

    /**
     * The common short name for the image.
     * For instance, the short name for "1F604" is "smile".
     * Not null
     */
    private String short_name;

    /**
     * A list of common short names for the image.
     * For instance, the short names for "1F92B" are "shushing_face"
     * and "face_with_finger_covering_closed_lips".
     * Not null
     */
    private List<String> short_names;

    /**
     * An ASCII version of the emoji, like ":)" for "1F604", or null where none exists.
     */
    private String text;

    /**
     * A list with the ASCII version of the emoji, like ";)" and ";-)" for "1F609",
     * or null where none exists.
     */
    private List<String> texts;

    /**
     * Category group name. For instance, "1F604" belongs to "Smileys & Emotion" category.
     * It can't be null
     */
    private String category;

    /**
     * Subcategory group name. For instance, "1F604" belongs to "face-smiling" subcategory.
     * It can't be null
     */
    private String subcategory;

    /**
     * Global sorting index for all emoji, based on Unicode CLDR ordering.
     * For instance, the sorting index for "1F604" is 3
     */
    private int sort_order;

    /**
     * Emoji or Unicode version in which this codepoint/sequence was added.
     * For instance, "1FAE8" was added by the version 15.0.
     * Not null
     */
    private String added_in;

    /**
     * True if the given image set has an Apple image available. Note that
     * the current sprite sheets are based on Apple images.
     * For instance, "1F604" has it, but "2695-FE0F" doesn't, so it is represented with "?"
     */
    private boolean has_img_apple;

    /**
     * True if the given image set has a Google image available. Note that
     * the current project doesn't include the Google sprite sheets
     */
    private boolean has_img_google;

    /**
     * True if the given image set has a Twitter image available. Note that
     * the current project doesn't include the Twitter sprite sheets
     */
    private boolean has_img_twitter;

    /**
     * True if the given image set has a Facebook image available. Note that
     * the current project doesn't include the Facebook sprite sheets
     */
    private boolean has_img_facebook;

    /**
     * For emojis that support multiple skin tone variations, a map of alternative emojis,
     * keyed by the skin tone value.
     * For instance, "1F3C3" has 5 skin variations: "1F3C3-1F3FB", ..., "1F3C3-1F3FF".
     * For emojis that support multiple skin tones within a single emoji, each skin tone is
     * separated by a dash character.
     * For instance, "1F9D1-200D-1F91D-200D-1F9D1" has all 25 combinations.
     * It can be null
     */
    private Map<String, Emoji> skin_variations = new HashMap<>();

    /**
     * Emoji that are no longer used, in preference of gendered versions.
     * It can be null
     */
    private String obsoletes;

    /**
     * Emoji that are no longer used, in preference of gendered versions.
     * It can be null
     */
    private String obsoleted_by;

    public Optional<String> getName() {
        return name == null ? Optional.empty() : Optional.of(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnified() {
        return unified;
    }

    public void setUnified(String unified) {
        this.unified = unified;
    }

    public String getNon_qualified() {
        return non_qualified;
    }

    public void setNon_qualified(String non_qualified) {
        this.non_qualified = non_qualified;
    }

    public Optional<String> getDocomo() {
        return docomo == null ? Optional.empty() : Optional.of(docomo);
    }

    public void setDocomo(String docomo) {
        this.docomo = docomo;
    }

    public Optional<String> getAu() {
        return au == null ? Optional.empty() : Optional.of(au);
    }

    public void setAu(String au) {
        this.au = au;
    }

    public Optional<String> getSoftbank() {
        return softbank == null ? Optional.empty() : Optional.of(softbank);
    }

    public void setSoftbank(String softbank) {
        this.softbank = softbank;
    }

    public Optional<String> getGoogle() {
        return google == null ? Optional.empty() : Optional.of(google);
    }

    public void setGoogle(String google) {
        this.google = google;
    }

    public Optional<String> getImage() {
        return image == null ? Optional.empty() : Optional.of(image);
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getSheet_x() {
        return sheet_x;
    }

    public void setSheet_x(int sheet_x) {
        this.sheet_x = sheet_x;
    }

    public int getSheet_y() {
        return sheet_y;
    }

    public void setSheet_y(int sheet_y) {
        this.sheet_y = sheet_y;
    }

    public Optional<String> getShort_name() {
        return short_name == null? Optional.empty() : Optional.of(short_name);
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }
    
    public String getCodeName() {
        return getShort_name().isPresent() ? ":" + getShort_name().get() + ":" : "";
    }

    public List<String> getShort_names() {
        return short_names;
    }

    public void setShort_names(List<String> short_names) {
        this.short_names = short_names;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getTexts() {
        return texts;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
    }

    public Optional<String> getCategory() {
        return category == null? Optional.empty() : Optional.of(category);
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public int getSort_order() {
        return sort_order;
    }

    public void setSort_order(int sort_order) {
        this.sort_order = sort_order;
    }

    public String getAdded_in() {
        return added_in;
    }

    public void setAdded_in(String added_in) {
        this.added_in = added_in;
    }

    public boolean isHas_img_apple() {
        return has_img_apple;
    }

    public void setHas_img_apple(boolean has_img_apple) {
        this.has_img_apple = has_img_apple;
    }

    public boolean isHas_img_google() {
        return has_img_google;
    }

    public void setHas_img_google(boolean has_img_google) {
        this.has_img_google = has_img_google;
    }

    public boolean isHas_img_twitter() {
        return has_img_twitter;
    }

    public void setHas_img_twitter(boolean has_img_twitter) {
        this.has_img_twitter = has_img_twitter;
    }

    public boolean isHas_img_facebook() {
        return has_img_facebook;
    }

    public void setHas_img_facebook(boolean has_img_facebook) {
        this.has_img_facebook = has_img_facebook;
    }

    public Map<String, Emoji> getSkin_variations() {
        return skin_variations;
    }

    public void setSkin_variations(Map<String, Emoji> skin_variations) {
        this.skin_variations = skin_variations;
    }

    public String getObsoletes() {
        return obsoletes;
    }

    public void setObsoletes(String obsoletes) {
        this.obsoletes = obsoletes;
    }

    public String getObsoleted_by() {
        return obsoleted_by;
    }

    public void setObsoleted_by(String obsoleted_by) {
        this.obsoleted_by = obsoleted_by;
    }

    /**
     * `True` if emoji is coded on two or more bytes
     */
    public boolean isDoubleByte(Emoji emoji) {
        return getUnified().contains("-");
    }

    public String character() {
        return unicodeCharacter();
    }

    private String unicodeCharacter() {
        StringBuilder emojiString = new StringBuilder();
        final String unified = getUnified();
        for (String s : unified.split("-")) {
            emojiString.append(Character.toChars(Integer.parseInt(s, 16)));
        }
        return emojiString.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emoji emoji = (Emoji) o;
        return sheet_x == emoji.sheet_x && sheet_y == emoji.sheet_y && sort_order == emoji.sort_order &&
                has_img_apple == emoji.has_img_apple && has_img_google == emoji.has_img_google && has_img_twitter == emoji.has_img_twitter && has_img_facebook == emoji.has_img_facebook &&
                Objects.equals(name, emoji.name) && Objects.equals(unified, emoji.unified) && Objects.equals(non_qualified, emoji.non_qualified) &&
                Objects.equals(docomo, emoji.docomo) && Objects.equals(au, emoji.au) && Objects.equals(softbank, emoji.softbank) && Objects.equals(google, emoji.google) && Objects.equals(image, emoji.image) &&
                Objects.equals(short_name, emoji.short_name) && Objects.equals(short_names, emoji.short_names) && Objects.equals(text, emoji.text) && Objects.equals(texts, emoji.texts) &&
                Objects.equals(category, emoji.category) && Objects.equals(subcategory, emoji.subcategory) && Objects.equals(added_in, emoji.added_in) &&
                Objects.equals(skin_variations, emoji.skin_variations) && Objects.equals(obsoletes, emoji.obsoletes) && Objects.equals(obsoleted_by, emoji.obsoleted_by);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, unified, non_qualified, docomo, au, softbank, google, image, sheet_x, sheet_y,
                short_name, short_names, text, texts, category, subcategory, sort_order, added_in, has_img_apple, has_img_google, has_img_twitter, has_img_facebook,
                skin_variations, obsoletes, obsoleted_by);
    }

    @Override
    public String toString() {
        return "Emoji{" +
                "name='" + name + '\'' +
                ", unified='" + unified + '\'' +
                ", non_qualified='" + non_qualified + '\'' +
                ", docomo='" + docomo + '\'' +
                ", au='" + au + '\'' +
                ", softbank='" + softbank + '\'' +
                ", google='" + google + '\'' +
                ", image='" + image + '\'' +
                ", sheet_x=" + sheet_x +
                ", sheet_y=" + sheet_y +
                ", short_name='" + short_name + '\'' +
                ", short_names=" + short_names +
                ", text='" + text + '\'' +
                ", texts=" + texts +
                ", category='" + category + '\'' +
                ", subcategory='" + subcategory + '\'' +
                ", sort_order=" + sort_order +
                ", added_in='" + added_in + '\'' +
                ", has_img_apple=" + has_img_apple +
                ", has_img_google=" + has_img_google +
                ", has_img_twitter=" + has_img_twitter +
                ", has_img_facebook=" + has_img_facebook +
                ", skin_variations=" + skin_variations +
                ", obsoletes='" + obsoletes + '\'' +
                ", obsoleted_by='" + obsoleted_by + '\'' +
                '}';
    }

    static Emoji parseEmojiFromCSVList(List<String> v) {
        int i = 0;
        Emoji emoji = new Emoji();
        emoji.name = v.get(i++);
        emoji.unified = v.get(i++); emoji.non_qualified = getNullableField(v.get(i++));
        emoji.docomo = getNullableField(v.get(i++)); emoji.au = getNullableField(v.get(i++)); emoji.softbank = getNullableField(v.get(i++)); emoji.google = getNullableField(v.get(i++));
        emoji.image = v.get(i++);
        emoji.sheet_x = Integer.parseInt(v.get(i++)); emoji.sheet_y = Integer.parseInt(v.get(i++));
        emoji.short_name = v.get(i++);
        emoji.short_names = parseListValues(v.get(i++));
        emoji.text = getNullableField(v.get(i++));
        emoji.texts = parseListValues(v.get(i++));
        emoji.category = v.get(i++); emoji.subcategory = v.get(i++);
        emoji.sort_order = Integer.parseInt(v.get(i++)); emoji.added_in = v.get(i++);
        emoji.has_img_apple = Boolean.parseBoolean(v.get(i++)); emoji.has_img_google = Boolean.parseBoolean(v.get(i++));
        emoji.has_img_twitter = Boolean.parseBoolean(v.get(i++)); emoji.has_img_facebook = Boolean.parseBoolean(v.get(i++));
        parseSkinVariations(emoji, v.get(i++));
        emoji.obsoletes = getNullableField(v.get(i++));
        emoji.obsoleted_by = getNullableField(v.get(i));
        return emoji;
    }

    private static String getNullableField(String v) {
        return (v == null || v.isEmpty()) ? null : v;
    }

    private static List<String> parseListValues(String v) {
        if (v == null || v.isEmpty()) {
            return null;
        }
        return Stream.of(v.split(ITEMS_LIST_DELIMITER)).collect(Collectors.toList());
    }

    private static void parseSkinVariations(Emoji emoji, String skinToneList) {
        if (skinToneList == null || skinToneList.isEmpty()) {
            // no skin variations
            return;
        }
        Stream.of(skinToneList.split(ITEMS_LIST_DELIMITER)).forEach(skinTone -> {
            Emoji skinEmoji = new Emoji();
            String[] values = skinTone.split(FIELDS_SKIN_DELIMITER);
            int i = 0;
            String tone = values[i++];
            skinEmoji.name = tone;
            skinEmoji.unified = values[i++];
            skinEmoji.non_qualified = getNullableField(values[i++]);
            skinEmoji.image = values[i++];
            skinEmoji.sheet_x = Integer.parseInt(values[i++]);
            skinEmoji.sheet_y = Integer.parseInt(values[i++]);
            skinEmoji.added_in = values[i++];
            skinEmoji.has_img_apple = Boolean.parseBoolean(values[i++]);
            skinEmoji.has_img_google = Boolean.parseBoolean(values[i++]);
            skinEmoji.has_img_twitter = Boolean.parseBoolean(values[i++]);
            skinEmoji.has_img_facebook = Boolean.parseBoolean(values[i++]);
            if (values.length > 11) {
                skinEmoji.obsoletes = getNullableField(values[i++]);
                if (values.length > 12) {
                    skinEmoji.obsoleted_by = getNullableField(values[i]);
                }
            }
            emoji.skin_variations.put(tone, skinEmoji);
        });
    }
}
