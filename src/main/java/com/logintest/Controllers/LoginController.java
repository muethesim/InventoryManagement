package com.logintest.Controllers;

import com.logintest.AutoCompletionUtil;
import com.logintest.ConnectionUtil;
import com.logintest.MenuUtil;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    MFXTextField username;
    @FXML
    MFXPasswordField password;
    @FXML
    Label alertLabel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> suggestions = new ArrayList<>();
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM accounts");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                suggestions.add(rs.getString("username"));
            }
            rs.close();
            pst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        AutoCompletionUtil.setUpAutoCompletion(username, suggestions);
    }
    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        if(username.getText().isEmpty() || username.getText().isEmpty()) {
            setAlert(Color.TOMATO, "Empty Credentials");
            return;
        }
        boolean status = false;
        try {
            Connection conn = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM accounts WHERE username = ? AND password = ?";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, username.getText());
            pst.setString(2, password.getText());

            ResultSet rs = pst.executeQuery();
            status = rs.next();
            MenuUtil.accessLevel = rs.getInt("access_level");

            rs.close();
            pst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!status) {
            setAlert(Color.TOMATO, "Enter Correct Username/Password");
            username.clear();
            password.clear();
        } else {
            setAlert(Color.GREEN, "Login Successful");
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
            try {
                Connection conn = ConnectionUtil.getConnection();
                String sql = "insert into login_log (time, date, user) values (?, ?, ?)";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                pst.setDate(2, Date.valueOf(LocalDateTime.now().toLocalDate()));
                pst.setString(3, username.getText());
                pst.executeUpdate();
                pst.close();
                conn.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            MenuUtil.loadMenu("mainMenu.fxml", username.getText());
        }

    }
    @FXML
    private void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    private void minimize(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML//alert label - wrong username/password
    protected void setAlert(Color color, String text) {
        alertLabel.setTextFill(color);
        alertLabel.setText(text);
    }
    @FXML
    private  void handleAdminLogin(ActionEvent actionEvent) throws IOException {
        Stage loginWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Stage adminPanelWindow = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MenuUtil.class.getResource("adminPanelLoginView.fxml"));
        Parent root =  fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(MenuUtil.class.getResource("admin.css")).toExternalForm());
        adminPanelWindow.initStyle(StageStyle.TRANSPARENT);
        adminPanelWindow.setScene(scene);
        adminPanelWindow.sizeToScene();
        adminPanelWindow.centerOnScreen();
        adminPanelWindow.setOnShowing(event -> loginWindow.hide());
        adminPanelWindow.setOnHiding(event -> loginWindow.show());
        adminPanelWindow.show();
    }
    @FXML
    private void handleCredits(ActionEvent actionEvent) throws IOException {
        Stage loginWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Stage creditsWindow = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MenuUtil.class.getResource("creditsWindow.fxml"));
        Parent root =  fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(MenuUtil.class.getResource("loginSettings.css")).toExternalForm());
        creditsWindow.initStyle(StageStyle.TRANSPARENT);
        creditsWindow.setScene(scene);
        creditsWindow.sizeToScene();
        creditsWindow.centerOnScreen();
        creditsWindow.setOnShowing(event -> loginWindow.hide());
        creditsWindow.setOnHiding(event -> loginWindow.show());
        creditsWindow.show();
    }
}