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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import admin.TestsManager;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import student.Student;
import student.StudentManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AdminUiController {

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
    private TabPane adminPage;
    @FXML
    private TableView<Student> studentRankingTableView;
    @FXML
    private ObservableList<Student> studentRankingObservableList;
    @FXML
    private TableColumn<Student, String> studentRankingPositionColumn;
    @FXML
    private TableColumn<Student, String> studentRankingNameColumn;
    @FXML
    private TableColumn<Student, String> studentRankingSurnameColumn;
    @FXML
    private TableColumn<Student, String> studentRankingEloColumn;

    @FXML
    private void initialize() {
        System.out.println("AdminUiController initialized");
        // Initialize the testsManager and studentManager
        this.testsManager = new TestsManager();
        this.studentManager = new StudentManager();

        testsManager.loadFromDB();

        // Initialize the studentsObservableList and set it as the items of the studentTableView
        studentsObservableList = FXCollections.observableArrayList();
        studentTableView.setItems(studentsObservableList);
        studentNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        markColumn.setCellValueFactory(cellData -> cellData.getValue().getMarkProperty());

        // Initialize the studentRankingObservableList and set it as the items of the studentRankingTableView
        studentRankingObservableList = FXCollections.observableArrayList();
        studentRankingTableView.setItems(studentRankingObservableList);
        studentRankingPositionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(studentRankingObservableList.indexOf(cellData.getValue()) + 1)));
        studentRankingNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        studentRankingSurnameColumn.setCellValueFactory(cellData -> cellData.getValue().getSurnameProperty());
        studentRankingEloColumn.setCellValueFactory(cellData -> cellData.getValue().getEloProperty());
        studentRankingPositionColumn.setSortable(false);
        studentRankingNameColumn.setSortable(false);
        studentRankingSurnameColumn.setSortable(false);
        studentRankingEloColumn.setSortable(false);

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

        timeline.setCycleCount(Timeline.INDEFINITE); // Set the timeline to repeat indefinitely
        timeline.play(); // Start the timeline

        // Convert the CalendarView to a Node and add it to the split pane
        Node calendarNode = calendarView;
        mySplitPane.getItems().set(1, calendarNode);
    }

    // Set the testsManager
    public void setMainModel(TestsManager testsManager) {
        this.testsManager = testsManager;
    }

    // Create a new timeline
    Timeline timeline = new Timeline(
        new KeyFrame(
            Duration.seconds(1), // Set interval of 1 second
            event -> {
                // Code to be executed every 1 second
                studentManager.calculateEloPoints();
                // the function put the students in order of elo points
                studentManager.getStudentsList().sort((student1, student2) -> student2.getElo().compareTo(student1.getElo()));
                studentRankingObservableList = FXCollections.observableArrayList(studentManager.getStudentsList());
                studentRankingTableView.setItems(studentRankingObservableList);
            }
        )
    );

    // Show the details of the events in the calendar
    public void showEventDetails() {
        List<Entry<?>> entries = calendar.findEntries("");
        for (Entry<?> entry : entries) {
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

            ArrayList<AdminTest> copyOfTestList = new ArrayList<>(testsManager.getTestsList());
            for (AdminTest test : copyOfTestList) {
                if (!findEntry(test.getSubject().getName(), test.getDate(), test.getInitialHour(), test.getFinalHour())) {
                    testsManager.removeTest(test.getSubject().getName(), test.getDate(), test.getInitialHour(), test.getFinalHour());
                }
            }

            // Add the test to the testsManager and save it to the CSV file
            testsManager.addTest(entry.getTitle(), entry.getStartDate().toString(), entry.getStartTime().getHour(), entry.getEndTime().getHour());
            testsManager.saveToCSV();
        }
    }

    private boolean findEntry(String title, String date, int initialHour, int finalHour) {
        for (Object entry : calendar.findEntries(title)) {
            Entry entryCasted = (Entry<?>) entry;
            if (entryCasted.getStartDate().toString().equals(date) && entryCasted.getStartTime().getHour() == initialHour && entryCasted.getEndTime().getHour() == finalHour) {
                return true;
            }
        }
        return false;
    }

    @FXML
    private void onStartButtonClick() {
        stackPane.getChildren().setAll(adminPage);
        showEventDetails();
    }
}