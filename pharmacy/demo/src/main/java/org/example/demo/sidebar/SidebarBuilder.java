package org.example.demo.sidebar;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;
import org.example.demo.ViewManager;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class SidebarBuilder {
    private static final int ICON_SIZE = 24;
    private final ViewManager viewManager;
    private Label lastSelected;

    private boolean isCollapsed = false;

    public SidebarBuilder(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public VBox buildSidebar() {
        VBox sidebar = new VBox();
        applyModernSidebarStyle(sidebar);
        sidebar.setPrefWidth(240);

        // Modern header with logo
        HBox header = createModernHeader();
        sidebar.getChildren().add(header);

        // Search bar
        HBox searchBar = addSearchBar();
        sidebar.getChildren().add(searchBar);

        // Menu items
        createMenuStructure().forEach((mainItem, subItems) -> {
            ImageView mainIcon = loadIcon(iconFileName(mainItem));
            Label mainLabel = createLabel(mainItem, mainIcon, true);

            VBox subItemsContainer = new VBox();
            applySubmenuStyle(subItemsContainer);

            if (subItems != null) {
                SVGPath chevron = createChevronIcon();
                HBox graphicContainer = new HBox(5, mainIcon, chevron);
                mainLabel.setGraphic(graphicContainer);

                for (String subItem : subItems) {
                    ImageView subIcon = loadIcon(iconFileName(subItem));
                    Label subLabel = createLabel(subItem, subIcon, false);
                    subLabel.setOnMouseClicked(e -> {
                        selectItem(subLabel);
                        viewManager.switchTo(slugify(subItem));
                    });
                    subItemsContainer.getChildren().add(subLabel);
                }

                mainLabel.setOnMouseClicked(e -> {
                    boolean willShow = !subItemsContainer.isVisible();
                    animateChevron(chevron, willShow);
                    toggleSubitems(subItemsContainer, willShow);
                });
            } else {
                mainLabel.setGraphic(mainIcon);
                mainLabel.setOnMouseClicked(e -> {
                    selectItem(mainLabel);
                    viewManager.switchTo(slugify(mainItem));
                });
            }

            sidebar.getChildren().addAll(mainLabel, subItemsContainer);
        });

        // Add user profile section
        sidebar.getChildren().add(createUserProfileSection());

        // Add collapse toggle button
        Button toggleBtn = createCollapseButton(sidebar);
        sidebar.getChildren().add(toggleBtn);

        return sidebar;
    }

    private void applyModernSidebarStyle(VBox sidebar) {
        sidebar.setStyle("-fx-background-color: linear-gradient(to bottom, #2d3436, #1e272e); " +
                "-fx-padding: 20 0 0 0; -fx-spacing: 5;");
    }

    private HBox createModernHeader() {
        HBox header = new HBox(10);
        ImageView logo = loadIcon("pharmacy-logo.png");
        logo.setFitWidth(32);
        logo.setFitHeight(32);
        Label appName = new Label("PharmaCare");
        appName.setStyle("-fx-text-fill: white; -fx-font-size: 18; -fx-font-weight: bold;");
        header.getChildren().addAll(logo, appName);
        header.setStyle("-fx-padding: 0 0 20 20; -fx-alignment: center-left;");
        return header;
    }

    private HBox addSearchBar() {
        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; " +
                "-fx-background-radius: 20; -fx-padding: 8 15 8 35;");

        ImageView searchIcon = loadIcon("search.svg");
        searchIcon.setFitWidth(16);
        searchIcon.setFitHeight(16);

        StackPane searchContainer = new StackPane(searchField, searchIcon);
        StackPane.setAlignment(searchIcon, Pos.CENTER_LEFT);
        StackPane.setMargin(searchIcon, new Insets(0, 0, 0, 10));

        HBox searchBox = new HBox(searchContainer);
        searchBox.setStyle("-fx-padding: 0 15 20 15;");
        return searchBox;
    }

    private void applySubmenuStyle(VBox container) {
        container.setStyle("-fx-padding: 0 0 0 30; -fx-spacing: 5;");
        container.setManaged(false);
        container.setVisible(false);
    }

    private Label createLabel(String text, ImageView icon, boolean isMain) {
        Label label = new Label(text, icon);
        label.setGraphicTextGap(15);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setStyle(getLabelStyle(isMain));
        label.setOnMouseEntered(e -> label.setStyle(label.getStyle() + "-fx-background-color: rgba(255,255,255,0.05);"));
        label.setOnMouseExited(e -> {
            String baseStyle = getLabelStyle(isMain);
            if (label != lastSelected) {
                label.setStyle(baseStyle);
            } else {
                label.setStyle(baseStyle + "-fx-background-color: rgba(9, 132, 227, 0.1); " +
                        "-fx-border-color: #0984e3; -fx-border-width: 0 0 0 3;");
            }
        });
        return label;
    }

    private String getLabelStyle(boolean isMain) {
        String baseStyle = "-fx-text-fill: #dfe6e9; -fx-font-size: 14; " +
                "-fx-padding: 12 20; -fx-cursor: hand; " +
                "-fx-background-radius: 6; -fx-border-radius: 6; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 1);";

        if (isMain) {
            return baseStyle + "-fx-font-weight: 600;";
        } else {
            return baseStyle.replace("14", "13") +
                    "-fx-text-fill: #b2bec3; " +
                    "-fx-padding: 10 20 10 30;";
        }
    }

    private SVGPath createChevronIcon() {
        SVGPath chevron = new SVGPath();
        chevron.setContent("M 0 2 L 6 8 L 0 14 Z");
        chevron.setFill(Color.web("#dfe6e9"));
        chevron.setScaleX(0.9);
        chevron.setScaleY(0.9);
        return chevron;
    }

    private void animateChevron(SVGPath chevron, boolean expand) {
        RotateTransition rt = new RotateTransition(Duration.millis(250), chevron);
        rt.setInterpolator(Interpolator.EASE_BOTH);
        rt.setByAngle(expand ? 90 : -90);
        rt.play();
    }

    private void toggleSubitems(VBox container, boolean show) {
        container.setManaged(show);
        container.setVisible(show);
    }

    private void selectItem(Label selected) {
        if (lastSelected != null) {
            lastSelected.setStyle(lastSelected.getStyle()
                    .replace("-fx-background-color: rgba(9, 132, 227, 0.1);", "")
                    .replace("-fx-border-color: #0984e3;", ""));
        }

        selected.setStyle(selected.getStyle() +
                "-fx-background-color: rgba(9, 132, 227, 0.1); " +
                "-fx-border-color: #0984e3; " +
                "-fx-border-width: 0 0 0 3;");
        lastSelected = selected;
    }

    private Button createCollapseButton(VBox sidebar) {
        Button toggleBtn = new Button();
        toggleBtn.setGraphic(loadIcon("menu.svg"));
        toggleBtn.setStyle("-fx-background-color: transparent; -fx-padding: 10; " +
                "-fx-background-radius: 20; -fx-cursor: hand;");
        toggleBtn.setOnAction(e -> toggleSidebar(sidebar));

        // Position at bottom
        VBox.setMargin(toggleBtn, new Insets(20, 0, 0, 0));
        return toggleBtn;
    }

    private void toggleSidebar(VBox sidebar) {
        isCollapsed = !isCollapsed;
        TranslateTransition tt = new TranslateTransition(Duration.millis(300), sidebar);
        tt.setInterpolator(Interpolator.EASE_BOTH);
        tt.setToX(isCollapsed ? -sidebar.getWidth() + 50 : 0);
        tt.play();
    }

    private HBox createUserProfileSection() {
        HBox profileBox = new HBox(10);
        profileBox.setStyle("-fx-padding: 20 15 10 15; -fx-border-color: #34495e; -fx-border-width: 1 0 0 0;");

        ImageView avatar = loadIcon("user-avatar.png");
        avatar.setFitWidth(40);
        avatar.setFitHeight(40);

        VBox userInfo = new VBox();
        Label userName = new Label("Admin User");
        userName.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        Label userRole = new Label("System Admin");
        userRole.setStyle("-fx-text-fill: #b2bec3; -fx-font-size: 12;");

        userInfo.getChildren().addAll(userName, userRole);
        profileBox.getChildren().addAll(avatar, userInfo);

        return profileBox;
    }

    private Map<String, String[]> createMenuStructure() {
        Map<String, String[]> menu = new LinkedHashMap<>();
        menu.put("Dashboard", null);
        menu.put("Inventory", new String[]{"Products", "Stock Levels", "Expiry Alerts"});
        menu.put("Sales", new String[]{"POS", "Transactions", "Returns"});
        menu.put("Purchasing", new String[]{"Orders", "Suppliers", "Receivings"});
        menu.put("Customers", new String[]{"Patients", "Prescriptions", "Loyalty"});
        menu.put("Reports", new String[]{"Sales Analytics", "Inventory Reports", "Financials"});
        menu.put("Settings", null);
        return menu;
    }

    private String iconFileName(String name) {
        Map<String, String> iconMappings = new LinkedHashMap<String, String>() {{
            put("Dashboard", "dashboard.png");
            put("Inventory", "package.png");
            put("Sales", "shopping-cart.png");
            put("Purchasing", "truck.png");
            put("Customers", "users.png");
            put("Reports", "pie-chart.png");
            // Add all other mappings with .png extension
        }};
        return iconMappings.getOrDefault(name, "default.png");
    }
    private String slugify(String name) {
        return name.toLowerCase().replace(" ", "-");
    }

    private ImageView loadIcon(String fileName) {
        try {
            // Load PNG from resources
            InputStream is = getClass().getResourceAsStream("/icons/" + fileName);
            if (is == null) {
                throw new RuntimeException("Icon not found: " + fileName);
            }

            Image pngImage = new Image(is);
            ImageView imageView = new ImageView(pngImage);
            imageView.setFitWidth(ICON_SIZE);
            imageView.setFitHeight(ICON_SIZE);
            return imageView;
        } catch (Exception e) {
            System.err.println("Error loading icon: " + fileName);

            // Create visible fallback (red placeholder)
            ImageView errorView = new ImageView();
            errorView.setFitWidth(ICON_SIZE);
            errorView.setFitHeight(ICON_SIZE);
            errorView.setStyle("-fx-background-color: rgba(255,0,0,0.3);");
            return errorView;
        }
    }
}