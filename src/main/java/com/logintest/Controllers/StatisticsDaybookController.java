package com.logintest.Controllers;

import com.logintest.ConnectionUtil;
import com.logintest.MenuUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StatisticsDaybookController implements Initializable {
    @FXML
    private DatePicker from;

    @FXML
    private PieChart pieChart;

    @FXML
    private Label purchase;

    @FXML
    private Label sales;

    @FXML
    private DatePicker to;

    @FXML
    private PieChart cashPieChart;

    @FXML
    private Label cashPurchase;

    @FXML
    private Label cashSales;

    @FXML
    void submitClicked(ActionEvent event) throws SQLException {
        pieChart.getData().clear();
        cashPieChart.getData().clear();
        int saleTotal = ConnectionUtil.getSalesTotal(String.valueOf(from.getValue()), String.valueOf(to.getValue()));
        sales.setText(saleTotal+"" + " Rs.");
        int purchaseTotal = ConnectionUtil.getPurchaseTotal(String.valueOf(from.getValue()), String.valueOf(to.getValue()));
        purchase.setText(purchaseTotal+"" + " Rs.");
        int cashSaleTotal = ConnectionUtil.cashSaleTotal(String.valueOf(from.getValue()), String.valueOf(to.getValue()));
        cashSales.setText(cashSaleTotal+"" + " Rs.");
        int cashPurchaseTotal = ConnectionUtil.cashPurchaseTotal(String.valueOf(from.getValue()), String.valueOf(to.getValue()));
        cashPurchase.setText(cashPurchaseTotal+"" + " Rs.");
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Sales", saleTotal),
                new PieChart.Data("Purchase", purchaseTotal));

        pieChart.getData().addAll(pieChartData);

        ObservableList<PieChart.Data> cashPieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Cash Sales", cashSaleTotal),
                new PieChart.Data("Cash Purchase", cashPurchaseTotal));

        cashPieChart.getData().addAll(cashPieChartData);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    private void handleSubmenu(ActionEvent event) throws IOException {
        /*if(((Node)event.getSource()).getId().equals("daybook")) {
            //button --> enclosed anchorpane --> anchorpane(mainViewOfSubmenu) --> anchorpane(mainView)
            MenuUtil.loadPage("StatisticsView/dayBook.fxml", (AnchorPane) ((Node) event.getSource()).getParent().getParent().getParent());
        }
        else if(((Node)event.getSource()).getId().equals("cashbook")) {
            MenuUtil.loadPage("StatisticsView/cashBook.fxml", (AnchorPane) ((Node) event.getSource()).getParent().getParent().getParent());
        }
        else*/ if(((Node)event.getSource()).getId().equals("backButton")) {
            //button --> anchorpane(mainViewOfSubmenu) --> anchorpane(mainView)
            MenuUtil.loadPage("StatisticsView.fxml", (AnchorPane) ((Node) event.getSource()).getParent().getParent());
        }
    }
}
