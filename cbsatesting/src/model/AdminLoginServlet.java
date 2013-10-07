
package model;

import java.io.IOException;
import java.security.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.UserManager;
import model.User;
//import webapp.controller.SessionManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;

/**
 * Enable registered users to log in to the site.
 * @author Nina
 */
public class AdminLoginServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String successurl = "/AdminConsole.jsp";
        String failurl = "/loginPage.jsp";
        String name = request.getParameter("admin_name");
        String password = request.getParameter("password");

        AdminManager um = new AdminManager();

        Admin user = um.getAdminByUserName(name);

        if (user == null) {
            System.out.println("User does not exist!");
        	request.setAttribute("admin_login_message", "User does not exist!");
        } else {
            int user_id = user.getUserId();
            if (user_id == -1) {
            	 System.out.println("Wrong User Name or Password!");
                request.setAttribute("admin_login_message", "Wrong User Name or Password!");
            } else {
            	System.out.println("User does exist");
                String id = Integer.toString(user_id);

                if (um.validatePass(name, password)) {
                    SessionManager sm = new SessionManager(request);
                    sm.addLoginCookie(response, true, name, user_id);
                    request.setAttribute("login_status", name + "," + id);
                    System.out.println("Success");
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(successurl);
                    dispatcher.forward(request, response);
                } else {
                    request.setAttribute("admin_login_message", "Wrong User Name or Password!");
                    System.out.println("Wrong User Name or Password!");
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(failurl);
                    dispatcher.forward(request, response);
                }
            }
        }

  

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
