package org.example.demo.views.product;

import javafx.collections.FXCollections;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
//import org.example.demo.controls.ModernButton;
//import org.example.demo.controls.ModernTextField;
import org.example.demo.AppColors;
import org.example.demo.AppEffects;
import org.example.demo.AppFonts;

public class ProductDamagesView {
    private ComboBox<Integer> itemsPerPageCombo;
    private Pagination pagination;
    private final int currentItemsShown = 10;

    public Parent getView() {
        // Main container with subtle gradient background
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(30));
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setStyle("-fx-background-color: linear-gradient(to bottom, #f8f9fa, #e9ecef);");

        // Title with icon and modern typography
        HBox titleBox = new HBox(10);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(
                "/org/example/demo/icons/products/damage-report.png")));
        icon.setFitWidth(32);
        icon.setFitHeight(32);

        Label titleLabel = new Label("Damaged Product Management");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: 700; -fx-text-fill: " + AppColors.PRIMARY + ";");
        titleLabel.setFont(AppFonts.titleFont());

        titleBox.getChildren().addAll(icon, titleLabel);
        VBox.setMargin(titleBox, new Insets(0, 0, 20, 0));

        // Add Damaged Product Card
        VBox addDamagedCard = createAddDamagedCard();

        // Results header with pagination controls
        HBox resultsHeader = createResultsHeader();

        // Modern table with hover effects
        VBox tableCard = createDamagedProductsTable();

        // Pagination control
        pagination = createPagination();

        mainContent.getChildren().addAll(
                titleBox,
                addDamagedCard,
                resultsHeader,
                tableCard,
                pagination
        );

        return mainContent;
    }

    private VBox createAddDamagedCard() {
        VBox card = new VBox(15);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12;");
        card.setEffect(AppEffects.cardShadow());

        Label header = new Label("Report Damaged Product");
        header.setStyle("-fx-font-size: 18px; -fx-font-weight: 600; -fx-text-fill: " + AppColors.PRIMARY + ";");
        header.setFont(AppFonts.subtitleFont());

        GridPane form = new GridPane();
        form.setAlignment(Pos.CENTER);
        form.setHgap(20);
        form.setVgap(15);
        form.setPadding(new Insets(10));

        // Form fields with proper types
        Label[] labels = {
                new Label("Product Name:"),
                new Label("Batch Number:"),
                new Label("Damage Date:"),
                new Label("Quantity:"),
                new Label("Reason:")
        };

        Control[] fields = {
                new ModernTextField(),
                new ModernTextField(),
                new DatePicker(),
                new Spinner<>(1, 1000, 1),
                new TextArea()
        };

        // Configure form layout
        for (int i = 0; i < labels.length; i++) {
            labels[i].setStyle("-fx-font-size: 14px; -fx-text-fill: " + AppColors.SECONDARY + ";");
            form.add(labels[i], 0, i);

            if (fields[i] instanceof TextArea) {
                ((TextArea) fields[i]).setPrefRowCount(3);
                form.add(fields[i], 1, i);
                GridPane.setColumnSpan(fields[i], 2);
            } else {
                form.add(fields[i], 1, i);
            }
        }

        // Action buttons with icons
        HBox buttons = new HBox(15);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        ImageView cancelIcon = new ImageView(new Image(getClass().getResourceAsStream(
                "/org/example/demo/icons/actions/cancel.png")));
        cancelIcon.setFitWidth(16);
        cancelIcon.setFitHeight(16);

        ImageView submitIcon = new ImageView(new Image(getClass().getResourceAsStream(
                "/org/example/demo/icons/actions/submit.png")));
        submitIcon.setFitWidth(16);
        submitIcon.setFitHeight(16);

        Button cancelBtn = new ModernButton("Cancel", "secondary", cancelIcon);
        Button submitBtn = new ModernButton("Submit Report", "primary", submitIcon);

        buttons.getChildren().addAll(cancelBtn, submitBtn);
        form.add(buttons, 1, labels.length);
        GridPane.setColumnSpan(buttons, 2);

        card.getChildren().addAll(header, form);
        return card;
    }

    private HBox createResultsHeader() {
        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(10, 20, 10, 20));

        // Results label with icon
        HBox resultsBox = new HBox(10);
        resultsBox.setAlignment(Pos.CENTER_LEFT);

        ImageView resultsIcon = new ImageView(new Image(getClass().getResourceAsStream(
                "/org/example/demo/icons/products/damage-list.png")));
        resultsIcon.setFitWidth(20);
        resultsIcon.setFitHeight(20);

        Label resultsLabel = new Label("Damaged Products");
        resultsLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 600; -fx-text-fill: " + AppColors.DARK + ";");

        resultsBox.getChildren().addAll(resultsIcon, resultsLabel);

        // Search field with icon
        HBox searchBox = new HBox();
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setStyle("-fx-background-color: white; -fx-background-radius: 5;");

        ImageView searchIcon = new ImageView(new Image(getClass().getResourceAsStream(
                "/org/example/demo/icons/actions/search.png")));
        searchIcon.setFitWidth(16);
        searchIcon.setFitHeight(16);

        TextField searchField = new ModernTextField();
        searchField.setPromptText("Search damaged products...");
        searchField.setPrefWidth(250);

        searchBox.getChildren().addAll(searchIcon, searchField);

        // Items per page dropdown
        HBox pageControls = new HBox(10);
        pageControls.setAlignment(Pos.CENTER_RIGHT);

        Label showLabel = new Label("Show:");
        showLabel.setStyle("-fx-text-fill: " + AppColors.SECONDARY + ";");

        itemsPerPageCombo = new ComboBox<>(FXCollections.observableArrayList(10, 25, 50, 100));
        itemsPerPageCombo.setValue(currentItemsShown);
        itemsPerPageCombo.setStyle("-fx-background-color: white; -fx-border-color: " + AppColors.LIGHT_GRAY + ";");
        itemsPerPageCombo.setPrefWidth(80);

        header.getChildren().addAll(resultsBox, searchBox, new Region(), pageControls);
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        pageControls.getChildren().addAll(showLabel, itemsPerPageCombo);

        return header;
    }

    private VBox createDamagedProductsTable() {
        VBox tableCard = new VBox();
        tableCard.setStyle("-fx-background-color: white; -fx-background-radius: 12;");
        tableCard.setEffect(AppEffects.cardShadow());
        tableCard.setPadding(new Insets(20));

        // Table header
        GridPane header = new GridPane();
        header.setHgap(15);
        header.setPadding(new Insets(0, 0, 15, 0));
        header.setStyle("-fx-border-color: " + AppColors.LIGHT_GRAY + "; -fx-border-width: 0 0 1 0;");

        String[] headers = {"ID", "Product", "Batch", "Date", "Qty", "Reason", "Actions"};
        double[] columnWeights = {0.5, 1.5, 1.0, 1.0, 0.5, 2.0, 0.8};

        for (int i = 0; i < headers.length; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.SOMETIMES);
            col.setPrefWidth(columnWeights[i] * 100);
            header.getColumnConstraints().add(col);

            Label headerLabel = new Label(headers[i]);
            headerLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: " + AppColors.DARK + "; -fx-font-size: 14px;");
            header.add(headerLabel, i, 0);
        }

        // Table content
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background: transparent; -fx-border-color: transparent;");

        GridPane tableContent = new GridPane();
        tableContent.setHgap(15);
        tableContent.setVgap(0);
        tableContent.getColumnConstraints().addAll(header.getColumnConstraints());

        // Sample data with real product examples
        String[][] sampleData = {
                {"1001", "Paracetamol 500mg", "BATCH-2023-01", "2023-05-15", "5", "Broken during transport", ""},
                {"1002", "Ibuprofen 200mg", "BATCH-2023-02", "2023-05-16", "2", "Expired", ""},
                {"1003", "Amoxicillin 250mg", "BATCH-2023-03", "2023-05-17", "10", "Damaged packaging", ""}
        };

        for (int row = 0; row < sampleData.length; row++) {
            for (int col = 0; col < sampleData[row].length - 1; col++) {
                Node cellContent = createTableCell(sampleData[row][col], false);
                tableContent.add(cellContent, col, row);
            }

            // Add action buttons with icons
            Node actions = createActionButtons();
            tableContent.add(actions, headers.length - 1, row);

            // Add hover effect row
            Region hoverRow = new Region();
            hoverRow.setStyle("-fx-background-color: transparent;");
            hoverRow.setPrefHeight(40);
            tableContent.add(hoverRow, 0, row);
            GridPane.setColumnSpan(hoverRow, headers.length);

            // Hover effect
            hoverRow.setOnMouseEntered(e -> hoverRow.setStyle("-fx-background-color: " + AppColors.HOVER + ";"));
            hoverRow.setOnMouseExited(e -> hoverRow.setStyle("-fx-background-color: transparent;"));
        }

        scrollPane.setContent(tableContent);
        tableCard.getChildren().addAll(header, scrollPane);
        return tableCard;
    }

    private Node createActionButtons() {
        HBox actionBox = new HBox(10);
        actionBox.setAlignment(Pos.CENTER);

        ImageView editIcon = new ImageView(new Image(getClass().getResourceAsStream(
                "/org/example/demo/icons/actions/edit.png")));
        editIcon.setFitWidth(16);
        editIcon.setFitHeight(16);

        ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream(
                "/org/example/demo/icons/actions/delete.png")));
        deleteIcon.setFitWidth(16);
        deleteIcon.setFitHeight(16);

        Button editBtn = new Button();
        editBtn.setGraphic(editIcon);
        editBtn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

        Button deleteBtn = new Button();
        deleteBtn.setGraphic(deleteIcon);
        deleteBtn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

        actionBox.getChildren().addAll(editBtn, deleteBtn);
        return actionBox;
    }

    private Node createTableCell(String content, boolean isActionCell) {
        Label label = new Label(content);
        label.setStyle("-fx-text-fill: " + AppColors.DARK + "; -fx-font-size: 13px; -fx-padding: 10 5;");
        label.setMaxWidth(Double.MAX_VALUE);
        return label;
    }

    private Pagination createPagination() {
        Pagination pagination = new Pagination(5, 0);
        pagination.setMaxPageIndicatorCount(5);
        pagination.setStyle("-fx-page-information-visible: false;");
        pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
        return pagination;
    }
}