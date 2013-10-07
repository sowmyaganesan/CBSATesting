<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<%@ page import="model.SessionManager" %>
<% //passed in data
    SessionManager sm = new SessionManager(request);
    boolean user_status = sm.isLoggedIn();
    String user_name = sm.getUserName();
    int user_id = sm.getUserID();
    
   
    String tester_login_message = (String) request.getAttribute("tester_login_message");
    //String admin_login_message = (String) request.getAttribute("admin_login_message");
    if(tester_login_message == null){
        tester_login_message = "";
    }
   // if(admin_login_message == null){
   //     admin_login_message = "";
   // }
%>
<body>

                     <% if(user_status){ %>
                     <div >
                     <p style = "float:right" id="sign_out">&nbsp;&nbsp; <a href="logout">Sign out</a>.</p>
                    <p style = "float:right" id="welcome_message">WELCOME <span id="username"><%=user_name %></span> !</p>
                    
                <%}else{%>
                     <%if(tester_login_message != ""){%>
                        <p style = "float:right"><%=tester_login_message%></p>
                    <%}%>
                    <form action="/cbsatesting/testerlogin" method="post" style = "float:right">
                        <input class="text_area" value="testername" type="text" name="tester_name"/>
                        <input class="text_area" value="password" type="password" name="password"/>
                        <input class="button" type="submit" value="Sign In"/>
                    </form>
                    </div>
                    <%}%>

</body>
</html>