package org.example.demo.views;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class SalesReportView {
    private ComboBox<Integer> itemsPerPageCombo;
    private int currentItemsShown = 100; // Default value

    public Parent getView() {
        // Main container
        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(30));
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.setStyle("-fx-background-color: #f8f9fa;");

        // Title section
        Label titleLabel = new Label("Sales Report");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        mainContainer.getChildren().add(titleLabel);

        // Generate Report section - card style
        VBox generateReportCard = new VBox(15);
        generateReportCard.setAlignment(Pos.CENTER);
        generateReportCard.setPadding(new Insets(20));
        generateReportCard.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 10;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8,0,2,2);
            -fx-min-width: 600px;
        """);

        // Generate Report title
        Label generateReportLabel = new Label("Generate Report");
        generateReportLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #3498db;");
        generateReportCard.getChildren().add(generateReportLabel);

        // Form grid
        GridPane formGrid = new GridPane();
        formGrid.setAlignment(Pos.CENTER);
        formGrid.setHgap(15);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(10));

        // Form fields
        String[] labels = {"Start Date:", "End Date:"};
        DatePicker[] datePickers = {new DatePicker(), new DatePicker()};

        for (int i = 0; i < labels.length; i++) {
            Label label = new Label(labels[i]);
            label.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");
            formGrid.add(label, 0, i);

            datePickers[i].setStyle("""
                -fx-font-size: 14px;
                -fx-pref-height: 35px;
                -fx-min-width: 250px;
                -fx-background-radius: 5;
                -fx-border-color: #ddd;
                -fx-border-radius: 5;
            """);
            formGrid.add(datePickers[i], 1, i);
        }

        // Generate button with nice styling
        Button generateButton = new Button("Generate Report");
        generateButton.setStyle("""
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-text-fill: white;
            -fx-background-color: #3498db;
            -fx-background-radius: 5;
            -fx-padding: 10 20;
            -fx-cursor: hand;
        """);
        generateButton.setOnMouseEntered(e -> generateButton.setStyle(generateButton.getStyle() + "-fx-background-color: #2980b9;"));
        generateButton.setOnMouseExited(e -> generateButton.setStyle(generateButton.getStyle() + "-fx-background-color: #3498db;"));

        // Add elements to form card
        formGrid.add(generateButton, 1, labels.length);
        generateReportCard.getChildren().add(formGrid);
        mainContainer.getChildren().add(generateReportCard);

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
        mainContainer.getChildren().add(showUpContainer);

        // Table section
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

        // Column constraints
        double[] columnWeights = {0.5, 1.5, 2.0, 1.5, 1.5, 1.5};
        String[] headers = {"S", "Invoice No", "Customer Name", "Date", "Total Amount", "Sold By"};

        for (int i = 0; i < headers.length; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
            col.setPercentWidth(columnWeights[i] * 100 / 8.5);
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

        // Sample data
        String[][] sampleData = {
                {"Q1", "#T-3243 545", "Jane Cooper", "21 Feb 2022", "$667.00", "Sales man"},
                {"Q2", "#R-4255 464", "Wade Warren", "22 Feb 2022", "$667.00", "Sales man"},
                {"Q3", "#678465 454", "Jenny Wilson", "23 Feb 2022", "$789.00", "Sales man"},
                {"Q4", "#634655 476", "Oay Hawkins", "24 Feb 2022", "$554.00", "Sales man"},
                {"Q5", "#657465 446", "Robert Fox", "25 Feb 2022", "$867.00", "Sales man"},
                {"Q6", "#365453 577", "Jacob Jones", "26 Feb 2022", "$980.00", "Sales man"},
                {"Q7", "#435748 937", "Cody Fisher", "27 Feb 2022", "$878.00", "Sales man"},
                {"Q8", "#463284 633", "Albert Flores", "28 Feb 2022", "$846.00", "Sales man"},
                {"Q9", "#753475", "Flood Miles", "29 Feb 2022", "$909.00", "Sales"}
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
        mainContainer.getChildren().add(tableCard);

        return mainContainer;
    }
}