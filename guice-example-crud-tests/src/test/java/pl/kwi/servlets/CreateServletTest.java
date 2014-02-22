package pl.kwi.servlets;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import pl.kwi.services.UserService;
import pl.kwi.validators.CreateValidator;

public class CreateServletTest {
	

	private HttpServletRequest request;
	private HttpServletResponse response;
	private UserService userService;
	private RequestDispatcher dispatcher;
	private CreateValidator validator;
	
	private CreateServlet servlet;
	

	@Before
	public void setUp() throws Exception {		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		userService = mock(UserService.class);
		dispatcher = mock(RequestDispatcher.class);
		validator = mock(CreateValidator.class);
		
		servlet = new CreateServlet();
		servlet.setUserService(userService);
		servlet.setCreateValidator(validator);
	}

	@Test
	public void service_display() throws ServletException, IOException {
		when(request.getParameter("action")).thenReturn("Display");
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		
		servlet.service(request, response);
		
		verify(request).getRequestDispatcher("pages/createJsp.jsp");
		verify(dispatcher).forward(request, response);
	}
	
	@Test
	public void service_create() throws ServletException, IOException {
		when(request.getParameter("action")).thenReturn("Create");
		when(request.getParameter("name")).thenReturn("User1");
		when(validator.getErrorMessages(request)).thenReturn(new HashMap<String, String>());
		
		servlet.service(request, response);
		
		verify(validator).getErrorMessages(request);
		verify(request).getParameter("name");
		verify(response).sendRedirect("table.do?action=Display");
	}
	
	@Test
	public void service_create_withErrorMessages() throws ServletException, IOException {
		when(request.getParameter("action")).thenReturn("Create");
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		
		Map<String, String> errorMessages = new HashMap<String, String>();
		errorMessages.put("key", "value");
		when(validator.getErrorMessages(request)).thenReturn(errorMessages);
		
		servlet.service(request, response);
		
		verify(validator).getErrorMessages(request);
		verify(request).getRequestDispatcher("pages/createJsp.jsp");
		verify(dispatcher).forward(request, response);
	}
	
	@Test
	public void service_back() throws ServletException, IOException {
		when(request.getParameter("action")).thenReturn("Back");
		
		servlet.service(request, response);	
		
		verify(response).sendRedirect("table.do?action=Display");
	}
	
	@Test(expected=ServletException.class)
	public void service_wrongAction() throws ServletException, IOException {
		when(request.getParameter("action")).thenReturn("action");
		
		servlet.service(request, response);		
	}

}
