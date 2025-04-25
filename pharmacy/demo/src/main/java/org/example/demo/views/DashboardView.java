package org.example.demo.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.function.Function;

public class DashboardView {
    public DashboardView() {System.out.println("Default font: " + Font.getDefault().getName());} // ✅ Add this line
    public Parent getView() {
        // Main content area (center region)
        VBox mainContent = new VBox();
        mainContent.setPadding(new Insets(20));
        mainContent.setSpacing(20);

        // Info cards container
        HBox infoBar = new HBox(30);
        infoBar.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(infoBar, Priority.ALWAYS);

        // Card creation function
        Function<String, VBox> makeCard = title -> {
            Label titleLbl = new Label(title);
            titleLbl.setStyle("-fx-text-fill: #555;");
            Label valueLbl = new Label("0");
            valueLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #222;");

            VBox card = new VBox(8, titleLbl, valueLbl);
            card.setAlignment(Pos.CENTER_LEFT);
            card.setPadding(new Insets(20, 25, 20, 25));
            card.setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 10;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 6,0,0,3);
            """);

            card.minWidthProperty().bind(infoBar.widthProperty().divide(4.5).subtract(30));
            card.prefWidthProperty().bind(infoBar.widthProperty().divide(4.5).subtract(30));
            card.maxWidthProperty().bind(infoBar.widthProperty().divide(3.5).subtract(30));

            card.minHeightProperty().bind(card.widthProperty().multiply(0.5));
            card.prefHeightProperty().bind(card.widthProperty().multiply(0.5));
            card.maxHeightProperty().bind(card.widthProperty().multiply(0.7));

            titleLbl.styleProperty().bind(
                    javafx.beans.binding.Bindings.concat(
                            "-fx-text-fill: #555; -fx-font-size: ",
                            card.widthProperty().divide(20), "px;"
                    )
            );

            valueLbl.styleProperty().bind(
                    javafx.beans.binding.Bindings.concat(
                            "-fx-font-weight: bold; -fx-text-fill: #222; -fx-font-size: ",
                            card.widthProperty().divide(10), "px;"
                    )
            );

            return card;
        };

        // Add cards to the infoBar
        infoBar.getChildren().addAll(
                makeCard.apply("Total Customers"),
                makeCard.apply("Total Sales"),
                makeCard.apply("Total Profit"),
                makeCard.apply("Out of Stock")
        );

        // Create the enhanced table card
        VBox tableCard = new VBox();
        tableCard.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 10;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 6,0,0,3);
            -fx-padding: 20;
        """);
        VBox.setVgrow(tableCard, Priority.ALWAYS);

        // Create column constraints first to ensure perfect alignment
        double[] columnWeights = {2.5, 1.5, 1.0, 1.5, 1.5, 1.0};
        ColumnConstraints[] columnConstraints = new ColumnConstraints[columnWeights.length];
        double totalWeight = 0;
        for (double weight : columnWeights) {
            totalWeight += weight;
        }

        for (int i = 0; i < columnWeights.length; i++) {
            columnConstraints[i] = new ColumnConstraints();
            columnConstraints[i].setHgrow(Priority.ALWAYS);
            columnConstraints[i].setPercentWidth(100.0 * columnWeights[i] / totalWeight);
        }

        // Table header with perfect alignment
        GridPane header = new GridPane();
        header.setHgap(15);
        header.setPadding(new Insets(0, 0, 15, 0));
        header.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0;");
        header.getColumnConstraints().addAll(columnConstraints);

        String[] headers = {"Medicine Name", "Expiry Date", "Qty", "Batch No.", "Status", "Price"};
        for (int i = 0; i < headers.length; i++) {
            Label headerLabel = new Label(headers[i]);
            headerLabel.setStyle("""
                -fx-font-weight: bold;
                -fx-text-fill: #3E4A61;
                -fx-font-size: 14px;
                -fx-alignment: CENTER_LEFT;
                -fx-padding: 0 0 5 0;
            """);
            header.add(headerLabel, i, 0);
        }

        // Create scrollable table content with THE SAME column constraints
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background: white; -fx-border-color: white;");

        GridPane tableContent = new GridPane();
        tableContent.setHgap(15);
        tableContent.setVgap(8);
        tableContent.setPadding(new Insets(10, 0, 0, 0));
        tableContent.getColumnConstraints().addAll(columnConstraints); // Same constraints!

        // Sample data with perfect alignment
        String[][] sampleData = {
                {"Paracetamol 500mg", "2024-12-31", "150", "BATCH001", "In Stock", "$5.99"},
                {"Ibuprofen 200mg", "2025-06-30", "75", "BATCH042", "Low Stock", "$7.50"},
                {"Amoxicillin 250mg", "2024-09-15", "30", "BATCH156", "Expiring Soon", "$12.99"},
                {"Lisinopril 10mg", "2026-03-01", "200", "BATCH210", "In Stock", "$9.25"},
                {"Atorvastatin 20mg", "2025-11-30", "90", "BATCH333", "In Stock", "$15.75"}
        };

        // Add data rows with perfect alignment under headers
        for (int row = 0; row < sampleData.length; row++) {
            for (int col = 0; col < sampleData[row].length; col++) {
                Label dataLabel = new Label(sampleData[row][col]);
                dataLabel.setStyle("""
                    -fx-text-fill: #555;
                    -fx-font-size: 13px;
                    -fx-alignment: CENTER_LEFT;
                    -fx-padding: 8 5;
                """);

                // Add alternating row colors
                if (row % 2 == 0) {
                    dataLabel.setStyle(dataLabel.getStyle() + "-fx-background-color: #f8f9fa;");
                }

                // Use the same column index as headers
                tableContent.add(dataLabel, col, row);
            }
        }

        scrollPane.setContent(tableContent);

        // Add components to table card
        tableCard.getChildren().addAll(header, scrollPane);

        // Add components to main content
        mainContent.getChildren().addAll(infoBar, tableCard);
        VBox.setVgrow(tableCard, Priority.ALWAYS);

        return mainContent;
    }
}
