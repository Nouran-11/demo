package org.example.demo;

import javafx.scene.text.Font;

public class FontLoader {
    public static void loadManropeFont() {
        Font.loadFont(FontLoader.class.getResourceAsStream("/fonts/Manrope-Regular.ttf"), 12);
    }
}
