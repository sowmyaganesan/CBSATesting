package model;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CloseProjectServlet
 */
@WebServlet("/closeproject")
public class CloseProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CloseProjectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String name = request.getParameter("name");
        if(name==null){
        	System.out.println("ERROR: missing paramenter name");
        	return;
        }
        ProjectManager pm = new ProjectManager();
        if(pm.closeProject(name)){
        	System.out.println("Success: project "+name+" closed");
        }
        
       //FIXME redirect does not work now
       // RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ManagerConsole.jsp");
       // dispatcher.forward(request, response);
        response.sendRedirect("/ManagerConsole.jsp");
        
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
