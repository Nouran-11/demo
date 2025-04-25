package org.example.demo;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.example.demo.views.*;
import org.example.demo.views.ProductDamagesView;
import org.example.demo.views.PurchaseListView;
import org.example.demo.views.PurchaseReturnView;
import org.example.demo.views.SalesReportView;
import org.example.demo.views.SalesReturnReportView;
import org.example.demo.views.product.*;
import org.example.demo.views.purchase.*;


public class ViewManager {
    private final BorderPane mainLayout;
    private static final String DEFAULT_VIEW = "dashboard";

    public ViewManager(BorderPane mainLayout) {
        this.mainLayout = mainLayout;
    }

    public Parent getInitialView() {
        return getView(DEFAULT_VIEW);
    }

    public void switchTo(String viewName) {
        mainLayout.setCenter(getView(viewName));
    }

    private Parent getView(String viewName) {
        try {
            return switch (viewName.toLowerCase()) {
                case "dashboard" -> new DashboardView().getView();
                case "purchase-list" -> new PurchaseListView().getView();
                case "payment" -> new PaymentView().getView();
                case "purchase-return" -> new PurchaseReturnView().getView();
                case "return-receive" -> new ReturnReceiveView().getView();
                case "product-list" -> new ProductListView().getView();
                case "product-package" -> new ProductPackageView().getView();
                case "product-damages" -> new ProductDamagesView().getView();
                case "sales-report" -> new SalesReportView().getView();
                case "sales-return-report" -> new SalesReturnReportView().getView();
                case "purchase-report" -> new PurchaseReportView().getView();
                case "reports" -> new ReportsView().getView();
                case "settings" -> new SettingsView().getView();
                case "stock" -> new StockView().getView();
                case "manufacturer" -> new ManufacturerView().getView();
                default -> throw new IllegalArgumentException("Unknown view: " + viewName);
            };
        } catch (Exception e) {
            System.err.println("Error loading view: " + viewName);
            e.printStackTrace();
            return new DashboardView().getView(); // Fallback view
        }
    }
}