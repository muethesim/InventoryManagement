package com.logintest.Controllers.AdminControllers;

import com.logintest.ConnectionUtil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class loginLogController implements Initializable {
    @FXML
    private TableView<ObservableList<String>> loginLogTable;
    @FXML
    private TableColumn<ObservableList<String>, Integer> idColumn;
    @FXML
    private TableColumn<ObservableList<String>, Timestamp> timeColumn;
    @FXML
    private TableColumn<ObservableList<String>, Date> dateColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> userColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(Integer.parseInt(cellData.getValue().get(0))));
        timeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(Timestamp.valueOf(cellData.getValue().get(1))));
        dateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(Date.valueOf(cellData.getValue().get(2))));
        userColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));

        String sql = "select id, time, date, user from login_log";
        try{
            Connection conn = ConnectionUtil.getConnection();
            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(sql);
            while (rst.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(String.valueOf(rst.getInt("id")));
                row.add(rst.getTimestamp("time").toString());
                row.add(rst.getDate("date").toString());
                row.add(rst.getString("user"));
                loginLogTable.getItems().add(row);
            }
            st.close();
            rst.close();
            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
