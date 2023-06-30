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
}
