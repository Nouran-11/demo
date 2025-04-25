package org.example.demo.views;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class PurchaseReturnView {
    private ComboBox<Integer> itemsPerPageCombo;
    private int currentItemsShown = 100;

    public Parent getView() {
        // Main content area
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(30));
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setStyle("-fx-background-color: #f8f9fa;");

        // Title section
        Label titleLabel = new Label("Purchase Return");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        mainContent.getChildren().add(titleLabel);

        // Add Purchase Return section - card style
        VBox addReturnCard = new VBox(15);
        addReturnCard.setAlignment(Pos.CENTER);
        addReturnCard.setPadding(new Insets(20));
        addReturnCard.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 10;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8,0,2,2);
            -fx-min-width: 600px;
        """);

        // Add Purchase Return title
        Label addReturnLabel = new Label("Add Purchase Return");
        addReturnLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #3498db;");
        addReturnCard.getChildren().add(addReturnLabel);

        // Form grid
        GridPane formGrid = new GridPane();
        formGrid.setAlignment(Pos.CENTER);
        formGrid.setHgap(15);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(10));

        // Form fields
        String[] labels = {"Start Date:", "End Date:", "Supplier Name:", "Reference:", "Amount:"};
        DatePicker startDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();
        TextField supplierField = new TextField();
        TextField referenceField = new TextField();
        TextField amountField = new TextField();

        // Set default dates to today
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());

        Control[] fields = {startDatePicker, endDatePicker, supplierField, referenceField, amountField};

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
        Button addButton = new Button("Add Return");
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

        // Add action handler
        addButton.setOnAction(e -> {
            String startDate = startDatePicker.getValue().format(DateTimeFormatter.ofPattern("dd MMM yyyy")) + " - 10:00";
            String endDate = endDatePicker.getValue().format(DateTimeFormatter.ofPattern("dd MMM yyyy")) + " - 10:00";
            String supplier = supplierField.getText();
            String reference = referenceField.getText();
            String amount = amountField.getText();

            System.out.println("Added Return: " + startDate + " to " + endDate +
                    " | Supplier: " + supplier + " | Ref: " + reference +
                    " | Amount: " + amount);

            // Here you would add the data to your table/model
        });

        formGrid.add(addButton, 1, labels.length);
        addReturnCard.getChildren().add(formGrid);
        mainContent.getChildren().add(addReturnCard);

        // Show-up section with dropdown
        HBox showUpContainer = new HBox(10);
        showUpContainer.setAlignment(Pos.CENTER);

        Label showUpLabel = new Label("Show up to");
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

        Label entriesLabel = new Label("Entries");
        entriesLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");

        showUpContainer.getChildren().addAll(showUpLabel, itemsPerPageCombo, entriesLabel);
        mainContent.getChildren().add(showUpContainer);

        // Main purchase return table
        VBox tableCard = createPurchaseReturnTable();
        mainContent.getChildren().add(tableCard);

        return mainContent;
    }

    private VBox createPurchaseReturnTable() {
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

        double[] columnWeights = {0.5, 1.5, 2.0, 1.5, 3.0, 1.0};
        String[] headers = {"", "Date", "Supplier Name", "Reference", "Comment", "Amount"};

        for (int i = 0; i < headers.length; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
            col.setPercentWidth(columnWeights[i] * 100 / 9.5);
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
                {"$", "19 Feb 2022", "Square Pharma", "6756457", "Research on rare diseases: ten years of progress and challenges at RORC", "$351.02"},
                {"#T-1765457", "21 Feb 2022", "ACI Limited", "4657546", "Research on rare diseases: ten years of progress and challenges at RORC", "$351.02"},
                {"#R-3478664", "22 Feb 2022", "AccoPharma", "37667447", "Research on rare diseases: ten years of progress and challenges at RORC", "$351.02"},
                {"#U-3987544", "25 Feb 2022", "Square Pharma", "63478664", "Research on rare diseases: ten years of progress and challenges at RORC", "$351.02"},
                {"#T-8794755", "24 Feb 2022", "ACC Pharma", "35467856", "Research on rare diseases: ten years of progress and challenges at RORC", "$351.02"},
                {"#T-1234567", "25 Feb 2022", "AccoPharma", "89356994", "Research on rare diseases: ten years of progress and challenges at RORC", "$351.02"}
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