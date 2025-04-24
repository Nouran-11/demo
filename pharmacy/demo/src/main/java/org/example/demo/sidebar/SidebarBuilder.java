package org.example.demo.sidebar;

import javafx.animation.RotateTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;
import org.example.demo.ViewManager;

import java.util.LinkedHashMap;
import java.util.Map;

public class SidebarBuilder {
    private final ViewManager viewManager;
    private Label lastSelected;

    public SidebarBuilder(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public VBox buildSidebar() {
        VBox sidebar = new VBox(5);
        sidebar.setStyle("-fx-background-color: #2d3436; -fx-padding: 20 0 0 0;");
        sidebar.setPrefWidth(220);

        Map<String, String[]> menu = createMenuStructure();

        menu.forEach((mainItem, subItems) -> {
            // Create main item
            Label mainLabel = createMainItem(mainItem);

            // Create subitems container
            VBox subItemsContainer = new VBox();
            subItemsContainer.setStyle("-fx-padding: 0 0 0 20;");
            subItemsContainer.setManaged(false);
            subItemsContainer.setVisible(false);

            if (subItems != null) {
                // Add chevron icon
                SVGPath chevron = createChevronIcon();
                mainLabel.setGraphic(chevron);

                // Create subitems
                for (String subItem : subItems) {
                    Label subLabel = createSubItem(subItem);
                    subLabel.setOnMouseClicked(e -> {
                        selectItem(subLabel);
                        viewManager.switchTo(subItem.toLowerCase().replace(" ", "-"));
                    });
                    subItemsContainer.getChildren().add(subLabel);
                }

                // Toggle animation
                mainLabel.setOnMouseClicked(e -> {
                    boolean willShow = !subItemsContainer.isVisible();
                    animateChevron(chevron, willShow);
                    toggleSubitems(subItemsContainer, willShow);
                });
            } else {
                mainLabel.setOnMouseClicked(e -> {
                    selectItem(mainLabel);
                    viewManager.switchTo(mainItem.toLowerCase());
                });
            }

            sidebar.getChildren().addAll(mainLabel, subItemsContainer);
        });

        return sidebar;
    }

    private Label createMainItem(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: #dfe6e9; -fx-font-weight: bold; -fx-font-size: 14; " +
                "-fx-padding: 12 15; -fx-cursor: hand;");
        label.setGraphicTextGap(10);
        label.setMaxWidth(Double.MAX_VALUE);
        return label;
    }

    private Label createSubItem(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: #b2bec3; -fx-font-size: 13; " +
                "-fx-padding: 10 15 10 25; -fx-cursor: hand;");
        label.setMaxWidth(Double.MAX_VALUE);
        return label;
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
            lastSelected.setStyle(lastSelected.getStyle()
                    .replace("-fx-background-color: #0984e3;", ""));
        }
        selected.setStyle(selected.getStyle() + "-fx-background-color: #0984e3;");
        lastSelected = selected;
    }

    private Map<String, String[]> createMenuStructure() {
        Map<String, String[]> menu = new LinkedHashMap<>();
        menu.put("Dashboard", null);
        menu.put("Purchase", new String[]{"Purchase List", "Payment", "Purchase Return", "Return Receive"});
        menu.put("Product", new String[]{"Product List", "Product Package", "Product Damages"});
        menu.put("Reports", new String[]{"Sales Report", "Sales Return Report", "Purchase Report"});
        menu.put("Stock", null);
        menu.put("Settings", null);
        return menu;
    }
}