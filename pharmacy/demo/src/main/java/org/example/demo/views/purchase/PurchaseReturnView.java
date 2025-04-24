package org.example.demo.views.purchase;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PurchaseReturnView {
    public Parent getView() {
        VBox box = new VBox(new Label("Purchase Return View"));
        box.setStyle("-fx-padding: 20;");
        return box;
    }
}
