package org.example.demo.views.product;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ProductDamagesView {
    public Parent getView() {
        VBox box = new VBox(new Label("Product Damages View"));
        box.setStyle("-fx-padding: 20;");
        return box;
    }
}
