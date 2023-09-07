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
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class StockRemoveController implements Initializable {
    @FXML
    private MFXTextField item;
    @FXML
    private MFXTextField count;
    @FXML
    private Label alertLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    void clearAction() {
        count.setText("");
        item.setText("");
    }
    @FXML
    void itemAutoCompletion() throws SQLException {
        List<String> lst = new ArrayList<>();
        ResultSet rst = ConnectionUtil.stockAddRemoveItemSearch(item.getText());
        while (rst.next()) {
            lst.add(rst.getString("itemname"));
        }
        AutoCompletionUtil.setUpAutoCompletion(item, lst);
    }
    @FXML
    void submitAction() throws SQLException {
        if(!item.getText().isEmpty() && !count.getText().isEmpty()) {
            alertLabel.setText(" ");
            ConnectionUtil.stockRemoveSubmit(item.getText(), count.getText());
            clearAction();
            alertLabel.setText("Stock removed successfully.");
            alertLabel.setTextFill(Color.GREEN);
        } else {
            alertLabel.setText("Fill all the fields !!!");
            alertLabel.setTextFill(Color.GREEN);
        }

    }
    @FXML
    private void handleSubmenu(ActionEvent event) throws IOException {
        /*if(((Node)event.getSource()).getId().equals("stockAdd")) {
            //button --> enclosed anchorpane --> anchorpane(mainViewOfSubmenu) --> anchorpane(mainView)
            MenuUtil.loadPage("StockView/stockAdd.fxml", (AnchorPane) ((Node) event.getSource()).getParent().getParent().getParent());
        }
        else if(((Node)event.getSource()).getId().equals("stockRemove")) {
            MenuUtil.loadPage("StockView/stockRemove.fxml", (AnchorPane) ((Node) event.getSource()).getParent().getParent().getParent());
        }
        else */if(((Node)event.getSource()).getId().equals("backButton")) {
            //button --> anchorpane(mainViewOfSubmenu) --> anchorpane(mainView)
            MenuUtil.loadPage("StockView.fxml", (AnchorPane) ((Node) event.getSource()).getParent().getParent());
        }
    }
}
