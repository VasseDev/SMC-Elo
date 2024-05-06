package student.ui;

import admin.AdminTest;
import admin.Subject;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Duration;
import main.TestsManager;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.EntryViewBase;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import main.db.MongoClientConnection;
import student.Mark;
import student.Student;
import student.StudentManager;
import student.StudentTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class StudentUIController {
    @FXML
    private SplitPane mySplitPane;
    @FXML
    ComboBox<String> gradeComboBox;
    @FXML
    TextField subjectTextField;
    @FXML
    TextField dateTextField;

    @FXML
    private TableView<Student> studentRankingTableView;
    @FXML
    private ObservableList<Student> studentRankingObservableList;
    @FXML
    private TableColumn<Student, String> studentRankingPositionColumn;
    @FXML
    private TableColumn<Student, String> studentRankingNameColumn;
    @FXML
    private TableColumn<Student, String> studentRankingEloColumn;

    private Student student;
    private TestsManager testsManager;
    private StudentManager studentManager;
    private Calendar calendar;
    private CalendarView calendarView;


    @FXML
    private void initialize() {
        System.out.println("StudentUiController initialized");
        // Initialize the testsManager and studentManager
        this.testsManager = new TestsManager();

        // Initialize the studentRankingObservableList and set it as the items of the studentRankingTableView
        studentRankingObservableList = FXCollections.observableArrayList();
        studentRankingTableView.setItems(studentRankingObservableList);
        studentRankingPositionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(studentRankingObservableList.indexOf(cellData.getValue()) + 1)));
        studentRankingNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        studentRankingEloColumn.setCellValueFactory(cellData -> cellData.getValue().getEloProperty());
        studentRankingPositionColumn.setSortable(false);
        studentRankingNameColumn.setSortable(false);
        studentRankingEloColumn.setSortable(false);

        testsManager.loadFromDB();

        mySplitPane.isResizable();

        gradeComboBox.getItems().addAll("1", "1+", "1½",
                "2-", "2", "2+", "2½",
                "3-", "3", "3+", "3½",
                "4-", "4", "4+", "4½",
                "5-", "5", "5+", "5½",
                "6-", "6", "6+", "6½",
                "7-", "7", "7+", "7½",
                "8-", "8", "8+", "8½",
                "9-", "9", "9+", "9½",
                "10-", "10");

        // Create a new calendar and add it to a new calendar source
        calendar = new Calendar("Verifiche");
        calendar.setReadOnly(true); // Disable creating new entries
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

        timeline.setCycleCount(Timeline.INDEFINITE); // Set the timeline to repeat indefinitely
        timeline.play(); // Start the timeline
    }

    // Create a new timeline
    Timeline timeline = new Timeline(
        new KeyFrame(
            Duration.seconds(5), // Set interval of 1 second
            event -> {
                MongoClientConnection mongoClientConnection = new MongoClientConnection();
                mongoClientConnection.importStudentList(this.studentManager, this.testsManager);
                // Code to be executed every 10 second
                this.studentManager.calculateEloPoints();
                // the function put the students in order of elo points
                this.studentManager.getStudentsList().sort((student1, student2) -> student2.getElo().compareTo(student1.getElo()));
                studentRankingObservableList = FXCollections.observableArrayList(this.studentManager.getStudentsList());
                studentRankingTableView.setItems(studentRankingObservableList);
            }
        )
    );

    public void setMainModel(StudentManager studentManager) {
        this.studentManager = studentManager;
    }

    public void setCurrentStudent(Student student) {
        System.out.println("Setting current student");
        System.out.println(student.getName());
        this.student = studentManager.findStudent(student.getName());
    }

    // Show the details of the events in the calendar
    public void showEventDetails() {
        List<Entry<?>> entries = calendar.findEntries("");
        for (Entry<?> entry : entries) {
            EntryViewBase<?> entryViewBase = calendarView.findEntryView(entry);

            if (entryViewBase != null) {
                // Add a mouse click event handler to the EntryViewBase
                entryViewBase.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                    subjectTextField.setText(entry.getTitle());
                    dateTextField.setText(entry.getStartDate().toString());
                });
            }
        }
    }

    @FXML
    private void onAddButtonClicked() {
        String subjectName = subjectTextField.getText();
        Subject subject = testsManager.getSubject(subjectName);
        String date = dateTextField.getText();
        String gradeString = gradeComboBox.getValue();
        if (subject == null || date.isEmpty() || gradeString == null) {
            System.out.println("Invalid input");
            return;
        }
        double grade = testsManager.convertGrade(gradeString);
        // find marching student in student list
        this.student.addTest(new StudentTest(subject, date, new Mark(grade), 12, 13));
        for (StudentTest studentTest : student.getTestsList()) {
            System.out.println(studentTest.getSubject().getName() + " " + studentTest.getDate() + " " + studentTest.getMark().getValue());
        }
        MongoClientConnection mongoClientConnection = new MongoClientConnection();
        mongoClientConnection.exportStudent(this.student);
    }
}
