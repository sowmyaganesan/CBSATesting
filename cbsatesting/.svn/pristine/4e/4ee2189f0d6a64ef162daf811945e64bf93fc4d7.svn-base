<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport"
	content="width=device-width, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">

<title>Scalability Monitor</title>

<link rel="stylesheet" type="text/css"
	href="/cbsatesting/script/dijit/themes/claro/claro.css" />
<link rel="stylesheet" type="text/css"
	href="/cbsatesting/style/main.css" />

<script src="/cbsatesting/script/dojo/dojo.js"
	djConfig="parseOnLoad: true"></script>
<script src="three.js"></script>
<script src="Detector.js"></script>
<script src="TrackballControls.js"></script>
<script src="THREEx.KeyboardState.js"></script>
<script src="THREEx.FullScreen.js"></script>
<script src="THREEx.WindowResize.js"></script>

<!-- load fonts -->

<script src="helvetiker_regular.typeface.js"></script>
<script type="text/javascript"> 
			
			function displayModel(modelName)
			{
				var name = modelName.replace(/ /g, '%20');
				document.location.href = "/cbsatesting/Scalability/ScalabilityModels/DisplayModel.jsp?Name=" + name;
			}
		</script>
		
<body class="claro">
	<p>
		<a href="../Performance/SaaSEvaluation.jsp"> SaaS Evaluation> </a> <a
			href="../ScalabilityValidation.jsp"> Scalability Validation> </a> <a
			href="../ScalabilityMonitor.jsp"> Scalability Monitor> </a> View
		Polygon
	</p>

	<h2>
		Model Monitor: <a href="#"
			onClick="displayModel('<%=request.getParameter("Name")%>')"> <%=request.getParameter("Name")%>
		</a>
	</h2>
	<%
		if (!(request.getParameter("Name").equalsIgnoreCase("CRAM") || (request
				.getParameter("Name").equalsIgnoreCase("CRUM")) || (request.getParameter("Name").equalsIgnoreCase("SEC") ))) {
			out.println("<p id='message'>There is no  3D graph but the models provided give other graphs</p>");
		} else {
			
			out.println("<div id=\"chart\" style=\"width: 1000px; height: 500px;background-color: #ffffcc;\"></div>");
		}
	%>

	<script type="text/javascript">
	   dojo.require("dojox.charting.Chart2D");
	   dojo.require("dojox.charting.themes.Wetland");
	   dojo.require("dojox.charting.widget.SelectableLegend");
	   dojo.require("dojox.charting.action2d.Tooltip");
	   
	   var modelName="<%=request.getParameter("Name")%>";
		var container, scene, camera, renderer, controls, stats;
		var keyboard = new THREEx.KeyboardState();
		var clock = new THREE.Clock();

		function init() {
			// SCENE
			scene = new THREE.Scene();

			// CAMERA
			var SCREEN_WIDTH = window.innerWidth, SCREEN_HEIGHT = window.innerHeight;
			var VIEW_ANGLE = 45, ASPECT = SCREEN_WIDTH / SCREEN_HEIGHT, NEAR = 0.1, FAR = 20000;
			camera = new THREE.PerspectiveCamera(VIEW_ANGLE, ASPECT, NEAR, FAR);
			scene.add(camera);
			camera.position.set(0, 200, 200);
			camera.lookAt(scene.position);
			// RENDERER
			if (Detector.webgl)
				renderer = new THREE.WebGLRenderer({
					antialias : true
				});
			else
				renderer = new THREE.CanvasRenderer();
			renderer.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

			container = document.getElementById("chart");

			// document.body.appendChild( container );
			container.appendChild(renderer.domElement);
			
			// CONTROLS
			controls = new THREE.TrackballControls(camera);
			
		}
		
		
		function add3dText(key1,key2,key3){
			
			var materialFront = new THREE.MeshBasicMaterial({
				color : 0xff0000
			});
			var materialSide = new THREE.MeshBasicMaterial({
				color : 0x000088
			});
			var materialArray = [ materialFront, materialSide ];
			console.log("setup the arrays and THREE objects");
			var textGeom1 = new THREE.TextGeometry(key1, {
				size : 8,
				height : 1,
				curveSegments : 3,
				font : "helvetiker",
				weight : "normal",
				style : "normal",
				bevelThickness : 1,
				bevelSize : 2,
				bevelEnabled : false,
				material : 0,
				extrudeMaterial : 1
			});
			// font: helvetiker, gentilis, droid sans, droid serif, optimer
			// weight: normal, bold

			var textMaterial = new THREE.MeshFaceMaterial(materialArray);
			var textMesh = new THREE.Mesh(textGeom1, textMaterial);

			textMesh.position.set(69.28, 0, -40);
			textMesh.rotation.x = -Math.PI / 4;
			scene.add(textMesh);

			var textGeom2 = new THREE.TextGeometry(key2, {
				size : 6,
				height : 1,
				curveSegments : 3,
				font : "helvetiker",
				weight : "normal",
				style : "normal",
				bevelThickness : 1,
				bevelSize : 2,
				bevelEnabled : false,
				material : 0,
				extrudeMaterial : 1
			});
			
			var textMesh2 = new THREE.Mesh(textGeom2, textMaterial);

			textMesh2.position.set(-69.28, 0, -40);
			textMesh2.rotation.x = -Math.PI / 4;
			scene.add(textMesh2);

			var textGeom3 = new THREE.TextGeometry(key3, {
				size : 6,
				height : 1,
				curveSegments : 3,
				font : "helvetiker",
				weight : "normal",
				style : "normal",
				bevelThickness : 1,
				bevelSize : 2,
				bevelEnabled : false,
				material : 0,
				extrudeMaterial : 1
			});
			
			var textMesh3 = new THREE.Mesh(textGeom3, textMaterial);

			textMesh3.position.set(0,0,80);
			textMesh3.rotation.x = -Math.PI / 4;
			scene.add(textMesh3);

			var textGeom4 = new THREE.TextGeometry("Time", {
				size : 6,
				height : 1,
				curveSegments : 3,
				font : "helvetiker",
				weight : "normal",
				style : "normal",
				bevelThickness : 1,
				bevelSize : 2,
				bevelEnabled : false,
				material : 0,
				extrudeMaterial : 1
			});
			
			var textMesh4 = new THREE.Mesh(textGeom4, textMaterial);

			textMesh4.position.set(0, 80, 0);
			textMesh4.rotation.x = -Math.PI / 4;
			scene.add(textMesh4);

			
		}

function add5dText(key1,key2,key3,key4,key5){
			
			var materialFront = new THREE.MeshBasicMaterial({
				color : 0xff0000
			});
			var materialSide = new THREE.MeshBasicMaterial({
				color : 0x000088
			});
			var materialArray = [ materialFront, materialSide ];
			var textGeom1 = new THREE.TextGeometry(key1, {
				size : 6,
				height : 1,
				curveSegments : 3,
				font : "helvetiker",
				weight : "normal",
				style : "normal",
				bevelThickness : 1,
				bevelSize : 2,
				bevelEnabled : false,
				material : 0,
				extrudeMaterial : 1
			});

			var textMaterial = new THREE.MeshFaceMaterial(materialArray);
			var textMesh = new THREE.Mesh(textGeom1, textMaterial);

			textMesh.position.set(0, 0, 100);
			textMesh.rotation.x = -Math.PI / 4;
			scene.add(textMesh);

			var textGeom2 = new THREE.TextGeometry(key2, {
				size : 6,
				height : 1,
				curveSegments : 3,
				font : "helvetiker",
				weight : "normal",
				style : "normal",
				bevelThickness : 1,
				bevelSize : 2,
				bevelEnabled : false,
				material : 0,
				extrudeMaterial : 1
			});
			
			var textMesh2 = new THREE.Mesh(textGeom2, textMaterial);

			textMesh2.position.set(0.951*100, 0, 0.309*100);
			textMesh2.rotation.x = -Math.PI / 4;
			scene.add(textMesh2);

			var textGeom3 = new THREE.TextGeometry(key3, {
				size : 6,
				height : 1,
				curveSegments : 3,
				font : "helvetiker",
				weight : "normal",
				style : "normal",
				bevelThickness : 1,
				bevelSize : 2,
				bevelEnabled : false,
				material : 0,
				extrudeMaterial : 1
			});
			
			var textMesh3 = new THREE.Mesh(textGeom3, textMaterial);

			textMesh3.position.set(0.588*100, 0, -0.809*100);
			textMesh3.rotation.x = -Math.PI / 4;
			scene.add(textMesh3);

			var textGeom4 = new THREE.TextGeometry(key4, {
				size : 6,
				height : 1,
				curveSegments : 3,
				font : "helvetiker",
				weight : "normal",
				style : "normal",
				bevelThickness : 1,
				bevelSize : 2,
				bevelEnabled : false,
				material : 0,
				extrudeMaterial : 1
			});
			
			var textMesh4 = new THREE.Mesh(textGeom4, textMaterial);

			textMesh4.position.set(-0.588*100, 0, -0.809*100);
			textMesh4.rotation.x = -Math.PI / 4;
			scene.add(textMesh4);
			
			var textGeom4 = new THREE.TextGeometry(key5, {
				size : 6,
				height : 1,
				curveSegments : 3,
				font : "helvetiker",
				weight : "normal",
				style : "normal",
				bevelThickness : 1,
				bevelSize : 2,
				bevelEnabled : false,
				material : 0,
				extrudeMaterial : 1
			});
			
			var textMesh4 = new THREE.Mesh(textGeom4, textMaterial);

			textMesh4.position.set(-0.951*100, 0, 0.309*100);
			textMesh4.rotation.x = -Math.PI / 4;
			scene.add(textMesh4);

			var textGeom4 = new THREE.TextGeometry("Time", {
				size : 6,
				height : 1,
				curveSegments : 3,
				font : "helvetiker",
				weight : "normal",
				style : "normal",
				bevelThickness : 1,
				bevelSize : 2,
				bevelEnabled : false,
				material : 0,
				extrudeMaterial : 1
			});
			
			var textMesh4 = new THREE.Mesh(textGeom4, textMaterial);

			textMesh4.position.set(0, 100, 0);
			textMesh4.rotation.x = -Math.PI / 4;
			scene.add(textMesh4);
		}

		
		function plotTuble(x, y, z, v) {
			var colorCode = Math.random()*0xffffff;
   var geometry = new THREE.Geometry();
   var geometry1 = new THREE.Geometry();
   var geometry2 = new THREE.Geometry();
   var geometry3 = new THREE.Geometry();
   var materialFront = new THREE.MeshBasicMaterial( { color: 0x000000 } );
	var materialSide = new THREE.MeshBasicMaterial( { color: 0x000088 } );
	var materialArray = [ materialFront, materialSide ];
   var wireframeMaterial = new THREE.MeshBasicMaterial( { color: colorCode, transparent: false, opacity: 1 } ); 
    


  // geometry.vertices.push( new THREE.Vector3( 0, v, 0 ) );
	 geometry.vertices.push( new THREE.Vector3( 1.732*x, 0, -x ) );
	 geometry.vertices.push( new THREE.Vector3( 1.732*x, 20, -x ) );
	 geometry.vertices.push( new THREE.Vector3( -1.732*y, 20, -y) );
	 geometry.vertices.push( new THREE.Vector3( -1.732*y, 0, -y) );
	 geometry.faces.push( new THREE.Face4( 0, 1, 2, 3) );
	 var obj = new THREE.Mesh( geometry.clone(), wireframeMaterial );
	 obj.position.set(0,v-20,0);
   	 scene.add(obj);

// geometry1.vertices.push( new THREE.Vector3( 0, v, 0 ) );
   geometry1.vertices.push( new THREE.Vector3( 0, 0, 2*z) );
    geometry1.vertices.push( new THREE.Vector3( 0, 20, 2*z) );
     geometry1.vertices.push( new THREE.Vector3( 1.732*x, 20, -x ) );
   
   geometry1.vertices.push( new THREE.Vector3( 1.732*x, 0, -x ) );
   geometry1.faces.push( new THREE.Face4( 0, 1, 2,3 ) );
  
	var obj1 = new THREE.Mesh( geometry1.clone(), wireframeMaterial );
	obj1.position.set(0,v-20,0);
    scene.add(obj1);
   
   //geometry2.vertices.push( new THREE.Vector3( 0, v, 0 ) );
   
   geometry2.vertices.push( new THREE.Vector3( -1.732*y, 0, -y) );
   geometry2.vertices.push( new THREE.Vector3( -1.732*y, 20, -y) );
   geometry2.vertices.push( new THREE.Vector3( 0, 20, 2*z) );
   geometry2.vertices.push( new THREE.Vector3( 0, 0, 2*z) );
   geometry2.faces.push( new THREE.Face4( 0, 1, 2,3 ) );
 
var obj2 = new THREE.Mesh( geometry2.clone(), wireframeMaterial );
obj2.position.set(0,v-20,0);
    scene.add(obj2);
    geometry3.vertices.push( new THREE.Vector3( 1.732*x, 20, -x ) );
    geometry3.vertices.push( new THREE.Vector3( -1.732*y, 20, -y) );
    geometry3.vertices.push( new THREE.Vector3( 0, 20, 2*z) );
    geometry3.faces.push( new THREE.Face3( 0, 1, 2) );
 
var obj3 = new THREE.Mesh( geometry3.clone(), wireframeMaterial );
obj3.position.set(0,v-20,0);
    scene.add(obj3);

    var text1 = new THREE.TextGeometry( x, 
	{
		size: 6, height: 1, curveSegments: 1,
		font: "helvetiker", weight: "normal", style: "normal"
	});
	var textMaterial1 = new THREE.MeshFaceMaterial(materialArray);
	var textMesh1 = new THREE.Mesh(text1, textMaterial1 );
	
	
	textMesh1.position.set( 1.732*x-5, v, -x-5);
	textMesh1.rotation.x = -Math.PI / 4;
	scene.add(textMesh1);

	var text2 = new THREE.TextGeometry( y, 
	{
		size: 6, height: 1, curveSegments: 1,
		font: "helvetiker", weight: "normal", style: "normal"
	});
	var textMaterial2 = new THREE.MeshFaceMaterial(materialArray);
	var textMesh2 = new THREE.Mesh(text2, textMaterial2 );
	
	
	textMesh2.position.set( -1.732*y-5, v, -y-5);
	textMesh2.rotation.x = -Math.PI / 4;
	scene.add(textMesh2);
  
  var text3 = new THREE.TextGeometry( z, 
	{
		size: 6, height: 1, curveSegments: 1,
		font: "helvetiker", weight: "normal", style: "normal"
	});
	var textMaterial3 = new THREE.MeshFaceMaterial(materialArray);
	var textMesh3 = new THREE.Mesh(text3, textMaterial3 );
	
	
	textMesh3.position.set( 0, v, 2*z-5);
	textMesh3.rotation.x = -Math.PI / 4;
	scene.add(textMesh3);
  
		}

		function plotaxis(axisLength) {
			//Shorten the vertex function
			function v(x, y, z) {
				return new THREE.Vector3(x, y, z);

			}

			//Create axis (point1, point2, colour)
			function createAxis(p1, p2, color) {
				var line, lineGeometry = new THREE.Geometry(), lineMat = new THREE.LineBasicMaterial(
						{
							color : color,
							lineWidth : 10
						});
				lineGeometry.vertices.push(p1, p2);
				line = new THREE.Line(lineGeometry, lineMat);
				scene.add(line);

			}

			createAxis(v(0, 0, 0), v(0, 0, 2 * axisLength), 0xFF0000);
			createAxis(v(0, 0, 0), v(1.732 * axisLength, 0, -axisLength),
					0x00FF00);
			createAxis(v(0, 0, 0), v(-1.732 * axisLength, 0, -axisLength),
					0x0000FF);
			createAxis(v(0, 0, 0), v(0, 2 * axisLength, 0), 0xFF0FF0);

		};
		
		function plotTuble5d(x, y, z, a, b, v) {
			   var colorCode = Math.random()*0xffffff;
			   var geometry = new THREE.Geometry();
			   var geometry1 = new THREE.Geometry();
			   var geometry2 = new THREE.Geometry();
			   var geometry3 = new THREE.Geometry();
			   var geometry4 = new THREE.Geometry();
			   var geometry5 = new THREE.Geometry();
			   var geometry6 = new THREE.Geometry();
			   var geometry7 = new THREE.Geometry();
			   var geometry8 = new THREE.Geometry();
			   var geometry9 = new THREE.Geometry();
			   var material = new THREE.LineBasicMaterial( { color: colorCode, linewidth: 10 } );
			  var materialFront = new THREE.MeshBasicMaterial( { color: 0x000000 } );
				var materialSide = new THREE.MeshBasicMaterial( { color: 0x000088 } );
				var materialArray = [ materialFront, materialSide ];
   				var wireframeMaterial = new THREE.MeshBasicMaterial( { color: colorCode, transparent: false, opacity: 1 } ); 
    
			  

			   geometry.vertices.push( new THREE.Vector3( 0, 0, x ) );

			   geometry.vertices.push( new THREE.Vector3( 0, 20, x ) );
			   geometry.vertices.push( new THREE.Vector3( 0.951*y, 20, 0.309*y ) );
			   geometry.vertices.push( new THREE.Vector3( 0.951*y, 0, 0.309*y ) );
			   geometry.faces.push( new THREE.Face4( 0, 1, 2, 3) );
	 			var obj = new THREE.Mesh( geometry.clone(), wireframeMaterial );
	 			obj.position.set(0,v-20,0);
   				scene.add(obj);

			    geometry1.vertices.push( new THREE.Vector3( 0.951*y, 0, 0.309*y ) );
			   geometry1.vertices.push( new THREE.Vector3( 0.951*y, 20, 0.309*y ) );
			   geometry1.vertices.push( new THREE.Vector3( 0.588*z, 20, -0.809*z) );
			   geometry1.vertices.push( new THREE.Vector3( 0.588*z, 0, -0.809*z) );
			   geometry1.faces.push( new THREE.Face4( 0, 1, 2, 3) );
			   var obj1 = new THREE.Mesh( geometry1.clone(), wireframeMaterial );
	 			obj1.position.set(0,v-20,0);
   				scene.add(obj1);

			   geometry2.vertices.push( new THREE.Vector3( 0.588*z, 0, -0.809*z) );
			   geometry2.vertices.push( new THREE.Vector3( 0.588*z, 20, -0.809*z) );
			   geometry2.vertices.push( new THREE.Vector3( -0.588*a, 20, -0.809*a) );
			   geometry2.vertices.push( new THREE.Vector3( -0.588*a, 0, -0.809*a) );
			   geometry2.faces.push( new THREE.Face4( 0, 1, 2, 3) );
			   var obj2 = new THREE.Mesh( geometry2.clone(), wireframeMaterial );
	 			obj2.position.set(0,v-20,0);
   				scene.add(obj2);

			   geometry3.vertices.push( new THREE.Vector3( -0.588*a, 0, -0.809*a) );
			   geometry3.vertices.push( new THREE.Vector3( -0.588*a, 20, -0.809*a) );
			   geometry3.vertices.push( new THREE.Vector3( -0.951*b, 20, 0.309*b) );
			   geometry3.vertices.push( new THREE.Vector3( -0.951*b, 0, 0.309*b) );
			   geometry3.faces.push( new THREE.Face4( 0, 1, 2, 3) );
			   var obj3 = new THREE.Mesh( geometry3.clone(), wireframeMaterial );
	 			obj3.position.set(0,v-20,0);
   				scene.add(obj3);

			   geometry4.vertices.push( new THREE.Vector3( -0.951*b, 0, 0.309*b) );
			   geometry4.vertices.push( new THREE.Vector3( -0.951*b, 20, 0.309*b) );
			   geometry4.vertices.push( new THREE.Vector3( 0, 20, x  ) );
			   geometry4.vertices.push( new THREE.Vector3( 0, 0, x  ) );
			   geometry4.faces.push( new THREE.Face4( 0, 1, 2, 3) );
			   var obj4 = new THREE.Mesh( geometry4.clone(), wireframeMaterial );
	 			obj4.position.set(0,v-20,0);
   				scene.add(obj4);

			   
			   geometry5.vertices.push( new THREE.Vector3( 0, v, x ) );
			   geometry5.vertices.push( new THREE.Vector3( 0.951*y, v, 0.309*y ) );
			   geometry5.vertices.push(new THREE.Vector3(0,v,0));
			   geometry5.faces.push( new THREE.Face3( 0, 1, 2) );
			   var obj5 = new THREE.Mesh( geometry5.clone(), wireframeMaterial );
	 			obj5.position.set(0,0,0);
   				scene.add(obj5);

   				geometry6.vertices.push( new THREE.Vector3( 0.951*y, v, 0.309*y ) );
   				geometry6.vertices.push( new THREE.Vector3( 0.588*z, v, -0.809*z) );
   				geometry6.vertices.push(new THREE.Vector3(0,v,0));
			   geometry6.faces.push( new THREE.Face3( 0, 1, 2) );
			   var obj6 = new THREE.Mesh( geometry6.clone(), wireframeMaterial );
	 			obj6.position.set(0,0,0);
   				scene.add(obj6);

   				geometry7.vertices.push( new THREE.Vector3( 0.588*z, v, -0.809*z) );
   				geometry7.vertices.push( new THREE.Vector3( -0.588*a, v, -0.809*a) );
   				geometry7.vertices.push(new THREE.Vector3(0,v,0));
			   geometry7.faces.push( new THREE.Face3( 0, 1, 2) );
			   var obj7 = new THREE.Mesh( geometry7.clone(), wireframeMaterial );
	 			obj7.position.set(0,0,0);
   				scene.add(obj7);
			  
			  geometry8.vertices.push( new THREE.Vector3( -0.588*a, v, -0.809*a) );
			  geometry8.vertices.push( new THREE.Vector3( -0.951*b, v, 0.309*b) );
			  geometry8.vertices.push(new THREE.Vector3(0,v,0));
			   geometry8.faces.push( new THREE.Face3( 0, 1, 2) );
			   var obj8 = new THREE.Mesh( geometry8.clone(), wireframeMaterial );
	 			obj8.position.set(0,0,0);
   				scene.add(obj8);

   				geometry9.vertices.push( new THREE.Vector3( -0.951*b, v, 0.309*b) );
			   	geometry9.vertices.push( new THREE.Vector3( 0, v, x ) );
			   	geometry9.vertices.push(new THREE.Vector3(0,v,0));
			   geometry9.faces.push( new THREE.Face3( 0, 1, 2) );
			   var obj9 = new THREE.Mesh( geometry9.clone(), wireframeMaterial );
	 			obj9.position.set(0,0,0);
   				scene.add(obj9);

   				var text1 = new THREE.TextGeometry( x, 
				{
					size: 6, height: 1, curveSegments: 1,
					font: "helvetiker", weight: "normal", style: "normal"
				});
				var textMaterial1 = new THREE.MeshFaceMaterial(materialArray);
				var textMesh1 = new THREE.Mesh(text1, textMaterial1 );
				
				
				textMesh1.position.set( 0, v, x-5);
				textMesh1.rotation.x = -Math.PI / 4;
				scene.add(textMesh1);

				var text2 = new THREE.TextGeometry( y, 
				{
					size: 6, height: 1, curveSegments: 1,
					font: "helvetiker", weight: "normal", style: "normal"
				});
				var textMaterial2 = new THREE.MeshFaceMaterial(materialArray);
				var textMesh2 = new THREE.Mesh(text2, textMaterial2 );
				
				
				textMesh2.position.set( 0.951*y-5, v, 0.309*y-5);
				textMesh2.rotation.x = -Math.PI / 4;
				scene.add(textMesh2);
			  
			  var text3 = new THREE.TextGeometry( z, 
				{
					size: 6, height: 1, curveSegments: 1,
					font: "helvetiker", weight: "normal", style: "normal"
				});
				var textMaterial3 = new THREE.MeshFaceMaterial(materialArray);
				var textMesh3 = new THREE.Mesh(text3, textMaterial3 );
				
				
				textMesh3.position.set( 0.588*z-5, v, -0.809*z-5);
				textMesh3.rotation.x = -Math.PI / 4;
				scene.add(textMesh3);

				var text4 = new THREE.TextGeometry( a, 
				{
					size: 6, height: 1, curveSegments: 1,
					font: "helvetiker", weight: "normal", style: "normal"
				});
				var textMaterial4 = new THREE.MeshFaceMaterial(materialArray);
				var textMesh4 = new THREE.Mesh(text4, textMaterial4 );
				
				
				textMesh4.position.set( -0.588*a-5, v, -0.809*a-5);
				textMesh4.rotation.x = -Math.PI / 4;
				scene.add(textMesh4);

			  var text5 = new THREE.TextGeometry( b, 
				{
					size: 6, height: 1, curveSegments: 1,
					font: "helvetiker", weight: "normal", style: "normal"
				});
				var textMaterial5 = new THREE.MeshFaceMaterial(materialArray);
				var textMesh5 = new THREE.Mesh(text5, textMaterial5 );
				
				
				textMesh5.position.set( -0.951*b-5, v, 0.309*b-5);
				textMesh5.rotation.x = -Math.PI / 4;
				scene.add(textMesh5);
  
			}


		function plotaxis5d(axisLength){
			   //Shorten the vertex function
			   function v(x,y,z){ 
			      return new THREE.Vertex(new THREE.Vector3(x,y,z)); 
			   }

			   //Create axis (point1, point2, colour)
			   function createAxis(p1, p2, color){
			      var line, lineGeometry = new THREE.Geometry(),
			      lineMat = new THREE.LineBasicMaterial({color: color, lineWidth: 10});
			      lineGeometry.vertices.push(p1, p2);
			      line = new THREE.Line(lineGeometry, lineMat);
			      scene.add(line);
			   }

			   createAxis(v(0, 0, 0), v(0, 0, axisLength), 0xFF0000);
			   createAxis(v(0, 0, 0), v(0.951*axisLength, 0, 0.309*axisLength), 0x0FF00F);
			   createAxis(v(0, 0, 0), v(0.588*axisLength, 0, -0.809*axisLength), 0x00FFF0);
			   createAxis(v(0, 0, 0), v(-0.588*axisLength, 0, -0.809*axisLength), 0xF00FFF);
			   createAxis(v(0, 0, 0), v(-0.951*axisLength, 0, 0.309*axisLength), 0xFF00FF);
			   createAxis(v(0, 0, 0), v(0, axisLength, 0), 0xFFFFFF);



			};
			
		function animate() {
			requestAnimationFrame(animate);
			render();
			update();
		}

		function update() {

			controls.update();

		}

		function render() {
			renderer.render(scene, camera);
		}

		dojo.addOnLoad(function() {
			dojo.xhrGet({
				url : "/cbsatesting/Scalability/ChartData?modelName="
						+ modelName,
				handleAs : "json",
				load : function(data) {
					init();
					console.log(data);
					showChart(data);
				}
			});
		  
		});
		

		function showChart(data) {
			var par1, par2, par3, par4, par5;
			var key1,key2,key3,key4,key5;
			var count=0;
			

			console.log(data[0]);
			for ( var j = 1; j < 6; j=j+2) {
				var i = 1;
				for ( var key in data[j]) {

					switch (i) {
					case 1:
						par1 = data[j][key];
						key1=key;
						break;
					case 2:
						par2 = data[j][key];
						key2=key;
						break;
					case 3:
						par3 = data[j][key];
						key3=key;
						break;
					case 4:
						par4 = data[j][key];
						key4=key;
						break;
					case 5:
						par5 = data[j][key];
						key5=key;
						break;
					}
					i++;

				}
				count++;
				if(!modelName.localeCompare("SEC")){
					plotaxis(40);
					// add 3D text
					add3dText(key1,key2,key3);
					plotTuble(par1, par2, par3,20*count);
				}
				else if(!modelName.localeCompare("CRAM")){
					plotaxis5d(100);
					add5dText(key1,key2,key3,key4,key5);
					plotTuble5d(par1, par2, par3,par4,par5,20*count);
					
				}
				else{
					plotaxis5d(100);
					add5dText(key1,key2,key3,key4,key5);
					plotTuble5d(par1, par2, par3,par4,par5,20*count);
				}
				

			}
			animate();
		}
	</script>




</body>
</html>