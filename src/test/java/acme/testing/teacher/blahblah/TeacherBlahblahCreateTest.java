package acme.testing.teacher.blahblah;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class TeacherBlahblahCreateTest extends TestHarness {

	// Test cases -------------------------------------------------------------
	@ParameterizedTest
	@CsvFileSource(resources = "/teacher/blahblah/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTeacherBlahblahCreateTest(final int recordIndex, final String ticker, final String caption, final String summary, final String cost, final String creationMoment, final String initDate, final String finishDate, final String hlink) {
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

		super.clickOnMenu("Teacher", "List Blahblah");
		super.checkListingExists();
		super.sortListing(1, "desc");
		super.clickOnListingRecord(0);

		super.checkFormExists();
		super.checkInputBoxHasValue("caption", caption);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("cost", cost);
		super.checkInputBoxHasValue("initDate", initDate);
		super.checkInputBoxHasValue("finishDate", finishDate);
		super.checkInputBoxHasValue("hlink", hlink);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/teacher/blahblah/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeTeacherBlahblahCreateTest(final int recordIndex, final String ticker, final String caption, final String summary, final String cost, final String creationMoment, final String initDate, final String finishDate, final String hlink) {
		super.signIn("teacher1", "teacher1");
		super.clickOnMenu("Teacher", "List Theory Tutorials");
		
		super.checkListingExists();
		super.sortListing(0, "desc");
		super.clickOnListingRecord(recordIndex);
		
		super.clickOnButton("View Blablah");
		super.clickOnSubmit("Delete");
		
		super.clickOnButton("New Blahblah");

		super.checkFormExists();
		super.fillInputBoxIn("caption", caption);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("cost", cost);
		super.fillInputBoxIn("initDate", initDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.clickOnSubmit("Create");
		super.checkErrorsExist();
		
		super.signOut();
	}

	@Test
	@Order(30)
	public void hackingTeacherBlahblahCreateTest() {
		super.checkNotLinkExists("Account");
		super.navigate("/teacher/blahblah/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.navigate("/teacher/blahblah/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("learner1", "learner1");
		super.navigate("/teacher/blahblah/create");
		super.checkPanicExists();
		super.signOut();
	}
}
