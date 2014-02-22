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

import pl.kwi.entities.UserEntity;
import pl.kwi.services.UserService;
import pl.kwi.validators.EditValidator;

public class EditServletTest {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private UserService userService;
	private RequestDispatcher dispatcher;
	private EditValidator validator;
	
	private EditServlet servlet;
	

	@Before
	public void setUp() throws Exception {		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		userService = mock(UserService.class);
		dispatcher = mock(RequestDispatcher.class);
		validator = mock(EditValidator.class);
		
		servlet = new EditServlet();
		servlet.setUserService(userService);
		servlet.setEditValidator(validator);
	}
	
	@Test
	public void service_display() throws ServletException, IOException {
		when(request.getParameter("userId")).thenReturn("1");
		when(request.getParameter("action")).thenReturn("Display");
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		UserEntity user = new UserEntity(1L, "User1");
		when(userService.readUser(1L)).thenReturn(user);
		
		servlet.service(request, response);
		
		verify(request).setAttribute("user", user);
		verify(request).getRequestDispatcher("pages/editJsp.jsp");
		verify(dispatcher).forward(request, response);
	}
	
	@Test
	public void service_update() throws ServletException, IOException {
		when(request.getParameter("action")).thenReturn("Update");
		when(request.getParameter("userId")).thenReturn("1");
		when(request.getParameter("name")).thenReturn("User1");
		when(validator.getErrorMessages(request)).thenReturn(new HashMap<String, String>());
		UserEntity user = new UserEntity(1L, "User1");
		when(userService.readUser(1L)).thenReturn(user);
		
		servlet.service(request, response);
		
		user.setName("User1");
		verify(userService).updateUser(user);
		verify(response).sendRedirect("table.do?action=Display");
	}
	
	@Test
	public void service_update_withErrorMessages() throws ServletException, IOException {
		when(request.getParameter("action")).thenReturn("Update");
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		when(request.getParameter("userId")).thenReturn("1");
		UserEntity user = new UserEntity(1L, "User1");
		when(userService.readUser(1L)).thenReturn(user);
		
		Map<String, String> errorMessages = new HashMap<String, String>();
		errorMessages.put("key", "value");
		when(validator.getErrorMessages(request)).thenReturn(errorMessages);
		
		servlet.service(request, response);
		
		verify(request).setAttribute("user", user);
		verify(validator).getErrorMessages(request);
		verify(request).getRequestDispatcher("pages/editJsp.jsp");
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
