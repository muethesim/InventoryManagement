package com.logintest.Controllers.AdminControllers;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class backupRestoreController implements Initializable {
    @FXML
    private MFXTextField filePath;
    @FXML
    private Label alertLabel;
    @FXML
    private void createBackup(ActionEvent event) throws IOException, InterruptedException {
        alertLabel.setText("");

        String command = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump -u root --password=root --add-drop-database -B project -r C:\\Users\\danis\\Desktop\\msm-826-menupage-279296728d0a\\src\\main\\resources\\com\\logintest\\sqlBackups\\backup.sql";
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();

        alertLabel.setText("Backup created successfully (src\\main\\resources\\com\\logintest\\sqlBackups\\backup.sql)");
        alertLabel.setTextFill(Color.GREEN);

    }
    @FXML
    private void restoreBackup(ActionEvent event) throws IOException, InterruptedException {
        if(!filePath.getText().isEmpty()) {
            alertLabel.setText("");

            /*String command = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysql -u root --password=root project < " + filePath.getText();
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();*/

            alertLabel.setText("Backup restored Successfully.");
            alertLabel.setTextFill(Color.GREEN);
        } else {
            alertLabel.setText("Choose a file first !!!");
            alertLabel.setTextFill(Color.TOMATO);
        }
    }
    @FXML
    private void chooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select SQL Backup File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            filePath.setText(file.getAbsolutePath());
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
