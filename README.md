# city-scape
creates a 3d scene of a city soft rendered in java.
![image of program](/3d_render_program.png)

visit https://drive.google.com/file/d/0B89wzKiSWz5nUThEcWNVV0xjSXc/view?usp=sharing to get an executable jar. Simply download then double click on the file.

A note of caution: The process in entierly soft rendered meaning that there is no gpu excelloration or openGL code. The 3D image created by running every single computation is run in the java virtual mathine and thus the cpu. Normal 3D imaging does not work this way, so it will be slow (do not drag your cursor around like crazy or the progam will stall/freeze). The purpose was so that I could get a better understanding of how 3D image creation works.

Quick run down of how it works:

The Veiw class initializes the whole project and creates the window. Also a ZBufferedRaster is created which is a java BufferedImage along side a 2d array of floats represeting the z-axis of each pixel. This has the ability to draw triangles and lines to the screen and makes sure that only the front most pixels of each one is drawn.

The controller class takes input from dragging, zooming, or clicking, thus notifying the program's model to mirror these changes.

The model is a internal representation of the scene, consists of a list of mesh objects that comprise the scene. It stores 3 versions of each mesh. A mesh is a bunch of verticies represented by a list of point objects (this point is usually just x, y, and z, but can also be a homogeneous point with 4 coordinates which is needed during the clipping step), and a list of the lines, and faces in the mesh. So then the three mesh lists in the model are the model, world, and veiw lists. The model list is meshes where all the coordinates are all reletive to the model, the world list is where each meshes point list is modified via matrix transformations to position it relative to a global set of coordinates, this includes translating, scaling, and rotating the objects into the correct position in the scene, and finally the view meshes have undergone a projection matrix tranformation in addition to clipping of the verticies outside of the projection frustrum. Each mesh then has the ability to turn itself into a list of lines and triangles so that they can be displayed by the ZBufferedRaster.

So then the process visually goes

model meshs --> world meshes --> view mesh --> ZBufferedRaster

Honestly I don't know if this was the best way to make the program but the reason for the 3 mesh lists is that depending on what actions are preformed only certain mesh lists need to be regenerated. When the camera is moved only the view meshes need to be regenerated, whereas clicking the randomize requires the model meshes to be remade which has cascading effects and requires then the regeneration of both the world and then view meshes. While none of the objects are moving in my program if one of the towers had been programed to move, it would mean that world mesh would change and thus only need to regenerate the view mesh.
