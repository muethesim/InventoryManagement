package com.logintest;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class MenuUtil {
    public static String loggedUser; //for globall use
    public static  int accessLevel;
    public static void changeColor(MFXButton button1, MFXButton button2, MFXButton button3, MFXButton button4,
                                   MFXButton button5, MFXButton button6, MFXButton button7, MFXButton button8) {
        button1.setStyle("-fx-text-fill:  #3aca89;");
        button2.setStyle("-fx-text-fill:  #7e8899;");
        button3.setStyle("-fx-text-fill:  #7e8899;");
        button4.setStyle("-fx-text-fill:  #7e8899;");
        button5.setStyle("-fx-text-fill:  #7e8899;");
        button6.setStyle("-fx-text-fill:  #7e8899;");
        button7.setStyle("-fx-text-fill:  #7e8899;");
        button8.setStyle("-fx-text-fill:  #7e8899;");
    }

    public static void loadPage(String name, AnchorPane hostPane) {
        try {
            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(MenuUtil.class.getResource("menuViews/" + name)));
            hostPane.getChildren().set(3, pane);
            AnchorPane.setTopAnchor(hostPane.getChildren().get(3), 42.0);
            AnchorPane.setRightAnchor(hostPane.getChildren().get(3), 0.0);
            AnchorPane.setBottomAnchor(hostPane.getChildren().get(3), 0.0);
            AnchorPane.setLeftAnchor(hostPane.getChildren().get(3), 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadMenu(String fileName, String userName) throws IOException {
        loggedUser = userName;

        Stage menuStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MenuUtil.class.getResource(fileName));
        Parent root =  fxmlLoader.load();

        Scene scene = new Scene(root);

        scene.getStylesheets().add(Objects.requireNonNull(MenuUtil.class.getResource("style.css")).toExternalForm());
        menuStage.initStyle(StageStyle.TRANSPARENT); //undecorated window
        menuStage.setScene(scene);

        menuStage.sizeToScene();    //minimum window size restriction
        menuStage.setMinWidth(1280.0);
        menuStage.setMinHeight(720.0);

        menuStage.centerOnScreen();
        menuStage.show();

        ResizeUtil.addResizeListener(menuStage); //utility class for resizing undecorated window
    }

    public static void handleLogout(MouseEvent event, String filename) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        Stage loginStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MenuUtil.class.getResource(filename));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(MenuUtil.class.getResource("loginPage.css")).toExternalForm());

        loginStage.initStyle(StageStyle.TRANSPARENT);
        loginStage.setScene(scene);
        loginStage.centerOnScreen();
        loginStage.show();
    }
}