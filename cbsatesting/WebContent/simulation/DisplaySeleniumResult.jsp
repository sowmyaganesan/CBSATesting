<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %> 
<%@ page import="java.io.*" %> 
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.lang.*" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<% 
	try {
		/* Create string of connection url within specified format with machine name, 
		port number and database name. Here machine name id localhost and 
		database name is usermaster. */ 
		String connectionURL = "jdbc:mysql://localhost:3306/cbsa"; 
		
		// declare a connection by using Connection interface 
		Connection connection = null; 
		Statement statement = null;
		ResultSet resultset = null;
		
		// Load JBBC driver "com.mysql.jdbc.Driver" 
		Class.forName("com.mysql.jdbc.Driver").newInstance(); 
		
		/* Create a connection by using getConnection() method that takes parameters of 
		string type connection url, user name and password to connect to database. */ 
		connection = DriverManager.getConnection(connectionURL, "root", "root");
		
		// check weather connection is established or not by isClosed() method 
		if(connection.isClosed())
		{
			throw new Exception();
		}
		statement = connection.createStatement();
		resultset = statement.executeQuery("SELECT * FROM `seleniumresult` ORDER BY `TestResultID` DESC LIMIT 15");
		if(resultset.next())
		{
	
		
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Selenium result</title>
<link rel="stylesheet" type="text/css" href="/cbsatesting/style/main.css" />
<link rel="stylesheet" type="text/css" href="/cbsatesting/script/dijit/themes/claro/claro.css" />
<script src="/cbsatesting/script/dojo/dojo.js" djConfig="parseOnLoad: true"></script>
</head>
<body class="claro">
<h2>Test Results</h2><table class='blueTable'><tr>
<td>TestResultID</td><td>TestProject</td><td>UserID</td><td>Timestamp</td><td>TestURL</td><td>TestSuite</td><td>ResultFile</td>
<td>Success</td><td>Fail</td>
</tr>
<%
do{
	out.println("<tr><td>" + resultset.getString(1) +"</td>");
	out.println("<td>" + resultset.getString(2) +"</td>");
	out.println("<td>" + resultset.getString(3) +"</td>");
	out.println("<td>" + resultset.getString(4) +"</td>");
	out.println("<td>" + resultset.getString(5) +"</td>");
	out.println("<td><a href='/cbsatesting/seleniumResults/" + resultset.getString("testproject") + "/" + resultset.getString("resultfile") +".html'>Link to Result</a></td> ");
	out.println("<td>" + resultset.getString(7) +"</td>");
	out.println("<td>" + resultset.getString(8) +"</td>");
	out.println("<td>" + resultset.getString(9) +"</td></tr>");
}while(resultset.next());


			}
			
			resultset.close();
			statement.close();
			connection.close();
		}
		catch(Exception ex)
		{
			out.println("Unable to connect to database. </br>" + ex.getMessage());
		}
	%>	
	</table><br/><div class='alignRight'><button value='' dojoType='dijit.form.Button' id='backBtn' onClick='window.history.back()'>Back</button></div>	
</body>
</html>