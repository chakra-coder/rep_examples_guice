package pl.kwi.validators;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;

public class CreateValidatorTest {

	private CreateValidator validator;
	private HttpServletRequest request;

	@Before
	public void setUp() throws Exception {
		validator = new CreateValidator();
		request = mock(HttpServletRequest.class);
	}
	
	@Test
	public void getErrorMessages() {
		
		when(request.getParameter("name")).thenReturn("User1");
		
		Map<String, String> errorMessages = validator.getErrorMessages(request);
		
		assertTrue(errorMessages.isEmpty());
		
	}
	
	@Test
	public void getErrorMessages_noName() {
		
		when(request.getParameter("name")).thenReturn(null);
		
		Map<String, String> errorMessages = validator.getErrorMessages(request);
		
		assertEquals("Please fill this field", errorMessages.get("name"));
		
	}

}
