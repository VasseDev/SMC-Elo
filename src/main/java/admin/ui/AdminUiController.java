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
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import login.ui.LoginUIController;
import main.TestsManager;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import main.db.MongoClientConnection;
import student.Student;
import student.StudentManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * AdminUiController is the controller class for the admin UI.
 * It handles the interaction between the user and the UI.
 */
public class AdminUiController {

    // Declare necessary variables
    @FXML
    private SplitPane mySplitPane;
    @FXML
    private TableView<StudentTable> studentTableView;
    @FXML
    private TableColumn<StudentTable, String> studentNameColumn;
    @FXML
    private TableColumn<StudentTable, String> markColumn;
    @FXML
    private TableView<Student> studentRankingTableView;
    @FXML
    private TableColumn<Student, String> studentRankingPositionColumn;
    @FXML
    private TableColumn<Student, String> studentRankingNameColumn;
    @FXML
    private TableColumn<Student, String> studentRankingEloColumn;
    @FXML
    private Label firstPlaceLabel;
    @FXML
    private Label secondPlaceLabel;
    @FXML
    private Label thirdPlaceLabel;

    private Calendar calendar;
    private CalendarView calendarView;
    private ObservableList<Student> studentRankingObservableList;
    private ObservableList<StudentTable> studentsObservableList;
    private Stage stage;
    private StudentManager studentManager;
    private TestsManager testsManager;

    /**
     * This method is called when the FXML file is loaded.
     * It initializes the controller class.
     */
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
        studentRankingEloColumn.setCellValueFactory(cellData -> cellData.getValue().getEloProperty());
        studentRankingPositionColumn.setSortable(false);
        studentRankingNameColumn.setSortable(false);
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

        updateStudentRanking(); // Update the student ranking

        timeline.setCycleCount(Timeline.INDEFINITE); // Set the timeline to repeat indefinitely
        timeline.play(); // Start the timeline

        // Convert the CalendarView to a Node and add it to the split pane
        Node calendarNode = calendarView;
        mySplitPane.getItems().set(1, calendarNode);
    }


    /**
     * This method sets the testsManager.
     *
     * @param testsManager the testsManager to set
     */
    public void setMainModel(TestsManager testsManager) {
        this.testsManager = testsManager;
    }

    /**
     * This method creates a new timeline.
     */
    Timeline timeline = new Timeline(
        new KeyFrame(
            Duration.seconds(5), // Set interval of 1 second
            event -> {
                updateStudentRanking();
            }
        )
    );

    /**
     * Updates the student ranking.
     * It retrieves the student list from the MongoDB database, calculates the Elo points, and updates the student ranking table view.
     */
    private void updateStudentRanking() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                MongoClientConnection mongoClientConnection = new MongoClientConnection();
                mongoClientConnection.importStudentList(studentManager, testsManager);
                return null;
            }
        };
        // Run the task in a separate thread
        new Thread(task).start();

        // Code to be executed every 5 second
        studentManager.calculateEloPoints();
        // the function put the students in order of elo points
        studentManager.getStudentsList().sort((student1, student2) -> student2.getElo().compareTo(student1.getElo()));
        studentRankingObservableList = FXCollections.observableArrayList(studentManager.getStudentsList());
        studentRankingTableView.setItems(studentRankingObservableList);
        if (!studentRankingObservableList.isEmpty()) {
            firstPlaceLabel.setText(studentRankingObservableList.get(0).getName() + " - " + studentRankingObservableList.get(0).getElo());
        }
        if (studentRankingObservableList.size() > 1) {
            secondPlaceLabel.setText(studentRankingObservableList.get(1).getName() + " - " + studentRankingObservableList.get(1).getElo());
        }
        if (studentRankingObservableList.size() > 2) {
            thirdPlaceLabel.setText(studentRankingObservableList.get(2).getName() + " - " + studentRankingObservableList.get(2).getElo());
        }
    }

    /**
     * This method shows the details of the events in the calendar.
     */
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

    /**
     * This method finds an entry in the calendar.
     *
     * @param title the title of the entry
     * @param date the date of the entry
     * @param initialHour the initial hour of the entry
     * @param finalHour the final hour of the entry
     * @return true if the entry is found, false otherwise
     */
    private boolean findEntry(String title, String date, int initialHour, int finalHour) {
        for (Object entry : calendar.findEntries(title)) {
            Entry entryCasted = (Entry<?>) entry;
            if (entryCasted.getStartDate().toString().equals(date) && entryCasted.getStartTime().getHour() == initialHour && entryCasted.getEndTime().getHour() == finalHour) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method sets the stage.
     *
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * This method handles the close window event.
     *
     * @param event the window event
     */
    public void closeWindowEvent(WindowEvent event) {
        TestsManager testsManager = new TestsManager();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().add(ButtonType.CANCEL);
        alert.getButtonTypes().add(ButtonType.YES);
        alert.setTitle("Chiusura applicazione");
        alert.setContentText("Sei sicuro di voler chiudere il programma?");
        alert.initOwner(stage.getOwner());
        Optional<ButtonType> res = alert.showAndWait();

        testsManager.loadToDB();

        if(res.isPresent()) {
            if(res.get().equals(ButtonType.CANCEL))
                //metodo che interrompe la chiusura della finestra
                event.consume();
        }

    }
}