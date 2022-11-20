package acme.testing.teacher.course;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class TeacherCourseCreateTest extends TestHarness {

	// Test cases -------------------------------------------------------------
	@ParameterizedTest
	@CsvFileSource(resources = "/teacher/course/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTeacherCourseCreateTest(final int recordIndex, final String ticker, final String caption, final String abstractText, final String link, final String publish) {
		super.signIn("teacher1", "teacher1");
		super.clickOnMenu("Teacher", "Create Course");

		super.checkFormExists();
		super.fillInputBoxIn("caption", caption);
		super.fillInputBoxIn("abstractText", abstractText);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Teacher", "List Courses");
		super.checkListingExists();
		super.sortListing(0, "desc");
		super.checkColumnHasValue(0, 0, ticker);
		super.clickOnListingRecord(0);

		super.checkFormExists();
		super.checkInputBoxHasValue("ticker", ticker);
		super.checkInputBoxHasValue("caption", caption);
		super.checkInputBoxHasValue("abstractText", abstractText);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/teacher/course/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeTeacherCourseCreateTest(final int recordIndex, final String ticker, final String caption, final String abstractText, final String link, final String publish) {
		super.signIn("teacher1", "teacher1");
		super.clickOnMenu("Teacher", "Create Course");

		super.checkFormExists();
		super.fillInputBoxIn("caption", caption);
		super.fillInputBoxIn("abstractText", abstractText);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");
		super.checkErrorsExist();
		
		super.signOut();
	}

	@Test
	@Order(30)
	public void hackingCreateTest() {
		super.checkNotLinkExists("Account");
		super.navigate("/authenticated/post/create");
		super.checkPanicExists();

		super.signIn("teacher1", "teacher1");
		super.navigate("/teacher/help-request/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("learner1", "learner1");
		super.navigate("/learner/course/create");
		super.checkPanicExists();
		super.signOut();
	}
}
