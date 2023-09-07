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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class InventoryAddController implements Initializable {
    @FXML
    private ChoiceBox<String> quantity;
    @FXML
    private ChoiceBox<String> gst;
    @FXML
    private MFXTextField id;
    @FXML
    private MFXTextField name;
    @FXML
    private MFXTextField sprice;
    @FXML
    private MFXTextField pprice;
    @FXML
    private MFXTextField mrp;
    @FXML
    private MFXTextField stock;
    @FXML
    private MFXTextField barcode;
    @FXML
    private Label alertLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        quantity.setValue("Unit");
        quantity.getItems().setAll("Unit", "KG", "L");
        gst.setValue("0%");
        gst.getItems().setAll("0%", "5%", "12%", "18%");

        String str;
        try {
            setId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setId() throws SQLException {
        id.setText(String.valueOf(ConnectionUtil.getidForInventoryAdd()));
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

    @FXML
    private void submitAction() throws SQLException {
        if(!name.getText().isEmpty() && !quantity.getValue().isEmpty() && !gst.getValue().isEmpty()
        && !sprice.getText().isEmpty() && !pprice.getText().isEmpty() && !mrp.getText().isEmpty()
        && !stock.getText().isEmpty()) {
            int idno = Integer.parseInt(id.getText());
            String nm = name.getText();
            int pp = Integer.parseInt(pprice.getText());
            int sp = Integer.parseInt(sprice.getText());
            int stk = Integer.parseInt(stock.getText());
            int mrprice = Integer.parseInt(mrp.getText());
            String brcd = barcode.getText();
            String qnty = quantity.getValue();
            String tx = gst.getValue();
            int tax = switch (tx) {
                case "5%" -> 5;
                case "12%" -> 12;
                case "18%" -> 18;
                default -> 0;
            };

            alertLabel.setText("");
            ConnectionUtil.inventoryAddItemWithoutBarcode(idno, nm, qnty, pp, sp, tax, mrprice, stk);
            alertLabel.setText("Item added successfully.");
            alertLabel.setTextFill(Color.GREEN);
            clearAction();
        }
        else if(!name.getText().isEmpty() && !quantity.getValue().isEmpty() && !gst.getValue().isEmpty()
                && !sprice.getText().isEmpty() && !pprice.getText().isEmpty() && !mrp.getText().isEmpty()
                && !stock.getText().isEmpty() && !barcode.getText().isEmpty()){
            int idno = Integer.parseInt(id.getText());
            String nm = name.getText();
            int pp = Integer.parseInt(pprice.getText());
            int sp = Integer.parseInt(sprice.getText());
            int stk = Integer.parseInt(stock.getText());
            int mrprice = Integer.parseInt(mrp.getText());
            String brcd = barcode.getText();
            String qnty = quantity.getValue();
            String tx = gst.getValue();
            int tax = switch (tx) {
                case "5%" -> 5;
                case "12%" -> 12;
                case "18%" -> 18;
                default -> 0;
            };

            alertLabel.setText("Item added successfully");
            ConnectionUtil.inventoryAddItemWithBarcode(idno, nm, qnty, pp, sp, tax, stk, mrprice, brcd);
            alertLabel.setText("Item added successfully.");
            alertLabel.setTextFill(Color.GREEN);
            clearAction();
        }
        else {
            alertLabel.setText("Fill the fields !!!");
            alertLabel.setTextFill(Color.TOMATO);
        }
    }
    @FXML
    private void clearAction() throws SQLException {
        setId();
        name.clear();
        pprice.clear();
        sprice.clear();
        stock.clear();
        barcode.clear();
        quantity.setValue("Unit");
        gst.setValue("0%");
        mrp.clear();
    }
}
