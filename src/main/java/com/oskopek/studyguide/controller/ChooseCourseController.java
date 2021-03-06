package com.oskopek.studyguide.controller;

import com.oskopek.studyguide.model.courses.Course;
import com.oskopek.studyguide.view.ChooseCourseDialogPaneCreator;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.List;

/**
 * Controller for choosing a course out of several choices.
 */
public class ChooseCourseController extends AbstractController {

    private final ListProperty<Course> courseListProperty;
    private Dialog<ButtonType> dialog;
    @FXML
    private TableView<Course> courseTableView;
    @FXML
    private TableColumn<Course, String> idColumn;
    @FXML
    private TableColumn<Course, String> nameColumn;
    @FXML
    private TableColumn<Course, Number> creditsColumn;

    /**
     * Default constructor.
     */
    public ChooseCourseController() {
        courseListProperty = new SimpleListProperty<>();
    }

    /**
     * Initializes the {@link #courseTableView} data bindings.
     */
    @FXML
    private void initialize() {
        courseTableView.itemsProperty().bindBidirectional(courseListProperty);
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameOrLocalizedNameProperty());
        creditsColumn.setCellValueFactory(cellData -> cellData.getValue().getCredits().creditValueProperty());
    }

    /**
     * Handles submitting the dialog in case the user double clicks into the found course table.
     *
     * @param event the generated event
     */
    @FXML
    private void handleOnMouseClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            applyDialog();
        }
    }

    /**
     * Handles submitting the dialog in case the presses enter into the found course table.
     *
     * @param event the generated event
     */
    @FXML
    private void handleOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            applyDialog();
        }
    }

    /**
     * Closes the dialog as if the "Apply" button was clicked.
     */
    private void applyDialog() {
        dialog.resultProperty().setValue(ButtonType.APPLY);
        dialog.close();
    }

    /**
     * Get the dialog (used for reporting double clicks in the table).
     *
     * @return the dialog
     */
    public Dialog<ButtonType> getDialog() {
        return dialog;
    }

    /**
     * Set the dialog (used for reporting double clicks in the table).
     *
     * @param dialog the dialog wrapper for {@link ChooseCourseDialogPaneCreator}
     * @see FindCoursesController#handleSearch()
     */
    public void setDialog(Dialog<ButtonType> dialog) {
        this.dialog = dialog;
    }

    /**
     * Sets the courseList displayed in the dialog.
     *
     * @param courseList non-null
     */
    public void setCourseList(List<Course> courseList) {
        courseListProperty.setValue(FXCollections.observableList(courseList));
    }

    /**
     * Utility method for returning the selected course.
     *
     * @return selected course, or null if none selected
     */
    public Course getChosenCourse() {
        return courseTableView.getSelectionModel().getSelectedItem();
    }
}
