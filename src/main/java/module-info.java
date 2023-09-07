module com.logintest {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    requires MaterialFX;
    requires java.sql;
    requires org.apache.pdfbox;


    opens com.logintest to javafx.fxml;
    exports com.logintest;
    exports com.logintest.Controllers;
    opens com.logintest.Controllers to javafx.fxml;
    exports com.logintest.Controllers.AdminControllers;
    opens com.logintest.Controllers.AdminControllers to javafx.fxml;
}