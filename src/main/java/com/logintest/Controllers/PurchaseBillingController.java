package com.logintest.Controllers;

import com.logintest.AutoCompletionUtil;
import com.logintest.ConnectionUtil;
import com.logintest.MenuUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PurchaseBillingController implements Initializable {
    @FXML
    private MFXTextField discount;
    @FXML
    private MFXTextField gtotal;
    @FXML
    private MFXButton addBtn;
    @FXML
    private MFXButton clearBtn;
    @FXML
    private MFXButton resetBtn;
    @FXML
    private MFXButton submitBtn;
    @FXML
    private MFXButton deleteBtn;
    @FXML
    private MFXTextField id;
    @FXML
    private MFXTextField item;
    @FXML
    private MFXTextField lbal;
    @FXML
    private MFXTextField ledger;
    @FXML
    private MFXTextField paid;
    @FXML
    private MFXTextField pprice;
    @FXML
    private MFXTextField purchaseid;
    @FXML
    private MFXTextField qnty;
    @FXML
    private MFXTextField remain;
    @FXML
    private MFXTextField sprice;
    @FXML
    private TableView<purchaseBillingDataViewModel> table;

    @FXML
    private TableColumn<purchaseBillingDataViewModel, String> tname;
    @FXML
    private MFXTextField total;
    @FXML
    private TableColumn<purchaseBillingDataViewModel, Integer> tprice;
    @FXML
    private TableColumn<purchaseBillingDataViewModel, Integer> tsales;
    @FXML
    private TableColumn<purchaseBillingDataViewModel, Integer> tquan;
    @FXML
    private TableColumn<purchaseBillingDataViewModel, Integer> ttotal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setid();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tname.setCellValueFactory(new PropertyValueFactory<>("name"));
        tprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tquan.setCellValueFactory(new PropertyValueFactory<>("qnty"));
        ttotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tsales.setCellValueFactory(new PropertyValueFactory<>("sprice"));
    }
    @FXML
    void add(ActionEvent event) {
        purchaseBillingDataViewModel put = new purchaseBillingDataViewModel(item.getText(),
                Integer.parseInt(pprice.getText()),
                Integer.parseInt(qnty.getText()),
                Integer.parseInt(sprice.getText()));
        ObservableList<purchaseBillingDataViewModel> list = table.getItems();
        list.add(put);
        table.setItems(list);
        totCalc();
        reset();
    }
    @FXML
    void clearAll() throws SQLException {
        setid();
        ledger.setText("CASH ACCOUNT");
        table.getItems().clear();
        reset();
        lbal.setText("");
        paid.setText("0");
        remain.setText("0");
        total.setText("0");
        discount.setText("0");
        gtotal.setText("0");
    }
    @FXML
    void delete(ActionEvent event) {
        table.getItems().removeAll(table.getSelectionModel().getSelectedItem());
        totCalc();
        reset();
    }
    @FXML
    void downClicked(ActionEvent event) throws SQLException {
        ledger.setEditable(false);
        item.setEditable(false);
        pprice.setEditable(false);
        sprice.setEditable(false);
        qnty.setEditable(false);
        addBtn.setDisable(true);
        resetBtn.setDisable(true);
        deleteBtn.setDisable(true);
        lbal.setEditable(false);
        submitBtn.setDisable(true);
        clearBtn.setDisable(true);
        paid.setEditable(false);
        discount.setEditable(false);
        int newId = Integer.parseInt(purchaseid.getText());
        newId -= 1;
        ResultSet rst = ConnectionUtil.purchaseBillingPurchaseDetail(newId);
        ResultSet rst1 = ConnectionUtil.purchaseBillingPurchaseUpdtedDetail(newId);
        purchaseid.setText(String.valueOf(newId));
        if(rst.next()) {
            ledger.setText(ConnectionUtil.purchaseBillingLedgerName(rst.getInt("ledgerid")));
            lbal.setText(String.valueOf(rst.getInt("updated")));
            paid.setText(String.valueOf(rst.getInt("payed")));
            discount.setText(String.valueOf(rst.getInt("discount")));
            gtotal.setText(String.valueOf(rst.getInt("total")));
            total.setText(String.valueOf(rst.getInt("total")-rst.getInt("discount")));
            remain.setText(String.valueOf(rst.getInt("total")-rst.getInt("payed")));
            table.getItems().clear();
            while(rst1.next()) {
                purchaseBillingDataViewModel newPut = new purchaseBillingDataViewModel(ConnectionUtil.purchaseBillingItemName(rst1.getInt("itemid")),
                        rst1.getInt("pprice"),
                        rst1.getInt("qnty"),
                        rst1.getInt("sprice"));
                ObservableList<purchaseBillingDataViewModel> list = table.getItems();
                list.add(newPut);
                table.setItems(list);
            }
        }
        else {
            table.getItems().clear();
            ledger.clear();
            lbal.clear();
            paid.clear();
            discount.clear();
            gtotal.clear();
            total.clear();
            remain.clear();
        }
    }
    @FXML
    void upClicked(ActionEvent event) throws SQLException {
        ledger.setEditable(false);
        item.setEditable(false);
        pprice.setEditable(false);
        sprice.setEditable(false);
        qnty.setEditable(false);
        addBtn.setDisable(true);
        resetBtn.setDisable(true);
        deleteBtn.setDisable(true);
        lbal.setEditable(false);
        submitBtn.setDisable(true);
        clearBtn.setDisable(true);
        paid.setEditable(false);
        discount.setEditable(false);
        int newId = Integer.parseInt(purchaseid.getText());
        newId += 1;
        ResultSet rst = ConnectionUtil.purchaseBillingPurchaseDetail(newId);
        ResultSet rst1 = ConnectionUtil.purchaseBillingPurchaseUpdtedDetail(newId);
        purchaseid.setText(String.valueOf(newId));
        if(rst.next()) {
            ledger.setText(ConnectionUtil.purchaseBillingLedgerName(rst.getInt("ledgerid")));
            lbal.setText(String.valueOf(rst.getInt("updated")));
            paid.setText(String.valueOf(rst.getInt("payed")));
            discount.setText(String.valueOf(rst.getInt("discount")));
            gtotal.setText(String.valueOf(rst.getInt("total")));
            total.setText(String.valueOf(rst.getInt("total")-rst.getInt("discount")));
            remain.setText(String.valueOf(rst.getInt("total")-rst.getInt("payed")));
            table.getItems().clear();
            while(rst1.next()) {
                purchaseBillingDataViewModel newPut = new purchaseBillingDataViewModel(ConnectionUtil.purchaseBillingItemName(rst1.getInt("itemid")),
                        rst1.getInt("pprice"),
                        rst1.getInt("qnty"),
                        rst1.getInt("sprice"));
                ObservableList<purchaseBillingDataViewModel> list = table.getItems();
                list.add(newPut);
                table.setItems(list);
            }
        }
        else {
            table.getItems().clear();
            ledger.clear();
            lbal.clear();
            paid.clear();
            discount.clear();
            gtotal.clear();
            total.clear();
            remain.clear();
        }
    }
    @FXML
    void fill(ActionEvent event) throws SQLException {
        String tm = item.getText();
        int[] str = ConnectionUtil.purchaseBillingVals(tm);
        pprice.setText(str[0]+"");
        sprice.setText(str[2]+"");
        id.setText(str[1]+"");
        qnty.requestFocus();
    }
    @FXML
    void itemFill(KeyEvent event) throws SQLException {
        List<String> lst= new ArrayList<>();
        ResultSet rst = ConnectionUtil.purchaseBillingItemSearch(item.getText());
        while(rst.next()) {
            lst.add(rst.getString("itemname"));
        }
        AutoCompletionUtil.setUpAutoCompletion(item, lst);
    }
    @FXML
    void ledSearch(KeyEvent event) throws SQLException {
        List<String> lst= new ArrayList<>();
        ResultSet rst = ConnectionUtil.purchaseBillingLedgerSearch(ledger.getText());
        while(rst.next()) {
            lst.add(rst.getString("ledgername"));
        }
        AutoCompletionUtil.setUpAutoCompletion(ledger, lst);
    }
    @FXML
    void ledgerBal(ActionEvent event) throws SQLException {
        item.requestFocus();
        lbal.setText(ConnectionUtil.purchaseBillinLedBal(ledger.getText())+"");
    }
    @FXML
    void paidEdited(KeyEvent event) {
        int gt = Integer.valueOf(gtotal.getText()) - Integer.valueOf(paid.getText());
        remain.setText(gt + "");
    }
    @FXML
    void reset() {
        sprice.clear();
        pprice.clear();
        id.clear();
        item.clear();
        qnty.clear();
    }
    @FXML
    void submit(ActionEvent event) throws SQLException {
        String lname = ledger.getText();
        String id = purchaseid.getText();
        String lid = ConnectionUtil.purchaseBillingLedgerid(lname)+"";
        String disc = discount.getText();
        String tot = gtotal.getText();
        String rec = paid.getText();
        int rem = Integer.parseInt(tot) - Integer.parseInt(rec);
        remain.setText(rem+"");
        ConnectionUtil.purchaseBillingSubmitted(lname, id, lid, disc, tot, rec);
        itret();
        int amt =Integer.parseInt(remain.getText());
        ConnectionUtil.purchaseBillingSubBalance(lid, amt);
        clearAll();
    }
    @FXML
    void tdisc(KeyEvent event) {
        try {
            int gt = Integer.valueOf(total.getText()) - Integer.valueOf(discount.getText());
            gtotal.setText(gt + "");
        }
        catch (Exception e) {
            int gt = Integer.valueOf(total.getText());
            gtotal.setText(gt + "");
        }
    }
    public void setid() throws SQLException {
        String str = String.valueOf(ConnectionUtil.purchaseBillingGetid());
        purchaseid.setText(str);
    }
    public void totCalc() {
        List<Integer> lst = new ArrayList<>();
        purchaseBillingDataViewModel put;
        int i, sum=0;
        for (i=0; i<table.getItems().size(); i++) {
            put = table.getItems().get(i);
            lst.add(put.getTotal());
            sum += lst.get(i);
        }
        total.setText(String.valueOf(sum));
        int gt = Integer.valueOf(total.getText()) - Integer.valueOf(discount.getText());
        gtotal.setText(gt+"");
    }

    public void itret() throws SQLException {
        List<List<String>> lst = new ArrayList<List<String>>();
        purchaseBillingDataViewModel inp;
        int i;
        for (i=0; i<table.getItems().size(); i++) {
            inp = table.getItems().get(i);
            lst.add(new ArrayList<>());
            lst.get(i).add(inp.getName());
            lst.get(i).add(String.valueOf(inp.getPrice()));
            lst.get(i).add(String.valueOf(inp.getQnty()));
            lst.get(i).add(String.valueOf(inp.getTotal()));
            lst.get(i).add(String.valueOf(inp.getSprice()));
        }
        String itm;
        for(i=0; i<lst.size(); i++) {
            itm = String.valueOf(ConnectionUtil.purchaseBillingItemid(lst.get(i).get(0)));
            ConnectionUtil.purchaseBillingSdetails(purchaseid.getText(), itm, lst.get(i).get(2), lst.get(i).get(1), lst.get(i).get(4));
            ConnectionUtil.purchaseBillingChangePrice(itm, lst.get(i).get(1), lst.get(i).get(4));
        }
    }
    @FXML
    private void handleSubmenu(ActionEvent event) throws IOException {
        if(((Node)event.getSource()).getId().equals("purchaseBilling")) {
            //button --> enclosed anchorpane --> anchorpane(mainViewOfSubmenu) --> anchorpane(mainView)
            MenuUtil.loadPage("PurchaseView/purchaseBilling.fxml", (AnchorPane) ((Node) event.getSource()).getParent().getParent().getParent());
        }
        else if(((Node)event.getSource()).getId().equals("purchaseReciept")) {
            MenuUtil.loadPage("PurchaseView/purchasePayment.fxml", (AnchorPane) ((Node) event.getSource()).getParent().getParent().getParent());
        }
        else if(((Node)event.getSource()).getId().equals("backButton")) {
            //button --> anchorpane(mainViewOfSubmenu) --> anchorpane(mainView)
            MenuUtil.loadPage("PurchaseView.fxml", (AnchorPane) ((Node) event.getSource()).getParent().getParent());
        }
    }
}
