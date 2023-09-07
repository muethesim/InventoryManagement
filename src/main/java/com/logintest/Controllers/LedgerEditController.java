package com.logintest.Controllers;

import com.logintest.ConnectionUtil;
import com.logintest.MenuUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LedgerEditController implements Initializable {
    @FXML
    private AnchorPane anchorPane;
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
    private MFXTextField search;
    @FXML
    private MFXButton saveButton;
    @FXML
    private MFXButton editButton;
    @FXML
    private MFXButton deleteButton;
    @FXML
    private Label alertLabel;
    @FXML
    private TableView<LedgerEditDataViewModel> table;
    @FXML
    private TableColumn<LedgerEditDataViewModel, Integer> tid;
    @FXML
    private TableColumn<LedgerEditDataViewModel, String> tname;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        type.setValue("Sundry Debtor");
        type.getItems().setAll("Sundry Debtor", "Sundry Creditor");
        tid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tname.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbUpdt("");
    }
    public void tbUpdt(String st) {
        table.getItems().clear();
        try {
            ResultSet rst = ConnectionUtil.ledgerEditDataSearch(st);
            LedgerEditDataViewModel tbl;
            ObservableList<LedgerEditDataViewModel> lst;
            lst = table.getItems();
            while (rst.next()) {
                tbl = new LedgerEditDataViewModel(rst.getInt("id"), rst.getString("ledgername"));
                lst.add(tbl);
            }
            table.setItems(lst);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        disableFieldsAndButton();
    }
    public void disableFieldsAndButton() {
        name.setDisable(true);
        address.setDisable(true);
        phoneno.setDisable(true);
        amount.setDisable(true);
        type.setDisable(true);
        saveButton.setDisable(true);
        editButton.setDisable(true);
        deleteButton.setDisable(true);
    }
    @FXML
    void tableClick() throws SQLException {
        alertLabel.setText("");
        if(!String.valueOf(table.getSelectionModel().getSelectedItem()).equals("null")) {
            if (!saveButton.isDisable()) {
                disableFieldsAndButton();
                saveButton.setDisable(true);
                editButton.setDisable(false);
                deleteButton.setDisable(false);
            }
            editButton.setDisable(false);
            deleteButton.setDisable(false);
            LedgerEditDataViewModel tbl;
            tbl = table.getSelectionModel().getSelectedItem();
            ResultSet rslt = ConnectionUtil.ledgerEditDataRetrieve(tbl.getId());
            rslt.next();
            id.setText(String.valueOf(rslt.getInt("id")));
            name.setText(rslt.getString("ledgername"));
            type.setValue(rslt.getString("type"));
            amount.setText(rslt.getString("amount"));
            phoneno.setText(rslt.getString("phone"));
            address.setText(rslt.getString("address"));
        }
    }
    @FXML
    void deleteAction() throws SQLException {
        LedgerEditDataViewModel tb = table.getSelectionModel().getSelectedItem();
        ConnectionUtil.ledgerEditDataDelete(tb.getId());
        tbUpdt(search.getText());
        alertLabel.setText("Deleted successfully.");
        alertLabel.setTextFill(Color.GREEN);
    }
    @FXML
    void editAction() {
        name.setDisable(false);
        address.setDisable(false);
        phoneno.setDisable(false);
        amount.setDisable(false);
        type.setDisable(false);
        saveButton.setDisable(false);
        editButton.setDisable(true);
        deleteButton.setDisable(true);
    }
    @FXML
    void saveAction(ActionEvent event) throws SQLException {
        int kid = Integer.parseInt(id.getText());
        String kname = name.getText();
        String ktype = type.getValue();
        String kphone = phoneno.getText();
        String kadd1 = address.getText();
        String kamount = amount.getText();
        ConnectionUtil.ledgerEditDataUpdate(kid, kname, ktype, kamount, kphone, kadd1);
        clear();
        tbUpdt(search.getText());
        alertLabel.setText("Edited item successfully.");
        alertLabel.setTextFill(Color.GREEN);
    }
    public void clear() {
        name.clear();
        type.setValue("Sundry Debtor");
        address.clear();
        amount.clear();
        phoneno.clear();
        saveButton.setDisable(true);
        editButton.setDisable(false);
        deleteButton.setDisable(true);
    }
    @FXML
    public void searchTableView() throws SQLException {
        tbUpdt(search.getText());
    }
    @FXML
    public void changeFocus() {
        anchorPane.requestFocus();
        editButton.setDisable(true);
        deleteButton.setDisable(true);
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
