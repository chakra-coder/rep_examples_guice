package pl.kwi.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.kwi.entities.UserEntity;
import pl.kwi.services.UserService;
import pl.kwi.validators.EditValidator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Class of Servlet handles requests from "Edit" jsp page. 
 * 
 * @author Krzysztof Wisniewski
 *
 */
@Singleton
public class EditServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UserService userService;
	
	@Inject
	private EditValidator editValidator;
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
				
		String action = request.getParameter("action");
		
		if("Display".equals(action)) {
			displayPage(request, response);
		} else if("Update".equals(action)) {
			handleUpdateButton(request, response);
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
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/editJsp.jsp");
		requestDispatcher.forward(request, response);
		
	}
	
	/**
	 * Method handles pressing button "Update" on page.
	 * 
	 * @param request object <code>HttpServletRequest</code> with request from browser
	 * @param response object <code>HttpServletResponse</code> with response to browser
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handleUpdateButton(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
					
		Map<String, String> errorMessages = editValidator.getErrorMessages(request);
		if(!errorMessages.isEmpty()) {			
			displayPage(request, response);
			return;
		}
		
		String userId = request.getParameter("userId");	
		String name = request.getParameter("name");	
		
		UserEntity user = userService.readUser(Long.valueOf(userId));
		user.setName(name);
		userService.updateUser(user);
		
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
