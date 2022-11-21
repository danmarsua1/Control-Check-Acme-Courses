package acme.testing.teacher.blahblah;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;


public class TeacherBlahblahListTest extends TestHarness  {
	
	// Test cases -------------------------------------------------------------
	@ParameterizedTest
	@CsvFileSource(resources = "/teacher/blahblah/list-blahblah.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTeacherBlahblahListTest(final int recordIndex, final String ticker, final String caption, final String summary, final String cost, final String creationMoment, final String initDate, final String finishDate, final String hlink) {
		super.signIn("teacher1", "teacher1");
		super.clickOnMenu("Teacher", "List Blahblah");
		super.checkListingExists();
		super.sortListing(0, "asc");
		
		super.checkColumnHasValue(recordIndex, 0, ticker);
		super.checkColumnHasValue(recordIndex, 1, caption);
		
		super.signOut();
	}
	
}