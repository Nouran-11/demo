package org.example.demo;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class AppEffects {
    public static DropShadow cardShadow() {
        DropShadow shadow = new DropShadow();
        shadow.setRadius(5);
        shadow.setColor(Color.color(0, 0, 0, 0.1));
        return shadow;
    }
}
