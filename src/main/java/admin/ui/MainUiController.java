package admin.ui;

import admin.AdminTest;
import admin.StudentTable;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.EntryViewBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import admin.TestsManager;
import student.Student;
import student.StudentManager;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MainUiController {

    private TestsManager testsManager;
    private StudentManager studentManager;
    boolean isDetailsViewOpen = false;
    @FXML
    private SplitPane mySplitPane;
    private CalendarView calendarView;
    private Calendar calendar;
    @FXML
    private TableView<StudentTable> studentTableView;
    @FXML
    private TableColumn<StudentTable, String> studentNameColumn;
    @FXML
    private TableColumn<StudentTable, String> markColumn;
    private ObservableList<StudentTable> studentsObservableList;


    @FXML
    private void initialize() {
        this.testsManager = new TestsManager();
        this.studentManager = new StudentManager();

        studentsObservableList = FXCollections.observableArrayList();
        studentTableView.setItems(studentsObservableList);
        studentNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        markColumn.setCellValueFactory(cellData -> cellData.getValue().getMarkProperty());

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
        ArrayList<AdminTest> adminTestArrayList = testsManager.readFromCSV();
        for (AdminTest adminTest : adminTestArrayList) {
            Entry<?> entry = new Entry<>(adminTest.getSubject().getName());
            entry.changeStartDate(LocalDate.parse(adminTest.getDate()));
            entry.changeStartTime(LocalTime.of(adminTest.getInitialHour(), 0));
            entry.changeEndTime(LocalTime.of(adminTest.getFinalHour(), 0));
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
                    ArrayList<Student> studentsList = studentManager.getStudentsList();
                    ArrayList<StudentTable> testsList = new ArrayList<>();
                    for(Student student : studentsList) {
                        testsList.add(new StudentTable(student, student.getTest(entry.getTitle(), entry.getStartDate().toString())));
                    }
                    studentsObservableList = FXCollections.observableArrayList(testsList);
                    studentTableView.setItems(studentsObservableList);
                });
            }
            testsManager.addTest(entry.getTitle(), entry.getStartDate().toString(), entry.getStartTime().getHour(), entry.getEndTime().getHour());
            System.out.println("\nSaved to CSV");
            testsManager.saveToCSV();
        }
    }
}
