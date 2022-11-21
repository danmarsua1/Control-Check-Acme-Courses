package acme.testing.teacher.blahblah;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class TeacherBlahblahUpdateTest extends TestHarness {

	// Test cases -------------------------------------------------------------
	@ParameterizedTest
	@CsvFileSource(resources = "/teacher/blahblah/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTeacherBlahblahUpdateTest(final int recordIndex, final String ticker, final String caption, final String summary, final String cost, final String creationMoment, final String initDate, final String finishDate, final String hlink) {
		super.signIn("teacher1", "teacher1");
		super.clickOnMenu("Teacher", "List Theory Tutorials");
		
		super.checkListingExists();
		super.sortListing(0, "desc");
		super.clickOnListingRecord(recordIndex);
		
		super.clickOnButton("New Blahblah");

		super.checkFormExists();
		super.fillInputBoxIn("caption", caption);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("cost", cost);
		super.fillInputBoxIn("initDate", initDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("hlink", hlink);
		super.clickOnSubmit("Create");
		
		super.clickOnButton("View Blablah");

		super.checkFormExists();
		super.fillInputBoxIn("caption", caption);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("initDate", initDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("cost", cost);
		super.fillInputBoxIn("hlink", hlink);
		super.clickOnSubmit("Update");

		super.clickOnButton("View Blablah");

		super.checkFormExists();
		super.checkInputBoxHasValue("caption", caption);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("cost", cost);
		super.checkInputBoxHasValue("initDate", initDate);
		super.checkInputBoxHasValue("finishDate", finishDate);
		super.checkInputBoxHasValue("hlink", hlink);

		super.signOut();
	}
}
