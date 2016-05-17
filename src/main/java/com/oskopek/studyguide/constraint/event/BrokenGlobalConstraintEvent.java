package com.oskopek.studyguide.constraint.event;

import com.oskopek.studyguide.constraint.Constraint;

/**
 * The event used for reporting broken global constraints.
 */
public class BrokenGlobalConstraintEvent extends StringMessageEvent {

    /**
     * Default constructor.
     *
     * @param message the message to use as a reason why the constraint is broken
     * @param broken the constraint that was broken and generated this event
     */
    public BrokenGlobalConstraintEvent(String message, Constraint broken) {
        super(message, broken);
    }

    @Override
    public String message() {
        return messages.getString("constraint.globalInvalid") + getMessage();
    }

    @Override
    public String toString() {
        return "BrokenGlobalConstraintEvent[" + getMessage() + "]";
    }
}