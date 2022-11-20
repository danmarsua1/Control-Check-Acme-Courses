package acme.testing.any.useraccount;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;


public class AnyUserAccountListTest extends TestHarness  {
	
	// Test cases -------------------------------------------------------------
	@ParameterizedTest
	@CsvFileSource(resources = "/any/user-account/list-user-account.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveAnyListTest(final int recordIndex, final String username, final String name, final String surname, final String email, final String roles) {
		super.clickOnMenu("Anonymous", "List User Accounts");
		super.checkListingExists();
		super.sortListing(0, "asc");
		
		super.checkColumnHasValue(recordIndex, 0, name);
		super.checkColumnHasValue(recordIndex, 1, surname);
		super.checkColumnHasValue(recordIndex, 2, roles);
	}

}