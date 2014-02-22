package pl.kwi.validators;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;

public class TableValidatorTest {

	private TableValidator validator;
	private HttpServletRequest request;

	@Before
	public void setUp() throws Exception {
		validator = new TableValidator();
		request = mock(HttpServletRequest.class);
	}
	
	@Test
	public void getErrorMessages() {
		
		when(request.getParameter("selectedUsersIds")).thenReturn("1");
		String[] selectedUserIdsArray = {"1"};
		when(request.getParameterValues("selectedUsersIds")).thenReturn(selectedUserIdsArray);
		
		Map<String, String> errorMessages = validator.getErrorMessages(request);
		
		assertTrue(errorMessages.isEmpty());
		
	}
	
	@Test
	public void getErrorMessages_noRowSelected() {
		
		when(request.getParameter("selectedUsersIds")).thenReturn(null);
		String[] selectedUserIdsArray = {"1"};
		when(request.getParameterValues("selectedUsersIds")).thenReturn(selectedUserIdsArray);
		
		Map<String, String> errorMessages = validator.getErrorMessages(request);
		
		assertEquals("Select at least on row", errorMessages.get("selectedUsersIds"));
		
	}
	
	@Test
	public void getErrorMessages_manyRowsSelected() {
		
		when(request.getParameter("selectedUsersIds")).thenReturn("1");
		String[] selectedUserIdsArray = {"1", "2"};
		when(request.getParameterValues("selectedUsersIds")).thenReturn(selectedUserIdsArray);
		
		Map<String, String> errorMessages = validator.getErrorMessages(request);
		
		assertEquals("Only one row can be selected", errorMessages.get("selectedUsersIds"));
		
	}

}
