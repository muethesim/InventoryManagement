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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class InventoryEditController implements Initializable {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private MFXButton deleteButton;
    @FXML
    private MFXButton editButton;
    @FXML
    private MFXButton saveButton;
    @FXML
    private MFXTextField barcode;
    @FXML
    private MFXTextField id;
    @FXML
    private ChoiceBox<String> gst;
    @FXML
    private MFXTextField mrp;
    @FXML
    private MFXTextField name;
    @FXML
    private MFXTextField pprice;
    @FXML
    private ChoiceBox<String> quantity;
    @FXML
    private MFXTextField search;
    @FXML
    private MFXTextField sprice;
    @FXML
    private MFXTextField stock;
    @FXML
    private Label alertLabel;
    @FXML
    private TableView<inventoryEditDataView> table;
    @FXML
    private TableColumn<inventoryEditDataView, String> tname;
    @FXML
    private TableColumn<inventoryEditDataView, Integer> tid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        quantity.setValue("Unit");
        quantity.getItems().setAll("Unit", "KG", "L");
        gst.setValue("0%");
        gst.getItems().setAll("0%", "5%", "12%", "18%");
        tid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tname.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbUpdt("");
    }
    public void tbUpdt(String st) {
        table.getItems().clear();
        try {
            ResultSet rst = ConnectionUtil.inventoryEditdataSearch(st);
            inventoryEditDataView tbl;
            ObservableList<inventoryEditDataView> lst;
            lst = table.getItems();
            while (rst.next()) {
                tbl = new inventoryEditDataView(rst.getInt("id"), rst.getString("itemname"));
                lst.add(tbl);
            }
            table.setItems(lst);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        disableFieldsAndButtons();
    }
    public void disableFieldsAndButtons() {
        name.setDisable(true);
        quantity.setDisable(true);
        pprice.setDisable(true);
        sprice.setDisable(true);
        barcode.setDisable(true);
        gst.setDisable(true);
        mrp.setDisable(true);
        deleteButton.setDisable(true);
        saveButton.setDisable(true);
        editButton.setDisable(true);
    }
    @FXML
    private void deleteAction() throws SQLException {
        inventoryEditDataView tb = table.getSelectionModel().getSelectedItem();
        ConnectionUtil.inventoryEditDataDelete(tb.getId());
        tbUpdt(search.getText());
        alertLabel.setText("Deleted successfully.");
        alertLabel.setTextFill(Color.GREEN);
    }
    @FXML
    private void editAction() {
        name.setDisable(false);
        quantity.setDisable(false);
        pprice.setDisable(false);
        sprice.setDisable(false);
        barcode.setDisable(false);
        gst.setDisable(false);
        mrp.setDisable(false);
        saveButton.setDisable(false);
        editButton.setDisable(true);
        deleteButton.setDisable(true);
    }
    @FXML
    private void saveAction() throws SQLException {
        String gs = gst.getValue();
        gs = gs.substring(0, gs.length()-1);
        int kid = Integer.parseInt(id.getText());
        String kname = name.getText();
        String kqnty = quantity.getValue();
        int kpp = Integer.parseInt(pprice.getText());
        int ksp = Integer.parseInt(sprice.getText());
        String kbar = barcode.getText();
        int kgst = Integer.parseInt(gs);
        int kmrp = Integer.parseInt(mrp.getText());
        ConnectionUtil.inventoryEditDataUpdate(kid, kname, kqnty, kpp, ksp, kbar, kgst, kmrp);
        clearTextFieldsAndDisableButtons();
        tbUpdt(search.getText());
        alertLabel.setText("Edited item successfully.");
        alertLabel.setTextFill(Color.GREEN);
    }
    public void clearTextFieldsAndDisableButtons() {
        name.clear();
        quantity.setValue("Unit");
        pprice.clear();
        sprice.clear();
        barcode.clear();
        gst.setValue("0%");
        mrp.clear();
        saveButton.setDisable(true);
        editButton.setDisable(false);
        deleteButton.setDisable(true);
    }
    @FXML
    private void tableRowClicked() throws SQLException {
        alertLabel.setText("");
        if(!String.valueOf(table.getSelectionModel().getSelectedItem()).equals("null")) {
            if (!saveButton.isDisable()) {
                disableFieldsAndButtons();
                saveButton.setDisable(true);
                editButton.setDisable(false);
                deleteButton.setDisable(false);
            }
            editButton.setDisable(false);
            deleteButton.setDisable(false);
            inventoryEditDataView tbl;
            tbl = table.getSelectionModel().getSelectedItem();
            ResultSet rslt = ConnectionUtil.inventoryEditDataRetrive(tbl.getId());
            rslt.next();
            id.setText(String.valueOf(rslt.getInt("id")));
            name.setText(rslt.getString("itemname"));
            quantity.setValue(rslt.getString("qntytype"));
            pprice.setText(String.valueOf(rslt.getInt("pprice")));
            sprice.setText(String.valueOf(rslt.getInt("sprice")));
            gst.setValue(rslt.getInt("gst") + "%");
            mrp.setText(String.valueOf(rslt.getInt("mrp")));
            barcode.setText(rslt.getString("barcode"));
        }
    }
    @FXML
    private void searched() throws SQLException {
        tbUpdt(search.getText());
    }
    @FXML
    private void changeFocus() {
        anchorPane.requestFocus();
        editButton.setDisable(true);
        deleteButton.setDisable(true);
    }
    @FXML
    private void handleSubmenu(ActionEvent event) throws IOException {
        /*if(((Node)event.getSource()).getId().equals("inventoryAdd")) {
            //button --> enclosed anchorpane --> anchorpane(mainViewOfSubmenu) --> anchorpane(mainView)
            MenuUtil.loadPage("InventoryView/inventoryAdd.fxml", (AnchorPane) ((Node) event.getSource()).getParent().getParent().getParent());
        }
        else if(((Node)event.getSource()).getId().equals("inventoryEdit")) {
            MenuUtil.loadPage("InventoryView/inventoryEdit.fxml", (AnchorPane) ((Node) event.getSource()).getParent().getParent().getParent());
        }
        else if(((Node)event.getSource()).getId().equals("inventoryView")) {
            MenuUtil.loadPage("InventoryView/inventoryView.fxml", (AnchorPane) ((Node) event.getSource()).getParent().getParent().getParent());
        }
        else*/ if(((Node)event.getSource()).getId().equals("backButton")) {
            //button --> anchorpane(mainViewOfSubmenu) --> anchorpane(mainView)
            MenuUtil.loadPage("InventoryView.fxml", (AnchorPane) ((Node) event.getSource()).getParent().getParent());
        }
    }
}