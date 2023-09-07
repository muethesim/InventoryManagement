package com.logintest.Controllers;

import com.logintest.MenuUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SalesController implements Initializable{
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    private void handleSubmenu(ActionEvent event) throws IOException {
        if(((Node)event.getSource()).getId().equals("salesBilling")) {
            //button --> enclosed anchorpane --> anchorpane(mainViewOfSubmenu) --> anchorpane(mainView)
            MenuUtil.loadPage("SalesView/salesBilling.fxml", (AnchorPane) ((Node) event.getSource()).getParent().getParent().getParent());
        }
        else if(((Node)event.getSource()).getId().equals("salesReciept")) {
            MenuUtil.loadPage("SalesView/salesReciept.fxml", (AnchorPane) ((Node) event.getSource()).getParent().getParent().getParent());
        }
        else if(((Node)event.getSource()).getId().equals("backButton")) {
            //button --> anchorpane(mainViewOfSubmenu) --> anchorpane(mainView)
            MenuUtil.loadPage("SalesView.fxml", (AnchorPane) ((Node) event.getSource()).getParent().getParent());
        }
    }
}
