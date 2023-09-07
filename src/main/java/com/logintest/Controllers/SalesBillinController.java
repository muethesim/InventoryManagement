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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class SalesBillinController implements Initializable {
    @FXML
    private MFXButton addBtn;
    @FXML
    private MFXButton clearBtn;
    @FXML
    private MFXButton deleteBtn;
    @FXML
    private MFXTextField discount;
    @FXML
    private MFXTextField gtotal;
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
    private MFXTextField price;
    @FXML
    private MFXTextField qnty;
    @FXML
    private MFXTextField remain;
    @FXML
    private MFXButton resetBtn;
    @FXML
    private MFXTextField saleid;
    @FXML
    private MFXButton submitBtn;
    @FXML
    private MFXTextField total;
    @FXML
    private Label alertLabel;

    @FXML
    private TableView<salesBillingDataViewModel> table;
    @FXML
    private TableColumn<salesBillingDataViewModel, String> tname;
    @FXML
    private TableColumn<salesBillingDataViewModel, Integer> tprice;
    @FXML
    private TableColumn<salesBillingDataViewModel, Integer> tquan;
    @FXML
    private TableColumn<salesBillingDataViewModel, Integer> ttotal;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String str;
        try {
            str = String.valueOf(ConnectionUtil.salesBillingGetid());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        saleid.setText(str);
        tname.setCellValueFactory(new PropertyValueFactory<>("name"));
        tprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tquan.setCellValueFactory(new PropertyValueFactory<>("qnty"));
        ttotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
    @FXML
    private void add() {
        salesBillingDataViewModel put = new salesBillingDataViewModel(item.getText(),
                Integer.parseInt(price.getText()),
                Integer.parseInt(qnty.getText()));
        ObservableList<salesBillingDataViewModel> list = table.getItems();
        list.add(put);
        table.setItems(list);
        totCalc();
        reset();
    }
    @FXML
    private void reset() {
        price.clear();
        id.clear();
        item.clear();
        qnty.clear();
    }
    public void totCalc() {
        List<Integer> lst = new ArrayList<>();
        salesBillingDataViewModel put;
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
    @FXML
    private void delete() {
        table.getItems().removeAll(table.getSelectionModel().getSelectedItem());
        totCalc();
        reset();
    }
    @FXML
    private void fill() throws SQLException {
        String tm = item.getText();
        int[] str = ConnectionUtil.salesBillinVals(tm);
        price.setText(str[0]+"");
        id.setText(str[1]+"");
        qnty.requestFocus();
    }
    @FXML
    private void submit() throws SQLException {
        String lname = ledger.getText();
        String id = saleid.getText();
        String lid = ConnectionUtil.salesBillingLedgerId(lname)+"";
        String disc = discount.getText();
        String tot = gtotal.getText();
        String rec = paid.getText();
        int rem = Integer.parseInt(tot) - Integer.parseInt(rec);
        remain.setText(rem+"");
        ConnectionUtil.salesBillingSubmitted(lname, id, lid, disc, tot, rec);
        itret();
        int amt =Integer.parseInt(remain.getText());
        ConnectionUtil.salesBillingAddBalance(lid, amt);
        clearAll();
        alertLabel.setText("Transaction Succesfull");
        alertLabel.setTextFill(Color.GREEN);
    }
    public void setid() throws SQLException {
        String str = String.valueOf(ConnectionUtil.salesBillingGetid());
        saleid.setText(str);
    }
    @FXML
    void ledgerBal(ActionEvent event) throws SQLException {
        item.requestFocus();
        lbal.setText(ConnectionUtil.salesBillingLedBal(ledger.getText())+"");
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
    @FXML
    void paidEdited(KeyEvent event) {
        int gt = Integer.valueOf(gtotal.getText()) - Integer.valueOf(paid.getText());
        remain.setText(gt + "");
    }
    @FXML
    private void clearAll() throws SQLException {
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
    void upClicked() throws SQLException {
        ledger.setEditable(false);
        item.setEditable(false);
        price.setEditable(false);
        qnty.setEditable(false);
        addBtn.setDisable(true);
        resetBtn.setDisable(true);
        deleteBtn.setDisable(true);
        lbal.setEditable(false);
        submitBtn.setDisable(true);
        clearBtn.setDisable(true);
        paid.setEditable(false);
        discount.setEditable(false);
        int newId = Integer.parseInt(saleid.getText());
        newId += 1;
        ResultSet rst = ConnectionUtil.salesBilingSaleDetail(newId);
        ResultSet rst1 = ConnectionUtil.salesBillingSaleUpdtedDetail(newId);
        saleid.setText(String.valueOf(newId));
        if(rst.next()) {
            ledger.setText(ConnectionUtil.salesBillinLedgerName(rst.getInt("ledgerid")));
            lbal.setText(String.valueOf(rst.getInt("updated")));
            paid.setText(String.valueOf(rst.getInt("recieved")));
            discount.setText(String.valueOf(rst.getInt("discount")));
            gtotal.setText(String.valueOf(rst.getInt("total")));
            total.setText(String.valueOf(rst.getInt("total")-rst.getInt("discount")));
            remain.setText(String.valueOf(rst.getInt("total")-rst.getInt("recieved")));
            table.getItems().clear();
            while(rst1.next()) {
                salesBillingDataViewModel newPut = new salesBillingDataViewModel(ConnectionUtil.salesBillingItemName(rst1.getInt("itemid")),
                        rst1.getInt("rate"),
                        rst1.getInt("qnty"));
                ObservableList<salesBillingDataViewModel> list = table.getItems();
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
    void downClicked() throws SQLException {
        ledger.setEditable(false);
        item.setEditable(false);
        price.setEditable(false);
        qnty.setEditable(false);
        addBtn.setDisable(true);
        resetBtn.setDisable(true);
        deleteBtn.setDisable(true);
        lbal.setEditable(false);
        submitBtn.setDisable(true);
        clearBtn.setDisable(true);
        paid.setEditable(false);
        discount.setEditable(false);
        int newId = Integer.parseInt(saleid.getText());
        newId -= 1;
        ResultSet rst = ConnectionUtil.salesBilingSaleDetail(newId);
        ResultSet rst1 = ConnectionUtil.salesBillingSaleUpdtedDetail(newId);
        saleid.setText(String.valueOf(newId));
        if(rst.next()) {
            ledger.setText(ConnectionUtil.salesRecieptGetLedgerName(rst.getInt("ledgerid")));
            lbal.setText(String.valueOf(rst.getInt("updated")));
            paid.setText(String.valueOf(rst.getInt("recieved")));
            discount.setText(String.valueOf(rst.getInt("discount")));
            gtotal.setText(String.valueOf(rst.getInt("total")));
            total.setText(String.valueOf(rst.getInt("total")-rst.getInt("discount")));
            remain.setText(String.valueOf(rst.getInt("total")-rst.getInt("recieved")));
            table.getItems().clear();
            while(rst1.next()) {
                salesBillingDataViewModel newPut = new salesBillingDataViewModel(ConnectionUtil.salesBillingItemName(rst1.getInt("itemid")),
                        rst1.getInt("rate"),
                        rst1.getInt("qnty"));
                ObservableList<salesBillingDataViewModel> list = table.getItems();
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
    void itemFill(KeyEvent event) throws SQLException {
        List<String> lst= new ArrayList<>();
        ResultSet rst = ConnectionUtil.salesBillingItemSearch(item.getText());
        while(rst.next()) {
            lst.add(rst.getString("itemname"));
        }
        AutoCompletionUtil.setUpAutoCompletion(item, lst);
    }
    @FXML
    void ledSearch(KeyEvent event) throws SQLException {
        List<String> lst= new ArrayList<>();
        ResultSet rst = ConnectionUtil.salesBillinLedgerSearch(ledger.getText());
        while(rst.next()) {
            lst.add(rst.getString("ledgername"));
        }
        AutoCompletionUtil.setUpAutoCompletion(ledger, lst);
    }
    public void itret() throws SQLException {
        List<List<String>> lst = new ArrayList<List<String>>();
        salesBillingDataViewModel inp;
        int i;
        for (i=0; i<table.getItems().size(); i++) {
            inp = table.getItems().get(i);
            lst.add(new ArrayList<>());
            lst.get(i).add(inp.getName());
            lst.get(i).add(String.valueOf(inp.getPrice()));
            lst.get(i).add(String.valueOf(inp.getQnty()));
            lst.get(i).add(String.valueOf(inp.getTotal()));
        }
        String itm;
        for(i=0; i<lst.size(); i++) {
            itm = String.valueOf(ConnectionUtil.salesBillingItemId(lst.get(i).get(0)));
            ConnectionUtil.salesBillingSdetails(saleid.getText(), itm, lst.get(i).get(2), lst.get(i).get(1));
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

    /*@FXML
    private void printInvoice() throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        String imageLocation = "C:\\Users\\danis\\Desktop\\msm-826-menupage-279296728d0a\\src\\main\\resources\\com\\logintest\\images\\logoMain.png";
        PDImageXObject pdImage = PDImageXObject.createFromFile(imageLocation, document);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        float scale = 0.25f;
        float imageWidth = pdImage.getWidth() * scale;
        float imageHeight = pdImage.getHeight() * scale;
        contentStream.drawImage(pdImage, 180, 700, imageWidth, imageHeight);

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
        contentStream.newLineAtOffset(265, 650);
        contentStream.showText("INVOICE");
        contentStream.endText();

        //Strin sales_id = saleid.getText() - 1;

        contentStream.close();
        File billFile = new File("C:\\Users\\danis\\Desktop\\msm-826-menupage-279296728d0a\\src\\main\\resources\\com\\logintest\\bills\\salesBills\\bill.pdf");
        document.save(billFile);
        document.close();
    }*/
}
