package com.logintest;

import java.sql.*;

public class ConnectionUtil {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/project";
        String username = "root";
        String password = "root";
        return DriverManager.getConnection(url, username, password);
    }
    public static ResultSet inventoryViewData(String str) throws SQLException {
        Connection conn = getConnection();
        Statement stm = conn.createStatement();
        String str1 = "SELECT * FROM items WHERE itemname like '%"+str+"%'";
        return stm.executeQuery(str1);
    }
    public static int getidForInventoryAdd() throws SQLException {
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery("SELECT MAX(id) from items");
        if(rst.next()) {
            return rst.getInt("MAX(id)")+1;
        }
        else {
            return 1;
        }
    }
    public static void inventoryAddItemWithoutBarcode(int idno, String nm, String qnty, int pp, int sp, int tax, int mrp, int stk) throws SQLException {
        String add = "insert into items (id, itemname, qntytype, pprice, sprice, gst, mrp, stock) values ("+idno+", \""+ nm + "\", \""+qnty+"\", "+pp+", "+sp+", "+tax+", " + mrp + ", "+stk+")";
        Statement stm = getConnection().createStatement();
        stm.executeUpdate(add);
    }
    public static void inventoryAddItemWithBarcode(int idno, String nm, String qnty, int pp, int sp, int tax, int stk, int mrp, String brdc) throws SQLException {
        String add = "insert into items (id, itemname, qntytype, pprice, sprice, gst, mrp, stock, barcode) values ("+idno+", \"" + nm + "\", \"" + qnty + "\", " + pp + ", " + sp + ", " + tax +  ", " + mrp + ", " + stk + ", " + brdc + ")";
        Statement stm = getConnection().createStatement();
        stm.executeUpdate(add);
    }
    public static ResultSet inventoryEditdataSearch(String str) throws SQLException {
        Statement stm = getConnection().createStatement();
        String str1 = "SELECT * FROM items WHERE itemname like '%"+str+"%'";
        return stm.executeQuery(str1);
    }
    public static ResultSet inventoryEditDataRetrive(int id) throws SQLException { //when clicking on row of tableView
        Statement stm = getConnection().createStatement();
        String str1 = "SELECT * FROM items WHERE id = " + id;
        return stm.executeQuery(str1);
    }
    public static void inventoryEditDataUpdate(int id, String name, String qnty, int pprice, int sprice, String barcode, int gst, int mrp) throws SQLException {
        String str = "UPDATE items set itemname = '" + name + "', qntytype = '" + qnty + "', pprice = "+ pprice+ ", sprice = " + sprice+ ", barcode = '"+ barcode+ "', gst = " + gst+ ", mrp = " + mrp+ " where id = "+ id;
        Statement stm = getConnection().createStatement();
        stm.executeUpdate(str);
    }
    public static void inventoryEditDataDelete(int id) throws SQLException {
        String str = "DELETE FROM items WHERE id = " + id;
        Statement stm = getConnection().createStatement();
        stm.executeUpdate(str);
    }
    public static int getidForLedgerAdd() throws SQLException {
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery("SELECT MAX(id) from ledger");
        if(rst.next()) {
            return rst.getInt("MAX(id)")+1;
        }
        else {
            return 1;
        }
    }
    public static void ledgerAddinsertData(int id, String name, String type, int amount, String address, String phno) throws SQLException {
        String str = "INSERT INTO ledger values (" + id + ", '" + name + "', '" + type + "', " + amount + ", '"+ phno+ "', '" + address + "')";
        Statement stm = getConnection().createStatement();
        stm.executeUpdate(str);
    }
    public static ResultSet ledgerEditDataRetrieve(int id) throws SQLException {
        Statement stm = getConnection().createStatement();
        String str1 = "SELECT * FROM ledger WHERE id = " + id;
        ResultSet rstset = stm.executeQuery(str1);
        return rstset;
    }
    public static ResultSet ledgerEditDataSearch(String str) throws SQLException {
        Statement stm = getConnection().createStatement();
        String str1 = "SELECT * FROM ledger WHERE ledgername like '%"+str+"%'";
        ResultSet rstset = stm.executeQuery(str1);
        return rstset;
    }
    public static void ledgerEditDataDelete(int id) throws SQLException {
        String str = "DELETE FROM ledger WHERE id = " + id;
        Statement stm = getConnection().createStatement();
        stm.executeUpdate(str);
    }
    public static void ledgerEditDataUpdate(int id, String name, String type, String amount, String phone, String add1) throws SQLException {
        String str = "UPDATE ledger set ledgername = '" + name + "', type = '" + type + "', amount = '" + amount + "', phone = '"+ phone+ "', address = '" + add1+ "' where id = "+ id;
        Statement stm = getConnection().createStatement();
        stm.executeUpdate(str);
    }
    public static void ledgerFundTransferSubmit(int id, int from, int to, int amt) throws SQLException {
        int fbal = ledgerFundTransferBalance(from);
        fbal-=amt;
        int tbal = ledgerFundTransferBalance(to);
        tbal+=amt;
        String str = "INSERT INTO ftransfer VALUES ("+id+", "+from+", "+to+", "+amt+", "+fbal+", "+tbal+", current_timestamp())";
        Statement stm = getConnection().createStatement();
        stm.executeUpdate(str);
    }
    public static int ledgerFundTransferGetid() throws SQLException {
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery("SELECT MAX(id) from ftransfer");
        if(rst.next()) {
            return rst.getInt("MAX(id)")+1;
        }
        else {
            return 1;
        }
    }
    public static ResultSet ledgerFundTransferSearch(String st) throws SQLException {
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery("SELECT ledgername FROM ledger WHERE ledgername like'%"+st+"%'");
        return rst;
    }
    public static int ledgerFundTransferGetLedgerNameId(String name) throws SQLException {
        String str = "SELECT id from ledger where ledgername = '"+name+"'";
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery(str);
        if (rst.next())
            return rst.getInt("id");
        else
            return 0;
    }
    public static void ledgerFundTransferSubMoney(int id, int amt) throws SQLException {
        String str = "UPDATE ledger set amount = amount-"+amt+" where id = "+id;
        Statement stm = getConnection().createStatement();
        stm.executeUpdate(str);
    }
    public static void ledgerFundTransferAddMoney(int id, int amt) throws SQLException {
        String str = "UPDATE ledger set amount = amount+"+amt+" where id = "+id;
        Statement stm = getConnection().createStatement();
        stm.executeUpdate(str);
    }
    public static int ledgerFundTransferBalance(int id) throws SQLException {
        String str = "SELECT amount from ledger where id = "+id;
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery(str);
        if (rst.next())
            return rst.getInt("amount");
        else
            return 0;
    }
    public static ResultSet stockAddRemoveItemSearch(String st) throws SQLException {
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery("SELECT itemname FROM items WHERE itemname like'%"+st+"%'");
        return rst;
    }
    public static void stockAddSubmit(String nm, String amt) throws SQLException {
        String str = "UPDATE items SET stock = stock + "+amt+" WHERE itemname = '"+nm+"'";
        Statement stm = getConnection().createStatement();
        stm.executeUpdate(str);
    }
    public static void stockRemoveSubmit(String nm, String amt) throws SQLException {
        String str = "UPDATE items SET stock = stock - "+amt+" WHERE itemname = '"+nm+"'";
        Statement stm = getConnection().createStatement();
        stm.executeUpdate(str);
    }
    public static ResultSet ledgerAddBalanceData(String str) throws SQLException {
        Statement stm = getConnection().createStatement();
        String str1 = "SELECT * FROM ledger WHERE ledgername like '%"+str+"%'";
        ResultSet rstset = stm.executeQuery(str1);
        return rstset;
    }
    public static void ledgerAddBalanceAdd(int id, int amount) throws SQLException {
        Statement stm = getConnection().createStatement();
        String str = "UPDATE ledger SET amount = amount + "+amount+" where id = "+id;
        stm.executeUpdate(str);
    }
    public static int ledgerAddBalanceGetLedgerid(String name) throws SQLException {
        String str = "SELECT id from ledger where ledgername = '"+name+"'";
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery(str);
        if (rst.next())
            return rst.getInt("id");
        else
            return 0;
    }
    public static int purchasePaymentGetid() throws SQLException {
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery("SELECT MAX(id) from payment");
        if(rst.next()) {
            return rst.getInt("MAX(id)")+1;
        }
        else {
            return 1;
        }
    }
    public static ResultSet purchasePaymentLedgerSearch(String st) throws SQLException {
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery("SELECT ledgername FROM ledger WHERE ledgername like'%"+st+"%'");
        return rst;
    }
    public static int purchasePaymentGetLedgerid(String name) throws SQLException {
        String str = "SELECT id from ledger where ledgername = '"+name+"'";
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery(str);
        if (rst.next())
            return rst.getInt("id");
        else
            return 0;
    }
    public static void purchasePaymentSubmit(int id, int lid, int amt) throws SQLException {
        int cbal = purchasePaymentBalance(lid);
        cbal += amt;
        String str = "INSERT INTO payment VALUES("+id+", "+lid+", "+amt+", "+cbal+", current_timestamp())";
        Statement stm = getConnection().createStatement();
        stm.executeUpdate(str);
    }
    public static int purchasePaymentBalance(int id) throws SQLException {
        String str = "SELECT amount from ledger where id = "+id;
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery(str);
        if (rst.next())
            return rst.getInt("amount");
        else
            return 0;
    }
    public static void purchasePaymentAddMoney(int id, int amt) throws SQLException {
        String str = "UPDATE ledger set amount = amount+"+amt+" where id = "+id;
        Statement stm = getConnection().createStatement();
        stm.executeUpdate(str);
    }
    public static ResultSet purchasePaymentGetDetails(int id) throws SQLException {
        String str = "SELECT * from payment where id = "+id;
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery(str);
        return rst;
    }
    public static String purchasePaymentGetLedgerName(int id) throws SQLException {
        String query = "select * from ledger where id = " + id + "";
        Statement stm = getConnection().createStatement();
        ResultSet rslt = stm.executeQuery(query);
        if(rslt.next())
            return rslt.getString("ledgername");
        else
            return null;
    }
    public static int salesRecieptGetid() throws SQLException {
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery("SELECT MAX(id) from receipt");
        if(rst.next()) {
            return rst.getInt("MAX(id)")+1;
        }
        else {
            return 1;
        }
    }
    public static ResultSet salesRecieptLedgerSearch(String st) throws SQLException {
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery("SELECT ledgername FROM ledger WHERE ledgername like'%"+st+"%'");
        return rst;
    }
    public static int salesRecieptGetLedgerid(String name) throws SQLException {
        String str = "SELECT id from ledger where ledgername = '"+name+"'";
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery(str);
        if (rst.next())
            return rst.getInt("id");
        else
            return 0;
    }
    public static void salesRecieptSubmit(int id, int lid, int amt) throws SQLException {
        int cbal = salesRecieptetBalance(lid);
        cbal -= amt;
        String str = "INSERT INTO receipt VALUES("+id+", "+lid+", "+amt+", "+cbal+", current_timestamp())";
        Statement stm = getConnection().createStatement();
        stm.executeUpdate(str);
    }
    public static int salesRecieptetBalance(int id) throws SQLException {
        String str = "SELECT amount from ledger where id = "+id;
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery(str);
        if (rst.next())
            return rst.getInt("amount");
        else
            return 0;
    }
    public static void salesRecieptSubMoney(int id, int amt) throws SQLException {
        String str = "UPDATE ledger set amount = amount-"+amt+" where id = "+id;
        Statement stm = getConnection().createStatement();
        stm.executeUpdate(str);
    }
    public static ResultSet salesRecietGetDetails(int id) throws SQLException {
        String str = "SELECT * from receipt where id = "+id;
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery(str);
        return rst;
    }
    public static String salesRecieptGetLedgerName(int id) throws SQLException {
        String query = "select * from ledger where id = " + id + "";
        Statement stm = getConnection().createStatement();
        ResultSet rslt = stm.executeQuery(query);
        if(rslt.next())
            return rslt.getString("ledgername");
        else
            return null;
    }
    public static int[] salesBillinVals(String item) throws SQLException {
        String query = "select * from items where itemname = '" + item + "'";
        Statement stm = getConnection().createStatement();
        ResultSet rslt = stm.executeQuery(query);
        rslt.next();
        int[] str = new int[3];
        str[0] = rslt.getInt("sprice");
        str[1] = rslt.getInt("id");
        return str;
    }
    public static int salesBillingGetid() throws SQLException {
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery("SELECT MAX(id) from sales");
        if(rst.next()) {
            return rst.getInt("MAX(id)")+1;
        }
        else {
            return 1;
        }
    }
    public static ResultSet salesBillingItemSearch(String st) throws SQLException {
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery("SELECT itemname FROM items WHERE itemname like'%"+st+"%'");
        return rst;
    }
    public static ResultSet salesBillinLedgerSearch(String st) throws SQLException {
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery("SELECT ledgername FROM ledger WHERE ledgername like'%"+st+"%'");
        return rst;
    }
    public static int salesBillingLedBal(String st) throws SQLException {
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery("SELECT amount FROM ledger WHERE ledgername = '"+st+"'");
        if (rst.next())
            return rst.getInt("amount");
        return 0;
    }
    public static void salesBillingSubmitted(String lname, String id, String lid, String disc, String tot, String rec) throws SQLException {
        int upd = salesBillingLedBal(lname);
        upd =upd+Integer.parseInt(tot)-Integer.parseInt(rec);
        String str = "INSERT INTO sales VALUES ("+id+", "+lid+", "+disc+", "+tot+", "+rec+", "+upd+", current_timestamp())";
        Statement stm = getConnection().createStatement();
        stm.executeUpdate(str);
    }
    public static void salesBillingSdetails(String sid, String iid, String qnty, String rate) throws SQLException {
        String str = "INSERT INTO saledetails VALUES("+sid+", "+iid+", "+qnty+", "+rate+")";
        Statement stm = getConnection().createStatement();
        stm.executeUpdate(str);
    }
    public static int salesBillingItemId(String name) throws SQLException {
        String str = "SELECT id from items where itemname = '"+name+"'";
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery(str);
        if (rst.next())
            return rst.getInt("id");
        else
            return 0;
    }
    public static void salesBillingAddBalance(String id, int amount) throws SQLException {
        Statement stm = getConnection().createStatement();
        String str = "UPDATE ledger SET amount = amount + "+amount+" where id = "+id;
        stm.executeUpdate(str);
    }
    public static int salesBillingLedgerId(String name) throws SQLException {
        String str = "SELECT id from ledger where ledgername = '"+name+"'";
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery(str);
        if (rst.next())
            return rst.getInt("id");
        else
            return 0;
    }
    public static ResultSet salesBilingSaleDetail(int id) throws SQLException {
        String str = "SELECT * from sales where id = "+id;
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery(str);
        return rst;
    }
    public static ResultSet salesBillingSaleUpdtedDetail(int id) throws SQLException {
        String str = "SELECT * from saledetails where saleid = "+id;
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery(str);
        return rst;
    }
    public static String salesBillinLedgerName(int id) throws SQLException {
        String query = "select * from ledger where id = " + id + "";
        Statement stm = getConnection().createStatement();
        ResultSet rslt = stm.executeQuery(query);
        if(rslt.next())
            return rslt.getString("ledgername");
        else
            return null;
    }
    public static String salesBillingItemName(int id) throws SQLException {
        String query = "select * from items where id = " + id + "";
        Statement stm = getConnection().createStatement();
        ResultSet rslt = stm.executeQuery(query);
        if(rslt.next())
            return rslt.getString("itemname");
        else
            return null;
    }
    public static int purchaseBillingGetid() throws SQLException {
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery("SELECT MAX(id) from purchase");
        if(rst.next()) {
            return rst.getInt("MAX(id)")+1;
        }
        else {
            return 1;
        }
    }
    public static int[] purchaseBillingVals(String item) throws SQLException {
        String query = "select * from items where itemname = '" + item + "'";
        Statement stm = getConnection().createStatement();
        ResultSet rslt = stm.executeQuery(query);
        rslt.next();
        int[] str = new int[3];
        str[0] = rslt.getInt("pprice");
        str[1] = rslt.getInt("id");
        str[2] = rslt.getInt("sprice");
        return str;
    }

    public static ResultSet purchaseBillingItemSearch(String st) throws SQLException {
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery("SELECT itemname FROM items WHERE itemname like'%"+st+"%'");
        return rst;
    }

    public static ResultSet purchaseBillingLedgerSearch(String st) throws SQLException {
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery("SELECT ledgername FROM ledger WHERE ledgername like'%"+st+"%'");
        return rst;
    }

    public static int purchaseBillinLedBal(String st) throws SQLException {
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery("SELECT amount FROM ledger WHERE ledgername = '"+st+"'");
        if (rst.next())
            return rst.getInt("amount");
        return 0;
    }

    public static int purchaseBillingLedgerid(String name) throws SQLException {
        String str = "SELECT id from ledger where ledgername = '"+name+"'";
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery(str);
        if (rst.next())
            return rst.getInt("id");
        else
            return 0;
    }

    public static void purchaseBillingSubmitted(String lname, String id, String lid, String disc, String tot, String rec) throws SQLException {
        int upd = purchaseBillinLedBal(lname);
        upd =upd-Integer.parseInt(tot)+Integer.parseInt(rec);
        String str = "INSERT INTO purchase VALUES ("+id+", "+lid+", "+disc+", "+tot+", "+rec+", "+upd+", current_timestamp())";
        Statement stm = getConnection().createStatement();
        stm.executeUpdate(str);
    }

    public static int purchaseBillingItemid(String name) throws SQLException {
        String str = "SELECT id from items where itemname = '"+name+"'";
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery(str);
        if (rst.next())
            return rst.getInt("id");
        else
            return 0;
    }

    public static void purchaseBillingSdetails(String pid, String iid, String qnty, String rate, String sprice) throws SQLException {
        String str = "INSERT INTO purchasedetails VALUES("+pid+", "+iid+", "+qnty+", "+rate+", "+sprice+")";
        Statement stm = getConnection().createStatement();
        stm.executeUpdate(str);
    }

    public static void purchaseBillingSubBalance(String id, int amount) throws SQLException {
        Statement stm = getConnection().createStatement();
        String str = "UPDATE ledger SET amount = amount - "+amount+" where id = "+id;
        stm.executeUpdate(str);
    }

    public static void purchaseBillingChangePrice(String id, String pprice, String sprice) throws SQLException {
        Statement stm = getConnection().createStatement();
        String str = "UPDATE items SET sprice = "+sprice+", pprice = "+pprice+" where id = "+id;
        stm.executeUpdate(str);
    }

    public static ResultSet purchaseBillingPurchaseDetail(int id) throws SQLException {
        String str = "SELECT * from purchase where id = "+id;
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery(str);
        return rst;
    }

    public static ResultSet purchaseBillingPurchaseUpdtedDetail(int id) throws SQLException {
        String str = "SELECT * from purchasedetails where prid = "+id;
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery(str);
        return rst;
    }

    public static String purchaseBillingLedgerName(int id) throws SQLException {
        String query = "select * from ledger where id = " + id + "";
        Statement stm = getConnection().createStatement();
        ResultSet rslt = stm.executeQuery(query);
        if(rslt.next())
            return rslt.getString("ledgername");
        else
            return null;
    }

    public static String purchaseBillingItemName(int id) throws SQLException {
        String query = "select * from items where id = " + id + "";
        Statement stm = getConnection().createStatement();
        ResultSet rslt = stm.executeQuery(query);
        if(rslt.next())
            return rslt.getString("itemname");
        else
            return null;
    }

    public static int getSalesTotal(String from, String to) throws SQLException {
        String str = "select SUM(total) from sales where sldate between DATE_SUB('"+from+"', INTERVAL 1 DAY) AND DATE_ADD('"+to+"', INTERVAL 1 DAY);";
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery(str);
        if(rst.next())
            return rst.getInt("SUM(total)");
        else
            return 0;
    }

    public static int getPurchaseTotal(String from, String to) throws SQLException {
        String str = "select SUM(total) from purchase where prdate between DATE_SUB('"+from+"', INTERVAL 1 DAY) AND DATE_ADD('"+to+"', INTERVAL 1 DAY);";
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery(str);
        if(rst.next())
            return rst.getInt("SUM(total)");
        else
            return 0;
    }

    public static int cashid() throws SQLException {
        String str = "SELECT id from ledger where ledgername = 'CASH ACCOUNT'";
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery(str);
        if (rst.next())
            return rst.getInt("id");
        else
            return 0;
    }

    public static int cashSaleTotal(String from, String to) throws SQLException {
        String str = "select SUM(total) from sales where sldate between DATE_SUB('"+from+"', INTERVAL 1 DAY) AND DATE_ADD('"+to+"', INTERVAL 1 DAY) AND ledgerid = "+cashid();
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery(str);
        if(rst.next())
            return rst.getInt("SUM(total)");
        else
            return 0;
    }

    public static int cashPurchaseTotal(String from, String to) throws SQLException {
        String str = "select SUM(total) from purchase where prdate between DATE_SUB('"+from+"', INTERVAL 1 DAY) AND DATE_ADD('"+to+"', INTERVAL 1 DAY) AND ledgerid = "+cashid();
        Statement stm = getConnection().createStatement();
        ResultSet rst = stm.executeQuery(str);
        if(rst.next())
            return rst.getInt("SUM(total)");
        else
            return 0;
    }
}