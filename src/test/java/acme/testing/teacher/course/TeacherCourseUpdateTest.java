package acme.testing.teacher.course;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class TeacherCourseUpdateTest extends TestHarness {

	// Test cases -------------------------------------------------------------
	@ParameterizedTest
	@CsvFileSource(resources = "/teacher/course/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTeacherCourseUpdateTest(final int recordIndex, final String ticker, final String caption,
			final String abstractText, final String link, final String publish) {
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
		super.fillInputBoxIn("caption", caption);
		super.fillInputBoxIn("abstractText", abstractText);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");
		
		super.clickOnMenu("Teacher", "List Courses");
		super.checkListingExists();
		super.sortListing(0, "desc");
		super.checkColumnHasValue(0, 0, ticker);
		super.clickOnListingRecord(0);
		
		super.checkFormExists();
		super.checkInputBoxHasValue("caption", caption);
		super.checkInputBoxHasValue("abstractText", abstractText);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}
}
