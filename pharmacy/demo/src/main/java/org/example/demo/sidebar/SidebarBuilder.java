package org.example.demo.sidebar;

import javafx.animation.RotateTransition;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;
import org.example.demo.ViewManager;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

public class SidebarBuilder {
    private final ViewManager viewManager;
    private Label lastSelected;
    private static final int ICON_SIZE = 20;

    public SidebarBuilder(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public VBox buildSidebar() {
        VBox sidebar = new VBox();
        sidebar.setStyle("-fx-background-color: #2d3436; -fx-padding: 20 0 0 0;");
        sidebar.setPrefWidth(220);

        // Add dashboard header
        Label dashboardHeader = new Label("Dashboard");
        dashboardHeader.setStyle("-fx-text-fill: white; -fx-font-size: 20; -fx-font-weight: bold; -fx-padding: 0 0 20 20;");
        sidebar.getChildren().add(dashboardHeader);

        createMenuStructure().forEach((mainItem, subItems) -> {
            ImageView mainIcon = loadIcon(iconFileName(mainItem));
            Label mainLabel = createLabel(mainItem, mainIcon, true);

            VBox subItemsContainer = new VBox();
            subItemsContainer.setStyle("-fx-padding: 0 0 0 30;"); // Increased left padding for subitems
            subItemsContainer.setManaged(false);
            subItemsContainer.setVisible(false);

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

        return sidebar;
    }

    private Label createLabel(String text, ImageView icon, boolean isMain) {
        Label label = new Label(text, icon);
        label.setGraphicTextGap(15);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setStyle(getLabelStyle(isMain));
        return label;
    }

    private String getLabelStyle(boolean isMain) {
        String baseStyle = "-fx-text-fill: #dfe6e9; -fx-font-size: 14; -fx-padding: 12 15; -fx-cursor: hand;";
        if (isMain) {
            return baseStyle + "-fx-font-weight: bold;";
        } else {
            return baseStyle.replace("14", "13") + "-fx-text-fill: #b2bec3;";
        }
    }

    private SVGPath createChevronIcon() {
        SVGPath chevron = new SVGPath();
        chevron.setContent("M 0 0 L 5 5 L 0 10 Z");
        chevron.setFill(Color.web("#dfe6e9"));
        chevron.setScaleX(0.8);
        chevron.setScaleY(0.8);
        return chevron;
    }

    private void animateChevron(SVGPath chevron, boolean expand) {
        RotateTransition rt = new RotateTransition(Duration.millis(200), chevron);
        rt.setByAngle(expand ? 90 : -90);
        rt.play();
    }

    private void toggleSubitems(VBox container, boolean show) {
        container.setManaged(show);
        container.setVisible(show);
    }

    private void selectItem(Label selected) {
        if (lastSelected != null) {
            lastSelected.setStyle(lastSelected.getStyle().replace("-fx-background-color: #0984e3;", ""));
        }
        selected.setStyle(selected.getStyle() + "-fx-background-color: #0984e3;");
        lastSelected = selected;
    }

    private Map<String, String[]> createMenuStructure() {
        Map<String, String[]> menu = new LinkedHashMap<>();
        menu.put("Dashboard", null);
        menu.put("Purchase", new String[]{"Purchase List", "Payment", "Purchase Return", "Return Receive"});
        menu.put("Products", new String[]{"Product List", "Product Package", "Product Damages"});
        menu.put("Reports", new String[]{"Sales Report", "Sales Return Report", "Purchase Report"});
        menu.put("Stock", null);
        menu.put("Customer", null);
        menu.put("Manufacturer", null);
        return menu;
    }

    private String iconFileName(String name) {
        Map<String, String> iconMappings = new LinkedHashMap<>();
        iconMappings.put("Dashboard", "dashboard.png");
        iconMappings.put("Purchase", "purchase.png");
        iconMappings.put("Products", "product.png");
        iconMappings.put("Reports", "reports.png");
        iconMappings.put("Stock", "stock.png");
        iconMappings.put("Customer", "customer.png");
        iconMappings.put("Manufacturer", "manufacturer.png");

        // Default fallback
        return iconMappings.getOrDefault(name, "default-icon.png");
    }

    private String slugify(String name) {
        return name.toLowerCase().replace(" ", "-");
    }

    private ImageView loadIcon(String fileName) {
        try {
            URL imageUrl = getClass().getResource("/icons/" + fileName);
            if (imageUrl != null) {
                ImageView imageView = new ImageView(new Image(imageUrl.toExternalForm()));
                imageView.setFitWidth(ICON_SIZE);
                imageView.setFitHeight(ICON_SIZE);
                return imageView;
            }
        } catch (Exception e) {
            System.err.println("Error loading icon: " + fileName);
        }
        // Fallback to empty ImageView if icon not found
        return new ImageView();
    }
}