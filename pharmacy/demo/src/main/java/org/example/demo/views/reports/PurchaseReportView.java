package org.example.demo.views;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class PurchaseReportView {
    private ComboBox<Integer> itemsPerPageCombo;
    private int currentItemsShown = 100; // Default value

    public Parent getView() {
        // Main content area
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(30));
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setStyle("-fx-background-color: #f8f9fa;");

        // Title section
        Label titleLabel = new Label("Purchase Report");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        mainContent.getChildren().add(titleLabel);

        // Date range section - card style
        VBox dateCard = new VBox(15);
        dateCard.setAlignment(Pos.CENTER);
        dateCard.setPadding(new Insets(20));
        dateCard.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 10;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8,0,2,2);
            -fx-min-width: 600px;
        """);

        // Date range title
        Label dateLabel = new Label("Date Range");
        dateLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #3498db;");
        dateCard.getChildren().add(dateLabel);

        // Date range form
        HBox dateRangeBox = new HBox(10);
        dateRangeBox.setAlignment(Pos.CENTER);

        DatePicker startDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();

        dateRangeBox.getChildren().addAll(
                new Label("Start Date:"), startDatePicker,
                new Label("End Date:"), endDatePicker
        );

        dateCard.getChildren().add(dateRangeBox);
        mainContent.getChildren().add(dateCard);

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

        Label entriesLabel = new Label("entries");
        entriesLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");

        // Add checkbox for "Show up to 100"
        CheckBox showAllCheckbox = new CheckBox("Show up to 100");
        showAllCheckbox.setSelected(true);
        showAllCheckbox.setStyle("-fx-font-size: 14px;");

        showUpContainer.getChildren().addAll(showUpLabel, itemsPerPageCombo, entriesLabel, showAllCheckbox);
        mainContent.getChildren().add(showUpContainer);

        // Main purchase table
        VBox tableCard = createPurchaseTable();
        mainContent.getChildren().add(tableCard);

        return mainContent;
    }

    private VBox createPurchaseTable() {
        VBox tableCard = new VBox();
        tableCard.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 10;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8,0,2,2);
            -fx-padding: 20;
            -fx-min-width: 1000px;
        """);

        // Table header
        GridPane header = new GridPane();
        header.setHgap(15);
        header.setPadding(new Insets(0, 0, 15, 0));
        header.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0;");

        // Column constraints for purchase table
        double[] columnWeights = {0.5, 2.0, 2.0, 2.0, 1.5, 1.5, 1.5};
        String[] headers = {"", "Product ID", "Purchase ID", "Manufacturer Name", "Date", "Total Amount", "Purchase By"};

        for (int i = 0; i < headers.length; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
            col.setPercentWidth(columnWeights[i] * 100 / 11); // Total weights = 11
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

        // Sample data from the image
        String[][] sampleData = {
                {"", "236167528575 367", "TPG2557 634", "Square Pragma", "21 Feb 2022", "$545.00", "Admin"},
                {"", "236167528575 367", "TPG2557 634", "ACI Limited", "21 Feb 2022", "$545.00", "Admin"},
                {"", "236167528575 367", "TPG2557 634", "ADNE Laboratories", "21 Feb 2022", "$545.00", "Admin"},
                {"", "236167528575 367", "TPG2557 634", "Aico-Pragma Ltd", "21 Feb 2022", "$545.00", "Admin"},
                {"", "236167528575 367", "TPG2557 634", "Alien-Pragma", "21 Feb 2022", "$545.00", "Admin"},
                {"", "236167528575 367", "TPG2557 634", "AMed Laboratories", "21 Feb 2022", "$545.00", "Admin"},
                {"", "236167528575 367", "TPG2557 634", "Square Pragma", "21 Feb 2022", "$545.00", "Admin"},
                {"", "236167528575 367", "TPG2557 634", "APC Pragma Ltd.", "21 Feb 2022", "$545.00", "Admin"}
        };

        for (int row = 0; row < sampleData.length; row++) {
            for (int col = 0; col < sampleData[row].length; col++) {
                Node content;
                if (col == 0) {
                    // Checkbox for first column
                    CheckBox checkBox = new CheckBox();
                    checkBox.setAlignment(Pos.CENTER);
                    content = checkBox;
                } else {
                    Label dataLabel = new Label(sampleData[row][col]);
                    dataLabel.setStyle("""
                        -fx-text-fill: #555;
                        -fx-font-size: 13px;
                        -fx-alignment: CENTER;
                        -fx-padding: 10 5;
                    """);
                    content = dataLabel;
                }

                if (row % 2 == 0) {
                    content.setStyle(content.getStyle() + "-fx-background-color: #f8f9fa;");
                }

                tableContent.add(content, col, row);
            }
        }

        scrollPane.setContent(tableContent);
        tableCard.getChildren().addAll(header, scrollPane);

        return tableCard;
    }
}