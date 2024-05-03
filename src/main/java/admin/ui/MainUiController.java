package admin.ui;

// Import necessary libraries
import admin.AdminTest;
import admin.StudentTable;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.EntryViewBase;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import admin.TestsManager;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import student.Student;
import student.StudentManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MainUiController {

    // Declare necessary variables
    private TestsManager testsManager;
    private StudentManager studentManager;
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
    private Button startButton;
    @FXML
    private StackPane stackPane;
    @FXML
    private AnchorPane calendarAnchorPane;

    @FXML
    private void initialize() {
        // Initialize the testsManager and studentManager
        this.testsManager = new TestsManager();
        this.studentManager = new StudentManager();

        // Initialize the studentsObservableList and set it as the items of the studentTableView
        studentsObservableList = FXCollections.observableArrayList();
        studentTableView.setItems(studentsObservableList);
        studentNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        markColumn.setCellValueFactory(cellData -> cellData.getValue().getMarkProperty());

        // Create a new calendar and add it to a new calendar source
        calendar = new Calendar("Verifiche");
        CalendarSource calendarSource = new CalendarSource("My Calendar Source");
        calendarSource.getCalendars().add(calendar);

        // Create a new calendar view and add the calendar source to it
        calendarView = new CalendarView();
        calendarView.getCalendarSources().clear();
        calendarView.getCalendarSources().add(calendarSource);
        calendarView.setRequestedTime(LocalTime.now());

        // Set the default view to the month page and disable unnecessary buttons
        calendarView.showMonthPage();
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowPageToolBarControls(false);
        calendarView.setShowPrintButton(false);
        calendarView.setShowSourceTrayButton(false);
        calendarView.setShowSearchField(false);

        // Read the CSV file and add the tests to the calendar
        ArrayList<AdminTest> adminTestArrayList = testsManager.readFromCSV();
        for (AdminTest adminTest : adminTestArrayList) {
            Entry<?> entry = new Entry<>(adminTest.getSubject().getName());
            entry.changeStartDate(LocalDate.parse(adminTest.getDate()));
            entry.changeEndDate(LocalDate.parse(adminTest.getDate()));
            entry.changeStartTime(LocalTime.of(adminTest.getInitialHour(), 0));
            entry.changeEndTime(LocalTime.of(adminTest.getFinalHour(), 0));
            calendar.addEntry(entry);
        }

        // Add an event handler to the calendar
        EventHandler<CalendarEvent> handler = e -> showEventDetails();
        calendar.addEventHandler(handler);

        // Convert the CalendarView to a Node and add it to the split pane
        Node calendarNode = calendarView;
        mySplitPane.getItems().set(1, calendarNode);
    }

    // Set the testsManager
    void setMainModel(TestsManager testsManager) {
        this.testsManager = testsManager;
    }

    // Show the details of the events in the calendar
    private void showEventDetails() {
        List<Entry<?>> entries = calendar.findEntries("");
        for (Entry<?> entry : entries) {
            EntryViewBase<?> entryViewBase = calendarView.findEntryView(entry);
            System.out.println("ciao " + entry.getTitle() + " " + entry.getStartDate() + " " + entry.getStartTime() + " " + entry.getEndTime());
            System.out.println("ciao " + entryViewBase);

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
            // Add the test to the testsManager and save it to the CSV file
            testsManager.addTest(entry.getTitle(), entry.getStartDate().toString(), entry.getStartTime().getHour(), entry.getEndTime().getHour());
            System.out.println("\nSaved to CSV");
            testsManager.saveToCSV();
        }
    }

    @FXML
    private void onStartButtonClick() {
        stackPane.getChildren().setAll(calendarAnchorPane);
        showEventDetails();
    }
}