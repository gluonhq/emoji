/*
 * Copyright (c) 2023, 2024, Gluon
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
import java.util.Properties;
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

        LOG.info("Copying files to offline...");
        Path offlinePath = files.toAbsolutePath().getParent().getParent()
                .resolve(Path.of("offline", "src", "main", "resources", "com", "gluonhq", "emoji", "offline"));
        Files.copy(files.resolve("emoji.csv"), offlinePath.resolve("emoji.csv"), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(files.resolve("sheet_apple_20.png"), offlinePath.resolve("sheet_apple_20.png"), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(files.resolve("sheet_apple_32.png"), offlinePath.resolve("sheet_apple_32.png"), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(files.resolve("sheet_apple_64.png"), offlinePath.resolve("sheet_apple_64.png"), StandardCopyOption.REPLACE_EXISTING);

        LOG.info("Copying files to emoji-core...");
        Path resourcesPath = files.toAbsolutePath().getParent().getParent()
                .resolve(Path.of("emoji", "src", "main", "resources", "com", "gluonhq", "emoji"));
        Files.copy(files.resolve("emoji.csv"), resourcesPath.resolve("emoji.csv"), StandardCopyOption.REPLACE_EXISTING);
        LOG.info("Updating emoji.properties...");
        Path propertiesFilePath = resourcesPath.resolve("emoji.properties");
        Properties properties = new Properties();
        properties.load(Files.newInputStream(propertiesFilePath));
        properties.setProperty("commit", COMMIT_NUMBER);
        properties.store(Files.newOutputStream(propertiesFilePath), null);
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
