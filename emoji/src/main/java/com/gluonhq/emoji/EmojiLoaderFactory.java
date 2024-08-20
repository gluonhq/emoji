package com.gluonhq.emoji;

import java.util.logging.Logger;

public class EmojiLoaderFactory {

    private static final Logger LOG = Logger.getLogger(EmojiLoaderFactory.class.getName());

    private static final String OFFLINE_LOADER_CLASS = "com.gluonhq.emoji.LocalEmojiSpriteLoader";
    private static final String ONLINE_LOADER_CLASS = "com.gluonhq.emoji.DownloadableEmojiSpriteLoader";

    private static EmojiSpriteLoader emojiSpriteLoader;
    public static EmojiSpriteLoader getEmojiImageLoader() {
        if (emojiSpriteLoader == null) {
            if (isClassAvailable(OFFLINE_LOADER_CLASS)) {
                LOG.info("Loading " + OFFLINE_LOADER_CLASS);
                emojiSpriteLoader = createInstance(OFFLINE_LOADER_CLASS);
            } else {
                LOG.info("Loading " + ONLINE_LOADER_CLASS);
                emojiSpriteLoader = createInstance(ONLINE_LOADER_CLASS);
            }
        }
        return emojiSpriteLoader;
    }

    private static boolean isClassAvailable(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static EmojiSpriteLoader createInstance(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            return (EmojiSpriteLoader) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + className, e);
        }
    }
}
