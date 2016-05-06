package com.oskopek.studyguide.persistence;

import com.oskopek.studyguide.model.StudyPlan;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * A simple and stable local integration test for {@link MFFHtmlScraper}.
 */
public class MFFHtmlScraperTest {

    private final String mffIoiInfoPath = "src/test/resources/com/oskopek/studyguide/persistence/ioi1516.html";
    private final String sisUrl = "file://" + Paths.get(".").toAbsolutePath()
            + "/src/test/resources/com/oskopek/studyguide/persistence/siscopy";
    private final String referenceFile
            = "src/test/resources/com/oskopek/studyguide/persistence/mff_bc_ioi_2015_2016.json";
    private MFFHtmlScraper scraper;

    @Before
    public void setUp() {
        scraper = new MFFHtmlScraper(sisUrl);
    }

    @Test
    public void testScrapeCourses() throws Exception {
        StudyPlan studyPlan = scraper.scrapeStudyPlan(Paths.get(mffIoiInfoPath));
        assertNotNull(studyPlan);
        // 61 base from IOI + 4 required courses + random deps
        assertEquals(70, studyPlan.getCourseRegistry().courseMapValues().size());
        StudyPlan verifyStudyPlan = new JsonDataReaderWriter().readFrom(referenceFile);
        assertEquals(studyPlan.getCourseRegistry(), verifyStudyPlan.getCourseRegistry());
        assertEquals(studyPlan.getSemesterPlan(), verifyStudyPlan.getSemesterPlan());
        assertEquals(studyPlan.getConstraints(), verifyStudyPlan.getConstraints());
        assertEquals(verifyStudyPlan, studyPlan);
    }

}
