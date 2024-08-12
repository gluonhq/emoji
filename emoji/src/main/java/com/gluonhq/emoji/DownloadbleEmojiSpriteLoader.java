package com.gluonhq.emoji;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

public class DownloadbleEmojiSpriteLoader implements EmojiSpriteLoader {

    private static final String COMMIT_NUMBER = "063f328d7951cb2e2a6678b06dcbdf8dd599fad7"; // tag 15.0.1
    private static final String EMOJI_PNG_URL = "https://github.com/iamcal/emoji-data/blob/" + COMMIT_NUMBER + "/sheets-clean/sheet_apple_%s_clean.png?raw=true";

    private static final String LOCAL_PATH = System.getProperty("user.home") + "/.gluon/emoji/" + COMMIT_NUMBER;
    private boolean initialized;

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public CompletableFuture<Boolean> initialize() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                downloadSprites(20, 32, 64);
                initialized = true;
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    private void downloadSprites(int... sizes) {
        for (int size : sizes) {
            String fileName = "sheet_apple_" + size + ".png";
            Path localPath = Paths.get(LOCAL_PATH, fileName);
            try {
                if (!Files.exists(localPath)) {
                    downloadFile(new URI(String.format(EMOJI_PNG_URL, size)).toURL(), localPath);
                }
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException("Unable to load local image file", e);
            }
        }
    }

    public Image loadEmojiSprite(int size) {
        if (!initialized) {
            throw new RuntimeException("Sprite Loader hasn't been initialized or completed initialization");
        }
        String fileName = "sheet_apple_" + size + ".png";
        Path localPath = Paths.get(LOCAL_PATH, fileName);
        try {
            return new Image(new FileInputStream(localPath.toFile()));
        } catch (IOException e) {
            throw new RuntimeException("Unable to load local image file", e);
        }
    }

    @Override
    public InputStream loadCSV() {
        return DownloadbleEmojiSpriteLoader.class.getResourceAsStream("emoji.csv");
    }

    private void downloadFile(URL url, Path filePath) throws IOException {
        Path parentDir = filePath.getParent();
        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }
        ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath.toFile());
             FileChannel fileChannel = fileOutputStream.getChannel()) {
            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        }
    }
}
