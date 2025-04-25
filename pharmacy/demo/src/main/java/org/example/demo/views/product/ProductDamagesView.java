package org.example.demo.views.product;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ProductDamagesView {
    private ComboBox<Integer> itemsPerPageCombo;
    private int currentItemsShown = 10; // Default value

    public Parent getView() {
        // Main content area
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(30));
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setStyle("-fx-background-color: #f8f9fa;");

        // Title section
        Label titleLabel = new Label("Damaged Product Information");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        mainContent.getChildren().add(titleLabel);

        // Add Damaged section
        VBox addDamagedCard = createAddDamagedCard();
        mainContent.getChildren().add(addDamagedCard);

        // Show-up section with dropdown
        HBox showUpContainer = new HBox(10);
        showUpContainer.setAlignment(Pos.CENTER);

        Label showUpLabel = new Label("Show");
        showUpLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");

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
            if (newVal != null) {
                currentItemsShown = newVal;
                System.out.println("Now showing " + newVal + " items per page");
            }
        });

        Label entriesLabel = new Label("entries per page");
        entriesLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");

        showUpContainer.getChildren().addAll(showUpLabel, itemsPerPageCombo, entriesLabel);
        mainContent.getChildren().add(showUpContainer);

        // Main damaged products table
        VBox tableCard = createDamagedProductsTable();
        mainContent.getChildren().add(tableCard);

        return mainContent;
    }

    private VBox createAddDamagedCard() {
        VBox addDamagedCard = new VBox(15);
        addDamagedCard.setAlignment(Pos.CENTER);
        addDamagedCard.setPadding(new Insets(20));
        addDamagedCard.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 10;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8,0,2,2);
            -fx-min-width: 600px;
        """);

        Label addDamagedLabel = new Label("Add Damaged Product");
        addDamagedLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #3498db;");
        addDamagedCard.getChildren().add(addDamagedLabel);

        GridPane formGrid = new GridPane();
        formGrid.setAlignment(Pos.CENTER);
        formGrid.setHgap(15);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(10));

        String[] labels = {"Product Name:", "Product Code:", "Damage Date:", "Damage Reason:"};
        TextField[] fields = {new TextField(), new TextField(), new TextField(), new TextField()};

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

        Button addButton = new Button("Add Damaged");
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

        formGrid.add(addButton, 1, labels.length);
        addDamagedCard.getChildren().add(formGrid);

        return addDamagedCard;
    }

    private VBox createDamagedProductsTable() {
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

        // Column constraints - matching the Q#, Date, Reference, Amount, Action structure
        double[] columnWeights = {1.0, 1.5, 2.0, 1.5, 1.0};
        String[] headers = {"Q#", "Date", "Reference", "Amount", "Action"};

        for (int i = 0; i < headers.length; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
            col.setPercentWidth(columnWeights[i] * 100 / 7.0);
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

        // Sample data with "✏️ ✕" actions like PurchaseListView
        String[][] sampleData = {
                {"Q2", "28 Feb 2022", "234234.32", "$120.00", "✏️ ✕"},
                {"Q3", "28 Feb 2022", "234324.2", "$328.85", "✏️ ✕"},
                {"Q4", "28 Feb 2022", "253243.4", "$328.85", "✏️ ✕"},
                {"Q5", "28 Feb 2022", "325435.45", "$106.58", "✏️ ✕"},
                {"Q6", "28 Feb 2022", "345435.45", "$106.58", "✏️ ✕"},
                {"Q7", "28 Feb 2022", "343454.35", "$219.78", "✏️ ✕"},
                {"Q8", "28 Feb 2022", "354535.4", "$475.22", "✏️ ✕"},
                {"Q9", "28 Feb 2022", "SP354.35.3L", "$275.43", "✏️ ✕"},
                {"", "28 Feb 2022", "545545.54", "$446.61", "✏️ ✕"}
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