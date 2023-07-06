package com.gluonhq.emoji.updater;

import com.gluonhq.connect.converter.InputStreamIterableInputConverter;
import com.gluonhq.connect.provider.InputStreamListDataReader;
import com.gluonhq.connect.provider.ListDataReader;
import com.gluonhq.connect.source.BasicInputDataSource;
import com.gluonhq.connect.source.InputDataSource;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * Run this class to get the latest emoji data from https://github.com/iamcal/emoji-data/
 *
 * It grabs the emoji.json and the related sprite sheet images, and add them to the emoji module,
 * converting the json format into a csv file that can be read without third party dependencies.
 *
 * Current Emoji version: 15.0 (September 2022)
 * Current emoji-data tag: 15.0.1 (May 2023)
 */
public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    /**
     * Update this commit number from https://github.com/iamcal/emoji-data/tags before running a new update
     */
    private static final String COMMIT_NUMBER = "063f328d7951cb2e2a6678b06dcbdf8dd599fad7"; // tag 15.0.1
    private static final String EMOJI_JSON_URL = "https://raw.githubusercontent.com/iamcal/emoji-data/" + COMMIT_NUMBER + "/emoji.json";
    private static final String EMOJI_20_PNG_URL = "https://github.com/iamcal/emoji-data/blob/" + COMMIT_NUMBER + "/sheets-clean/sheet_apple_20_clean.png?raw=true";
    private static final String EMOJI_32_PNG_URL = "https://github.com/iamcal/emoji-data/blob/" + COMMIT_NUMBER + "/sheets-clean/sheet_apple_32_clean.png?raw=true";
    private static final String EMOJI_64_PNG_URL = "https://github.com/iamcal/emoji-data/blob/" + COMMIT_NUMBER + "/sheets-clean/sheet_apple_64_clean.png?raw=true";

    public Main() throws IOException {
        Path files = Path.of("files");
        if (!Files.exists(files)) {
           Files.createDirectory(files);
        }
        LOG.info("Downloading files...");
        downloadFile(new URL(EMOJI_JSON_URL), files.resolve("emoji.json"));
        downloadFile(new URL(EMOJI_20_PNG_URL), files.resolve("sheet_apple_20.png"));
        downloadFile(new URL(EMOJI_32_PNG_URL), files.resolve("sheet_apple_32.png"));
        downloadFile(new URL(EMOJI_64_PNG_URL), files.resolve("sheet_apple_64.png"));

        LOG.info("Parsing emoji.json...");
        List<Emoji> emojiList = new ArrayList<>();
        try (final InputStream emojiStream = new FileInputStream(files.resolve("emoji.json").toFile())) {
            InputDataSource dataSource = new BasicInputDataSource(emojiStream);
            InputStreamIterableInputConverter<Emoji> converter = new EmojiIterableInputConverter(Emoji.class);
            ListDataReader<Emoji> listDataReader = new InputStreamListDataReader<>(dataSource, converter);
            for (Iterator<Emoji> it = listDataReader.iterator(); it.hasNext(); ) {
                emojiList.add(it.next());
            }
        }

        LOG.info("Creating emoji.csv...");
        PrintWriter writer = new PrintWriter(files.resolve("emoji.csv").toFile());
        for (Emoji sample : emojiList) {
            writer.println(sample.toString());
        }
        writer.close();

        LOG.info("Copying files to emoji-core...");
        Path resourcesPath = files.toAbsolutePath().getParent().getParent()
                .resolve(Path.of("emoji", "src", "main", "resources", "com", "gluonhq", "emoji"));
        Files.copy(files.resolve("emoji.csv"), resourcesPath.resolve("emoji.csv"), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(files.resolve("sheet_apple_20.png"), resourcesPath.resolve("util").resolve("sheet_apple_20.png"), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(files.resolve("sheet_apple_32.png"), resourcesPath.resolve("util").resolve("sheet_apple_32.png"), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(files.resolve("sheet_apple_64.png"), resourcesPath.resolve("util").resolve("sheet_apple_64.png"), StandardCopyOption.REPLACE_EXISTING);

        LOG.info("Done!");
    }

    private void downloadFile(URL url, Path filePath) throws IOException {
        ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath.toFile());
             FileChannel fileChannel = fileOutputStream.getChannel()) {
            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        }
    }

    public static void main(String[] args) throws IOException {
        new Main();
    }
}
