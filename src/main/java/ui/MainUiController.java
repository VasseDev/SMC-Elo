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
import javafx.scene.input.MouseEvent;
import model.Test;
import model.TestsManager;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MainUiController {

    private TestsManager testsManager;
    boolean isDetailsViewOpen = false;
    @FXML
    private SplitPane mySplitPane;
    @FXML
    private TextField subjectTextField;
    @FXML
    private TextField dateTextField;
    @FXML
    private TextField hourTextField;
    private CalendarView calendarView;
    private Calendar calendar;


    @FXML
    private void initialize() {
        this.testsManager = new TestsManager();

        // Create a new calendar
        calendar = new Calendar("Verifiche");

        // Create a new calendar source and add the calendar to it
        CalendarSource calendarSource = new CalendarSource("My Calendar Source");
        calendarSource.getCalendars().add(calendar);


        // Create a new calendar view and add the calendar source to it
        calendarView = new CalendarView();
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
        calendarView.setShowSearchField(false);

        // Cycle through the tests and add them to the calendar
        ArrayList<Test> testArrayList = testsManager.readFromCSV();
        for (Test test : testArrayList) {
            Entry<?> entry = new Entry<>(test.getSubject().getName());
            entry.changeStartDate(LocalDate.parse(test.getDate()));
            entry.changeStartTime(LocalTime.of(test.getInitialHour(), 0));
            entry.changeEndTime(LocalTime.of(test.getFinalHour(), 0));
            calendar.addEntry(entry);
        }

        calendarView.dateProperty().addListener((observable, oldValue, newValue) -> {
            showEventDetails();
        });

        EventHandler<CalendarEvent> handler = e -> showEventDetails();

        calendar.addEventHandler(handler);

        // Convert the CalendarView to a Node
        Node calendarNode = calendarView;

        // Add the calendar node to the split pane
        mySplitPane.getItems().set(1, calendarNode);
    }

    void setMainModel(TestsManager testsManager) {
        this.testsManager = testsManager; // Set the class manager
    }

    private void showEventDetails() {
        List<Entry<?>> entries = calendar.findEntries("");
        for (Entry<?> entry : entries) {
            // Get the EntryViewBase for the entry
            EntryViewBase<?> entryViewBase = calendarView.findEntryView(entry);

            if (entryViewBase != null) {
                // Add a mouse click event handler to the EntryViewBase
                entryViewBase.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                    subjectTextField.setText(entry.getTitle());
                    dateTextField.setText(entry.getStartDate().toString());
                    hourTextField.setText(entry.getStartTime().toString());
                });
            }
            testsManager.addTest(entry.getTitle(), entry.getStartDate().toString(), entry.getStartTime().getHour(), entry.getEndTime().getHour());
        }
    }

    @FXML
    private void onSaveButtonClicked() {
        System.out.println("\nPrint button clicked");
        testsManager.saveToCSV();
    }

}
