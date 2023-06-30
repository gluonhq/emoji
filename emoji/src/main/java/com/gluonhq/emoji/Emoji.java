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
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The Emoji class holds all the parameters that define an Emoji instance,
 * like its name, unified codepoints, x and y positions of the emoji image that
 * can be located in a sprite sheet, or category and alternative emojis.
 */
public class Emoji {

    private static final String ITEMS_LIST_DELIMITER = "!";
    private static final String FIELDS_SKIN_DELIMITER = ",";

    private String name;

    private String unified;

    private String nonQualified;

    private String docomo;

    private String au;

    private String softbank;

    private String google;

    private String image;

    private int sheetX;

    private int sheetY;

    private String shortName;

    private List<String> shortNameList;

    private String text;

    private List<String> textList;

    private String category;

    private String subcategory;

    private int sortOrder;

    private String addedIn;

    private boolean hasImgApple;

    private boolean hasImgGoogle;

    private boolean hasImgTwitter;

    private boolean hasImgFacebook;

    private Map<String, Emoji> skinVariationMap = new HashMap<>();

    private String obsoletes;

    private String obsoletedBy;

    /**
     * Gets the official Unicode name, like "SMILING FACE WITH OPEN MOUTH AND SMILING EYES".
     *
     * @return a string with the name of the emoji
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the official Unicode name, like "SMILING FACE WITH OPEN MOUTH AND SMILING EYES".
     *
     * @param name a string with the name of the emoji
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the Unicode codepoint, like "1F604", without the 0x prefix.
     * It can have one or more codepoints, separated by a dash character, and include
     * a variant selector "FE0F", a zero width joiner "-200D",
     * skin tone ("1F3FB" to "1F3FB") or hairstyle modifiers ("1F9B0" to "1F9B3").
     *
     * @return a string with the unicode codepoint(s) of the emoji
     */
    public String getUnified() {
        return unified;
    }

    /**
     * Sets the Unicode codepoint, like "1F604", without the 0x prefix.
     * It can have one or more codepoints, separated by a dash character, and include
     * a variant selector "FE0F", a zero width joiner "-200D",
     * skin tone ("1F3FB" to "1F3FB") or hairstyle modifiers ("1F9B0" to "1F9B3").
     *
     * @param unified a string with the unicode codepoint(s) of the emoji
     */
    public void setUnified(String unified) {
        this.unified = unified;
    }

    /**
     * Gets the non-qualified version of the emojis that use a variation selector ("FE0F").
     * For instance, for WORLD MAP ("1F5FA-FE0F") the non-qualified version is "1F5FA"
     *
     * @return a string with the non-qualified version of the emoji, it can be null
     */
    public String getNonQualified() {
        return nonQualified;
    }

    /**
     * Sets the non-qualified version of the emojis that use a variation selector ("FE0F").
     * For instance, for WORLD MAP ("1F5FA-FE0F") the non-qualified version is "1F5FA"
     *
     * @param nonQualified a string with the non-qualified version of the emoji, it can be null
     */
    public void setNonQualified(String nonQualified) {
        this.nonQualified = nonQualified;
    }

    /**
     * Gets the legacy Unicode codepoints used by NTT Docomo (previously styled as DoCoMo),
     * a Japanese phone carrier, or null.
     * For instance,for THUMBS UP SIGN ("1F44D"), the docomo version is "E727".
     *
     * @return the docomo codepoints of the emoji, or null
     */
    public String getDocomo() {
        return docomo;
    }

    /**
     * Sets the legacy Unicode codepoints used by NTT Docomo (previously styled as DoCoMo),
     * a Japanese phone carrier, or null.
     * For instance,for THUMBS UP SIGN ("1F44D"), the docomo version is "E727".
     *
     * @param docomo a string with the docomo codepoints, or null
     */
    public void setDocomo(String docomo) {
        this.docomo = docomo;
    }

    /**
     * Gets the legacy Unicode codepoints used by au by KDDI, a Japanese phone carrier.
     * For instance,for THUMBS UP SIGN ("1F44D"), the au version is "E4F9".
     *
     * @return the docomo codepoints of the emoji, or null
     */
    public String getAu() {
        return au;
    }

    /**
     * Sets The legacy Unicode codepoints used by au by KDDI, a Japanese phone carrier.
     * For instance,for THUMBS UP SIGN ("1F44D"), the au version is "E4F9".
     *
     * @param au a string with the au codepoints, or null
     */
    public void setAu(String au) {
        this.au = au;
    }

    /**
     * Gets the legacy Unicode codepoints used by SoftBank, a Japanese phone carrier, or null.
     * For instance,for THUMBS UP SIGN ("1F44D"), the softbank version is "E00E".
     *
     * @return the docomo codepoints of the emoji, or null
     */
    public String getSoftbank() {
        return softbank;
    }

    /**
     * Sets the legacy Unicode codepoints used by SoftBank, a Japanese phone carrier.
     * For instance,for THUMBS UP SIGN ("1F44D"), the softbank version is "E00E".
     *
     * @param softbank a string with the softbank codepoints, or null
     */
    public void setSoftbank(String softbank) {
        this.softbank = softbank;
    }

    /**
     * Gets the legacy Unicode codepoints used by Google on Android devices, or null.
     * For instance,for THUMBS UP SIGN ("1F44D"), the google version is "FEB97".
     *
     * @return the google codepoints of the emoji or empty
     */
    public String getGoogle() {
        return google;
    }

    /**
     * Sets The legacy Unicode codepoints used by Google on Android devices.
     * For instance,for THUMBS UP SIGN ("1F44D"), the google version is "FEB97".
     *
     * @param google a string with the google codepoints, or null
     */
    public void setGoogle(String google) {
        this.google = google;
    }

    /**
     * Gets the name of the image file, like "1f604.png".
     *
     * @return the name of the image file
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the name of the image file, like "1f604.png".
     *
     * @param image a string with the name of the image file of the emoji
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets the x position of the image in the sprite sheets.
     * For instance, for "1F604", the x position is 32
     *
     * @return the x position of the emoji in the sprite sheets
     */
    public int getSheetX() {
        return sheetX;
    }

    /**
     * Sets the x position of the image in the sprite sheets.
     * For instance, for "1F604", the x position is 32
     *
     * @param sheetX the x position of the emoji in the sprite sheets
     */
    public void setSheetX(int sheetX) {
        this.sheetX = sheetX;
    }

    /**
     * Gets the y position of the image in the sprite sheets.
     * For instance, for "1F604", the x position is 25
     *
     * @return the x position of the emoji in the sprite sheets
     */
    public int getSheetY() {
        return sheetY;
    }

    /**
     * Sets the y position of the image in the sprite sheets.
     * For instance, for "1F604", the x position is 25
     *
     * @param sheetY the y position of the emoji in the sprite sheets
     */
    public void setSheetY(int sheetY) {
        this.sheetY = sheetY;
    }

    /**
     * Gets the common short name of the emoji.
     * For instance, the short name for "1F604" is "smile".
     *
     * @return the common short name of the emoji
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Sets the common short name for the emoji.
     * For instance, the short name for "1F604" is "smile".
     *
     * @param shortName the short name of the emoji
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * Gets the common short name of the emoji wrapped with colons.
     * For instance, the code name for "1F604" is ":smile:".
     *
     * @return the common short name of the emoji wrapped with colons
     */
    public String getCodeName() {
        return ":" + getShortName() + ":";
    }

    /**
     * Gets a list of common short names for the emoji.
     * For instance, the short names for "1F92B" are "shushing_face"
     * and "face_with_finger_covering_closed_lips".
     *
     * @return a list of one or more common short names for the emoji
     */
    public List<String> getShortNameList() {
        return shortNameList;
    }

    /**
     * Sets the list of common short names for the emoji.
     * For instance, the short names for "1F92B" are "shushing_face"
     * and "face_with_finger_covering_closed_lips".
     *
     * @param shortNameList a list of common short names for the emoji
     */
    public void setShortNameList(List<String> shortNameList) {
        this.shortNameList = shortNameList;
    }

    /**
     * Gets an ASCII version of the emoji, like ":)" for "1F604", or null if none exists.
     *
     * @return a string with the ASCII version of the emoji, or null
     */
    public String getText() {
        return text;
    }

    /**
     * Sets an ASCII version of the emoji, like ":)" for "1F604", or null if none exists.
     *
     * @param text a string with the ASCII version of the emoji, can be null
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets a list with the ASCII version of the emoji, like ";)" and ";-)" for "1F609",
     * or empty if none exists.
     *
     * @return a list with the ASCII versions of the emoji
     */
    public List<String> getTextList() {
        return textList;
    }

    /**
     * Sets a list with the ASCII version of the emoji, like ";)" and ";-)" for "1F609".
     *
     * @param textList a list with the ASCII versions of the emoji
     */
    public void setTextList(List<String> textList) {
        this.textList = textList;
    }

    /**
     * Gets the category group name.
     * For instance, "1F604" belongs to "Smileys & Emotion" category.
     *
     * @return the category group name of the emoji or empty
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category group name of the emoji.
     * For instance, "1F604" belongs to "Smileys & Emotion" category.
     *
     * @param category a string with the category group name of the emoji.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the subcategory group name of the emoji.
     * For instance, "1F604" belongs to "face-smiling" subcategory.
     *
     * @return the subcategory group name of the emoji
     */
    public String getSubcategory() {
        return subcategory;
    }

    /**
     * Sets the subcategory group name of the emoji.
     * For instance, "1F604" belongs to "face-smiling" subcategory.
     *
     * @param subcategory a string with the subcategory group name of the emoji.
     */
    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    /**
     * Gets the global sorting index for the emoji, based on Unicode CLDR ordering.
     * For instance, the sorting index for "1F604" is 3
     *
     * @return the global sorting index for the emoji
     */
    public int getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the global sorting index for the emoji, based on Unicode CLDR ordering.
     * For instance, the sorting index for "1F604" is 3
     *
     * @param sortOrder the global sorting index for the emoji
     */
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Gets the Emoji or Unicode version in which this codepoint/sequence was added.
     * For instance, "1FAE8" was added by the version 15.0.
     *
     * @return a string with the Emoji version of the emoji
     */
    public String getAddedIn() {
        return addedIn;
    }

    /**
     * Sets the Emoji or Unicode version in which this codepoint/sequence was added.
     * For instance, "1FAE8" was added by the version 15.0.
     *
     * @param addedIn a string with the Emoji version of the emoji
     */
    public void setAddedIn(String addedIn) {
        this.addedIn = addedIn;
    }

    /**
     * Gets if the emoji has an Apple image available or false otherwise.
     * Note that the current sprite sheets are based on Apple images.
     * For instance, "1F604" has it, but "2695-FE0F" doesn't, so it is represented with "?"
     *
     * @return true if the emoji has an Apple image, or false otherwise
     */
    public boolean hasImgApple() {
        return hasImgApple;
    }

    /**
     * Sets if the emoji has an Apple image available.
     * Note that the current sprite sheets are based on Apple images.
     * For instance, "1F604" has it, but "2695-FE0F" doesn't, so it is represented with "?"
     *
     * @param hasImgApple true if the emoji has an Apple image available, false otherwise
     */
    public void setHasImgApple(boolean hasImgApple) {
        this.hasImgApple = hasImgApple;
    }


    /**
     * Gets if the emoji has a Google image available or false otherwise.
     * Note that the current project doesn't include the Google sprite sheets
     *
     * @return true if the emoji has a Google image, or false otherwise
     */
    public boolean hasImgGoogle() {
        return hasImgGoogle;
    }

    /**
     * Sets if the emoji has a Google image available.
     * Note that the current project doesn't include the Google sprite sheets
     *
     * @param hasImgGoogle true if the emoji has a Google image available, false otherwise
     */
    public void setHasImgGoogle(boolean hasImgGoogle) {
        this.hasImgGoogle = hasImgGoogle;
    }

    /**
     * Gets if the emoji has a Twitter image available or false otherwise.
     * Note that the current project doesn't include the Twitter sprite sheets
     *
     * @return true if the emoji has a Twitter image, or false otherwise
     */
    public boolean hasImgTwitter() {
        return hasImgTwitter;
    }

    /**
     * Sets if the emoji has a Twitter image available.
     * Note that the current project doesn't include the Twitter sprite sheets
     *
     * @param hasImgTwitter true if the emoji has a Twitter image available, false otherwise
     */
    public void setHasImgTwitter(boolean hasImgTwitter) {
        this.hasImgTwitter = hasImgTwitter;
    }

    /**
     * Gets if the emoji has a Facebook image available or false otherwise.
     * Note that the current project doesn't include the Facebook sprite sheets
     *
     * @return true if the emoji has a Facebook image, or false otherwise
     */
    public boolean hasImgFacebook() {
        return hasImgFacebook;
    }

    /**
     * Sets if the emoji has a Facebook image available.
     * Note that the current project doesn't include the Facebook sprite sheets
     *
     * @param hasImgFacebook true if the emoji has a Facebook image available, false otherwise
     */
    public void setHasImgFacebook(boolean hasImgFacebook) {
        this.hasImgFacebook = hasImgFacebook;
    }

    /**
     * For emojis that support multiple skin tone variations, gets a map of alternative emojis,
     * with keys, the skin tone values, like "1F3FB", and values, the alternative emojis.
     * For instance, "1F3C3" has 5 skin variations: "1F3C3-1F3FB", ..., "1F3C3-1F3FF".
     * For emojis that support multiple skin tones within a single emoji, each skin tone is
     * separated by a dash character.
     * For instance, "1F9D1-200D-1F91D-200D-1F9D1" has all 25 combinations.
     *
     * @return a map with the skin variations of the emoji, or empty
     */
    public Map<String, Emoji> getSkinVariationMap() {
        return skinVariationMap;
    }

    /**
     * For emojis that support multiple skin tone variations, sets a map of alternative emojis,
     * with keys, the skin tone values, like "1F3FB", and values, the alternative emojis.
     * For instance, "1F3C3" has 5 skin variations: "1F3C3-1F3FB", ..., "1F3C3-1F3FF".
     * For emojis that support multiple skin tones within a single emoji, each skin tone is
     * separated by a dash character.
     * For instance, "1F9D1-200D-1F91D-200D-1F9D1" has all 25 combinations.
     *
     * @param skinVariationMap a map with the skin variations of the emoji, that can be empty
     */
    public void setSkinVariationMap(Map<String, Emoji> skinVariationMap) {
        this.skinVariationMap = skinVariationMap;
    }

    /**
     * Gets the unicode codepoints of emojis that are no longer used, in preference of gendered versions.
     * For instance, "1F468-200D-1F469-200D-1F466" obsoletes "1F46A".
     *
     * @return a string with the unicode codepoints of emojis that are no longer used, or null
     */
    public String getObsoletes() {
        return obsoletes;
    }

    /**
     * Sets the unicode codepoints of emojis that are no longer used, in preference of gendered versions.
     * For instance, "1F468-200D-1F469-200D-1F466" obsoletes "1F46A".
     *
     * @param obsoletes a string with the unicode codepoints of emojis that are no longer used, or null
     */
    public void setObsoletes(String obsoletes) {
        this.obsoletes = obsoletes;
    }

    /**
     * Gets the unicode codepoints of emojis that are no longer used, in preference of gendered versions.
     * For instance, "26F9-FE0F" is obsoleted by "26F9-FE0F-200D-2642-FE0F".
     *
     * @return a string with the unicode codepoints of emojis that are no longer used, or null
     */
    public String getObsoletedBy() {
        return obsoletedBy;
    }

    /**
     * Gets the unicode codepoints of emojis that are no longer used, in preference of gendered versions.
     * For instance, "26F9-FE0F" is obsoleted by "26F9-FE0F-200D-2642-FE0F".
     *
     * @param obsoletedBy a string with the unicode codepoints of emojis that are no longer used, or null
     */
    public void setObsoletedBy(String obsoletedBy) {
        this.obsoletedBy = obsoletedBy;
    }

    /**
     * @return true if the emoji is coded on two or more bytes
     */
    public boolean isDoubleByte(Emoji emoji) {
        return getUnified().contains("-");
    }

    /**
     * Gets the unicode character of the emoji in UTF-16 representation.
     * For instance, for an emoji with unified value "1F44B", returns "\uD83D\uDC4B"
     *
     * @return the unicode character of the emoji
     */
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
        return sheetX == emoji.sheetX && sheetY == emoji.sheetY && sortOrder == emoji.sortOrder &&
                hasImgApple == emoji.hasImgApple && hasImgGoogle == emoji.hasImgGoogle && hasImgTwitter == emoji.hasImgTwitter && hasImgFacebook == emoji.hasImgFacebook &&
                Objects.equals(name, emoji.name) && Objects.equals(unified, emoji.unified) && Objects.equals(nonQualified, emoji.nonQualified) &&
                Objects.equals(docomo, emoji.docomo) && Objects.equals(au, emoji.au) && Objects.equals(softbank, emoji.softbank) && Objects.equals(google, emoji.google) && Objects.equals(image, emoji.image) &&
                Objects.equals(shortName, emoji.shortName) && Objects.equals(shortNameList, emoji.shortNameList) && Objects.equals(text, emoji.text) && Objects.equals(textList, emoji.textList) &&
                Objects.equals(category, emoji.category) && Objects.equals(subcategory, emoji.subcategory) && Objects.equals(addedIn, emoji.addedIn) &&
                Objects.equals(skinVariationMap, emoji.skinVariationMap) && Objects.equals(obsoletes, emoji.obsoletes) && Objects.equals(obsoletedBy, emoji.obsoletedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, unified, nonQualified, docomo, au, softbank, google, image, sheetX, sheetY,
                shortName, shortNameList, text, textList, category, subcategory, sortOrder, addedIn, hasImgApple, hasImgGoogle, hasImgTwitter, hasImgFacebook,
                skinVariationMap, obsoletes, obsoletedBy);
    }

    @Override
    public String toString() {
        return "Emoji{" +
                "name='" + name + '\'' +
                ", unified='" + unified + '\'' +
                ", nonQualified='" + nonQualified + '\'' +
                ", docomo='" + docomo + '\'' +
                ", au='" + au + '\'' +
                ", softbank='" + softbank + '\'' +
                ", google='" + google + '\'' +
                ", image='" + image + '\'' +
                ", sheetX=" + sheetX +
                ", sheetY=" + sheetY +
                ", shortName='" + shortName + '\'' +
                ", shortNameList=" + shortNameList +
                ", text='" + text + '\'' +
                ", texts=" + textList +
                ", category='" + category + '\'' +
                ", subcategory='" + subcategory + '\'' +
                ", sortOrder=" + sortOrder +
                ", addedIn='" + addedIn + '\'' +
                ", hasImgApple=" + hasImgApple +
                ", hasImgGoogle=" + hasImgGoogle +
                ", hasImgTwitter=" + hasImgTwitter +
                ", hasImgFacebook=" + hasImgFacebook +
                ", skinVariationMap=" + skinVariationMap +
                ", obsoletes='" + obsoletes + '\'' +
                ", obsoletedBy='" + obsoletedBy + '\'' +
                '}';
    }

    static Emoji parseEmojiFromCSVList(List<String> v) {
        int i = 0;
        Emoji emoji = new Emoji();
        emoji.name = v.get(i++);
        emoji.unified = v.get(i++); emoji.nonQualified = getNullableField(v.get(i++));
        emoji.docomo = getNullableField(v.get(i++)); emoji.au = getNullableField(v.get(i++)); emoji.softbank = getNullableField(v.get(i++)); emoji.google = getNullableField(v.get(i++));
        emoji.image = v.get(i++);
        emoji.sheetX = Integer.parseInt(v.get(i++)); emoji.sheetY = Integer.parseInt(v.get(i++));
        emoji.shortName = v.get(i++);
        emoji.shortNameList = parseListValues(v.get(i++));
        emoji.text = getNullableField(v.get(i++));
        emoji.textList = parseListValues(v.get(i++));
        emoji.category = v.get(i++); emoji.subcategory = v.get(i++);
        emoji.sortOrder = Integer.parseInt(v.get(i++)); emoji.addedIn = v.get(i++);
        emoji.hasImgApple = Boolean.parseBoolean(v.get(i++)); emoji.hasImgGoogle = Boolean.parseBoolean(v.get(i++));
        emoji.hasImgTwitter = Boolean.parseBoolean(v.get(i++)); emoji.hasImgFacebook = Boolean.parseBoolean(v.get(i++));
        parseSkinVariations(emoji, v.get(i++));
        emoji.obsoletes = getNullableField(v.get(i++));
        emoji.obsoletedBy = getNullableField(v.get(i));
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
            skinEmoji.name = emoji.getName() + ":" + EmojiSkinTone.getSkinVariationName(tone);
            skinEmoji.unified = values[i++];
            skinEmoji.nonQualified = getNullableField(values[i++]);
            skinEmoji.image = values[i++];
            skinEmoji.sheetX = Integer.parseInt(values[i++]);
            skinEmoji.sheetY = Integer.parseInt(values[i++]);
            skinEmoji.shortName = emoji.getShortName() + ":" + tone;
            skinEmoji.shortNameList = List.of(skinEmoji.shortName);
            skinEmoji.category = emoji.getCategory();
            skinEmoji.subcategory = emoji.getSubcategory();
            skinEmoji.sortOrder = emoji.getSortOrder();
            skinEmoji.addedIn = values[i++];
            skinEmoji.hasImgApple = Boolean.parseBoolean(values[i++]);
            skinEmoji.hasImgGoogle = Boolean.parseBoolean(values[i++]);
            skinEmoji.hasImgTwitter = Boolean.parseBoolean(values[i++]);
            skinEmoji.hasImgFacebook = Boolean.parseBoolean(values[i++]);
            if (values.length > 11) {
                skinEmoji.obsoletes = getNullableField(values[i++]);
                if (values.length > 12) {
                    skinEmoji.obsoletedBy = getNullableField(values[i]);
                }
            }
            emoji.skinVariationMap.put(tone, skinEmoji);
        });
    }
}
