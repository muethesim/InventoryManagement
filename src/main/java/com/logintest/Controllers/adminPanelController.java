package com.logintest.Controllers;

import com.logintest.ConnectionUtil;
import com.logintest.Main;
import com.logintest.MenuUtil;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class adminPanelController implements Initializable {
    @FXML
    MFXPasswordField adminPassword;
    @FXML
    AnchorPane mainAmchorPane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(MenuUtil.class.getResource("adminViews/addUser.fxml")));
            mainAmchorPane.getChildren().set(2, pane);
            AnchorPane.setTopAnchor(mainAmchorPane.getChildren().get(2), 42.0);
            AnchorPane.setRightAnchor(mainAmchorPane.getChildren().get(2), 0.0);
            AnchorPane.setBottomAnchor(mainAmchorPane.getChildren().get(2), 0.0);
            AnchorPane.setLeftAnchor(mainAmchorPane.getChildren().get(2), 235.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void adminLogin(ActionEvent actionEvent) throws IOException {
        boolean status = false;
        try {
            Connection conn = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM accounts WHERE username = ? AND password = ?";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, "admin");
            pst.setString(2, adminPassword.getText());

            ResultSet rs = pst.executeQuery();
            status = rs.next();

            rs.close();
            pst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!status) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong Password");
            alert.setHeaderText("The password you entered is incorrect.");
            alert.show();
        } else {
            FXMLLoader loader = new FXMLLoader(adminPanelController.class.getResource("/com/logintest/adminPanelView.fxml"));
            Parent root = loader.load();
            ((Node)actionEvent.getSource()).getScene().setRoot(root);
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

    @FXML
    private void handleAdminMenu(ActionEvent event) {
        if (((Node)event.getSource()).getId().equals("addUser")) {
            try {
                AnchorPane hostPane = (AnchorPane) ((Node) event.getSource()).getParent().getParent();
                AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(MenuUtil.class.getResource("adminViews/addUser.fxml")));
                hostPane.getChildren().set(2, pane);
                AnchorPane.setTopAnchor(hostPane.getChildren().get(2), 42.0);
                AnchorPane.setRightAnchor(hostPane.getChildren().get(2), 0.0);
                AnchorPane.setBottomAnchor(hostPane.getChildren().get(2), 0.0);
                AnchorPane.setLeftAnchor(hostPane.getChildren().get(2), 235.0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (((Node)event.getSource()).getId().equals("removeEditUser")) {
            try {
                AnchorPane hostPane = (AnchorPane) ((Node) event.getSource()).getParent().getParent();
                AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(MenuUtil.class.getResource("adminViews/viewEditUser.fxml")));
                hostPane.getChildren().set(2, pane);
                AnchorPane.setTopAnchor(hostPane.getChildren().get(2), 42.0);
                AnchorPane.setRightAnchor(hostPane.getChildren().get(2), 0.0);
                AnchorPane.setBottomAnchor(hostPane.getChildren().get(2), 0.0);
                AnchorPane.setLeftAnchor(hostPane.getChildren().get(2), 235.0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (((Node)event.getSource()).getId().equals("backupRestoreUser")) {
            try {
                AnchorPane hostPane = (AnchorPane) ((Node) event.getSource()).getParent().getParent();
                AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(MenuUtil.class.getResource("adminViews/backupRestoreUser.fxml")));
                hostPane.getChildren().set(2, pane);
                AnchorPane.setTopAnchor(hostPane.getChildren().get(2), 42.0);
                AnchorPane.setRightAnchor(hostPane.getChildren().get(2), 0.0);
                AnchorPane.setBottomAnchor(hostPane.getChildren().get(2), 0.0);
                AnchorPane.setLeftAnchor(hostPane.getChildren().get(2), 235.0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (((Node)event.getSource()).getId().equals("loginLog")) {
            try {
                AnchorPane hostPane = (AnchorPane) ((Node) event.getSource()).getParent().getParent();
                AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(MenuUtil.class.getResource("adminViews/loginLog.fxml")));
                hostPane.getChildren().set(2, pane);
                AnchorPane.setTopAnchor(hostPane.getChildren().get(2), 42.0);
                AnchorPane.setRightAnchor(hostPane.getChildren().get(2), 0.0);
                AnchorPane.setBottomAnchor(hostPane.getChildren().get(2), 0.0);
                AnchorPane.setLeftAnchor(hostPane.getChildren().get(2), 235.0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
