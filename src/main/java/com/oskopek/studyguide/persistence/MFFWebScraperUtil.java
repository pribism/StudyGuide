package com.oskopek.studyguide.persistence;

import com.oskopek.studyguide.model.StudyPlan;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * A quick util class to scrape a MFF html study plan.
 */
public final class MFFWebScraperUtil {

    private static final String sisWebUrl = "https://is.cuni.cz/studium";
    private static final String mffIoiInfoUrl = "http://www.mff.cuni.cz/studium/bcmgr/ok/ib3a21.htm";
    private final MFFHtmlScraper scraper;

    /**
     * An empty default private constructor.
     */
    private MFFWebScraperUtil() {
        // intentionally empty // TODO add weld init
        scraper = new MFFHtmlScraper(sisWebUrl);
    }

    /**
     * The main method to run the util. Will overwrite if an existing file with the same name exists.
     * Naming strategy: {@code test-current_time_in_millis.json}.
     *
     * @param args the command line arguments, ignored
     * @throws IOException if an exception during loading the page or writing the converted json happens
     */
    public static void main(String[] args) throws IOException {
        MFFWebScraperUtil mffWebScraperUtil = new MFFWebScraperUtil();
        mffWebScraperUtil.run();
    }

    /**
     * The actual "main" method that does all the work. Scrape the study plan from the given URL,
     * prints the courses that were scraped and saves the plan to a file.
     *
     * @throws IOException if an error during scraping or persisting happened
     */
    private void run() throws IOException {
        StudyPlan studyPlan = scraper.scrapeStudyPlan(mffIoiInfoUrl);
        System.out.println(Arrays.toString(studyPlan.getCourseRegistry().courseMapValues().toArray()));
        Path outputFile = Paths.get("test-" + System.currentTimeMillis() + ".json");
        System.out.println("Writing output to " + outputFile);
        new JsonDataReaderWriter().writeTo(studyPlan, outputFile.toString());
    }
}
