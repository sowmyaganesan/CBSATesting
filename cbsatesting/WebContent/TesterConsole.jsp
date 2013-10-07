<%@page import="java.util.StringTokenizer"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.sql.*"%>
<%@ page import="model.SessionManager" %>
<%@ page import="model.ProjectManager" %>
<%@ page import="model.Project" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<% 
		SessionManager sm = new SessionManager(request);
boolean user_status = sm.isLoggedIn();
String user_name = sm.getUserName();
int user_id = sm.getUserID();

	
	%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>CBSA Account update</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="project.css" rel="stylesheet" type="text/css" />
</head>
<body>
<!-- start header -->
<div id="wrapper">
<div id="header"><h1>Tester Console</h1></div>
<hr></hr>
<!-- end header -->
<!-- start page -->
<div id="page">
	<!-- start content -->
	<div id="content" style="width: auto;">
		<div class="post">
			<h3 class="title">Project Detail</h3>
			<div class="entry">
		

		<form name="contract" id="form1" method="post"  >
			 <table border="1" cellspacing="1" cellpadding="1">
			  	<tr>
        		 <td><strong>Project Name</strong></td>
				 <td><strong>Instance Id</strong></td>
			     <td><strong>Tools</strong></td>
			     <td><strong>ScriptPath</strong></td>
		       </tr>
		      <%
		      	ProjectManager pm = new ProjectManager();
		      	ArrayList<Project> projects = pm.getProjectByTester(user_name);
		      	for(int i=0;i<projects.size();i++){
		      		Project proj = projects.get(i);
					%>
			
				  	<tr>
                    <td ><%=proj.Name %></td>
				     <td><%=proj.Instance_id %></td>
				     <td><%=proj.Tools %></td>
				     <td><%=proj.ScriptPath %></td>
			       </tr>
			       <%}%>
			       <tr>
				
		       </tr>
			      </table>
	          </form>
	
	         
<p>&nbsp;</p>
		  </div>
			
		</div>
		
	</div>
	<div>

<!-- end footer -->
</body>
</html>
