<%@page import="java.util.StringTokenizer"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.sql.*"%>
<%@ page import="model.SessionManager" %>
<%@ page import="model.ProjectManager" %>
<%@ page import="model.Project" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
   response.setHeader( "Pragma", "no-cache" );
   response.setHeader( "Cache-Control", "no-cache" );
   response.setDateHeader( "Expires", 0 );
%>
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
<script>

function closeproject(obj)
{
len=document.contract.checkbox.length;
//alert(len);
// alert(String);
if(len==undefined){len=1;
	 	if(document.contract.checkbox.checked == true){
	
 		name = document.contract.checkbox.value; 
	//alert(name);
 		createHttpRequest(obj, '/cbsatesting/closeproject?name='+name);  

 	
 		}
}else{
	 for(k=0;k<len;k++)
	 {
	
 	if(document.contract.checkbox[k].checked == true){
	
 		name = document.contract.checkbox[k].value; 
	//alert(name);
	createHttpRequest(obj, '/cbsatesting/closeproject?name='+name);  
 		//window.open('TestProject/confirm_delete_env.jsp?checkBox='+checkBox+"&edit_type="+String);    
 		
 	}
 	
 } 
	 }
}

function createHttpRequest(obj, url)
{
    link=obj.href;
    if(typeof XMLHttpRequest != "undefined") {
        request = new XMLHttpRequest();     
    } 
    else if(window.ActiveXObject)   {
        request = new ActiveXObject("Msxml2.XMLHTTP");

        if(!request) {
            request = new ActiveXObject("Microsoft.XMLHTTP");
        }
    }


    if(request) {

       

       // request.onreadystatechange = handleRequest;
        request.open("POST",url,false);
        request.send(null);
        
        }
    else {
        alert("error on Page createHttpRequest");
    }   
    return true;

}

function refreshPage(){
    window.location.reload();
} 


</script>
</head>

<body>
<!-- start header -->
<div id="wrapper">
<div id="header"><h1>Manager Console</h1></div>
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
		      	ArrayList<Project> projects = pm.getProjectByOwner(user_name);
		      	for(int i=0;i<projects.size();i++){
		      		Project proj = projects.get(i);
					%>
			
				  	<tr>
                    <td > <input type="checkbox" name="checkbox" id="checkbox"<%=i%> value="<%=proj.Name%>" />
                    <%= proj.Name %></td>
				     <td><%=proj.Instance_id %></td>
				     <td><%=proj.Tools %></td>
				     <td><%=proj.ScriptPath %></td>
			       </tr>
			       <%}%>
			       <tr>
				
		       </tr>
			      </table>
			      <table>
			      <tr>
			      <td ></td>
			      <td></td>
			      <td><input name="close" id="close_project" type="button" value="Close" onclick="closeproject(this); return true; "/>
 			      <td><input name="refresh" id="refresh_project" type="button" value="Refresh" onclick="javascript:window.location='ManagerConsole.jsp'"/></td>
			      </tr></table>
				 <p>&nbsp;</p>
	          </form>
	
	         
<p>&nbsp;</p>
		  </div>
			
		</div>
		
	</div>
	<div>
	<!-- create new instance form -->
	<div id="wrapper">

<hr></hr>
<!-- end header -->
<!-- start page -->
<div id="page">
	<!-- start content -->
	<div id="content">
		<div class="post">
			<h3 class="title">Create a New Project</h3>
			<div class="entry">
			
				<form id="form2" method="post"action="/cbsatesting/openproject">
				<input type="hidden" name="owner" type="text" id="owner" size="15" value="<%=user_name%>"/>
				  <table width="500" border="0">
				  <tr>
				    <td style="width:250px"><strong>Name</strong></td>
				    <td><input name="name" type="text" id="name" size="15" /></td>
			      </tr>
			  	  <tr>
				    <td style="width:250px"><strong>AWS Instance ID</strong></td>
				    <td>
				    <!--<input name="instanceid" type="text" id="instanceid" size="15" />  -->
				    <%String dburl ="jdbc:mysql://localhost:3306/amazon_instance";
			Class.forName("com.mysql.jdbc.Driver");
 			Connection con = DriverManager.getConnection(dburl, "root", "root");
 			Statement statement = con.createStatement();
 			ResultSet rs = statement.executeQuery("SELECT Amazon_instance_id FROM instance WHERE (current_state is NULL or current_state <> 'terminate') and is_used = 'F';");
 			%>
				    <select name="instanceid" id="instanceid">
				    <% 
				    	while(rs.next()){
				    		String instanceId = rs.getString(1);%>
				    		 <option value="<%=instanceId%>"><%=instanceId%></option>
				    	<%}%>
				    
			          </select>
				    </td>
			      </tr>
				  <tr>
				    <td style="width:250px"><strong>Tools</strong></td>
				    <td>
				      <input name="tools" type="text" id="tools" size="25" value="Selenium, J-meter"/>
					</td>
				    </tr>
				  <tr>
				    <td style="width:250px"><strong>Testing Script Location</strong></td>
				    <td>
				      <input readonly="readonly" name="path " type="text" id="path" size="55" value="C:\LoadTestScript\Magento_Memo"/>
				    </td>
			      </tr>
				  <tr>
				    <td>
				    	<input type="submit" id="submit" value="submit" />
<!--  				    	<INPUT TYPE="button" VALUE="Back" onClick="history.go(-1);" />-->
				    </tr>
				    
				  </table>
				  	
               
<p>&nbsp;</p>
 
	          </form>
<p>&nbsp;</p>
	</div>
	<!-- end content -->
	<!-- start sidebar -->
		<!-- end sidebar -->
	<div style="clear: both;">&nbsp;</div>
</div>
<!-- end page -->
<!-- start footer -->

</div>
<!-- end footer -->
</body>
</html>
