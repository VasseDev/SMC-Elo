module student.managment.system {
    // 'requires' directives
    requires com.calendarfx.view;
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.incubator.vector;
    requires org.kordamp.bootstrapfx.core;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    requires org.mongodb.driver.sync.client;

    // 'opens' directives
    opens admin to javafx.fxml;
    opens admin.ui to javafx.fxml;
    opens login.ui to javafx.fxml;
    opens main to javafx.fxml;
    opens student to javafx.fxml;
    opens student.ui to javafx.fxml;

    // 'exports' directives
    exports admin;
    exports admin.ui;
    exports main;
    exports student;
    exports student.ui to javafx.fxml;
}