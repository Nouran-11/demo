package org.example.demo.views.reports;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PurchaseReportView {
    public Parent getView() {
        VBox box = new VBox(new Label("Purchase Report View"));
        box.setStyle("-fx-padding: 20;");
        return box;
    }
}

