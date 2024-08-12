package com.gluonhq.emoji;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

public interface EmojiSpriteLoader {

    boolean isInitialized();

    CompletableFuture<Boolean> initialize();

    Image loadEmojiSprite(int size);

    InputStream loadCSV();
}
