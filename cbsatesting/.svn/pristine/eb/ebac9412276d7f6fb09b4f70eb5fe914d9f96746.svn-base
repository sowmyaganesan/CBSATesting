package model;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OpenProjectServlet
 */
@WebServlet("/openproject")
public class OpenProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OpenProjectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String instanceId = request.getParameter("instanceid");
        String tools = request.getParameter("tools");
        String scriptPath = request.getParameter("path");
        System.out.println(request.getRequestURL());
        
        if(name==null){
        	System.out.println("ERROR: missing paramenter name");
        	return;
        }
        ProjectManager pm = new ProjectManager();
        if(pm.createProject(name, owner, instanceId, "Open", tools, scriptPath)>0){
        	System.out.println("Success: project "+name+" opened");
        }else{
        	System.out.println("ERROR: cannot create project "+name);
        }
        
       //FIXME redirect does not work now
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ManagerConsole.jsp");
        dispatcher.forward(request, response);
        
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
