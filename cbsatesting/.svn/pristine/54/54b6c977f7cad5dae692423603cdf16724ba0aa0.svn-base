// Closed Line from 3D Points
// Three.js r.51 (Updated for r.52)

// WestLangley

var camera, scene, renderer, controls;

// dom
var container = document.createElement('div');
document.body.appendChild(container);

// info
function addText(text)
{
	var info = document.createElement( 'div' );
	info.style.position = 'absolute';
	info.style.top = '10px';
	info.style.width = '100%';
	info.style.textAlign = 'center';
	info.style.color = '#0ff';
	info.style.backgroundColor = 'transparent';
	info.style.zIndex = '1'; // renderer domElement covers it up
	info.style.fontFamily = 'Monospace';
	info.innerHTML = text;
	container.appendChild( info );
}

// renderer
renderer = new THREE.WebGLRenderer();
renderer.setSize( window.innerWidth, window.innerHeight );
container.appendChild( renderer.domElement );

// scene
scene = new THREE.Scene();

// camera
camera = new THREE.PerspectiveCamera( 45, window.innerWidth / window.innerHeight, 1, 10000 );
camera.position.set( 0, 10, 20 );
camera.lookAt(scene.position);
scene.add(camera);

// controls
controls = new THREE.OrbitControls( camera );

// geometry

// material

// animate

function plotTuble(x, y, z, a, b, v) {
   var colorCode = Math.random()*0xffffff;
   var geometry = new THREE.Geometry();
   var material = new THREE.LineBasicMaterial( { color: colorCode, linewidth: 1 } );
    

   geometry.vertices.push( new THREE.Vector3( 0, v, 0 ) );
   geometry.vertices.push( new THREE.Vector3( 0, 0, x ) );

   geometry.vertices.push( new THREE.Vector3( 0, v, 0 ) );
   geometry.vertices.push( new THREE.Vector3( 0.951*y, 0, 0.309*y ) );

   geometry.vertices.push( new THREE.Vector3( 0, v, 0 ) );
   geometry.vertices.push( new THREE.Vector3( 0.588*z, 0, -0.809*z) );

   geometry.vertices.push( new THREE.Vector3( 0, v, 0 ) );
   geometry.vertices.push( new THREE.Vector3( -0.588*a, 0, -0.809*a) );

   geometry.vertices.push( new THREE.Vector3( 0, v, 0 ) );
   geometry.vertices.push( new THREE.Vector3( -0.951*b, 0, 0.309*b) );
   
   geometry.vertices.push( new THREE.Vector3( 0, 0, x  ) );
   geometry.vertices.push( new THREE.Vector3( 0.951*y, 0, 0.309*y) );
   geometry.vertices.push( new THREE.Vector3( 0.588*z, 0, -0.809*z) );
   geometry.vertices.push( new THREE.Vector3( -0.588*a, 0, -0.809*a ) );
   geometry.vertices.push( new THREE.Vector3( -0.951*b, 0, 0.309*b ) );
   geometry.vertices.push( new THREE.Vector3( 0, 0, x  ) );
   /*
   createAxis(v(0, 0, 0), v(0, 0, axisLength), 0xFF0000);
   createAxis(v(0, 0, 0), v(0.951*axisLength, 0, 0.309*axisLength), 0x0FF00F);
   createAxis(v(0, 0, 0), v(0.588*axisLength, 0, -0.809*axisLength), 0x00FFF0);
   createAxis(v(0, 0, 0), v(-0.588*axisLength, 0, -0.809*axisLength), 0xF00FFF);
   createAxis(v(0, 0, 0), v(-0.951*axisLength, 0, 0.309*axisLength), 0xFF00FF);
   createAxis(v(0, 0, 0), v(0, axisLength, 0), 0xFFFFFF);
*/
   var line = new THREE.Line( geometry, material, THREE.LineStrip);
   line.position.set( 0, 0, 0 );
   scene.add( line );
   requestAnimationFrame( animate );

   controls.update();

   render();
}

plotTuble(3, 3, 3, 3, 3, 3);
// plotTuble(5,5,5);


var plotaxis = function(axisLength){
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

   createAxis(v(0, 0, 0), v(0, 0, axisLength), 0xFF0000);
   createAxis(v(0, 0, 0), v(0.951*axisLength, 0, 0.309*axisLength), 0x0FF00F);
   createAxis(v(0, 0, 0), v(0.588*axisLength, 0, -0.809*axisLength), 0x00FFF0);
   createAxis(v(0, 0, 0), v(-0.588*axisLength, 0, -0.809*axisLength), 0xF00FFF);
   createAxis(v(0, 0, 0), v(-0.951*axisLength, 0, 0.309*axisLength), 0xFF00FF);
   createAxis(v(0, 0, 0), v(0, axisLength, 0), 0xFFFFFF);

};


// render
function render() {

    renderer.render( scene, camera );

}

// animate
function animate() {

    requestAnimationFrame( animate );

    controls.update();

    render();

}

//To use enter the axis length
plotaxis(6);
animate();
