package pl.kwi.servlets;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import pl.kwi.entities.UserEntity;
import pl.kwi.services.UserService;

public class DeleteServletTest {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private UserService userService;
	private RequestDispatcher dispatcher;
	
	private DeleteServlet servlet;
	

	@Before
	public void setUp() throws Exception {		
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		userService = mock(UserService.class);
		dispatcher = mock(RequestDispatcher.class);
		
		servlet = new DeleteServlet();
		servlet.setUserService(userService);
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
		verify(request).getRequestDispatcher("pages/deleteJsp.jsp");
		verify(dispatcher).forward(request, response);
	}
	
	@Test
	public void service_delete() throws ServletException, IOException {
		when(request.getParameter("action")).thenReturn("Delete");
		when(request.getParameter("userId")).thenReturn("1");
		UserEntity user = new UserEntity(1L, "User1");
		when(userService.readUser(1L)).thenReturn(user);
		
		servlet.service(request, response);
		
		verify(userService).deleteUser(user);
		verify(response).sendRedirect("table.do?action=Display");
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
