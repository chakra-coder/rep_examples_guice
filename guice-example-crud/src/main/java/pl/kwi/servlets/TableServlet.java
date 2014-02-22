package pl.kwi.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.kwi.services.UserService;
import pl.kwi.validators.TableValidator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class TableServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UserService userService;
	
	@Inject
	private TableValidator validator;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
				
		String action = request.getParameter("action");		
		
		if("Display".equals(action)) {
			displayPage(request, response);
		} else if("Create".equals(action)) {
			handleActionCreate(request, response);
		} else if("View".equals(action)) {
			handleActionView(request, response);
		} else if("Edit".equals(action)) {
			handleActionEdit(request, response);
		} else if("Delete".equals(action)) {
			handleActionDelete(request, response);
		} else {
			throw new ServletException("No handling of action: " + action);
		}
			
	}
	
	/**
	 * Method displays page *.jsp with table.
	 * 
	 * @param request object <code>HttpServletRequest</code> with request from browser
	 * @param response object <code>HttpServletResponse</code> with response to browser
	 * @throws ServletException
	 * @throws IOException
	 */
	private void displayPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		request.setAttribute("users", userService.getUserList());
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/tableJsp.jsp");
		requestDispatcher.forward(request, response);
		
	}
	
	/**
	 * Method handles action "Create".
	 * 
	 * @param request object <code>HttpServletRequest</code> with request from browser
	 * @param response object <code>HttpServletResponse</code> with response to browser
	 * @throws ServletException
	 * @throws IOException
	 */
	private void handleActionCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		response.sendRedirect("create.do?action=Display");
		
	}
	
	/**
	 * Method handles action "View".
	 * 
	 * @param request object <code>HttpServletRequest</code> with request from browser
	 * @param response object <code>HttpServletResponse</code> with response to browser
	 * @throws ServletException
	 * @throws IOException
	 */
	private void handleActionView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		Map<String, String> errorMessages = validator.getErrorMessages(request);
		if(!errorMessages.isEmpty()) {			
			displayPage(request, response);
			return;
		}
		
		String userId = request.getParameter("selectedUsersIds");
		response.sendRedirect("view.do?action=Display&userId=" + userId);
		
	}
	
	/**
	 * Method handles action "Edit".
	 * 
	 * @param request object <code>HttpServletRequest</code> with request from browser
	 * @param response object <code>HttpServletResponse</code> with response to browser
	 * @throws ServletException
	 * @throws IOException
	 */
	private void handleActionEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		Map<String, String> errorMessages = validator.getErrorMessages(request);
		if(!errorMessages.isEmpty()) {			
			displayPage(request, response);
			return;
		}
		
		String userId = request.getParameter("selectedUsersIds");
		response.sendRedirect("edit.do?action=Display&userId=" + userId);
		
	}
	
	/**
	 * Method handles action "Delete".
	 * 
	 * @param request object <code>HttpServletRequest</code> with request from browser
	 * @param response object <code>HttpServletResponse</code> with response to browser
	 * @throws ServletException
	 * @throws IOException
	 */
	private void handleActionDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		Map<String, String> errorMessages = validator.getErrorMessages(request);
		if(!errorMessages.isEmpty()) {			
			displayPage(request, response);
			return;
		}
		
		String userId = request.getParameter("selectedUsersIds");
		response.sendRedirect("delete.do?action=Display&userId=" + userId);
		
	}

}
