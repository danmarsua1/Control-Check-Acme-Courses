package acme.testing.any.course;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;


public class AnyCourseShowTest extends TestHarness  {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/any/course/list-course.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveAnyShowTest(final int recordIndex, final String ticker, final String caption, final String abstractText, final String link, final String publish) {
		super.clickOnMenu("Anonymous", "List Courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("ticker", ticker);
		super.checkInputBoxHasValue("caption", caption);
		super.checkInputBoxHasValue("abstractText", abstractText);
		super.checkInputBoxHasValue("link", link);
	}
	
	
}