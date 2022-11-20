package acme.testing.any.course;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;


public class AnyCourseListTest extends TestHarness  {
	
	// Test cases -------------------------------------------------------------
	@ParameterizedTest
	@CsvFileSource(resources = "/any/course/list-course.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveAnyListTest(final int recordIndex, final String ticker, final String caption, final String abstractText, final String link, final String publish) {
		super.clickOnMenu("Anonymous", "List Courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		
		super.checkColumnHasValue(recordIndex, 0, caption);
		super.checkColumnHasValue(recordIndex, 1, abstractText);
	}

}