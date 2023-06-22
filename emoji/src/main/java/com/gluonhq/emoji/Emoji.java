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
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents the data for each emoji as parsed from emoji.json
 * {
 *     "name": "WHITE UP POINTING INDEX",
 *     "unified": "261D-FE0F",
 *     "non_qualified": "261D",
 *     "docomo": null,
 *     "au": "E4F6",
 *     "softbank": "E00F",
 *     "google": "FEB98",
 *     "image": "261d.png",
 *     "sheet_x": 1,
 *     "sheet_y": 2,
 *     "short_name": "point_up",
 *     "short_names": [
 *         "point_up"
 *     ],
 *     "text": null,
 *     "texts": null,
 *     "category": "People & Body",
 *     "subcategory": "hand-single-finger",
 *     "sort_order": 170,
 *     "added_in": "1.4",
 *     "has_img_apple": true,
 *     "has_img_google": true,
 *     "has_img_twitter": true,
 *     "has_img_facebook": false,
 *     "skin_variations": {
 *         "1F3FB": {
 *             "unified": "261D-1F3FB",
 *             "image": "261d-1f3fb.png",
 *             "sheet_x": 1,
 *             "sheet_y": 3,
 *             "added_in": "6.0",
 *             "has_img_apple": true,
 *             "has_img_google": false,
 *             "has_img_twitter": false,
 *             "has_img_facebook": false,
 *         }
 *         ...
 *         "1F3FB-1F3FC": {
 *             ...
 *         }
 *     },
 *     "obsoletes": "ABCD-1234",
 *     "obsoleted_by": "5678-90EF"
 * }
 */
public class Emoji {

    private static final Logger LOG = Logger.getLogger(Emoji.class.getName());

    private static final String ITEMS_LIST_DELIMITER = "!";
    private static final String FIELDS_SKIN_DELIMITER = ",";

    private String name;
    private String unified;
    private String non_qualified;

    private String docomo;
    private String au;
    private String softbank;
    private String google;
    private String image;

    private int sheet_x;
    private int sheet_y;

    private String short_name;
    private List<String> short_names;

    private String text;
    private List<String> texts;

    private String category;
    private String subcategory;
    private int sort_order;
    private String added_in;

    private boolean has_img_apple;
    private boolean has_img_google;
    private boolean has_img_twitter;
    private boolean has_img_facebook;

    private Map<String, Emoji> skin_variations = new HashMap<>();
    private String obsoletes;
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
