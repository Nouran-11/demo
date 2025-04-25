package org.example.demo.views;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManufacturerView {

    private TableView<Manufacturer> tableView;
    private VBox view;
    private Button newManufacturerButton;
    private TextField searchField;

    public ManufacturerView() {
        createView();
    }

    private void createView() {
        searchField = new TextField();
        searchField.setPromptText("Search anything");

        Button searchButton = new Button("Search");

        HBox searchBox = new HBox(5, searchField, searchButton);
        searchBox.setAlignment(Pos.CENTER_LEFT);

        newManufacturerButton = new Button("+ New Manufacturer");

        HBox topBar = new HBox(10, searchBox, newManufacturerButton);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10));
        HBox.setHgrow(searchField, Priority.ALWAYS);

        tableView = new TableView<>();
        tableView.setPlaceholder(new Label("No manufacturers available"));

        TableColumn<Manufacturer, String> dateCol = new TableColumn<>("Date");
        TableColumn<Manufacturer, String> nameCol = new TableColumn<>("Name");
        TableColumn<Manufacturer, String> companyCol = new TableColumn<>("Company Name");
        TableColumn<Manufacturer, String> emailCol = new TableColumn<>("Email");
        TableColumn<Manufacturer, String> phoneCol = new TableColumn<>("Phone");
        TableColumn<Manufacturer, String> addressCol = new TableColumn<>("Address");
        TableColumn<Manufacturer, String> balanceCol = new TableColumn<>("Balance");

        tableView.getColumns().addAll(dateCol, nameCol, companyCol, emailCol, phoneCol, addressCol, balanceCol);

        view = new VBox(10, topBar, tableView);
        view.setPadding(new Insets(10));
        newManufacturerButton.setOnAction(e -> {
            AddManufacturerDialog dialog = new AddManufacturerDialog();

            // Create a simple modal window
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Manufacturer");
            dialogStage.initModality(Modality.APPLICATION_MODAL); // Block input to other windows

            Scene scene = new Scene((Parent) dialog.getView()); // cast VBox to Parent
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            dialogStage.showAndWait(); // Wait until it's closed
        });
    }

    public Parent getView() {
        return view; // return the VBox, which is your main layout container
    }


    public Button getNewManufacturerButton() {
        return newManufacturerButton;
    }

    public TableView<Manufacturer> getTableView() {
        return tableView;
    }

    public TextField getSearchField() {
        return searchField;
    }

    // Dummy manufacturer class (replace or extend later)
    public static class Manufacturer {
    }
}
