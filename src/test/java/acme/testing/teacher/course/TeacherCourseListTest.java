package acme.testing.teacher.course;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;


public class TeacherCourseListTest extends TestHarness  {
	
	// Test cases -------------------------------------------------------------
	@ParameterizedTest
	@CsvFileSource(resources = "/teacher/course/list-course.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTeacherCourseListTest(final int recordIndex, final String ticker, final String caption, final String abstractText, final String link, final String publish) {
		super.signIn("teacher1", "teacher1");
		super.clickOnMenu("Teacher", "List Courses");
		super.checkListingExists();
		super.sortListing(0, "asc");
		
		super.checkColumnHasValue(recordIndex, 0, ticker);
		super.checkColumnHasValue(recordIndex, 1, caption);
		super.checkColumnHasValue(recordIndex, 2, abstractText);
		
		super.signOut();
	}
	
}