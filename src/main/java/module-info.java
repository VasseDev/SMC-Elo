module com.manager.smc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.calendarfx.view;

    opens com.manager.smc to javafx.fxml;
    exports com.manager.smc;
}