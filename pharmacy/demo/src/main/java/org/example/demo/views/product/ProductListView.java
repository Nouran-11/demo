package org.example.demo.views.product;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ProductListView {
    public Parent getView() {
        VBox box = new VBox(new Label("Product List View"));
        box.setStyle("-fx-padding: 20;");
        return box;
    }
}
