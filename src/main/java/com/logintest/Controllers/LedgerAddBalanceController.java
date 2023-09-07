package com.logintest.Controllers;

import com.logintest.AutoCompletionUtil;
import com.logintest.ConnectionUtil;
import com.logintest.MenuUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LedgerAddBalanceController implements Initializable {
    @FXML
    private MFXTextField amount;
    @FXML
    private MFXTextField ledger;
    @FXML
    private Label alertLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    void clearAction() {
        ledger.clear();
        amount.clear();
    }
    @FXML
    void submitAction() throws SQLException {
        if (!ledger.getText().isEmpty() && !amount.getText().isEmpty()) {
            alertLabel.setText(" ");
            int id = ConnectionUtil.ledgerAddBalanceGetLedgerid(ledger.getText());
            ConnectionUtil.ledgerAddBalanceAdd(id, Integer.parseInt(amount.getText()));
            clearAction();
            alertLabel.setText("Balance added successfully");
            alertLabel.setTextFill(Color.GREEN);
        } else {
            alertLabel.setText("Fill all the fields !!!");
        }
    }
    @FXML
    void ledgerAutoCompletion() throws SQLException {
        List<String> lst= new ArrayList<>();
        ResultSet rst = ConnectionUtil.ledgerAddBalanceData(ledger.getText());
        while(rst.next()) {
            lst.add(rst.getString("ledgername"));
        }
        AutoCompletionUtil.setUpAutoCompletion(ledger, lst);
    }


    @FXML
    private void handleSubmenu(ActionEvent event) throws IOException {
        /*if(((Node)event.getSource()).getId().equals("ledgerAdd")) {
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
        else*/ if(((Node)event.getSource()).getId().equals("backButton")) {
            //button --> anchorpane(mainViewOfSubmenu) --> anchorpane(mainView)
            MenuUtil.loadPage("LedgerView.fxml", (AnchorPane) ((Node) event.getSource()).getParent().getParent());
        }
    }
}
