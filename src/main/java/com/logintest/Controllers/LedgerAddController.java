package com.logintest.Controllers;

import com.logintest.ConnectionUtil;
import com.logintest.MenuUtil;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LedgerAddController implements Initializable {
    @FXML
    private MFXTextField id;
    @FXML
    private MFXTextField name;
    @FXML
    private ChoiceBox<String> type;
    @FXML
    private MFXTextField amount;
    @FXML
    private MFXTextField address;
    @FXML
    private MFXTextField phoneno;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label alertLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        type.setValue("Sundry Debtor");
        type.getItems().add("Sundry Debtor");
        type.getItems().add("Sundry Creditor");
        try {
            idset();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void idset() throws SQLException {
        id.setText(String.valueOf(ConnectionUtil.getidForLedgerAdd()));
    }
    @FXML
    private void submitAction() throws SQLException {
        if(!name.getText().isEmpty() && !type.getValue().isEmpty() && !amount.getText().isEmpty() && !address.getText().isEmpty()
        && !phoneno.getText().isEmpty() && !phoneno.getText().isEmpty()) {
            if (String.valueOf(type.getValue()).equals("Sundry Creditor")) {
                amount.setText("-"+amount.getText());
            }
            alertLabel.setText("");
            idset();
            if (amount.getText().equals(""))
                amount.setText("0");
            ConnectionUtil.ledgerAddinsertData(Integer.parseInt(id.getText()), name.getText(), String.valueOf(type.getValue()), Integer.parseInt(amount.getText()), address.getText(), phoneno.getText());
            clearAction();
            idset();
            alertLabel.setText("Ledger Added successfully.");
            alertLabel.setTextFill(Color.GREEN);
        } else {
            alertLabel.setText("Fill all the fields !!!");
            alertLabel.setTextFill(Color.TOMATO);
        }
    }

    @FXML
    private void numberValidation() { // todo
        String [] list = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        for (String l : list) {
            alertLabel.setText("");
            if (!amount.getText().equals(l)) {
                alertLabel.setText("Enter Integer for amount.");
                alertLabel.setTextFill(Color.TOMATO);
                break;
            }
        }
    }
    @FXML
    private void clearAction() {
        alertLabel.setText("");
        name.clear();
        type.setValue("Sundry Debtor");
        amount.clear();
        address.clear();
        phoneno.clear();
    }
    @FXML
    public void changeFocus() {
        anchorPane.requestFocus();
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
