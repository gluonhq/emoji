package com.gluonhq.emoji.util;

import com.gluonhq.emoji.Emoji;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.gluonhq.emoji.EmojiData.emojiFromUnicodeString;
import static com.gluonhq.emoji.util.TextUtils.convertToStringAndEmojiObjects;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextUtilTest {

    @Test
    public void emojiFromStringTest() {
        List<Object> wavingHands = convertToStringAndEmojiObjects("\uD83D\uDC4B");
        assertFalse(wavingHands.isEmpty());
        assertEquals(1, wavingHands.size());
        Object wavingHandsObject = wavingHands.get(0);
        assertTrue(wavingHandsObject instanceof Emoji);
        Emoji emoji = (Emoji) wavingHandsObject;
        assertNotNull(emoji);
        assertEquals("1F44B", emoji.getUnified());
        assertEquals("\uD83D\uDC4B", emoji.character());
    }

    @Test
    public void textFromStringTest() {
        List<Object> textList = convertToStringAndEmojiObjects("this is text");
        assertFalse(textList.isEmpty());
        assertEquals(1, textList.size());
        Object textObject = textList.get(0);
        assertTrue(textObject instanceof String);
        String text = (String) textObject;
        assertEquals("this is text", text);
    }

    @Test
    public void emojiAndTextFromStringTest() {
        List<Object> list = convertToStringAndEmojiObjects("this is an emoji: \uD83D\uDC4B");
        assertFalse(list.isEmpty());
        assertEquals(2, list.size());
        Object textObject = list.get(0);
        assertTrue(textObject instanceof String);
        String text = (String) textObject;
        assertEquals("this is an emoji: ", text);
        Object wavingHandsObject = list.get(1);
        assertTrue(wavingHandsObject instanceof Emoji);
        Emoji emoji = (Emoji) wavingHandsObject;
        assertNotNull(emoji);
        assertEquals("1F44B", emoji.getUnified());
        assertEquals("\uD83D\uDC4B", emoji.character());
    }

    @Test
    public void emojiToneFromStringTest() {
        List<Object> wavingHands = convertToStringAndEmojiObjects("\uD83D\uDC4B\uD83C\uDFFC");
        assertFalse(wavingHands.isEmpty());
        assertEquals(1, wavingHands.size());
        Object wavingHandsObject = wavingHands.get(0);
        assertTrue(wavingHandsObject instanceof Emoji);
        Emoji emoji = (Emoji) wavingHandsObject;
        assertNotNull(emoji);
        assertEquals("1F44B-1F3FC", emoji.getUnified());
    }

    @Test
    public void emojiTwoTonesFromStringTest() {
        List<Object> peopleHoldingHands = convertToStringAndEmojiObjects(
                "\uD83E\uDDD1\uD83C\uDFFC\u200D\uD83E\uDD1D\u200D\uD83E\uDDD1\uD83C\uDFFD");
        assertFalse(peopleHoldingHands.isEmpty());
        assertEquals(1, peopleHoldingHands.size());
        Object peopleHoldingHandsObject = peopleHoldingHands.get(0);
        assertTrue(peopleHoldingHandsObject instanceof Emoji);
        Emoji emoji = (Emoji) peopleHoldingHandsObject;
        assertNotNull(emoji);
        assertEquals("1F9D1-1F3FC-200D-1F91D-200D-1F9D1-1F3FD", emoji.getUnified());
    }

    // Not all emojis from people & body category can have a skin tone
    @Test
    public void emojiAndToneFromStringTest() {
        List<Object> list = convertToStringAndEmojiObjects("\uD83D\uDC63\uD83C\uDFFC");
        assertFalse(list.isEmpty());
        assertEquals(2, list.size());
        Object footprintObject = list.get(0);
        assertTrue(footprintObject instanceof Emoji);
        Emoji emoji1 = (Emoji) footprintObject;
        assertNotNull(emoji1);
        assertEquals("1F463", emoji1.getUnified());
        Object toneObject = list.get(1);
        assertTrue(toneObject instanceof Emoji);
        Emoji emoji2 = (Emoji) toneObject;
        assertNotNull(emoji2);
        assertEquals("1F3FC", emoji2.getUnified());
    }

    @Test
    public void emojiHairFromStringTest() {
        List<Object> personList = convertToStringAndEmojiObjects("\uD83E\uDDD1\u200D\uD83E\uDDB2");
        assertFalse(personList.isEmpty());
        assertEquals(1, personList.size());
        Object personObject = personList.get(0);
        assertTrue(personObject instanceof Emoji);
        Emoji emoji = (Emoji) personObject;
        assertNotNull(emoji);
        assertEquals("1F9D1-200D-1F9B2", emoji.getUnified());
    }

    @Test
    public void emojiFlagFromStringTest() {
        List<Object> flagList = convertToStringAndEmojiObjects(
                "\uD83C\uDFF4\uDB40\uDC67\uDB40\uDC62\uDB40\uDC73\uDB40\uDC63\uDB40\uDC74\uDB40\uDC7F");
        assertFalse(flagList.isEmpty());
        assertEquals(1, flagList.size());
        Object flagObject = flagList.get(0);
        assertTrue(flagObject instanceof Emoji);
        Emoji emoji = (Emoji) flagObject;
        assertNotNull(emoji);
        assertEquals("1F3F4-E0067-E0062-E0073-E0063-E0074-E007F", emoji.getUnified());
    }

    @Test
    public void emojiTwoFlagsFromStringTest() {
        List<Object> flagList = convertToStringAndEmojiObjects(
                "\uD83C\uDFF4\u200D\u2620\uFE0F\uD83C\uDFF4\uDB40\uDC67\uDB40\uDC62\uDB40\uDC73\uDB40\uDC63\uDB40\uDC74\uDB40\uDC7F");
        assertFalse(flagList.isEmpty());
        assertEquals(2, flagList.size());
        Object flag1Object = flagList.get(0);
        assertTrue(flag1Object instanceof Emoji);
        Emoji emoji1 = (Emoji) flag1Object;
        assertNotNull(emoji1);
        assertEquals("1F3F4-200D-2620-FE0F", emoji1.getUnified());
        Object flag2Object = flagList.get(1);
        assertTrue(flag2Object instanceof Emoji);
        Emoji emoji2 = (Emoji) flag2Object;
        assertNotNull(emoji2);
        assertEquals("1F3F4-E0067-E0062-E0073-E0063-E0074-E007F", emoji2.getUnified());
    }

}
