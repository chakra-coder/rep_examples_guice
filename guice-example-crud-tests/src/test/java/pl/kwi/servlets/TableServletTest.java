package pl.kwi.servlets;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import pl.kwi.entities.UserEntity;
import pl.kwi.services.UserService;
import pl.kwi.validators.TableValidator;

public class TableServletTest {

	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private UserService userService;
	private RequestDispatcher dispatcher;
	private TableValidator validator;
	
	private TableServlet servlet;

	@Before
	public void setUp() throws Exception {		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		userService = mock(UserService.class);
		dispatcher = mock(RequestDispatcher.class);
		validator = mock(TableValidator.class);
		
		servlet = new TableServlet();
		servlet.setUserService(userService);
		servlet.setValidator(validator);
	}

	@Test
	public void service_display() throws ServletException, IOException {
		when(request.getParameter("action")).thenReturn("Display");
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		
		List<UserEntity> users = new ArrayList<UserEntity>();
		users.add(new UserEntity(1L, "User1"));
		users.add(new UserEntity(2L, "User2"));
		when(userService.getUserList()).thenReturn(users);
		
		servlet.service(request, response);
		
		verify(request).setAttribute("users", users);
		verify(request).getRequestDispatcher("pages/tableJsp.jsp");
		verify(dispatcher).forward(request, response);
	}
	
	@Test
	public void service_create() throws ServletException, IOException {
		when(request.getParameter("action")).thenReturn("Create");
		
		servlet.service(request, response);
		
		verify(response).sendRedirect("create.do?action=Display");
	}
	
	@Test
	public void service_view() throws ServletException, IOException {
		when(request.getParameter("action")).thenReturn("View");
		when(request.getParameter("selectedUsersIds")).thenReturn("1");
		when(validator.getErrorMessages(request)).thenReturn(new HashMap<String, String>());
		
		servlet.service(request, response);
		
		verify(validator).getErrorMessages(request);
		verify(request).getParameter("selectedUsersIds");
		verify(response).sendRedirect("view.do?action=Display&userId=1");
	}
	
	@Test
	public void service_view_withErrorMessages() throws ServletException, IOException {
		when(request.getParameter("action")).thenReturn("View");
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		
		Map<String, String> errorMessages = new HashMap<String, String>();
		errorMessages.put("key", "value");
		when(validator.getErrorMessages(request)).thenReturn(errorMessages);
				
		List<UserEntity> users = new ArrayList<UserEntity>();
		users.add(new UserEntity(1L, "User1"));
		users.add(new UserEntity(2L, "User2"));
		when(userService.getUserList()).thenReturn(users);
		
		servlet.service(request, response);
		
		verify(validator).getErrorMessages(request);
		verify(request).setAttribute("users", users);
		verify(request).getRequestDispatcher("pages/tableJsp.jsp");
		verify(dispatcher).forward(request, response);
	}
	
	@Test
	public void service_edit() throws ServletException, IOException {
		when(request.getParameter("action")).thenReturn("Edit");
		when(request.getParameter("selectedUsersIds")).thenReturn("1");
		when(validator.getErrorMessages(request)).thenReturn(new HashMap<String, String>());
		
		servlet.service(request, response);
		
		verify(validator).getErrorMessages(request);
		verify(request).getParameter("selectedUsersIds");
		verify(response).sendRedirect("edit.do?action=Display&userId=1");
	}
	
	@Test
	public void service_edit_withErrorMessages() throws ServletException, IOException {
		when(request.getParameter("action")).thenReturn("Edit");
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		
		Map<String, String> errorMessages = new HashMap<String, String>();
		errorMessages.put("key", "value");
		when(validator.getErrorMessages(request)).thenReturn(errorMessages);
				
		List<UserEntity> users = new ArrayList<UserEntity>();
		users.add(new UserEntity(1L, "User1"));
		users.add(new UserEntity(2L, "User2"));
		when(userService.getUserList()).thenReturn(users);
		
		servlet.service(request, response);
		
		verify(validator).getErrorMessages(request);
		verify(request).setAttribute("users", users);
		verify(request).getRequestDispatcher("pages/tableJsp.jsp");
		verify(dispatcher).forward(request, response);
	}
	
	@Test
	public void service_delete() throws ServletException, IOException {
		when(request.getParameter("action")).thenReturn("Delete");
		when(request.getParameter("selectedUsersIds")).thenReturn("1");
		when(validator.getErrorMessages(request)).thenReturn(new HashMap<String, String>());
		
		servlet.service(request, response);
		
		verify(validator).getErrorMessages(request);
		verify(request).getParameter("selectedUsersIds");
		verify(response).sendRedirect("delete.do?action=Display&userId=1");
	}
	
	@Test
	public void service_delete_withErrorMessages() throws ServletException, IOException {
		when(request.getParameter("action")).thenReturn("Delete");
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		
		Map<String, String> errorMessages = new HashMap<String, String>();
		errorMessages.put("key", "value");
		when(validator.getErrorMessages(request)).thenReturn(errorMessages);
				
		List<UserEntity> users = new ArrayList<UserEntity>();
		users.add(new UserEntity(1L, "User1"));
		users.add(new UserEntity(2L, "User2"));
		when(userService.getUserList()).thenReturn(users);
		
		servlet.service(request, response);
		
		verify(validator).getErrorMessages(request);
		verify(request).setAttribute("users", users);
		verify(request).getRequestDispatcher("pages/tableJsp.jsp");
		verify(dispatcher).forward(request, response);
	}
	
	@Test(expected=ServletException.class)
	public void service_wrongAction() throws ServletException, IOException {
		when(request.getParameter("action")).thenReturn("action");
		
		servlet.service(request, response);		
	}

}
