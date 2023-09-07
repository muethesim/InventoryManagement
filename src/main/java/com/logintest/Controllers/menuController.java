package com.logintest.Controllers;

import com.logintest.MenuUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class menuController implements Initializable {
    @FXML private AnchorPane mainView;
    @FXML private MFXButton homeButton;
    @FXML private MFXButton salesButton;
    @FXML private MFXButton purchaseButton;
    @FXML private MFXButton inventoryButton;
    @FXML private MFXButton ledgerButton;
    @FXML private MFXButton stockButton;
    @FXML private MFXButton historyButton;
    @FXML private MFXButton statisticsButton;
    @FXML private Label timeanddate;
    @FXML private Label loggedinUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (MenuUtil.accessLevel > 2) {
            purchaseButton.setDisable(true);
            inventoryButton.setDisable(true);
            ledgerButton.setDisable(true);
            stockButton.setDisable(true);
            historyButton.setDisable(true);
            statisticsButton.setDisable(true);
        }
        //homepage loading at start and clock initialization
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                timeanddate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("E  dd-MM-yyyy   hh:mm:ss a")));
            }
        };
        timer.start();
        MenuUtil.loadPage("HomeView.fxml", mainView);
        MenuUtil.changeColor(homeButton, salesButton, purchaseButton, inventoryButton,
                ledgerButton, stockButton, historyButton, statisticsButton);
        loggedinUser.setText("Logged in as : " + (MenuUtil.loggedUser).toUpperCase());
    }

    @FXML
    private void handleLoading(ActionEvent event) throws IOException {
        if (((Node)event.getSource()).getId().equals("homeButton")) {
            MenuUtil.loadPage("HomeView.fxml", mainView);
            MenuUtil.changeColor(homeButton, salesButton, purchaseButton, inventoryButton,
                    ledgerButton, stockButton, historyButton, statisticsButton);
        }
        else if (((Node)event.getSource()).getId().equals("salesButton")) {
            MenuUtil.loadPage("SalesView.fxml", mainView);
            MenuUtil.changeColor(salesButton, homeButton, purchaseButton, inventoryButton,
                    ledgerButton, stockButton, historyButton, statisticsButton);
        }
        else if (((Node)event.getSource()).getId().equals("purchaseButton")) {
            MenuUtil.loadPage("PurchaseView.fxml", mainView);
            MenuUtil.changeColor(purchaseButton, salesButton, homeButton, inventoryButton,
                    ledgerButton, stockButton, historyButton, statisticsButton);
        }
        else if (((Node)event.getSource()).getId().equals("inventoryButton")) {
            MenuUtil.loadPage("InventoryView.fxml", mainView);
            MenuUtil.changeColor(inventoryButton, salesButton, purchaseButton, homeButton,
                    ledgerButton, stockButton, historyButton, statisticsButton);
        }
        else if (((Node)event.getSource()).getId().equals("ledgerButton")) {
            MenuUtil.loadPage("LedgerView.fxml", mainView);
            MenuUtil.changeColor(ledgerButton, salesButton, purchaseButton, inventoryButton,
                    homeButton, stockButton, historyButton, statisticsButton);
        }
        else if (((Node)event.getSource()).getId().equals("stockButton")) {
            MenuUtil.loadPage("StockView.fxml", mainView);
            MenuUtil.changeColor(stockButton, salesButton, purchaseButton, inventoryButton,
                    ledgerButton, homeButton, historyButton, statisticsButton);
        }
        else if (((Node)event.getSource()).getId().equals("historyButton")) {
            MenuUtil.loadPage("HistoryView.fxml", mainView);
            MenuUtil.changeColor(historyButton, salesButton, purchaseButton, inventoryButton,
                    ledgerButton, stockButton, homeButton, statisticsButton);
        }
        else if (((Node)event.getSource()).getId().equals("statisticsButton")) {
            MenuUtil.loadPage("StatisticsView.fxml", mainView);
            MenuUtil.changeColor(statisticsButton, salesButton, purchaseButton, inventoryButton,
                    ledgerButton, stockButton, historyButton, homeButton);
        }
    }

    @FXML
    private void handleLogout(MouseEvent mouseEvent) throws IOException {
        MenuUtil.handleLogout(mouseEvent, "login.fxml");
    }

    @FXML
    private void minimize(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void maximizeMinimize(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setMaximized(!stage.isMaximized());
    }

    @FXML
    private void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}