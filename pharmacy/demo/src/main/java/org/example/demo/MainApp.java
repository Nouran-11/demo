package org.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.demo.sidebar.SidebarBuilder;

public class MainApp extends Application {
    private static final int APP_WIDTH = 1200;
    private static final int APP_HEIGHT = 700;
    private static final String APP_TITLE = "Pharmacy Management System";
    private static final String BACKGROUND_STYLE = "-fx-background-color: #f5f6fa;";
    private static final String STYLESHEET_PATH = "/styles/main.css";

    @Override
    public void start(Stage primaryStage) {
        try {
            loadResources();

            BorderPane root = createRootLayout();
            setupUI(root);

            // Create scene and configure stage
            Scene scene = new Scene(root, APP_WIDTH, APP_HEIGHT);

            // If you have CSS
            if (getClass().getResource(STYLESHEET_PATH) != null) {
                scene.getStylesheets().add(getClass().getResource(STYLESHEET_PATH).toExternalForm());
            }

            configureStage(primaryStage, scene);

        } catch (Exception e) {
            handleStartupError(e);
        }
    }

    private void loadResources() {
        FontLoader.loadManropeFont();
    }

    private BorderPane createRootLayout() {
        BorderPane root = new BorderPane();
        root.setStyle(BACKGROUND_STYLE);
        return root;
    }

    private void setupUI(BorderPane root) {
        ViewManager viewManager = new ViewManager(root);
        SidebarBuilder sidebarBuilder = new SidebarBuilder(viewManager);

        root.setLeft(sidebarBuilder.buildSidebar());
        root.setCenter(viewManager.getInitialView());
    }

    private void configureStage(Stage stage, Scene scene) {
        stage.setTitle(APP_TITLE);
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.show();  // This is crucial - it makes the window appear
    }

    private void handleStartupError(Exception e) {
        System.err.println("Application startup failed:");
        e.printStackTrace();
        // TODO: Replace with proper logging or user-friendly error dialog
    }

    public static void main(String[] args) {
        launch(args);
    }
}