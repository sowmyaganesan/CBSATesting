<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>login</title>
</head>
<%@ include file="header.jsp" %>
<%@ page import="model.SessionManager" %>
<% //passed in data
    SessionManager adminsm = new SessionManager(request);
    boolean admin_status = adminsm.isLoggedIn();
    String admin_name = adminsm.getUserName();
    int admin_id = adminsm.getUserID();
    
   
    String admin_login_message = (String) request.getAttribute("admin_login_message");
    if(admin_login_message == null){
        admin_login_message = "";
    }


%>
<body>
  
                    <br/><br/><br/>
            <div style = "float:left"><h2>Administrator Login:
</h2>
       
                    <form action="adminlogin" method="post">
                        <input class="text_area" value="adminname" type="text" name="admin_name"/>
                        <input class="text_area" value="password" type="password" name="password"/>
                        <input class="button" type="submit" value="Sign In"/>
                    </form>
                     <% if(admin_status){ %>
                    
                <%}else{%>
                     <%if(admin_login_message != ""){%>
                        <p><%=admin_login_message%></p>
                    <%}%>
                    <%}%>
                   </div> 
</body>
</html>