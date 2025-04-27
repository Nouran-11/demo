package org.example.demo;

import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.demo.sidebar.SidebarBuilder;
import org.example.demo.FontLoader;

public class MainApp extends Application {


    // Configuration constants
    private static final int APP_WIDTH = 1200;
    private static final int APP_HEIGHT = 700;
    private static final String APP_TITLE = "Pharmacy Management System";
    private static final String BACKGROUND_STYLE = "-fx-background-color: #f5f6fa;";

    @Override
    public void start(Stage primaryStage) {

        try {
            // Initialize application resources
            initializeResources();

            // Create root layout with view management
            BorderPane root = createRootLayout();
            setupApplicationUI(root);

            // Configure and show main window
            launchMainWindow(primaryStage, root);

        } catch (Exception e) {
            handleFatalError(e, primaryStage);
        }
    }

    private void initializeResources() {
        // Load application font
        FontLoader.loadManropeFont();

        if (!FontLoader.isManropeLoaded()) {
            System.err.println("Warning: Using fallback system font");
        }
    }

    private BorderPane createRootLayout() {
        BorderPane root = new BorderPane();
        root.setStyle(BACKGROUND_STYLE);
        return root;
    }

    private void setupApplicationUI(BorderPane root) {
        ViewManager viewManager = new ViewManager(root);
        SidebarBuilder sidebarBuilder = new SidebarBuilder(viewManager);

        root.setLeft(sidebarBuilder.buildSidebar());
        root.setCenter(viewManager.getInitialView());
    }

    private void launchMainWindow(Stage stage, BorderPane root) {
        Scene mainScene = createMainScene(root);


        stage.setTitle(APP_TITLE);
        stage.setScene(mainScene);
        configureWindowBehavior(stage);
        stage.show();
    }

    private Scene createMainScene(BorderPane root) {
        Scene scene = new Scene(root, APP_WIDTH, APP_HEIGHT);
        scene.getRoot().setStyle(BACKGROUND_STYLE);
        return scene;
    }



    private void configureWindowBehavior(Stage stage) {
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setOnCloseRequest(event -> {
            // Add any cleanup operations here
            System.out.println("Application closing...");
        });
    }

    private void handleFatalError(Exception e, Stage stage) {
        System.err.println("Fatal initialization error:");
        e.printStackTrace();

        // Show error scene if possible
        try {
            BorderPane errorRoot = new BorderPane();
            errorRoot.setCenter(new Label("Application failed to initialize\n" + e.getMessage()));
            stage.setScene(new Scene(errorRoot, 400, 200));
            stage.show();
        } catch (Exception fatal) {
            System.err.println("Could not even show error screen!");
            fatal.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            System.err.println("Critical application failure:");
            e.printStackTrace();
        }
    }

}