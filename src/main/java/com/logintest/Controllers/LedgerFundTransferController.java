package com.logintest.Controllers;

import com.logintest.AutoCompletionUtil;
import com.logintest.ConnectionUtil;
import com.logintest.MenuUtil;
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

public class LedgerFundTransferController implements Initializable {
    @FXML
    private MFXTextField amount;
    @FXML
    private MFXTextField from;
    @FXML
    private MFXTextField id;
    @FXML
    private MFXTextField to;
    @FXML
    private Label alertLabel;

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
        if (!(amount.getText().isEmpty() || from.getText().isEmpty() || to.getText().isEmpty())) {
            alertLabel.setText("");
            int tint = ConnectionUtil.ledgerFundTransferGetLedgerNameId(to.getText());
            int fint = ConnectionUtil.ledgerFundTransferGetLedgerNameId(from.getText());
            int amt = Integer.parseInt(amount.getText());
            ConnectionUtil.ledgerFundTransferSubmit(Integer.parseInt(id.getText()), fint, tint, amt);
            ConnectionUtil.ledgerFundTransferAddMoney(tint, amt);
            ConnectionUtil.ledgerFundTransferSubMoney(fint, amt);
            setId();
            from.setText("");
            to.setText("");
            amount.setText("");
            alertLabel.setText("Fund transfered successfully.");
            alertLabel.setTextFill(Color.GREEN);
        } else {
            alertLabel.setText("Fill all fields !!!");
            alertLabel.setTextFill(Color.TOMATO);
        }
    }
    public void setId() throws SQLException {
        id.setText(String.valueOf(ConnectionUtil.ledgerFundTransferGetid()));
    }
    @FXML
    private void dataAutoCompletionForTo(KeyEvent event) throws SQLException {
        List<String> lst= new ArrayList<>();
            System.out.println("in");
            ResultSet rst = ConnectionUtil.ledgerFundTransferSearch(to.getText());
            while(rst.next()) {
                lst.add(rst.getString("ledgername"));
            }
            AutoCompletionUtil.setUpAutoCompletion(to, lst);
    }
    @FXML
    private void dataAutoCompletionForFrom() throws SQLException {
        List<String> lst= new ArrayList<>();
        ResultSet rst = ConnectionUtil.ledgerFundTransferSearch(from.getText());
        while(rst.next()) {
            lst.add(rst.getString("ledgername"));
        }
        AutoCompletionUtil.setUpAutoCompletion(from, lst);
    }
    @FXML
    private void clearAction() throws SQLException {
        id.setText(String.valueOf(ConnectionUtil.ledgerFundTransferGetid()));
        amount.clear();
        from.clear();
        to.clear();
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
