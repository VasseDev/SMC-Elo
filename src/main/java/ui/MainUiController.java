package ui;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.EntryViewBase;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;

import java.time.LocalTime;

public class MainUiController {

    @FXML
    private SplitPane mySplitPane;
    @FXML
    private TextField subjectTextField;
    @FXML
    private TextField dateTextField;
    @FXML
    private TextField hourTextField;

    @FXML
    private void initialize() {
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
            if (e.getTarget() instanceof Entry) {
                System.out.println("aaa");
                EntryViewBase entryViewBase = (EntryViewBase) e.getTarget();
                Entry entry = entryViewBase.getEntry();
                subjectTextField.setText(entry.getTitle());
            }
        };

        calendar.addEventHandler(handler);


        // Convert the CalendarView to a Node
        Node calendarNode = calendarView;

        // Add the calendar node to the split pane
        mySplitPane.getItems().set(1, calendarNode);
    }
}
