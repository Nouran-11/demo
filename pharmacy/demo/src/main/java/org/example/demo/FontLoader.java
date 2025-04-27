package org.example.demo;

import javafx.scene.text.Font;

public class FontLoader {
    private static final String FONT_REGULAR = "/fonts/Manrope-Regular.ttf";
    private static boolean fontLoaded = false;

    public static void loadManropeFont() {
        try {
            // Load font from resources using getResourceAsStream
            Font font = Font.loadFont(
                    FontLoader.class.getResourceAsStream(FONT_REGULAR),
                    12 // Default size, actual size can be set when used
            );

            if (font != null) {
                fontLoaded = true;
                System.out.println("Manrope font loaded successfully: " + font.getName());
            } else {
                System.err.println("Failed to load Manrope font (font object is null)");
                System.err.println("Possible causes:");
                System.err.println("1. Font file not found at: " + FONT_REGULAR);
                System.err.println("2. Corrupted font file");
                System.err.println("3. Invalid font format");
            }
        } catch (Exception e) {
            System.err.println("Error loading Manrope font:");
            e.printStackTrace();
            System.err.println("Font path attempted: " + FONT_REGULAR);
            System.err.println("Ensure:");
            System.err.println("1. The font file exists in src/main/resources/fonts/");
            System.err.println("2. The filename is exactly 'Manrope-Regular.ttf' (case-sensitive)");
            System.err.println("3. The file is properly included in your build");
        }
    }

    public static boolean isManropeLoaded() {
        // Check if font is available to JavaFX
        return fontLoaded && Font.getFamilies().contains("Manrope");
    }
}
