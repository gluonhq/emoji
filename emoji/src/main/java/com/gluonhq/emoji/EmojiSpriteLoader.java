package com.gluonhq.emoji;

import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;

public interface EmojiSpriteLoader {

    Image loadEmojiSprite(int size);

    InputStream loadCSV();
}
