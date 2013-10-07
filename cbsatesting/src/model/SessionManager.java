/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import javax.servlet.http.Cookie;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nina Fu
 */
public class SessionManager {

    private static String SESSION_NAME = "virtualLabCookie";
    private static String STATUS_ATTRIBUTE_NAME = "login_status";
    private static String STATUS_LOGGED_IN = "TRUE"; //or 1 and do parseInt
    private static String STATUS_LOGGED_OUT = "FALSE"; //or 0 and do parseInt

    private static int NO_ID = -1;

    private String userName;
    private int userID;
    private boolean userStatus;

    public SessionManager(HttpServletRequest request) {
        String value = getSessionCookie(request.getCookies(), SESSION_NAME);
        String status = (String) request.getAttribute(STATUS_ATTRIBUTE_NAME);
        if (!value.equals("")) {
            String[] parts = value.split(",");
            userName = parts[0];
            userID = Integer.parseInt(parts[1]);
            userStatus = true;
        } else if ((status != null) && (!status.equals("")) && (!status.equals(STATUS_LOGGED_OUT))) {
                String[] parts = status.split(",");
                userName = parts[0];
                System.out.println(userName);
                userID = Integer.parseInt(parts[1]);
                System.out.println(userID);
                userStatus = true;
        } else {
                userName = null;
                userID = NO_ID;
                userStatus = false;
        }
    }

    public String getSessionCookie(Cookie[] cookies, String cookie_name){
        if(cookies != null){
                for(int i = 0; i < cookies.length; i++){
                    Cookie cookie = cookies[i];
                    if(cookie.getName().equals(cookie_name)){
                        return cookie.getValue();
                    }
                }
        }
        return "";
    }

    public boolean isLoggedIn() {
        return userStatus;
    }

    public String getUserName() {
        return userName;
    }
    public int getUserID() {
        return userID;
    }

    public void addLoginCookie(HttpServletResponse response, boolean status, String name, int id) {
        userStatus = status;
        userName = name;
        userID = id;

        Cookie cookie = new Cookie (SESSION_NAME, name + "," + Integer.toString(id));
        cookie.setMaxAge (24*60*60);//keep it for 24 hours
        cookie.setPath ("/");

        response.addCookie(cookie);
        System.out.println("set up the cookie");
    }

    public void removeLoginCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(int i = 0; i < cookies.length; i++){
                if(cookies[i].getName().equals(SESSION_NAME)){
                    userName = null;
                    userID = NO_ID;
                    userStatus = false;

                    cookies[i].setMaxAge(0);
                    cookies[i].setPath("/");
                    response.addCookie(cookies[i]);
                }
            }
        }
    }

}

