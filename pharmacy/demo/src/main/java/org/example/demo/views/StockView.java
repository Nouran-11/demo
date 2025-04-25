package org.example.demo.views;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class StockView {
    private ComboBox<Integer> itemsPerPageCombo;
    private int currentItemsShown = 100;

    public Parent getView() {
        // Main content area
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(30, 30, 50, 30));
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setStyle("-fx-background-color: #f8f9fa;");

        // Title section
        Label titleLabel = new Label("Stock Report");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        mainContent.getChildren().add(titleLabel);

        // Search section - redesigned for better proportions
        VBox searchCard = new VBox(15);
        searchCard.setAlignment(Pos.CENTER);
        searchCard.setPadding(new Insets(20, 40, 20, 40));  // More horizontal padding
        searchCard.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 10;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8,0,2,2);
            -fx-max-width: 800px;  // Constrained width
            -fx-alignment: center;
        """);

        // Search title with improved spacing
        Label searchLabel = new Label("Search");
        searchLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #3498db;");
        VBox.setMargin(searchLabel, new Insets(0, 0, 10, 0));
        searchCard.getChildren().add(searchLabel);

        // Search field with better proportions
        HBox searchFieldContainer = new HBox();
        searchFieldContainer.setAlignment(Pos.CENTER);

        TextField searchField = new TextField();
        searchField.setPromptText("Search anything...");
        searchField.setStyle("""
            -fx-font-size: 14px;
            -fx-pref-height: 40px;
            -fx-pref-width: 500px;  // Comfortable width
            -fx-max-width: 500px;
            -fx-background-radius: 20px;  // Rounded corners
            -fx-border-radius: 20px;
            -fx-border-color: #ddd;
            -fx-padding: 0 20px;
        """);

        // Search button with matching style
        Button searchButton = new Button("Search");
        searchButton.setStyle("""
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-text-fill: white;
            -fx-background-color: #3498db;
            -fx-background-radius: 20px;
            -fx-padding: 10 25;
            -fx-cursor: hand;
            -fx-border-radius: 20px;
        """);
        HBox.setMargin(searchButton, new Insets(0, 0, 0, 10));

        searchButton.setOnMouseEntered(e -> searchButton.setStyle(searchButton.getStyle() + "-fx-background-color: #2980b9;"));
        searchButton.setOnMouseExited(e -> searchButton.setStyle(searchButton.getStyle() + "-fx-background-color: #3498db;"));

        searchFieldContainer.getChildren().addAll(searchField, searchButton);
        searchCard.getChildren().add(searchFieldContainer);
        mainContent.getChildren().add(searchCard);

        // Show entries section
        HBox showEntriesContainer = new HBox(10);
        showEntriesContainer.setAlignment(Pos.CENTER);
        showEntriesContainer.setStyle("-fx-padding: 0 0 20 0;");

        Label showLabel = new Label("Show");
        showLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");

        itemsPerPageCombo = new ComboBox<>(FXCollections.observableArrayList(10, 25, 50, 100));
        itemsPerPageCombo.setValue(currentItemsShown);
        itemsPerPageCombo.setStyle("""
            -fx-font-size: 14px;
            -fx-background-color: white;
            -fx-border-color: #ddd;
            -fx-border-radius: 5;
            -fx-pref-width: 80px;
        """);

        itemsPerPageCombo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) currentItemsShown = newVal;
        });

        Label entriesLabel = new Label("entries per page");
        entriesLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");

        showEntriesContainer.getChildren().addAll(showLabel, itemsPerPageCombo, entriesLabel);
        mainContent.getChildren().add(showEntriesContainer);

        // Main stock table
        mainContent.getChildren().add(createStockTable());
        return mainContent;
    }

    private VBox createStockTable() {
        VBox tableCard = new VBox();
        tableCard.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 10;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8,0,2,2);
            -fx-padding: 20;
            -fx-min-width: 1200px;
        """);

        // Table header
        GridPane header = new GridPane();
        header.setHgap(15);
        header.setPadding(new Insets(0, 0, 15, 0));
        header.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0;");

        double[] columnWeights = {2.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        String[] headers = {"Medicine Name", "Manufacturer Name", "Sale Price", "Purchase Price",
                "In Qty", "Stock", "Stock Box", "Stock Sale Price", "Stock Purchase Price"};

        for (int i = 0; i < headers.length; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
            col.setPercentWidth(columnWeights[i] * 100 / 11);
            col.setHalignment(HPos.CENTER);
            header.getColumnConstraints().add(col);

            Label headerLabel = new Label(headers[i]);
            headerLabel.setStyle("""
                -fx-font-weight: bold;
                -fx-text-fill: #3E4A61;
                -fx-font-size: 14px;
                -fx-alignment: CENTER;
            """);
            header.add(headerLabel, i, 0);
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background: white; -fx-border-color: white;");

        GridPane tableContent = new GridPane();
        tableContent.setHgap(15);
        tableContent.setVgap(8);
        tableContent.getColumnConstraints().addAll(header.getColumnConstraints());

        String[][] sampleData = {
                {"Almex (400ml)", "Beximco", "$328.85", "$105.55", "60", "50", "08", "$948.55", "$328.85"},
                // ... rest of your data rows
        };

        for (int row = 0; row < sampleData.length; row++) {
            for (int col = 0; col < sampleData[row].length; col++) {
                Label dataLabel = new Label(sampleData[row][col]);
                dataLabel.setStyle("""
                    -fx-text-fill: #555;
                    -fx-font-size: 13px;
                    -fx-alignment: CENTER;
                    -fx-padding: 10 5;
                """);
                if (row % 2 == 0) {
                    dataLabel.setStyle(dataLabel.getStyle() + "-fx-background-color: #f8f9fa;");
                }
                tableContent.add(dataLabel, col, row);
            }
        }

        scrollPane.setContent(tableContent);
        tableCard.getChildren().addAll(header, scrollPane);
        return tableCard;
    }
}