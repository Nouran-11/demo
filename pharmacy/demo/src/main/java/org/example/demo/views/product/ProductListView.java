package org.example.demo.views.product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class ProductListView {
    private TableView<ProductType> tableView;
    private ComboBox<Integer> itemsPerPageCombo;
    private int currentItemsShown = 10;

    public Parent getView() {
        // Main content area
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(30));
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setStyle("-fx-background-color: #f8f9fa;");

        // Title section
        Label titleLabel = new Label("Product Types");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        mainContent.getChildren().add(titleLabel);

        // Search and Add section
        HBox searchAddContainer = new HBox(15);
        searchAddContainer.setAlignment(Pos.CENTER);
        searchAddContainer.setPadding(new Insets(10));

        TextField searchField = new TextField();
        searchField.setPromptText("Search product types...");
        searchField.setStyle("""
            -fx-font-size: 14px;
            -fx-pref-height: 35px;
            -fx-min-width: 300px;
            -fx-background-radius: 5;
            -fx-border-color: #ddd;
            -fx-border-radius: 5;
        """);

        Button searchButton = new Button("🔍 Search");
        searchButton.setStyle("""
            -fx-font-size: 14px;
            -fx-text-fill: white;
            -fx-background-color: #3498db;
            -fx-background-radius: 5;
            -fx-padding: 10 20;
            -fx-cursor: hand;
        """);

        Button addTypeButton = new Button("+ Add Type");
        addTypeButton.setStyle("""
            -fx-font-size: 14px;
            -fx-text-fill: white;
            -fx-background-color: #2ecc71;
            -fx-background-radius: 5;
            -fx-padding: 10 20;
            -fx-cursor: hand;
        """);

        searchAddContainer.getChildren().addAll(searchField, searchButton, addTypeButton);
        mainContent.getChildren().add(searchAddContainer);

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

        // Main products table
        VBox tableCard = createProductsTable();
        mainContent.getChildren().add(tableCard);

        return mainContent;
    }

    private VBox createProductsTable() {
        VBox tableCard = new VBox();
        tableCard.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 10;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8,0,2,2);
            -fx-padding: 20;
            -fx-min-width: 800px;
        """);

        // Table
        tableView = new TableView<>();
        tableView.setItems(getProductTypes());

        // Column setup
        String[] headers = {"Sl", "Type Name", "Created By", "Status", "Action"};
        double[] columnWeights = {0.5, 2.0, 1.5, 1.0, 1.0};

        for (int i = 0; i < headers.length; i++) {
            TableColumn<ProductType, ?> column;

            switch (i) {
                case 0:
                    column = new TableColumn<>(headers[i]);
                    column.setCellValueFactory(new PropertyValueFactory<>("sl"));
                    break;
                case 1:
                    column = new TableColumn<>(headers[i]);
                    column.setCellValueFactory(new PropertyValueFactory<>("typeName"));
                    break;
                case 2:
                    column = new TableColumn<>(headers[i]);
                    column.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
                    break;
                case 3:
                    column = new TableColumn<>(headers[i]);
                    column.setCellValueFactory(new PropertyValueFactory<>("status"));
                    break;
                case 4:
                    TableColumn<ProductType, Void> actionColumn = new TableColumn<>(headers[i]);
                    actionColumn.setCellFactory(param -> new TableCell<ProductType, Void>() {
                        private final Button viewButton = new Button("👁️");
                        private final Button editButton = new Button("✏️");
                        private final HBox pane = new HBox(5, viewButton, editButton);

                        {
                            viewButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                            editButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

                            viewButton.setOnAction(event -> {
                                ProductType pt = getTableView().getItems().get(getIndex());
                                System.out.println("Viewing: " + pt.getTypeName());
                            });

                            editButton.setOnAction(event -> {
                                ProductType pt = getTableView().getItems().get(getIndex());
                                System.out.println("Editing: " + pt.getTypeName());
                            });
                        }

                        @Override
                        protected void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(pane);
                            }
                        }
                    });
                    column = actionColumn;
                    break;
                default:
                    column = new TableColumn<>();
            }

            column.setStyle("-fx-alignment: CENTER;");
            tableView.getColumns().add(column);
        }

        tableCard.getChildren().add(tableView);
        return tableCard;
    }

    private ObservableList<ProductType> getProductTypes() {
        return FXCollections.observableArrayList(
                new ProductType(1, "Medicine", "admin", "Active"),
                new ProductType(2, "Beauty Product", "admin", "Active"),
                new ProductType(3, "Suppositories", "admin", "Active"),
                new ProductType(4, "Drops", "admin", "Active"),
                new ProductType(5, "Inhalers", "admin", "Active"),
                new ProductType(6, "Injections", "admin", "Active"),
                new ProductType(7, "Suppositories", "admin", "Active")
        );
    }

    public static class ProductType {
        private final Integer sl;
        private final String typeName;
        private final String createdBy;
        private final String status;

        public ProductType(Integer sl, String typeName, String createdBy, String status) {
            this.sl = sl;
            this.typeName = typeName;
            this.createdBy = createdBy;
            this.status = status;
        }

        public Integer getSl() { return sl; }
        public String getTypeName() { return typeName; }
        public String getCreatedBy() { return createdBy; }
        public String getStatus() { return status; }
    }
}