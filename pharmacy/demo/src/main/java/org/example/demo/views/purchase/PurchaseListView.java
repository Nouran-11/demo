package org.example.demo.views;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PurchaseListView {
    public Parent getView() {
        // Main content area - centered with nice padding
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(30));
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setStyle("-fx-background-color: #f8f9fa;");

        // Title section with improved styling
        Label titleLabel = new Label("Product Management");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        mainContent.getChildren().add(titleLabel);

        // Add Product section - card style
        VBox addProductCard = new VBox(15);
        addProductCard.setAlignment(Pos.CENTER);
        addProductCard.setPadding(new Insets(20));
        addProductCard.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 10;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8,0,2,2);
            -fx-min-width: 600px;
        """);

        // Add Product title
        Label addProductLabel = new Label("Add New Product");
        addProductLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #3498db;");
        addProductCard.getChildren().add(addProductLabel);

        // Form grid
        GridPane formGrid = new GridPane();
        formGrid.setAlignment(Pos.CENTER);
        formGrid.setHgap(15);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(10));

        // Form fields
        String[] labels = {"Product Name:", "Product Code:", "Date & Time:"};
        TextField[] fields = {
                new TextField(),
                new TextField(),
                new TextField()  // Could be replaced with DatePicker for better UX
        };

        // Add form elements
        for (int i = 0; i < labels.length; i++) {
            Label label = new Label(labels[i]);
            label.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");
            formGrid.add(label, 0, i);

            fields[i].setStyle("""
                -fx-font-size: 14px;
                -fx-pref-height: 35px;
                -fx-min-width: 250px;
                -fx-background-radius: 5;
                -fx-border-color: #ddd;
                -fx-border-radius: 5;
            """);
            formGrid.add(fields[i], 1, i);
        }

        // Add button with nice styling
        Button addButton = new Button("Add Product");
        addButton.setStyle("""
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-text-fill: white;
            -fx-background-color: #3498db;
            -fx-background-radius: 5;
            -fx-padding: 10 20;
            -fx-cursor: hand;
        """);
        addButton.setOnMouseEntered(e -> addButton.setStyle(addButton.getStyle() + "-fx-background-color: #2980b9;"));
        addButton.setOnMouseExited(e -> addButton.setStyle(addButton.getStyle() + "-fx-background-color: #3498db;"));

        // Add elements to form card
        formGrid.add(addButton, 1, labels.length);
        addProductCard.getChildren().add(formGrid);
        mainContent.getChildren().add(addProductCard);

        // Show-up section with improved styling
        HBox showUpContainer = new HBox(10);
        showUpContainer.setAlignment(Pos.CENTER);

        Label showUpLabel = new Label("Showing");
        showUpLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");

        Label showUpValue = new Label("100");
        showUpValue.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label entriesLabel = new Label("entries");
        entriesLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");

        showUpContainer.getChildren().addAll(showUpLabel, showUpValue, entriesLabel);
        mainContent.getChildren().add(showUpContainer);

        // Main product table with enhanced styling
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
        double[] columnWeights = {2.0, 1.5, 1.5, 1.5, 1.5, 1.0, 0.8};
        String[] headers = {"Name", "Brand", "Product Code", "Expire Date", "Type", "Price", "Actions"};

        for (int i = 0; i < headers.length; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
            col.setPercentWidth(columnWeights[i] * 100 / 10.0);
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

        // Sample data
        String[][] sampleData = {
                {"General Supplier", "Napa Extra", "Bev-63264-587", "25 Feb 2022", "Medicine", "$120.00", "✏️ ✕"},
                {"Pharma Inc.", "Lipitor", "Lip-45821-321", "15 Mar 2023", "Medicine", "$95.50", "✏️ ✕"},
                {"Health Plus", "Vitamin D", "Vit-78542-123", "30 Nov 2024", "Supplement", "$24.99", "✏️ ✕"},
                {"MediCorp", "Amoxicillin", "Amx-96325-741", "10 Jan 2023", "Antibiotic", "$65.75", "✏️ ✕"},
                {"General Supplier", "Napa Extra", "Bev-63264-587", "25 Feb 2022", "Medicine", "$120.00", "✏️ ✕"},
                {"Pharma Inc.", "Lipitor", "Lip-45821-321", "15 Mar 2023", "Medicine", "$95.50", "✏️ ✕"},
                {"Health Plus", "Vitamin D", "Vit-78542-123", "30 Nov 2024", "Supplement", "$24.99", "✏️ ✕"}
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
        mainContent.getChildren().add(tableCard);

        return mainContent;
    }
}