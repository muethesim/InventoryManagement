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

public class LedgerController implements Initializable{
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    private void handleSubmenu(ActionEvent event) throws IOException {
        if(((Node)event.getSource()).getId().equals("ledgerAdd")) {
            //button --> enclosed anchorpane --> anchorpane(mainViewOfSubmenu) --> anchorpane(mainView)
            MenuUtil.loadPage("LedgerView/ledgerAdd.fxml", (AnchorPane) ((Node) event.getSource()).getParent().getParent().getParent());
        }
        else if(((Node)event.getSource()).getId().equals("ledgerEdit")) {
            MenuUtil.loadPage("LedgerView/ledgerEdit.fxml", (AnchorPane) ((Node) event.getSource()).getParent().getParent().getParent());
        }
        else if(((Node)event.getSource()).getId().equals("ledgerView")) {
            MenuUtil.loadPage("LedgerView/ledgerView.fxml", (AnchorPane) ((Node) event.getSource()).getParent().getParent().getParent());
        }
        else if(((Node)event.getSource()).getId().equals("ledgerFundTransfer")) {
            MenuUtil.loadPage("LedgerView/ledgerFundTransfer.fxml", (AnchorPane) ((Node) event.getSource()).getParent().getParent().getParent());
        }
        else if(((Node)event.getSource()).getId().equals("ledgerAddBalance")) {
            MenuUtil.loadPage("LedgerView/ledgerAddBalance.fxml", (AnchorPane) ((Node) event.getSource()).getParent().getParent().getParent());
        }
        else if(((Node)event.getSource()).getId().equals("backButton")) {
            //button --> anchorpane(mainViewOfSubmenu) --> anchorpane(mainView)
            MenuUtil.loadPage("LedgerView.fxml", (AnchorPane) ((Node) event.getSource()).getParent().getParent());
        }
    }
}
