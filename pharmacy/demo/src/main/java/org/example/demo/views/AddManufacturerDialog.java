package org.example.demo.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AddManufacturerDialog {

    private VBox view;
    private TextField nameField;
    private TextField emailField;
    private TextField phoneField;
    private TextField addressField;
    private TextField balanceField;
    private Button cancelButton;
    private Button saveButton;

    public AddManufacturerDialog() {
        createView();
    }

    private void createView() {
        view = new VBox(10);
        view.setPadding(new Insets(20));
        view.setAlignment(Pos.CENTER_LEFT);

        nameField = new TextField();
        nameField.setPromptText("Manufacturer Name");

        emailField = new TextField();
        emailField.setPromptText("Email Address");

        phoneField = new TextField();
        phoneField.setPromptText("(202) 345 345 655");

        addressField = new TextField();
        addressField.setPromptText("Address Line");

        balanceField = new TextField();
        balanceField.setPromptText("Previous Balance");

        cancelButton = new Button("Cancel");
        saveButton = new Button("Save Changes");

        HBox buttons = new HBox(10, cancelButton, saveButton);

        view.getChildren().addAll(
                new Label("Manufacturer Name:"), nameField,
                new Label("Email Address:"), emailField,
                new Label("Phone:"), phoneField,
                new Label("Address Line:"), addressField,
                new Label("Previous Balance:"), balanceField,
                buttons
        );
    }

    public Node getView() {
        return view;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public TextField getNameField() {
        return nameField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public TextField getPhoneField() {
        return phoneField;
    }

    public TextField getAddressField() {
        return addressField;
    }

    public TextField getBalanceField() {
        return balanceField;
    }
}
