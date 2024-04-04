module com.manager.smc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.calendarfx.view;

    opens model to javafx.fxml;
    exports model;
    exports ui;
    opens ui to javafx.fxml;
}