package com.manager.smc;

import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.Entry;
import com.calendarfx.view.EntryViewBase;
import com.calendarfx.view.page.DayPage;
import com.calendarfx.view.page.MonthPage;
import com.calendarfx.view.popover.EntryDetailsView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.IOException;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalTime;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;

import static com.calendarfx.model.CalendarEvent.*;

public class MainApp extends Application {
    private Stage primaryStage;
    private com.manager.smc.MainUiController mainController;
    private boolean dayPage;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Esempio struttura base");

        initialize();

    }

    public static void main(String[] args) {
        launch(args);
    }

    private void initialize() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("mainUI.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();

            mainController=loader.getController();
            //mainController.setMainApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            // Create a new calendar
            Calendar calendar = new Calendar("Verifiche");

            // Create a new calendar source and add the calendar to it
            CalendarSource calendarSource = new CalendarSource("My Calendar Source");
            calendarSource.getCalendars().add(calendar);


            // Create a new calendar view and add the calendar source to it
            CalendarView calendarView = new CalendarView();
            calendarView.getCalendarSources().clear();
            calendarView.getCalendarSources().add(calendarSource);
            calendarView.setRequestedTime(LocalTime.now());

            // Set the default view to the month page
            calendarView.showMonthPage();

            // Disable navigation buttons and today button
            calendarView.setShowAddCalendarButton(false);
            calendarView.setShowPageToolBarControls(false);
            calendarView.setShowPrintButton(false);
            calendarView.setShowSourceTrayButton(false);

            EventHandler<CalendarEvent> handler = e -> {
                System.out.println("Event handler called");
                System.out.println("Event target: " + e.getTarget());
                if (e.getTarget() instanceof EntryViewBase) {
                    EntryViewBase entryViewBase = (EntryViewBase) e.getTarget();
                    Entry entry = entryViewBase.getEntry();
                    System.out.println("Entry: " + entry);
                }
            };

            calendar.addEventHandler(handler);

            // a


            // Convert the CalendarView to a Node
            Node calendarNode = calendarView;

            // Add the calendar node to the split pane
            SplitPane splitPane = (SplitPane) rootLayout.lookup("#mySplitPane");
            splitPane.getItems().set(1, calendarNode);

            //L'operatore :: si può utilizzare per fare chiamate di metodi di oggetti (si utilizza
            primaryStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void closeWindowEvent(WindowEvent event) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().add(ButtonType.CANCEL);
        alert.getButtonTypes().add(ButtonType.YES);
        alert.setTitle("Chiusura applicazione");
        alert.setContentText("Sei sicuro di voler chiudere il programma?");
        alert.initOwner(primaryStage.getOwner());
        Optional<ButtonType> res = alert.showAndWait();

        if(res.isPresent()) {
            if(res.get().equals(ButtonType.CANCEL))
                //metodo che interrompe la chiusura della finestra
                event.consume();
        }

    }

}

