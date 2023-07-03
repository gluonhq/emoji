/*
 * Copyright (c) 2023, Gluon
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
package com.gluonhq.emoji.util;

import com.gluonhq.emoji.Emoji;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.stage.Screen;

public class EmojiImageUtils {

    private static Image emojiSprite20;
    private static Image emojiSprite32;
    private static SoftReference<Image> emojiSprite64;
    
    private static Map<Emoji, SoftReference<Image>> emojiCache = new HashMap<>();
    private static final Logger LOG = Logger.getLogger(EmojiImageUtils.class.getName());

    public static final String IMAGE_VIEW_EMOJI_PROPERTY = "emoji_unified";

    // private static final Map<String, Image> emojiMap20;
    // private static final Map<String, Image> emojiMap32;
    /*static {
        emojiSprite20 = new Image(EmojiImageUtils.class.getResourceAsStream("/org/signal/sheet_apple_20.png"));
        emojiSprite32 = new Image(EmojiImageUtils.class.getResourceAsStream("/org/signal/sheet_apple_32.png"));
        emojiMap20 = new HashMap<>();
        emojiMap32 = new HashMap<>();
    }*/

    public static Image getImage20() {
        if (emojiSprite20 == null) {
            emojiSprite20 = new Image(EmojiImageUtils.class.getResourceAsStream("sheet_apple_20.png"));
        }
        return emojiSprite20;
    }

    public static Image getImage32() {
        if (emojiSprite32 == null) {
            emojiSprite32 = new Image(EmojiImageUtils.class.getResourceAsStream("sheet_apple_32.png"));
        }
        return emojiSprite32;
    }
    
    public static Image getImage64() {
        Image image64 = emojiSprite64 == null ? null : emojiSprite64.get();
        if (image64 == null) {
            image64 = new Image(EmojiImageUtils.class.getResourceAsStream("sheet_apple_64.png"));
            emojiSprite64 = new SoftReference<>(image64);
        }
        return image64;
    }

    public static Rectangle2D getViewportFor64(Emoji emoji) {
        return new Rectangle2D(
                emoji.getSheetX() * 66,
                emoji.getSheetY() * 66,
                66,
                66
        );
    }

    public static Rectangle2D getViewportFor32(Emoji emoji) {
        return new Rectangle2D(
                emoji.getSheetX() * 34,
                emoji.getSheetY() * 34,
                34,
                34
        );
    }

    public static Rectangle2D getViewportFor20(Emoji emoji) {
        return new Rectangle2D(
                emoji.getSheetX() * 22,
                emoji.getSheetY() * 22,
                22,
                22
        );
    }

    /**
     * Returns true is one of the Screen is a Retina display
     * or a scaling factor of more than equal to 1.5
     * @return true if one of the screen is a Retina display
     */
    public static boolean isRetina() {
        return Screen.getScreens().stream().mapToDouble(Screen::getOutputScaleX).max().orElse(1.0) >= 1.5;
    }

    /**
     * Provides ImageView containing emoji with a max size of 64 pixels.
     * When {@link #isRetina()} is true, it creates {@link ImageView} with a
     * larger pixel image and fit them to the specified size.
     * 
     * @param emoji Emoji for which we need to create the ImageView
     * @param size The height and width of the ImageView
     * @return ImageView containing the emoji image
     */
    public static ImageView emojiView(Emoji emoji, double size) {
        return emojiView(emoji, size, 1.0);
    }

    private static Image extractGlyph64(int x, int y, int width, int height) throws IOException {
        Image bigImage = getImage64();
        WritableImage glyphImage = new WritableImage(width, height);
        PixelReader pixelReader = bigImage.getPixelReader();
        for (int px = 0; px < width; px++) {
            for (int py = 0; py < height; py++) {
                int sourceX = x + px;
                int sourceY = y + py;
                int argb = pixelReader.getArgb(sourceX, sourceY);
                glyphImage.getPixelWriter().setArgb(px, py, argb);
            }
        }
        return glyphImage;
    }

    /**
     * Provides ImageView containing emoji with a max size of 64 pixels.
     * When {@link #isRetina()} is true, it creates {@link ImageView} with a
     * larger pixel image and fit them to the specified size.
     * The property with key {@link #IMAGE_VIEW_EMOJI_PROPERTY} is set to link back to
     * the emoji if needed.
     *
     * @param emoji Emoji for which we need to create the ImageView
     * @param size The height and width of the ImageView
     * @param offset Offset for the ImageView
     * @return ImageView containing the emoji image
     */
    public static ImageView emojiView(Emoji emoji, double size, double offset) {
        final ImageView emojiView = new ImageView() {
            @Override
            public double getBaselineOffset() {
                return super.getBaselineOffset() * offset;
            }
        };
        emojiView.setSmooth(true);
        emojiView.setPreserveRatio(true);
        emojiView.getProperties().put(IMAGE_VIEW_EMOJI_PROPERTY, emoji.getUnified());
        boolean gotImage = false;
        if (isRetina() || size > 32) {
            try {
                SoftReference<Image> imageRef = emojiCache.get(emoji);
                Image image = (imageRef != null) ? imageRef.get() : null;
                if ((image == null)) {
                    image = extractGlyph64(emoji.getSheetX() * 66,
                            emoji.getSheetY() * 66,
                            66,
                            66);
                    imageRef = new SoftReference<>(image);
                    emojiCache.put(emoji, imageRef);
                }
                emojiView.setImage(image);
                gotImage = true;
            } catch (IOException | OutOfMemoryError ex) {
                LOG.log(Level.SEVERE, "Error getting emojiView for emoji " + emoji.getUnified() + ": " + ex.getMessage(), ex);
                size = 20;
            }
        }

        if (!gotImage) {
            if (size <= 20) {
                emojiView.setImage(getImage20());
                emojiView.setViewport(getViewportFor20(emoji));
            } else if (size <= 32) {
                emojiView.setImage(getImage32());
                emojiView.setViewport(getViewportFor32(emoji));
            }
        }
        emojiView.setFitHeight(size);
        return emojiView;
    }

    /*public static Image getImage20(Emoji emoji) {
        return emojiMap20.computeIfAbsent(emoji.getShort_name().orElse(""), s -> {
            final PixelReader pixelReader = emojiSprite20.getPixelReader();
            // each image has a padding of 1px
            final int x = emoji.getSheet_x() * 22;
            final int y = emoji.getSheet_y() * 22;
            return new WritableImage(pixelReader, x, y, 20, 20);
        });
    }
    
    public static Image getImage32(Emoji emoji) {
        return emojiMap32.computeIfAbsent(emoji.getShort_name().orElse(""), s -> {
            final PixelReader pixelReader = emojiSprite32.getPixelReader();
            // each image has a padding of 1px
            final int x = emoji.getSheet_x() * 34;
            final int y = emoji.getSheet_y() * 34;
            return new WritableImage(pixelReader, x, y, 32, 32);
        });
    }*/
}