package com.logintest.Controllers.AdminControllers;

import com.logintest.ConnectionUtil;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class addUserController implements Initializable {
    @FXML
    private ChoiceBox<String> accessLevelChoiceBox;
    @FXML
    private MFXTextField usernameField;
    @FXML
    private MFXTextField passwordField;
    @FXML
    private MFXTextField fnameField;
    @FXML
    private MFXTextField lnameField;
    @FXML
    private MFXTextField phoneNo;
    @FXML
    Label alertLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accessLevelChoiceBox.getItems().add("2");
        accessLevelChoiceBox.getItems().add("3");
    }

    @FXML
    private void submitAction() {
        String sql = "insert into accounts(username, password, first_name, last_name, phone_no, access_level) values(?, ?, ?, ?, ?, ?)";
        if(!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty() && !fnameField.getText().isEmpty()
                && !lnameField.getText().isEmpty() && !phoneNo.getText().isEmpty() && accessLevelChoiceBox.getValue() != null) {
            try {
                Connection conn = ConnectionUtil.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, usernameField.getText());
                pst.setString(2, passwordField.getText());
                pst.setString(3, fnameField.getText());
                pst.setString(4, lnameField.getText());
                pst.setString(5, phoneNo.getText());
                pst.setInt(6, Integer.parseInt(accessLevelChoiceBox.getSelectionModel().getSelectedItem()));
                pst.executeUpdate();

                pst.close();
                conn.close();
                alertLabel.setText("User Created Successfully");
                alertLabel.setTextFill(Color.GREEN);
                clearAction();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            alertLabel.setText("Fill all the fields !!!");
            alertLabel.setTextFill(Color.TOMATO);
        }
    }
    @FXML
    private void clearAction() {
        usernameField.clear();
        passwordField.clear();
        fnameField.clear();
        lnameField.clear();
        phoneNo.clear();
        accessLevelChoiceBox.getSelectionModel().clearSelection();
    }
}
