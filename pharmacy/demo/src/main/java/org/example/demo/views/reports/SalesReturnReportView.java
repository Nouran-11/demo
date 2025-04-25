package org.example.demo.views;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class SalesReturnReportView {
    private ComboBox<Integer> itemsPerPageCombo;
    private int currentItemsShown = 100; // Default value

    public Parent getView() {
        // Main content area
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(30));
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setStyle("-fx-background-color: #f8f9fa;");

        // Title section
        Label titleLabel = new Label("Sales Return Report");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        mainContent.getChildren().add(titleLabel);

        // Search section - card style
        VBox searchCard = new VBox(15);
        searchCard.setAlignment(Pos.CENTER);
        searchCard.setPadding(new Insets(20));
        searchCard.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 10;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8,0,2,2);
            -fx-min-width: 600px;
        """);

        // Search title
        Label searchLabel = new Label("Search Returns");
        searchLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #3498db;");
        searchCard.getChildren().add(searchLabel);

        // Search form grid
        GridPane searchGrid = new GridPane();
        searchGrid.setAlignment(Pos.CENTER);
        searchGrid.setHgap(15);
        searchGrid.setVgap(10);
        searchGrid.setPadding(new Insets(10));

        // Search fields
        String[] searchLabels = {"Return ID:", "Date Range:"};
        TextField returnIdField = new TextField();
        DatePicker startDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();

        HBox dateRangeBox = new HBox(10, new Label("From:"), startDatePicker,
                new Label("To:"), endDatePicker);
        dateRangeBox.setAlignment(Pos.CENTER_LEFT);

        for (int i = 0; i < searchLabels.length; i++) {
            Label label = new Label(searchLabels[i]);
            label.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");
            searchGrid.add(label, 0, i);

            if (i == 0) {
                returnIdField.setStyle("""
                    -fx-font-size: 14px;
                    -fx-pref-height: 35px;
                    -fx-min-width: 250px;
                    -fx-background-radius: 5;
                    -fx-border-color: #ddd;
                    -fx-border-radius: 5;
                """);
                searchGrid.add(returnIdField, 1, i);
            } else {
                dateRangeBox.setStyle("-fx-alignment: CENTER_LEFT;");
                searchGrid.add(dateRangeBox, 1, i);
            }
        }

        // Search button
        Button searchButton = new Button("Search");
        searchButton.setStyle("""
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-text-fill: white;
            -fx-background-color: #3498db;
            -fx-background-radius: 5;
            -fx-padding: 10 20;
            -fx-cursor: hand;
        """);
        searchButton.setOnMouseEntered(e -> searchButton.setStyle(searchButton.getStyle() + "-fx-background-color: #2980b9;"));
        searchButton.setOnMouseExited(e -> searchButton.setStyle(searchButton.getStyle() + "-fx-background-color: #3498db;"));

        searchGrid.add(searchButton, 1, searchLabels.length);
        searchCard.getChildren().add(searchGrid);
        mainContent.getChildren().add(searchCard);

        // Show-up section with dropdown
        HBox showUpContainer = new HBox(10);
        showUpContainer.setAlignment(Pos.CENTER);

        Label showUpLabel = new Label("Show");
        showUpLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");

        // Create dropdown for items per page
        itemsPerPageCombo = new ComboBox<>(FXCollections.observableArrayList(10, 25, 50, 100));
        itemsPerPageCombo.setValue(currentItemsShown);
        itemsPerPageCombo.setStyle("""
            -fx-font-size: 14px;
            -fx-background-color: white;
            -fx-border-color: #ddd;
            -fx-border-radius: 5;
            -fx-pref-width: 80px;
        """);

        // Add listener for when selection changes
        itemsPerPageCombo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                currentItemsShown = newVal;
                System.out.println("Now showing " + newVal + " items per page");
            }
        });

        Label entriesLabel = new Label("entries per page");
        entriesLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");

        showUpContainer.getChildren().addAll(showUpLabel, itemsPerPageCombo, entriesLabel);
        mainContent.getChildren().add(showUpContainer);

        // Main return products table
        VBox tableCard = createReturnsTable();
        mainContent.getChildren().add(tableCard);

        return mainContent;
    }

    private VBox createReturnsTable() {
        VBox tableCard = new VBox();
        tableCard.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 10;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8,0,2,2);
            -fx-padding: 20;
            -fx-min-width: 800px;
        """);

        // Table header
        GridPane header = new GridPane();
        header.setHgap(15);
        header.setPadding(new Insets(0, 0, 15, 0));
        header.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0;");

        // Column constraints for return products
        double[] columnWeights = {1.5, 2.0, 1.5, 1.5, 1.5, 1.5, 1.0};
        String[] headers = {"Product Code", "Product Name", "Condition", "Return Qty",
                "Total Received", "Available Qty", "Action"};

        for (int i = 0; i < headers.length; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
            col.setPercentWidth(columnWeights[i] * 100 / 10.5); // Total weights = 10.5
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

        // Table content with scroll
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background: white; -fx-border-color: white;");

        GridPane tableContent = new GridPane();
        tableContent.setHgap(15);
        tableContent.setVgap(8);
        tableContent.getColumnConstraints().addAll(header.getColumnConstraints());

        // Sample data for return products
        String[][] sampleData = {
                {"#1-43654554", "Xelstm", "20mg", "Good", "200", "150", "150", "✏️ ✕"},
                {"#1-43654554", "Xelstm", "20mg", "Good", "200", "150", "150", "✏️ ✕"},
                {"#1-43654554", "Xelstm", "20mg", "Good", "200", "150", "150", "✏️ ✕"},
                {"#1-43654555", "Lipitor", "40mg", "Good", "150", "100", "200", "✏️ ✕"},
                {"#1-43654556", "Amoxicillin", "500mg", "Damaged", "50", "25", "75", "✏️ ✕"}
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