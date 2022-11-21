package acme.testing.teacher.blahblah;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;


public class TeacherBlahblahShowTest extends TestHarness  {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/teacher/blahblah/list-blahblah.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTeacherBlahblahListTest(final int recordIndex, final String ticker, final String caption, final String summary, final String cost, final String creationMoment, final String initDate, final String finishDate, final String hlink) {
		super.signIn("teacher1", "teacher1");
		super.clickOnMenu("Teacher", "List Blahblah");
		super.checkListingExists();
		super.sortListing(0, "asc");
		
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("ticker", ticker);
		super.checkInputBoxHasValue("creationMoment", creationMoment);
		super.checkInputBoxHasValue("caption", caption);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("cost", cost);
		super.checkInputBoxHasValue("initDate", initDate);
		super.checkInputBoxHasValue("finishDate", finishDate);
		super.checkInputBoxHasValue("hlink", hlink);
		
		super.signOut();
	}	
	
}
