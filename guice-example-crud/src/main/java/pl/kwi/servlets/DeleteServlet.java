package pl.kwi.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.kwi.entities.UserEntity;
import pl.kwi.services.UserService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Class of Servlet handles requests from "Delete" jsp page. 
 * 
 * @author Krzysztof Wisniewski
 *
 */
@Singleton
public class DeleteServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UserService userService;
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
				
		String action = request.getParameter("action");
		
		if("Display".equals(action)) {
			displayPage(request, response);
		} else if("Delete".equals(action)) {
			handleDeleteButton(request, response);
		} else if("Back".equals(action)) {
			handleBackButton(request, response);
		} else {
			throw new ServletException("No handling of action: " + action);
		}
			
	}
	
	/**
	 * Method displays page connected with current servlet.
	 * 
	 * @param request object <code>HttpServletRequest</code> with request from browser
	 * @param response object <code>HttpServletResponse</code> with response to browser
	 * @throws ServletException
	 * @throws IOException
	 */
	private void displayPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String userId = request.getParameter("userId");
		UserEntity user = userService.readUser(Long.valueOf(userId));
		request.setAttribute("user", user);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/deleteJsp.jsp");
		requestDispatcher.forward(request, response);
		
	}
	
	/**
	 * Method handles pressing button "Delete" on page.
	 * 
	 * @param request object <code>HttpServletRequest</code> with request from browser
	 * @param response object <code>HttpServletResponse</code> with response to browser
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handleDeleteButton(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		String userId = request.getParameter("userId");	
		
		UserEntity user = userService.readUser(Long.valueOf(userId));
		userService.deleteUser(user);
		
		response.sendRedirect("table.do?action=Display");
		
	}
	
	/**
	 * Method handles pressing button "Back" on page.
	 * 
	 * @param request object <code>HttpServletRequest</code> with request from browser
	 * @param response object <code>HttpServletResponse</code> with response to browser
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handleBackButton(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
					
		response.sendRedirect("table.do?action=Display");
		
	}

}
