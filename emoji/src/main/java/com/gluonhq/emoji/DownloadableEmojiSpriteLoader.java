/*
 * Copyright (c) 2024, Gluon
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
import java.nio.file.StandardCopyOption;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DownloadableEmojiSpriteLoader implements EmojiSpriteLoader {

    private static final Logger LOG = Logger.getLogger(DownloadableEmojiSpriteLoader.class.getName());

    private static final String EMOJI_PNG_URL = "https://github.com/iamcal/emoji-data/blob/%s/sheets-clean/sheet_apple_%s_clean.png?raw=true";
    private static final String LOCAL_PATH = System.getProperty("user.home") + "/.gluon/emoji/%s";
    private static final int[] EMOJI_SIZES = new int[] { 20, 32, 64 };
    private boolean initialized;
    private final String commit;

    public DownloadableEmojiSpriteLoader() {
        Properties properties = new Properties();
        try (InputStream input = DownloadableEmojiSpriteLoader.class.getResourceAsStream("emoji.properties")) {
            properties.load(input);
            commit = properties.getProperty("commit");
        } catch (Exception ex) {
            LOG.severe("Unable to find emoji.properties");
            throw new RuntimeException("emoji.properties not available", ex);
        }
    }

    @Override
    public boolean isInitialized() {
        if (!initialized) {
            for (int size : EMOJI_SIZES) {
                if (!localFileExists(size)) {
                    return false;
                }
            }
        }
        return initialized = true;
    }

    @Override
    public CompletableFuture<Boolean> initialize() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                downloadSprites(EMOJI_SIZES);
                initialized = true;
                return true;
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "Downloading of sprites failed", e);
                return false;
            }
        }, Executors.newSingleThreadExecutor());
    }

    private void downloadSprites(int... sizes) {
        for (int size : sizes) {
            if (!localFileExists(size)) {
                try {
                    LOG.fine("Download sprite file for size: " + size);
                    String url = String.format(EMOJI_PNG_URL, commit, size);
                    downloadFile(new URI(url).toURL(), getLocalFilePath(size));
                } catch (IOException | URISyntaxException e) {
                    LOG.severe("Download sprite failed: " + e.getMessage());
                    throw new RuntimeException("Unable to load local image file", e);
                }
            }
        }
    }

    public Image loadEmojiSprite(int size) {
        if (!initialized) {
            throw new RuntimeException("Sprite Loader hasn't been initialized or completed initialization");
        }
        try {
            return new Image(new FileInputStream(getLocalFilePath(size).toFile()));
        } catch (IOException e) {
            LOG.severe("Loading of local image file failed: " + e.getMessage());
            throw new RuntimeException("Unable to load local image file", e);
        }
    }

    @Override
    public InputStream loadCSV() {
        return DownloadableEmojiSpriteLoader.class.getResourceAsStream("emoji.csv");
    }

    private void downloadFile(URL url, Path filePath) throws IOException {
        Path parentDir = filePath.getParent();
        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }
        try (InputStream inputStream = url.openStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Downloading file failed", e);
        }
    }

    private boolean localFileExists(int size) {
        Path localFilePath = getLocalFilePath(size);
        return Files.exists(localFilePath);
    }

    private Path getLocalFilePath(int size) {
        String fileName = "sheet_apple_" + size + ".png";
        return Paths.get(String.format(LOCAL_PATH, commit), fileName);
    }
}
