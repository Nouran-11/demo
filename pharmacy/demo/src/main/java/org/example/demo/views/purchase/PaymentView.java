package org.example.demo.views.purchase;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PaymentView {
    public Parent getView() {
        VBox box = new VBox(new Label("Payment View"));
        box.setStyle("-fx-padding: 20;");
        return box;
    }
}
