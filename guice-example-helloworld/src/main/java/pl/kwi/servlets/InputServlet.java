package pl.kwi.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/input.do")
public class InputServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
				
		String submit = request.getParameter("submit");
		
		if("Display".equals(submit)){
			displayPage(request, response);
			return;
		}else if("OK".equals(submit)){
			handleOkButton(request, response);
			return;
		}
			
	}
	
	private void displayPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/inputJsp.jsp");
		requestDispatcher.forward(request, response);
		
	}
	
	private void handleOkButton(HttpServletRequest request, HttpServletResponse response) throws IOException{
					
		String name = request.getParameter("name");	
		System.out.println("---name: " + name);
		response.sendRedirect("output.do?submit=Display");
		
	}

}
