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

public class SalesRecieptController implements Initializable {
    @FXML
    private MFXTextField id;
    @FXML
    private MFXTextField ledger;
    @FXML
    private MFXTextField amount;
    @FXML
    private Label alertLabel;
    @FXML
    private MFXButton submitButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void submitAction() throws SQLException {
        if (!(ledger.getText().isEmpty() || amount.getText().isEmpty())) {
            alertLabel.setText(" ");
            int led = ConnectionUtil.salesRecieptGetLedgerid(ledger.getText());
            ConnectionUtil.salesRecieptSubmit(Integer.parseInt(id.getText()), led, Integer.parseInt(amount.getText()));
            ConnectionUtil.salesRecieptSubMoney(led, Integer.parseInt(amount.getText()));
            ledger.setText("");
            amount.setText("");
            setId();
            alertLabel.setText("Payment Successful.");
            alertLabel.setTextFill(Color.GREEN);
        } else {
            alertLabel.setText("Fill all fields !!!");
            alertLabel.setTextFill(Color.TOMATO);
        }
    }
    public void setId() throws SQLException {
        id.setText(String.valueOf(ConnectionUtil.salesRecieptGetid()));
    }
    @FXML
    private void ledgerAutoCompletion() throws SQLException {
        List<String> lst= new ArrayList<>();
        ResultSet rst = ConnectionUtil.salesRecieptLedgerSearch(ledger.getText());
        while(rst.next()) {
            lst.add(rst.getString("ledgername"));
        }
        AutoCompletionUtil.setUpAutoCompletion(ledger, lst);
    }
    @FXML
    private void upAction() throws SQLException {
        ledger.setEditable(false);
        amount.setEditable(false);
        submitButton.setDisable(true);
        int newId = Integer.parseInt(id.getText());
        newId += 1;
        ResultSet rst = ConnectionUtil.salesRecietGetDetails(newId);
        id.setText(String.valueOf(newId));
        if(rst.next()) {
            ledger.setText(ConnectionUtil.salesRecieptGetLedgerName(rst.getInt("ledgerid")));
            amount.setText(rst.getInt("amount") + "");
        }
        else {
            amount.clear();
            ledger.clear();
        }
    }
    @FXML
    void downAction() throws SQLException {
        ledger.setDisable(true);
        amount.setDisable(true);
        int newId = Integer.parseInt(id.getText());
        newId -= 1;
        ResultSet rst = ConnectionUtil.salesRecietGetDetails(newId);
        id.setText(String.valueOf(newId));
        if(rst.next()) {
            ledger.setText(ConnectionUtil.salesRecieptGetLedgerName(rst.getInt("ledgerid")));
            amount.setText(rst.getInt("amount") + "");
        }
        else {
            amount.clear();
            ledger.clear();
        }
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
