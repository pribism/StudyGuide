package com.oskopek.studyguide.model;

import com.oskopek.studyguide.model.constraints.Constraints;
import com.oskopek.studyguide.model.courses.CourseRegistry;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Default implementation of a {@link StudyPlan}.
 */
public class DefaultStudyPlan implements StudyPlan {

    private final ObjectProperty<CourseRegistry> courseRegistry;
    private final ObjectProperty<SemesterPlan> semesterPlan;
    private final ObjectProperty<Constraints> constraints;

    /**
     * Create an empty instance of a study plan.
     */
    public DefaultStudyPlan() {
        this.courseRegistry = new SimpleObjectProperty<>(new CourseRegistry());
        this.semesterPlan = new SimpleObjectProperty<>(new SemesterPlan());
        this.constraints = new SimpleObjectProperty<>(new Constraints());
    }

    @Override
    public CourseRegistry getCourseRegistry() {
        return courseRegistry.get();
    }

    /**
     * Private setter for Jackson persistence.
     *
     * @param courseRegistry the {@link CourseRegistry} to set
     */
    private void setCourseRegistry(CourseRegistry courseRegistry) {
        this.courseRegistry.set(courseRegistry);
    }

    @Override
    public SemesterPlan getSemesterPlan() {
        return semesterPlan.get();
    }

    /**
     * Private setter for Jackson persistence.
     *
     * @param semesterPlan the {@link SemesterPlan} to set
     */
    private void setSemesterPlan(SemesterPlan semesterPlan) {
        this.semesterPlan.set(semesterPlan);
    }

    @Override
    public Constraints getConstraints() {
        return constraints.get();
    }

    /**
     * Private setter for Jackson persistence.
     *
     * @param constraints the {@link Constraints} to set
     */
    private void setConstraints(Constraints constraints) {
        this.constraints.set(constraints);
    }

    /**
     * The JavaFX property for {@link #getSemesterPlan()}.
     *
     * @return the property of {@link #getSemesterPlan()}
     */
    public ObjectProperty<SemesterPlan> semesterPlanProperty() {
        return semesterPlan;
    }

    /**
     * The JavaFX property for {@link #getConstraints()}.
     *
     * @return the property of {@link #getConstraints()}
     */
    public ObjectProperty<Constraints> constraintsProperty() {
        return constraints;
    }

    /**
     * The JavaFX property for {@link #getCourseRegistry()}.
     *
     * @return the property of {@link #getCourseRegistry()}
     */
    public ObjectProperty<CourseRegistry> courseRegistryProperty() {
        return courseRegistry;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getSemesterPlan()).append(getConstraints())
                .append(getCourseRegistry()).toHashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultStudyPlan)) {
            return false;
        }
        DefaultStudyPlan that = (DefaultStudyPlan) o;
        return new EqualsBuilder().append(getSemesterPlan(), that.getSemesterPlan())
                .append(getConstraints(), that.getConstraints()).append(getCourseRegistry(), that.getCourseRegistry())
                .isEquals();
    }

    @Override
    public String toString() {
        return "DefaultStudyPlan[semesters=" + getSemesterPlan() + ", courses=" + getCourseRegistry() + ", constraints="
                + getConstraints() + ']';
    }
}
