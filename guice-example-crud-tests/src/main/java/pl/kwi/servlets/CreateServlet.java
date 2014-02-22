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
import pl.kwi.validators.CreateValidator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Class of Servlet handles requests from "Create" jsp page. 
 * 
 * @author Krzysztof Wisniewski
 *
 */
@Singleton
public class CreateServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UserService userService;
	
	@Inject
	private CreateValidator createValidator;
	
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
			handleCreateButton(request, response);
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
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/createJsp.jsp");
		requestDispatcher.forward(request, response);
		
	}
	
	/**
	 * Method handles pressing button "Create" on page.
	 * 
	 * @param request object <code>HttpServletRequest</code> with request from browser
	 * @param response object <code>HttpServletResponse</code> with response to browser
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handleCreateButton(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
					
		Map<String, String> errorMessages = createValidator.getErrorMessages(request);
		if(!errorMessages.isEmpty()) {			
			displayPage(request, response);
			return;
		}
		
		String name = request.getParameter("name");	
		UserEntity user = new UserEntity();
		user.setName(name);
		userService.createUser(user);
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

	
	// ************************************************************************************** //
	// ****************************** GETTERS AND SETTERS *********************************** //
	// ************************************************************************************** //
	
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setCreateValidator(CreateValidator createValidator) {
		this.createValidator = createValidator;
	}

}
