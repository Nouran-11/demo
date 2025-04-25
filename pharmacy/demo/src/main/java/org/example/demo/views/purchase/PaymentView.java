package org.example.demo.views.purchase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PaymentView extends BorderPane {
    private TableView<Payment> paymentTable;
    private ObservableList<Payment> payments;
    private int paymentCounter = 9; // Starting counter for new payments

    public PaymentView() {
        initializeUI();
        loadSampleData();
    }

    public Parent getView() {
        return this;
    }

    private void initializeUI() {
        // Title
        Label title = new Label("Supplier Payment List");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setPadding(new Insets(10));

        // Filter Section
        HBox filterBox = createFilterBox();

        // Entries Selector
        HBox entriesBox = createEntriesSelector();

        // TableView
        paymentTable = new TableView<>();
        createTableColumns();

        // "Add New" Button
        Button addNewButton = new Button("+ Add New");
        addNewButton.setStyle("-fx-background-color: #5c6bc0; -fx-text-fill: white;");
        addNewButton.setOnAction(e -> showPaymentDialog(null));
        HBox bottomBox = new HBox(addNewButton);
        bottomBox.setPadding(new Insets(10));

        // Layout
        VBox topBox = new VBox(title, filterBox, entriesBox);
        setTop(topBox);
        setCenter(paymentTable);
        setBottom(bottomBox);
    }

    private HBox createFilterBox() {
        HBox filterBox = new HBox(10);
        filterBox.setPadding(new Insets(10));

        ComboBox<String> supplierCombo = new ComboBox<>();
        supplierCombo.getItems().addAll("All Suppliers", "Square Pharma", "ACI Limited");
        supplierCombo.setPromptText("Enter supplier");

        TextField invoiceField = new TextField();
        invoiceField.setPromptText("Invoice ID");

        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setPromptText("Start Date");
        DatePicker endDatePicker = new DatePicker();
        endDatePicker.setPromptText("End Date");

        Button filterButton = new Button("🔍");

        filterBox.getChildren().addAll(
                supplierCombo,
                invoiceField,
                startDatePicker,
                endDatePicker,
                filterButton
        );

        return filterBox;
    }

    private HBox createEntriesSelector() {
        HBox entriesBox = new HBox(10);
        entriesBox.setPadding(new Insets(0, 10, 10, 10));

        ComboBox<Integer> entriesCombo = new ComboBox<>();
        entriesCombo.getItems().addAll(10, 25, 50, 100);
        entriesCombo.setValue(100);

        entriesBox.getChildren().addAll(
                new Label("Show up to"), entriesCombo, new Label("Entries")
        );

        return entriesBox;
    }

    private void createTableColumns() {
        TableColumn<Payment, String> slCol = new TableColumn<>("Sl");
        slCol.setCellValueFactory(new PropertyValueFactory<>("sl"));

        TableColumn<Payment, String> invoiceCol = new TableColumn<>("Invoice ID");
        invoiceCol.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));

        TableColumn<Payment, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Payment, String> supplierCol = new TableColumn<>("Supplier Name");
        supplierCol.setCellValueFactory(new PropertyValueFactory<>("supplier"));

        TableColumn<Payment, String> accountCol = new TableColumn<>("Account");
        accountCol.setCellValueFactory(new PropertyValueFactory<>("account"));

        TableColumn<Payment, String> methodCol = new TableColumn<>("Payment Method");
        methodCol.setCellValueFactory(new PropertyValueFactory<>("method"));

        TableColumn<Payment, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Payment, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Payment, Void> approvalCol = new TableColumn<>("Approval");
        approvalCol.setCellFactory(param -> new TableCell<>() {
            private final Button approveButton = new Button("Approve");

            {
                approveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                approveButton.setOnAction(event -> {
                    Payment payment = getTableView().getItems().get(getIndex());
                    // Here you could change the status in the model
                    approveButton.setDisable(true);
                    approveButton.setText("Approved");
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(approveButton);
                }
            }
        });

        TableColumn<Payment, Void> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button viewBtn = new Button("👁");
            private final Button editBtn = new Button("✏");
            private final Button printBtn = new Button("🖨");

            {
                viewBtn.setOnAction(event -> {
                    Payment payment = getTableView().getItems().get(getIndex());
                    showPaymentDialog(payment);
                });

                editBtn.setOnAction(event -> {
                    Payment payment = getTableView().getItems().get(getIndex());
                    showPaymentDialog(payment);
                });

                printBtn.setOnAction(event -> {
                    Payment payment = getTableView().getItems().get(getIndex());
                    // Print logic
                });

                viewBtn.setStyle("-fx-background-color: transparent;");
                editBtn.setStyle("-fx-background-color: transparent;");
                printBtn.setStyle("-fx-background-color: transparent;");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox(5, viewBtn, editBtn, printBtn);
                    setGraphic(hBox);
                }
            }
        });

        paymentTable.getColumns().addAll(
                slCol, invoiceCol, dateCol, supplierCol, accountCol,
                methodCol, typeCol, amountCol, approvalCol, actionCol
        );
    }

    private void loadSampleData() {
        payments = FXCollections.observableArrayList(
                new Payment("1", "#B-1234567", "26 Feb 2022", "Square Pharma", "Cash", "Cash Hand", "Payment", 120.00),
                new Payment("2", "#T-454655", "27 Feb 2022", "ACI Limited", "Cash", "Cash Hand", "Payment", 120.00),
                new Payment("3", "#B-335454", "28 Feb 2022", "A Laboratories", "Cash", "Cash Hand", "Payment", 120.00),
                new Payment("4", "#R-323435", "01 Mar 2022", "Alco Pharma", "Cash", "Cash Hand", "Payment", 120.00),
                new Payment("5", "#E-576576", "02 Mar 2022", "Square Pharma", "Cash", "Cash Hand", "Payment", 120.00),
                new Payment("6", "#A-546565", "03 Mar 2022", "Jone Coper", "Cash", "Cash Hand", "Payment", 120.00),
                new Payment("7", "#V-456555", "04 Mar 2022", "APC Pharma", "Cash", "Cash Hand", "Payment", 120.00),
                new Payment("8", "#B-1234567", "05 Mar 2022", "AI Laboratories", "Cash", "Cash Hand", "Payment", 120.00)
        );

        paymentTable.setItems(payments);
    }

    private void showPaymentDialog(Payment payment) {
        PaymentDialog dialog = new PaymentDialog(payment);
        dialog.showAndWait().ifPresent(result -> {
            if (payment == null) {
                // Add new payment
                result.sl = String.valueOf(paymentCounter++);
                payments.add(result);
            } else {
                // Update existing payment
                int index = payments.indexOf(payment);
                if (index >= 0) {
                    payments.set(index, result);
                }
            }
        });
    }

    // Payment Model - changed to non-static and mutable for editing
    public static class Payment {
        private String sl;
        private final String invoiceId;
        private final String date;
        private final String supplier;
        private final String account;
        private final String method;
        private final String type;
        private final double amount;

        public Payment(String sl, String invoiceId, String date, String supplier,
                       String account, String method, String type, double amount) {
            this.sl = sl;
            this.invoiceId = invoiceId;
            this.date = date;
            this.supplier = supplier;
            this.account = account;
            this.method = method;
            this.type = type;
            this.amount = amount;
        }

        public String getSl() { return sl; }
        public String getInvoiceId() { return invoiceId; }
        public String getDate() { return date; }
        public String getSupplier() { return supplier; }
        public String getAccount() { return account; }
        public String getMethod() { return method; }
        public String getType() { return type; }
        public double getAmount() { return amount; }
    }
}