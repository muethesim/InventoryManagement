package com.logintest.Controllers;

import com.logintest.ConnectionUtil;
import com.logintest.MenuUtil;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class InventoryViewController implements Initializable {
    @FXML
    private MFXTextField search;
    @FXML
    private TableColumn<inventoryDataViewModel, String> sitems;
    @FXML
    private TableColumn<inventoryDataViewModel, Integer> sstocks;
    @FXML TableColumn<inventoryDataViewModel, Integer> sprice;
    @FXML TableColumn<inventoryDataViewModel, Integer> svalue;
    @FXML
    private TableView<inventoryDataViewModel> stable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sitems.setCellValueFactory(new PropertyValueFactory<>("sname"));
        sstocks.setCellValueFactory(new PropertyValueFactory<>("sstocks"));
        sprice.setCellValueFactory(new PropertyValueFactory<>("sprice"));
        svalue.setCellValueFactory(new PropertyValueFactory<>("svalue"));
        tbUpdt("");
    }
    public void tbUpdt(String st) {
        stable.getItems().clear();
        try {
            ResultSet rst = ConnectionUtil.inventoryViewData(st);
            inventoryDataViewModel tbl;
            ObservableList<inventoryDataViewModel> lst;
            lst = stable.getItems();
            while (rst.next()) {
                tbl = new inventoryDataViewModel(rst.getString("itemname"), rst.getInt("stock"), rst.getInt("sprice"));
                lst.add(tbl);
            }
            stable.setItems(lst);
            rst.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void searched(KeyEvent event) {
        tbUpdt(search.getText());
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

