package com.oskopek.studyguide.constraint;

import com.oskopek.studyguide.model.CourseEnrollment;
import com.oskopek.studyguide.model.courses.Course;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Checks if the number of times a student is enrolled in a course is less than or equal to N.
 */
public class GlobalCourseRepeatedEnrollmentConstraint extends GlobalConstraint {

    private int maxRepeatedEnrollment;
    private static final String message = "constraint.globalCourseRepeatedEnrollmentInvalid";

    /**
     * Default course.
     *
     * @param maxRepeatedEnrollment the maximum count of course enrollments per course
     */
    public GlobalCourseRepeatedEnrollmentConstraint(int maxRepeatedEnrollment) {
        this.maxRepeatedEnrollment = maxRepeatedEnrollment;
    }

    @Override
    public void validate() {
        Map<Course, List<CourseEnrollment>> groupByCourse = semesterPlan.getSemesterList().stream()
                .flatMap(s -> s.getCourseEnrollmentList().stream())
                .collect(Collectors.groupingBy(ce -> ce.getCourse()));
        for (Map.Entry<Course, List<CourseEnrollment>> entry : groupByCourse.entrySet()) {
            Course course = entry.getKey();
            List<CourseEnrollment> enrollments = entry.getValue();
            if (enrollments.size() > maxRepeatedEnrollment) {
                fireBrokenEvent(generateMessage(enrollments.size(), maxRepeatedEnrollment, course));
            }
        }
    }

    /**
     * Generates a message from the given parameters (localized). Used for populating the message of
     * {@link StringMessageEvent}s (usually upon breaking a constraint).
     *
     * @param enrolledTimes the number of times the student enrolled in the course
     * @param maxRepeatedEnrollment the maximum number of times one can enroll into a given course
     * @param course the course that was enrolled into too many times
     * @return the String to use as a message, localized
     */
    private String generateMessage(int enrolledTimes, int maxRepeatedEnrollment, Course course) {
        return String.format(messages.getString(message), enrolledTimes, maxRepeatedEnrollment, course.getName());
    }
}