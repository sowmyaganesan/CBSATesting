// standard global variables
var container, scene, camera, renderer, controls, stats;
var keyboard = new THREEx.KeyboardState();
var clock = new THREE.Clock();
// custom global variables
var cube;

init();
plotaxis(40);
plotTuble(10, 20, 30, 40);

animate();

// FUNCTIONS 		
function init() 
{
	// SCENE
	scene = new THREE.Scene();
	// CAMERA
	var SCREEN_WIDTH = window.innerWidth, SCREEN_HEIGHT = window.innerHeight;
	var VIEW_ANGLE = 45, ASPECT = SCREEN_WIDTH / SCREEN_HEIGHT, NEAR = 0.1, FAR = 20000;
	camera = new THREE.PerspectiveCamera( VIEW_ANGLE, ASPECT, NEAR, FAR);
	scene.add(camera);
	camera.position.set(0,150,400);
	camera.lookAt(scene.position);	
	// RENDERER
	if ( Detector.webgl )
		renderer = new THREE.WebGLRenderer( {antialias:true} );
	else
		renderer = new THREE.CanvasRenderer(); 
	renderer.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
	container = document.createElement( 'div' );
	document.body.appendChild( container );
	container.appendChild( renderer.domElement );
	// EVENTS
	THREEx.WindowResize(renderer, camera);
	THREEx.FullScreen.bindKey({ charCode : 'm'.charCodeAt(0) });
	// CONTROLS
	controls = new THREE.TrackballControls( camera );
	
	
	// add 3D text
	var materialFront = new THREE.MeshBasicMaterial( { color: 0xff0000 } );
	var materialSide = new THREE.MeshBasicMaterial( { color: 0x000088 } );
	var materialArray = [ materialFront, materialSide ];
	var textGeom1 = new THREE.TextGeometry( "Time", 
	{
		size: 8, height: 1, curveSegments: 3,
		font: "helvetiker", weight: "normal", style: "normal",
		bevelThickness: 1, bevelSize: 2, bevelEnabled: false,
		material: 0, extrudeMaterial: 1
	});
	// font: helvetiker, gentilis, droid sans, droid serif, optimer
	// weight: normal, bold
	
	var textMaterial = new THREE.MeshFaceMaterial(materialArray);
	var textMesh = new THREE.Mesh(textGeom1, textMaterial );
	
	
	textMesh.position.set( 0,0,80 );
	textMesh.rotation.x = -Math.PI / 4;
	scene.add(textMesh);
	
	var textGeom2 = new THREE.TextGeometry( "Resource used", 
	{
		size: 8, height: 1, curveSegments: 3,
		font: "helvetiker", weight: "normal", style: "normal",
		bevelThickness: 1, bevelSize: 2, bevelEnabled: false,
		material: 0, extrudeMaterial: 1
	});
	var textMaterial2 = new THREE.MeshFaceMaterial(materialArray);
	var textMesh2 = new THREE.Mesh(textGeom2, textMaterial );
	
	
	textMesh2.position.set( 69.28, 0, -40 );
	textMesh2.rotation.x = -Math.PI / 4;
	scene.add(textMesh2);
	
	var textGeom3 = new THREE.TextGeometry( "System Load", 
	{
		size: 8, height: 1, curveSegments: 3,
		font: "helvetiker", weight: "normal", style: "normal",
		bevelThickness: 1, bevelSize: 2, bevelEnabled: false,
		material: 0, extrudeMaterial: 1
	});
	var textMaterial3 = new THREE.MeshFaceMaterial(materialArray);
	var textMesh3 = new THREE.Mesh(textGeom3, textMaterial );
	
	
	textMesh3.position.set( -69.28, 0, -40 );
	textMesh3.rotation.x = -Math.PI / 4;
	scene.add(textMesh3);
	
	var textGeom4 = new THREE.TextGeometry( "System Performance", 
	{
		size: 8, height: 1, curveSegments: 3,
		font: "helvetiker", weight: "normal", style: "normal",
		bevelThickness: 1, bevelSize: 2, bevelEnabled: false,
		material: 0, extrudeMaterial: 1
	});
	var textMaterial4 = new THREE.MeshFaceMaterial(materialArray);
	var textMesh4 = new THREE.Mesh(textGeom4, textMaterial );
	
	
	textMesh4.position.set( 0, 80, 0);
	textMesh4.rotation.x = -Math.PI / 4;
	scene.add(textMesh4);
	
}
function plotTuble(x, y, z, v) {
   var colorCode = Math.random()*0xffffff;
   var geometry = new THREE.Geometry();
   var material = new THREE.LineBasicMaterial( { color: colorCode, linewidth: 1 } );
    


   geometry.vertices.push( new THREE.Vector3( 0, v, 0 ) );
 geometry.vertices.push( new THREE.Vector3( 1.732*x, 0, -x ) );

   geometry.vertices.push( new THREE.Vector3( 0, v, 0 ) );
   geometry.vertices.push( new THREE.Vector3( -1.732*y, 0, -y) );

   geometry.vertices.push( new THREE.Vector3( 0, v, 0 ) );
   geometry.vertices.push( new THREE.Vector3( 0, 0, 2*z) );
   
   geometry.vertices.push( new THREE.Vector3( 1.732*x, 0, -x ) );
   geometry.vertices.push( new THREE.Vector3( -1.732*y, 0, -y) );
   geometry.vertices.push( new THREE.Vector3( 0, 0, 2*z) );
   geometry.vertices.push( new THREE.Vector3( 1.732*x, 0, -x ) );
/*
    createAxis(v(0, 0, 0), v(0, 0, 2*axisLength), 0xFF0000);
    createAxis(v(0, 0, 0), v(1.732*axisLength, 0, -axisLength), 0x00FF00);
    createAxis(v(0, 0, 0), v(-1.732*axisLength, 0, -axisLength), 0x0000FF);
*/  

    var line = new THREE.Line( geometry, material, THREE.LineStrip);
    line.position.set( 0, 0, 0 );
    scene.add( line );
    }
    
    function plotaxis(axisLength){
    //Shorten the vertex function
    function v(x,y,z){ 
            return new THREE.Vertex(new THREE.Vector3(x,y,z)); 
    }
    
    //Create axis (point1, point2, colour)
    function createAxis(p1, p2, color){
            var line, lineGeometry = new THREE.Geometry(),
            lineMat = new THREE.LineBasicMaterial({color: color, lineWidth: 1});
            lineGeometry.vertices.push(p1, p2);
            line = new THREE.Line(lineGeometry, lineMat);
            scene.add(line);
    }
    
    createAxis(v(0, 0, 0), v(0, 0, 2*axisLength), 0xFF0000);
    createAxis(v(0, 0, 0), v(1.732*axisLength, 0, -axisLength), 0x00FF00);
    createAxis(v(0, 0, 0), v(-1.732*axisLength, 0, -axisLength), 0x0000FF);
    createAxis(v(0, 0, 0), v(0, 2*axisLength, 0), 0xFF0FF0);
   
};
function animate() 
{
    requestAnimationFrame( animate );
	render();		
	update();
}

function update()
{
	if ( keyboard.pressed("z") ) 
	{ 
		// do something
	}
	
	controls.update();
	
}

function render() 
{
	renderer.render( scene, camera );
}