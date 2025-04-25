package org.example.demo.views.purchase;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PaymentDialog extends Dialog<PaymentView.Payment> {
    private ComboBox<String> supplierCombo;
    private TextField amountField;
    private ComboBox<String> accountCombo;
    private ComboBox<String> methodCombo;

    public PaymentDialog(PaymentView.Payment payment) {
        setTitle(payment == null ? "Add New Payment" : "Edit Payment");

        // Create buttons
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create form
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Supplier section
        Label supplierTitle = new Label("Supplier Name");
        supplierTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        supplierCombo = new ComboBox<>();
        supplierCombo.getItems().addAll("Square Pharma", "ACI Limited", "Alco Pharma", "Jome Cooper");

        // Payment details
        amountField = new TextField();
        amountField.setPromptText("Enter amount");

        accountCombo = new ComboBox<>();
        accountCombo.getItems().addAll("Cash", "Bank", "Credit");

        methodCombo = new ComboBox<>();
        methodCombo.getItems().addAll("Cash Hand", "Bank Transfer", "Cheque");

        // Layout form
        grid.add(supplierTitle, 0, 0, 2, 1);
        grid.add(new Label("Supplier:"), 0, 1);
        grid.add(supplierCombo, 1, 1);
        grid.add(new Label("Amount:"), 0, 2);
        grid.add(amountField, 1, 2);
        grid.add(new Label("Account:"), 0, 3);
        grid.add(accountCombo, 1, 3);
        grid.add(new Label("Method:"), 0, 4);
        grid.add(methodCombo, 1, 4);

        // Set existing values if editing
        if (payment != null) {
            supplierCombo.setValue(payment.getSupplier());
            amountField.setText(String.valueOf(payment.getAmount()));
            accountCombo.setValue(payment.getAccount());
            methodCombo.setValue(payment.getMethod());
        }

        getDialogPane().setContent(grid);

        // Convert result to Payment object when Save is clicked
        setResultConverter(buttonType -> {
            if (buttonType == saveButtonType) {
                return new PaymentView.Payment(
                        "Q1",
                        "#B-" + (int)(Math.random() * 1000000), // Random invoice ID
                        java.time.LocalDate.now().toString(),   // Current date
                        supplierCombo.getValue(),
                        accountCombo.getValue(),
                        methodCombo.getValue(),
                        "Payment",
                        Double.parseDouble(amountField.getText())
                );
            }
            return null;
        });
    }
}
