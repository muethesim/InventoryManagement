package com.logintest.Controllers.AdminControllers;
import com.logintest.*;

import com.logintest.ConnectionUtil;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class viewEditUserController implements Initializable {
    @FXML
    private TableView<ObservableList<String>> accountsTable;
    @FXML
    private TableColumn<ObservableList<String>, Integer> idColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> usernameColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> passwordColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> fnameColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> lnameColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> phoneNoColumn;
    @FXML
    private TableColumn<ObservableList<String>, Integer> accessLevelColumn;
    @FXML
    MFXTextField usernameField;
    @FXML
    MFXTextField passwordField;
    @FXML
    MFXTextField fnameField;
    @FXML
    MFXTextField lnameField;
    @FXML
    MFXTextField phoneNo;
    @FXML
    MFXTextField accessLevelField;
    @FXML
    private Label alertLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(Integer.parseInt(cellData.getValue().get(0))));
        usernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
        passwordColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));
        fnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));
        lnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));
        phoneNoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(5)));
        accessLevelColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(Integer.parseInt(cellData.getValue().get(6))));

        String sql = "select id, username, password, first_name, last_name, phone_no, access_level from accounts";

        try {
            Connection conn = ConnectionUtil.getConnection();
            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(sql);
            while (rst.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(String.valueOf(rst.getInt("id")));
                row.add(rst.getString("username"));
                row.add(rst.getString("password"));
                row.add(rst.getString("first_name"));
                row.add(rst.getString("last_name"));
                row.add(rst.getString("phone_no"));
                row.add(String.valueOf(rst.getInt("access_level")));

                accountsTable.getItems().add(row);
            }
            st.close();
            rst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void removeAction() {
        if(!accountsTable.getSelectionModel().isEmpty()) {
            alertLabel.setText("");
            ObservableList<String> selectedItem = accountsTable.getSelectionModel().getSelectedItem();
            String sql = "delete from accounts where id = ?";
            try {
                Connection conn = ConnectionUtil.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, selectedItem.get(0));
                pst.executeUpdate();

                pst.close();
                conn.close();

                ObservableList<ObservableList<String>> currentList = accountsTable.getItems();
                currentList.clear();
                initialize(null, null);

                alertLabel.setText("User Removed Successfully");
                alertLabel.setTextFill(Color.GREEN);
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            alertLabel.setText("Select a Row first !!!");
            alertLabel.setTextFill(Color.TOMATO);
        }
    }
    public void clearFields() {
        usernameField.clear();
        passwordField.clear();
        fnameField.clear();
        lnameField.clear();
        phoneNo.clear();
        accessLevelField.clear();
    }
    @FXML
    private void fillData() {
        if(!accountsTable.getSelectionModel().isEmpty()) {
            ObservableList<String> selectedItem = accountsTable.getSelectionModel().getSelectedItem();
            usernameField.setText(selectedItem.get(1));
            passwordField.setText(selectedItem.get(2));
            fnameField.setText(selectedItem.get(3));
            lnameField.setText(selectedItem.get(4));
            phoneNo.setText(selectedItem.get(5));
            accessLevelField.setText(selectedItem.get(6));
        }
    }
    @FXML
    private void editAction() throws IOException {
        if(!accountsTable.getSelectionModel().isEmpty()) {
            alertLabel.setText("");
            String sql = "update accounts set username = ?, password = ?, first_name = ?, last_name = ?, " +
                    "phone_no = ?, access_level = ? where id = ?";
            ObservableList<String> selectedItem = accountsTable.getSelectionModel().getSelectedItem();

            try {
                Connection conn = ConnectionUtil.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, usernameField.getText());
                pst.setString(2, passwordField.getText());
                pst.setString(3, fnameField.getText());
                pst.setString(4, lnameField.getText());
                pst.setString(5, phoneNo.getText());
                pst.setString(6, accessLevelField.getText());
                pst.setString(7, selectedItem.get(0));
                pst.executeUpdate();

                pst.close();
                conn.close();

                ObservableList<ObservableList<String>> currentList = accountsTable.getItems();
                currentList.clear();
                initialize(null, null);

                alertLabel.setText("User Updated Successfully");
                alertLabel.setTextFill(Color.GREEN);
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            alertLabel.setText("Select a Row first !!!");
            alertLabel.setTextFill(Color.TOMATO);
        }
    }
}
