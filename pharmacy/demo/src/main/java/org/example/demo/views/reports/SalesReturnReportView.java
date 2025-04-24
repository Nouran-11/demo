package org.example.demo.views.reports;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SalesReturnReportView {
    public Parent getView() {
        VBox box = new VBox(new Label("Sales Return Report View"));
        box.setStyle("-fx-padding: 20;");
        return box;
    }
}
