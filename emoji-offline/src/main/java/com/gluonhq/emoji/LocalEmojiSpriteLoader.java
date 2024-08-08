package com.gluonhq.emoji;

import javafx.scene.image.Image;

import java.io.InputStream;

public class LocalEmojiSpriteLoader implements EmojiSpriteLoader {
    @Override
    public Image loadEmojiSprite(int size) {
        switch (size) {
            case 20:
                return new Image(LocalEmojiSpriteLoader.class.getResourceAsStream("sheet_apple_20.png"));
            case 32:
                return new Image(LocalEmojiSpriteLoader.class.getResourceAsStream("sheet_apple_32.png"));
            case 64:
                return new Image(LocalEmojiSpriteLoader.class.getResourceAsStream("sheet_apple_64.png"));
            default:
                throw new IllegalArgumentException("Unsupported size: " + size);
        }
    }

    @Override
    public InputStream loadCSV() {
        return LocalEmojiSpriteLoader.class.getResourceAsStream("emoji.csv");
    }
}
