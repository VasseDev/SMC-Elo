module com.manager.smc {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.core;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;

    requires org.kordamp.bootstrapfx.core;
    requires com.calendarfx.view;
    requires jdk.incubator.vector;

    opens login.ui to javafx.fxml;

    opens admin to javafx.fxml;
    exports admin;
    exports admin.ui;
    opens admin.ui to javafx.fxml;
    exports student;
    opens student to javafx.fxml;
    exports main;
    opens main to javafx.fxml;
}