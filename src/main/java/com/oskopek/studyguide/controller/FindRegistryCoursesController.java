package com.oskopek.studyguide.controller;

import com.oskopek.studyguide.model.courses.Course;
import com.oskopek.studyguide.model.courses.CourseRegistry;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.simmetrics.builders.StringMetricBuilder;
import org.simmetrics.metrics.CosineSimilarity;
import org.simmetrics.simplifiers.Simplifiers;
import org.simmetrics.tokenizers.Tokenizers;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Finds course being searched for in the {@link CourseRegistry}.
 */
public class FindRegistryCoursesController extends FindCoursesController implements FindCourses {

    @FXML
    private TextArea searchArea;

    @FXML
    private Button searchButton;

    private CourseRegistry courseRegistry;

    public FindRegistryCoursesController(CourseRegistry courseRegistry) {
        this.courseRegistry = courseRegistry;
    }

    /**
     * Computes a similarity index for all courses to the {@code key}, sorts the courses according to it and returns
     * the first 5 (most similar).
     *
     * @see org.simmetrics.StringMetric
     * @see CosineSimilarity
     * @param key the key to search
     * @param locale the locale in which to search courses
     * @return the 5 most similar courses to the key
     */
    @Override
    public List<Course> findCourses(String key, Locale locale) {
        return courseRegistry.getCourses().parallelStream()
                .map(((Course course) -> new HashMap.SimpleEntry<>(
                        StringMetricBuilder
                                .with(new CosineSimilarity<>())
                                .simplify(Simplifiers.toLowerCase())
                                .simplify(Simplifiers.removeDiacritics())
                                .tokenize(Tokenizers.whitespace())
                                .build()
                                .compare(course.name(locale), key), course)))
                .sorted((a, b) -> Float.compare(a.getKey(), b.getKey()))
                .limit(5)
                .map((a) -> a.getValue())
                .collect(Collectors.toList());
    }
}