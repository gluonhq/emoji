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

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.gluonhq.emoji.EmojiData.categories;
import static com.gluonhq.emoji.EmojiData.copyEmoji;
import static com.gluonhq.emoji.EmojiData.emojiForText;
import static com.gluonhq.emoji.EmojiData.emojiFromCategory;
import static com.gluonhq.emoji.EmojiData.emojiFromCodeName;
import static com.gluonhq.emoji.EmojiData.emojiFromCodepoints;
import static com.gluonhq.emoji.EmojiData.emojiFromShortName;
import static com.gluonhq.emoji.EmojiData.emojiFromUnicodeString;
import static com.gluonhq.emoji.EmojiData.emojiWithTone;
import static com.gluonhq.emoji.EmojiData.emojiWithoutTone;
import static com.gluonhq.emoji.EmojiData.getEmojiCollection;
import static com.gluonhq.emoji.EmojiData.search;
import static com.gluonhq.emoji.EmojiData.shortNamesSet;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmojiDataTest {

    @Test
    public void emojiForShortNameTest() {
        Optional<Emoji> wavingHands = emojiFromShortName("wave");
        assertTrue(wavingHands.isPresent());
        Emoji emoji = assertDoesNotThrow(wavingHands::get);
        assertNotNull(emoji);
        assertEquals("1F44B", emoji.getUnified());
        assertEquals("\uD83D\uDC4B", emoji.character());
    }

    @Test
    public void emojiForTextKnown() {
        assertTrue(emojiForText("smile").isPresent());
        assertEquals("😄", emojiForText("smile").get());
    }

    @Test
    public void emojiForTextUnknown() {
        assertFalse(emojiForText("unknown").isPresent());
        assertEquals(Optional.empty(), emojiForText("unknown"));
    }

    @Test
    public void emojiForCodeNameTest() {
        Optional<Emoji> smile = emojiFromCodeName(":smile:");
        assertNotNull(smile);
        assertTrue(smile.isPresent());
        Emoji emoji = assertDoesNotThrow(smile::get);
        assertNotNull(emoji);
        assertEquals("1F604", emoji.getUnified());
    }

    @Test
    public void emojiFromUnicodeTest() {
        Optional<Emoji> wavingHands = emojiFromUnicodeString("\uD83D\uDC4B");
        assertTrue(wavingHands.isPresent());
        Emoji emoji = assertDoesNotThrow(wavingHands::get);
        assertNotNull(emoji);
        assertEquals("1F44B", emoji.getUnified());
        assertEquals("\uD83D\uDC4B", emoji.character());
    }

    @Test
    public void emojiFromCodepointsTest() {
        Optional<Emoji> wavingHands = emojiFromCodepoints("1F44B");
        assertFalse(wavingHands.isEmpty());
        Emoji emoji = assertDoesNotThrow(wavingHands::get);
        assertNotNull(emoji);
        assertEquals("1F44B", emoji.getUnified());
        assertEquals("\uD83D\uDC4B", emoji.character());
    }

    @Test
    public void emojiForSkinToneTest() {
        Optional<Emoji> wavingHandsTone = emojiFromCodepoints("1F44B-1F3FC");
        assertFalse(wavingHandsTone.isEmpty());
        Emoji emoji = assertDoesNotThrow(wavingHandsTone::get);
        assertNotNull(emoji);
        assertEquals("1F44B-1F3FC", emoji.getUnified());
        assertEquals("\uD83D\uDC4B\uD83C\uDFFC", emoji.character());
    }

    @Test
    public void emojiForCountryFlagTest() {
        Optional<Emoji> scotland = emojiFromCodepoints("1F3F4-E0067-E0062-E0073-E0063-E0074-E007F");
        assertFalse(scotland.isEmpty());
        Emoji emoji = assertDoesNotThrow(scotland::get);
        assertNotNull(emoji);
        assertEquals("1F3F4-E0067-E0062-E0073-E0063-E0074-E007F", emoji.getUnified());
        assertEquals("\uD83C\uDFF4\uDB40\uDC67\uDB40\uDC62\uDB40\uDC73\uDB40\uDC63\uDB40\uDC74\uDB40\uDC7F", emoji.character());
    }

    @Test
    public void invalidTwoEmojisUnicodeTest() {
        Optional<Emoji> twoEmojis = emojiFromCodepoints("1F603-1F606");
        assertTrue(twoEmojis.isEmpty());
    }

    @Test
    public void validEmojiConnectorUnicodeTest() {
        Optional<Emoji> family = emojiFromCodepoints("1F9D1-200D-1F91D-200D-1F9D1");
        assertFalse(family.isEmpty());
        Emoji emoji = assertDoesNotThrow(family::get);
        assertNotNull(emoji);
        assertEquals("1F9D1-200D-1F91D-200D-1F9D1", emoji.getUnified());
        assertEquals("\uD83E\uDDD1\u200D\uD83E\uDD1D\u200D\uD83E\uDDD1", emoji.character());
    }

    @Test
    public void invalidTwoFlagsUnicodeTest() {
        Optional<Emoji> twoFlags = emojiFromCodepoints("1F1E6-1F1E8-1F1E6-1F1E9");
        assertTrue(twoFlags.isEmpty());
    }

    @Test
    public void emojiFromCategoryTest() {
        List<Emoji> categoryList = emojiFromCategory("People & Body");
        assertFalse(categoryList.isEmpty());
        assertTrue(categoryList.size() > 2000);
        List<Emoji> emptyList = emojiFromCategory("People&Body");
        assertTrue(emptyList.isEmpty());
    }

    @Test
    public void searchTest() {
        List<Emoji> emojiList = search("wales");
        assertFalse(emojiList.isEmpty());
        assertTrue(emojiList.size() == 1);
        List<Emoji> emptyList = search("nothing");
        assertTrue(emptyList.isEmpty());
    }

    @Test
    public void shortNamesSetTest() {
        Set<String> set = shortNamesSet();
        assertFalse(set.isEmpty());
        assertTrue(set.size() > 3000);
    }

    @Test
    public void emojiCollectionTest() {
        Collection<Emoji> list = getEmojiCollection();
        assertFalse(list.isEmpty());
        assertTrue(list.size() > 3000);
    }

    @Test
    public void categoriesTest() {
        Set<String> set = categories();
        assertFalse(set.isEmpty());
        assertEquals(10, set.size());
    }

    @Test
    public void copyEmojiTest() {
        Optional<Emoji> wavingHands = emojiFromCodepoints("1F44B");
        assertFalse(wavingHands.isEmpty());
        Emoji emoji = assertDoesNotThrow(wavingHands::get);
        assertNotNull(emoji);
        assertEquals("1F44B", emoji.getUnified());
        assertEquals("\uD83D\uDC4B", emoji.character());
        Emoji copy = copyEmoji(emoji);
        assertEquals("1F44B", copy.getUnified());
        assertEquals("\uD83D\uDC4B", copy.character());
    }

    @Test
    public void emojiWithToneTest() {
        Optional<Emoji> wavingHands = emojiFromCodepoints("1F44B");
        assertFalse(wavingHands.isEmpty());
        Emoji emoji = assertDoesNotThrow(wavingHands::get);
        assertNotNull(emoji);
        assertEquals("1F44B", emoji.getUnified());
        Emoji emojiTone = emojiWithTone(emoji, EmojiSkinTone.MEDIUM_LIGHT_SKIN_TONE);
        assertEquals("1F44B-1F3FC", emojiTone.getUnified());
    }

    @Test
    public void emojiWithTwoTonesTest() {
        Optional<Emoji> peopleHoldingHands = emojiFromCodepoints("1F9D1-200D-1F91D-200D-1F9D1");
        assertFalse(peopleHoldingHands.isEmpty());
        Emoji emoji = assertDoesNotThrow(peopleHoldingHands::get);
        assertNotNull(emoji);
        assertEquals("1F9D1-200D-1F91D-200D-1F9D1", emoji.getUnified());
        Emoji emojiTone = emojiWithTone(emoji, EmojiSkinTone.MEDIUM_LIGHT_SKIN_TONE, EmojiSkinTone.MEDIUM_SKIN_TONE);
        assertEquals("1F9D1-1F3FC-200D-1F91D-200D-1F9D1-1F3FD", emojiTone.getUnified());
    }

    @Test
    public void emojiWithoutToneTest() {
        Optional<Emoji> wavingHandsTone = emojiFromCodepoints("1F44B-1F3FC");
        assertFalse(wavingHandsTone.isEmpty());
        Emoji emojiTone = assertDoesNotThrow(wavingHandsTone::get);
        assertNotNull(emojiTone);
        assertTrue(emojiTone.getSkinVariationMap().isEmpty());
        assertEquals("1F44B-1F3FC", emojiTone.getUnified());
        Emoji emoji = emojiWithoutTone(emojiTone);
        assertNotNull(emoji);
        assertEquals("1F44B", emoji.getUnified());
    }

    @Test
    public void emojiWithoutDoubleToneTest() {
        Optional<Emoji> handshakeTone = emojiFromCodepoints("1FAF1-1F3FB-200D-1FAF2-1F3FC");
        assertFalse(handshakeTone.isEmpty());
        Emoji emojiTone = assertDoesNotThrow(handshakeTone::get);
        assertNotNull(emojiTone);
        assertTrue(emojiTone.getSkinVariationMap().isEmpty());
        assertEquals("1FAF1-1F3FB-200D-1FAF2-1F3FC", emojiTone.getUnified());
        Emoji emoji = emojiWithoutTone(emojiTone);
        assertNotNull(emoji);
        assertEquals("1F91D", emoji.getUnified());
        assertFalse(emoji.getSkinVariationMap().isEmpty());
    }
}