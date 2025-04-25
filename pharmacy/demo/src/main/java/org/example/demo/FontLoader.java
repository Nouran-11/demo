package org.example.demo;

import javafx.scene.text.Font;

public class FontLoader {

    private static final String FONT_PATH = "/fonts/Manrope-Regular.ttf";

    public static void loadManropeFont() {
        try {
            Font font = Font.loadFont(FontLoader.class.getResource("/fonts/Manrope-Regular.ttf").toExternalForm(), 12);
            System.out.println("Font loaded: " + font.getName());

        } catch (Exception e) {
            System.err.println("Failed to load Manrope font:");
            e.printStackTrace();
        }
    }
}

