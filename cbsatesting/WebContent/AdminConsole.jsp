<%@page import="java.util.StringTokenizer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.amazonaws.services.ec2.model.DescribeInstancesResult"%>
<%@page import="com.amazonaws.services.ec2.model.DescribeInstancesRequest"%>
<%@page import="com.amazonaws.auth.BasicAWSCredentials"%>
<%@page import="com.amazonaws.services.ec2.AmazonEC2Client"%>
<%@page import="com.amazonaws.auth.AWSCredentials"%>
<%@ page import="java.sql.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<% String myname =  (String)session.getAttribute("username");
	//String addresses = (String)session.getAttribute("addresses");
	//String email = (String)session.getAttribute("email");
	//String phone = (String)session.getAttribute("phone");
	String user_id = (String)session.getAttribute("user_id");
	String termination_success=(String)session.getAttribute("termination_success");
	String secretkey="K3Ed4zqXi/hIAoUVlPSW+ACKyHe0Zj4sCFfZ+Ni3"; //MNJ - replaced old with new secret key.
 	String accesskey="AKIAIJLOXRTX2UFEFXVQ";   //MNJ - replaced old with new access key.
 	String startdate=null;
	
	   AWSCredentials credentials = new BasicAWSCredentials(accesskey,secretkey);    
	   AmazonEC2Client ec2 = new AmazonEC2Client(credentials);
	  // ec2.setEndpoint("https://ec2.us-east-1.amazonaws.com"); 
	   
	   ec2.setEndpoint("https://ec2.us-west-1.amazonaws.com"); 
	   DescribeInstancesResult desc_result = new DescribeInstancesResult();
	   DescribeInstancesRequest desc_request = new DescribeInstancesRequest();
	
	
	if(termination_success=="termination_success")
	{
		Thread.sleep(3000);
		response.sendRedirect("AdminConsole.jsp");
		session.removeAttribute("termination_success");
	}
	
	%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>CBSA Account update</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="project.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="javascript">
 
		function temp(String)
	 {
	 len=document.contract.checkbox.length;
	//alert(len);
	// alert(String);
	if(len==undefined)
		{len=1;
	 	 	if(document.contract.checkbox.checked == true){
			
		 		checkBox = document.contract.checkbox.value; 
			//alert(checkBox);
		 		window.open('TestProject/confirm_delete_env.jsp?checkBox='+checkBox+"&edit_type="+String);  
		
		 	
		 }}else
			 {
			 for(k=0;k<len;k++)
			 {
			
		 	if(document.contract.checkbox[k].checked == true){
			
		 		checkBox = document.contract.checkbox[k].value; 
			//alert(checkBox);
		 		window.open('TestProject/confirm_delete_env.jsp?checkBox='+checkBox+"&edit_type="+String);    
		 		
		 	}
		 	
		 } 
			 }
	 }
 function delete_env(String)
 {
if(String=="start"){var r= confirm('Are you sure you want to start instance?');}
if(String=="stop"){var r= confirm('Are you sure you want to stop instance?');}
if(String=="terminate"){
var r= confirm('Are you sure you want to terminate instance?');}
	 if(r == true)
		 {
		// alert('you selected yes');
		temp(String);
		alert('Please refresh to get latest status');
		return true;}
			
	 else(r==false)
	 {return false;
		}
 }

 
</script>
 <script type="text/javascript" language="javascript">
 <!--
		function check() {
	 if(document.forms.form2.os.value=="windows2008r2" && document.forms.form2.architecture.value=="32")
	 {
	 alert("Please select an instance with 64 Bit architecure. Windows 2008 R2 doesnt support x86.");
	 return false;
	 }
	 if(document.forms.form2.instance_type.value=="select_instance" && document.forms.form2.os.value=="select_os")
		 {
		 alert("Please select Instance type and OS");
		 return false;
		 }
}
 function ok() {
	 	
	 	var r =confirm("Are you sure you want to create an instance?");
	 	if(r ==true)
	 		{<%session.setAttribute("ok","ok");%>
	 		window.location="TestProject.html";
	 		return true;}
	 	
	 	else 
	 		return false;
	 }
function Choice()
{
	switch (document.forms.form2.instance_type.value){

case "m1.small":
	document.forms.form2.memory.value="1.7";
	document.forms.form2.architecture.value="32";
	document.forms.form2.cpu.value="1";
	document.forms.form2.storage.value="160";
	break;
case "m1.large":
	document.forms.form2.memory.value="7.5";
	document.forms.form2.architecture.value="64";
	document.forms.form2.cpu.value="4";
	document.forms.form2.storage.value="850";
	break;
case "m1.xlarge":
	document.forms.form2.memory.value="15";
	document.forms.form2.architecture.value="64";
	document.forms.form2.cpu.value="8";
	document.forms.form2.storage.value="1690";
	break;
case "t1.micro":
	document.forms.form2.memory.value="0.613";
	document.forms.form2.architecture.value="32";
	document.forms.form2.cpu.value="2";
	document.forms.form2.storage.value="32";
	break;
case "m2.xlarge":
	document.forms.form2.memory.value="17.1";
	document.forms.form2.architecture.value="64";
	document.forms.form2.cpu.value="6";
	document.forms.form2.storage.value="420";
	break;
case "c1.medium":
	document.forms.form2.memory.value="1.7";
	document.forms.form2.architecture.value="32";
	document.forms.form2.cpu.value="5";
	document.forms.form2.storage.value="320";
	break;
case "c1.xlarge":
	document.forms.form2.memory.value="7";
	document.forms.form2.architecture.value="20";
	document.forms.form2.cpu.value="20";
	document.forms.form2.storage.value="1690";
	break;
default : ;



	}

}

<%
if((String)session.getAttribute("instance_create_success")==("instance_create_success"))
		{%>
		alert("You have successfully created an instance. Please read contract carefully\n\nPassword generation and encryption can sometimes take more than 30 minutes.\n\nPlease wait at least 15 minutes after launching an instance before trying to retrieve the generated password");
		// window.open("generate_contract.jsp"); 
		<%
	session.removeAttribute("instance_create_success");
		} %>
 //-->
</script>
</head>
<body>
<!-- start header -->
<div id="wrapper">
<div id="logo">
	
	<h2>
	</h2>
</div>
<div id="header">
<h1>Admin Console</h1>
</div>
<hr></hr>
<!-- end header -->
<!-- start page -->
<div id="page">
	<!-- start content -->
	<div id="content" style="width: auto;">
		<div class="post">
			<h3 class="title">Environment Details</h3>
			<div class="entry">
			<%String dburl ="jdbc:mysql://localhost:3306/amazon_instance";
			Class.forName("com.mysql.jdbc.Driver");
 			Connection con = DriverManager.getConnection(dburl, "root", "root");
 			Statement statement = con.createStatement();
 			ResultSet rs = statement.executeQuery("SELECT * FROM instance;");
 			%>

		<form name="contract" id="form1" method="post"  >
			 <table border="1" cellspacing="1" cellpadding="1">
			  	<tr>
                
				 <td  ><strong>   Instance  Id  </strong></td>
				 <td ><strong>Public DNS Name</strong></td>
			     <td><strong>OS</strong></td>
			     <td><strong>Memory</strong></td>
			     <td><strong>Storage</strong></td>
			     <td><strong>Signed By</strong></td>
			     <td><strong>Date Created</strong></td>
			     <td><strong>Client </strong></td>
			     <td><strong>Current Status</strong></td>
		       </tr>
		      <%int i=0;
 			while(rs.next())
 			{
 			ArrayList<String> instancesId = new ArrayList<String>();
 		
 				String current_state = rs.getString("current_state");
 				System.out.print("current_state: " +current_state);
 				if(current_state != null && current_state.equalsIgnoreCase("terminate")){     //MNJ - added:  current_state!=null && 
 					System.out.println("In terminated state");
 				}
 				else
 				{ 					
 					System.out.println("Not in terminated state");
 					instancesId.add(rs.getString("amazon_instance_id"));
 		 			desc_request.setInstanceIds(instancesId);
 		 			
 		 			System.out.print(instancesId);
 		 			//error
 		 			desc_result= ec2.describeInstances(desc_request);	
 		 			String tempStr = desc_result.toString();			
 			  //StringTokenizer stringTokenizer = new StringTokenizer(tempStr);    			
	          //System.out.print(stringTokenizer.toString());
 			  System.out.print("Temp Str: " + tempStr);
				String[] arr1 = tempStr.split("State: ");
				String[] arr2 = arr1[1].split(",");
				String[]  arr3 = arr2[1].split(":");
			//	out.println(desc_result.toString());
			String[] arr4 = desc_result.toString().split("PublicDnsName: ");
			String[] arr5 = arr4[1].split(",");
			
				//ut.println("PubcliDnsName:"+ arr5[0]);
				startdate = rs.getString("date_signed");				
				session.setAttribute("startdate", startdate);
			%>
			
				  	<tr>
                    <td > <input type="checkbox" name="checkbox" id="checkbox"<%=i%> value="<%=rs.getString("amazon_instance_id") %>" />
                    <%=rs.getString("amazon_instance_id") %></td>
				     <td><%=arr5[0]%></td>
				     <td><%=rs.getString("os") %></td>
				     <td><%=rs.getString("ram") %></td>
				     <td><%=rs.getString("storage") %></td>
				     <td><%=rs.getString("signed_by") %></td>
				     <td><%=rs.getString("Start_date") %></td>
				     <td><%=rs.getString("client_name") %></td>
				     <td><%=arr3[1]%></td>				     			 
			       </tr><%}i++;
			       }rs.close(); %>
			       <tr>
				
		       </tr>
			      </table>
			      <table>
			      <tr>
			      <td ></td>
			      <td></td>
			      <td><input name="stop" id="stop_instance" type="button" value="Stop" onclick="delete_env('stop')"/></td>
			      <td><input name="start" id="start_instance" type="button" value="Start" onclick="delete_env('start')"/></td>
			      <td><input name="terminate" id="terminate_instance" type="button" value="Terminate" onclick="delete_env('terminate')"/></td>
			      <td><input name="refresh" id="refresh_instance" type="button" value="Refresh" onclick="javascript:window.location='AdminConsole.jsp'"/></td>
			      </tr></table>
				 <p>&nbsp;</p>
	          </form>
	          <%
 
String select[] = request.getParameterValues("radio"); 
if (select != null && select.length != 0) {
out.println("You have selected: ");
for (int m = 0; m< select.length; m++) {
out.println(select[i]); 
}
} 
con.close();
%>
	         
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
			<h3 class="title">Create a New Instance</h3>
			<div class="entry">
			
				<form id="form2" method="post"action="TestProject/create_test_env.jsp">
				  <table width="250" border="0">
				  <tr>
				    <td><label><strong>OS</strong></label></td>
				    <td><label>
				      <select name="os" id="os" onchange="Choice();">
						<option value="select_os">Select OS</option>
				        <option value="Magento">Magento</option>
				      </select>
				    </label></td>
			      </tr>
				  <tr>
				    <td><strong>Instance Type</strong></td>
				    <td><label>
				      <select name="instance_type" id="instance_type" onchange="Choice();">
				        <option value="select_instance">Select Instance</option>
				         <option value="t1.micro">T1.Micro</option>
				        <option value="m1.small">M1.Small</option>
				        <option value="m1.large">M1.Large</option>
				        <option value="m1.xlarge">M1.Xlarge</option>
				        <option value="m2.xlarge">M2.Xlarge</option>
				        <option value="c1.medium">C1.Medium</option>
				        <option value="c1.xlarge">C1.Xlarge</option>
			          </select>
			        </label></td>
			      </tr>
				  <tr>
				    <td><strong>Memory</strong></td>
				    <td><label>
				      <input readonly="readonly"  name="memory" type="text" id="memory" size="15" />
				    </label></td>
				    </tr>
				  <tr>
				    <td><strong>Architecture (Bit)</strong></td>
				    
				    <td><label>
				      <input readonly="readonly" name="architecture " type="text" id="architecture" size="15" />
				      
				    </label></td>
			      </tr>
				  <tr>
				    <td><strong>Computing (core)</strong></td>
				    <td><label>
				      <input readonly="readonly"  name="cpu" type="text" id="cpu" size="15" />
			        </label></td>
			      </tr>
				  <tr>
				    <td><strong>Storage</strong></td>
				    <td><input readonly="readonly"  name="storage" type="text" id="storage" size="15" />
			        </td>
			      </tr>
				  
				  <tr>
				    <td><strong>Delivery Date</strong></td>
				    <td><input readonly="readonly"  name="current_date" type="text" id="current_date" size="15" /></td>
				    </tr>
				  <tr>
				    <td>&nbsp;</td>
				    <td>
				    	<input type="submit" id="submit" value="submit" />
				    	<INPUT TYPE="button" VALUE="Back" onClick="history.go(-1);" />
				    <!--  <input name="confirm" type="submit" onClick="return ok()"  id="confirm" value="confirm" /> --></td>
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
