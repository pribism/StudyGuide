package com.oskopek.studyguide.controller;

import com.google.common.eventbus.Subscribe;
import com.oskopek.studyguide.constraint.Constraint;
import com.oskopek.studyguide.constraint.event.BrokenCourseGroupConstraintEvent;
import com.oskopek.studyguide.constraint.event.BrokenGlobalConstraintEvent;
import com.oskopek.studyguide.constraint.event.BrokenResetEvent;
import com.oskopek.studyguide.constraint.event.StringMessageEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.HashSet;
import java.util.Set;

public class ConstraintsController extends AbstractController {

    @FXML
    private ListView<StringMessageEvent> listView;

    private ObservableList<StringMessageEvent> brokenConstraintEventList;

    private Set<Constraint> constraintSet;

    /**
     * Initializes the {@link #listView} data bindings.
     */
    @FXML
    private void initialize() {
        eventBus.register(this);
        brokenConstraintEventList = FXCollections.observableArrayList();
        listView.setItems(brokenConstraintEventList);
        constraintSet = new HashSet<>();
    }

    private void onBrokenConstraintInternal(StringMessageEvent event) {
        if (!constraintSet.contains(event.getBrokenConstraint())) {
            constraintSet.add(event.getBrokenConstraint());
        } else {
            removeAllBrokenEventsFromList(event.getBrokenConstraint());
        }
        brokenConstraintEventList.add(event); // overwrite, use the newer one always
    }

    @Subscribe
    public void onBrokenConstraint(BrokenCourseGroupConstraintEvent event) {
        logger.trace("CourseGroupConstraint {} broken.", event.getBrokenConstraint());
        onBrokenConstraintInternal(event);
    }

    @Subscribe
    public void onBrokenConstraint(BrokenGlobalConstraintEvent event) {
        logger.trace("GlobalConstraint {} broken.", event.getBrokenConstraint());
        onBrokenConstraintInternal(event);
    }

    private void removeAllBrokenEventsFromList(Constraint constraint) {
        brokenConstraintEventList
                .removeIf(stringMessageEvent -> stringMessageEvent.getBrokenConstraint().equals(constraint));
    }

    /**
     * Handler for constraint fixed events.
     *
     * @param event the observed event
     */
    @Subscribe
    public void onFixedConstraint(BrokenResetEvent event) {
        logger.debug("Constraint {} fixed.", event.getOriginallyBroken());
        if (constraintSet.contains(event.getOriginallyBroken())) {
            constraintSet.remove(event.getOriginallyBroken());
            removeAllBrokenEventsFromList(event.getOriginallyBroken());
        }
    }

}