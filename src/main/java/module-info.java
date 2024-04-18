module com.manager.smc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.calendarfx.view;

    opens admin to javafx.fxml;
    exports admin;
    exports admin.ui;
    opens admin.ui to javafx.fxml;
    exports student;
    opens student to javafx.fxml;
}