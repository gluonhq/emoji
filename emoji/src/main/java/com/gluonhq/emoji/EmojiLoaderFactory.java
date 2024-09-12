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

import java.util.logging.Logger;

public class EmojiLoaderFactory {

    private static final Logger LOG = Logger.getLogger(EmojiLoaderFactory.class.getName());

    private static final String OFFLINE_LOADER_CLASS = "com.gluonhq.emoji.LocalEmojiSpriteLoader";
    private static final String ONLINE_LOADER_CLASS = "com.gluonhq.emoji.DownloadableEmojiSpriteLoader";

    private static EmojiSpriteLoader emojiSpriteLoader;
    public static EmojiSpriteLoader getEmojiImageLoader() {
        if (emojiSpriteLoader == null) {
            if (isClassAvailable(OFFLINE_LOADER_CLASS)) {
                LOG.fine("Loading " + OFFLINE_LOADER_CLASS);
                emojiSpriteLoader = createInstance(OFFLINE_LOADER_CLASS);
            } else {
                LOG.fine("Loading " + ONLINE_LOADER_CLASS);
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
