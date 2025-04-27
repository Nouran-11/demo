package org.example.demo;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.example.demo.views.*;
import org.example.demo.views.product.ProductDamagesView;
import org.example.demo.views.product.ProductListView;
import org.example.demo.views.product.ProductPackageView;
import org.example.demo.views.purchase.PaymentView;
import org.example.demo.views.purchase.ReturnReceiveView;

import java.util.HashMap;
import java.util.Map;

public class ViewManager {
    private final BorderPane mainLayout;
    private final Map<String, Parent> viewCache;
    private static final String DEFAULT_VIEW = "dashboard";

    public ViewManager(BorderPane mainLayout) {
        this.mainLayout = mainLayout;
        this.viewCache = new HashMap<>();
        initializeViewCache();
    }

    private void initializeViewCache() {
        // Preload frequently used views
        viewCache.put(DEFAULT_VIEW, new DashboardView().getView());
        viewCache.put("product-list", new ProductListView().getView());
        viewCache.put("purchase-list", new PurchaseListView().getView());
    }

    public Parent getInitialView() {
        return getView(DEFAULT_VIEW);
    }

    public void switchTo(String viewName) {
        Parent view = getView(viewName);
        if (view != null) {
            mainLayout.setCenter(view);
        } else {
            System.err.println("Failed to load view: " + viewName);
            mainLayout.setCenter(getFallbackView());
        }
    }

    private Parent getView(String viewName) {
        try {
            return viewCache.computeIfAbsent(viewName, this::createView);
        } catch (Exception e) {
            handleViewError(viewName, e);
            return getFallbackView();
        }
    }

    private Parent createView(String viewName) {
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
    }

    private void handleViewError(String viewName, Exception e) {
        System.err.println("Error loading view: " + viewName);
        e.printStackTrace();
        // Consider adding error logging or user notification here
    }

    private Parent getFallbackView() {
        return new DashboardView().getView(); // Default fallback view
    }

    public void clearCache() {
        viewCache.clear();
        initializeViewCache(); // Reinitialize with basic views
    }

    public void clearViewCache(String viewName) {
        viewCache.remove(viewName.toLowerCase());
    }
}