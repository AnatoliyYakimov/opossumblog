/**
 * 
 */
package servlet;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import com.yakimov.validators.RegistrationValidator;

/**
 * @author yakimov
 *
 */
public class SignupTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void signupValidatorShouldBeValid() {
		String login = "User";
		String pass = "12345678";
		String conf = "12345678";
		List<String> violations = RegistrationValidator.validate(login, pass, conf);
		
		assertTrue(violations.isEmpty());
	}
	
	@Test
	public void signupValidatorShouldBeInvalid() {
		String login = "User";
		String pass = "1234567";
		String conf = "12345678";
		List<String> violations = RegistrationValidator.validate(login, pass, conf);
		
		assertFalse(violations.isEmpty());
		assertTrue(violations.size() == 2);
	}

}
