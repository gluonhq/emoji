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
package com.gluonhq.emoji;

public enum EmojiCategory {
    
    SMILEYS_PEOPLE("Smileys & Emotion, People & Body", "\uD83D\uDE00", "emoji-symbol"),
    NATURE("Animals & Nature", "\uD83D\uDC3B", "emoji-animal-symbol"),
    FOOD_DRINK("Food & Drink", "\uD83C\uDF54", "emoji-food-symbol"),
    ACTIVITY("Activities", "\u26BD", "emoji-activity-symbol"),
    TRAVEL("Travel & Places", "\uD83D\uDE80", "emoji-travel-symbol"),
    OBJECTS("Objects", "\uD83D\uDCA1", "emoji-object-symbol"),
    SYMBOLS("Symbols", "\uD83D\uDC95", "emoji-symbol-symbol"),
    FLAGS("Flags", "\uD83C\uDF8C", "emoji-flag-symbol");

    private final String category;
    private final String unicode;
    private final String styleClass;

    EmojiCategory(String category, String unicode, String styleClass) {
        this.category = category;
        this.unicode = unicode;
        this.styleClass = styleClass;
    }
    
    public String categoryName() {
        return category;
    }

    public String getUnicode() {
        return unicode;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
