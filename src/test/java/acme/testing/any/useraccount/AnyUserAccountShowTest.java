package acme.testing.any.useraccount;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;


public class AnyUserAccountShowTest extends TestHarness  {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/any/user-account/list-user-account.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveAnyShowTest(final int recordIndex, final String username, final String name, final String surname, final String email, final String roles) {
		super.clickOnMenu("Anonymous", "List User Accounts");
		super.checkListingExists();
		super.sortListing(0, "asc");
		
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("identity.name", name);
		super.checkInputBoxHasValue("identity.surname", surname);
		super.checkInputBoxHasValue("identity.email", email);
		super.checkInputBoxHasValue("roleList", roles);
	}
	
	
}