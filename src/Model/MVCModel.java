/*
city-scape: a 3d scene of a city soft rendered in java
Copyright (C) 2017  Wil Gaboury

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/
*/

package Model;

import Model.Mesh.Edge;
import Model.Mesh.Face;
import Model.Mesh.Mesh;
import Model.Mesh.Vertex;
import View.MVCView;
import View.RenderLine;
import View.RenderTriangle;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by 18wgaboury on 5/7/2017.
 * This objects job is to keep all the instance data about the application so that it can be modified by the controller
 * when users perform actions and update the view object to display any changes to application state
 */
public class MVCModel
	{
	private ArrayList<Object3D> modelMeshes;
	private ArrayList<Mesh> worldMeshes;
	private ArrayList<Mesh> viewMeshes;
	private ArrayList<RenderTriangle> renderTriangles;
	private ArrayList<RenderLine> renderLines;

	private TransformationMatrix camera;
	private TransformationMatrix projection;

	private int screenHeight;
	private int screenWidth;

	private MVCView view;

	/**
	 * simple constructor
	 * @param view the view to update when there is changes to application state
	 */
	public MVCModel(MVCView view)
		{
		modelMeshes = new ArrayList<>();
		worldMeshes = new ArrayList<>();
		viewMeshes = new ArrayList<>();
		renderTriangles = new ArrayList<>();
		renderLines = new ArrayList<>();
		camera = (new TransformationMatrix.IdentityMatrix()).apply(new TransformationMatrix.Transpose(0,0,100));
		camera.apply(new TransformationMatrix.YAxisRotation(Math.toRadians(45)));
		camera.apply(new TransformationMatrix.WeirdXRotate(camera, Math.toRadians(-20)));
		projection = new TransformationMatrix.IdentityMatrix();
		screenHeight = 400;
		screenWidth = 500;
		this.view = view;
		}

	/**
	 * takes in a ModelMeshesModifier and allows it to modify internal list of meshes before then updateing all of the
	 * other mesh lists.
	 * @param m a mesh modifier
	 */
	public void modifyModelMeshes(ModelMeshesModifier m)
		{
		m.run(modelMeshes);
		updateWorldMeshes();
		}

	/**
	 * whenever there is changes to the model meshes then the world meshes need updating and this method should be called,
	 * it then subsequently updates all other mesh lists
	 */
	private void updateWorldMeshes()
		{
		worldMeshes.clear();
		for (Object3D o : modelMeshes)
			{ worldMeshes.add(o.getModel().clone().apply(o.getModelToWorld())); }
		updateViewMeshes();
		}

	/**
	 * this is called after changes to the camera matrix and it regenerates the view meshes and updates all subsequent
	 * mesh lists
	 */
	private void updateViewMeshes()
		{
		viewMeshes.clear();
		for (Mesh m : worldMeshes)
			{ viewMeshes.add(m.clone().apply(new TransformationMatrix.Inverse(camera))); }
		updateProjectionMeshes();
		}

	/**
	 * this generates the projection meshes before then updating the MVCView object of a change in application state
	 */
	private void updateProjectionMeshes()
		{
		renderTriangles.clear();
		renderLines.clear();

		for (Mesh m : viewMeshes)
			{
			Mesh newM = m.clone();

			newM.homogenize();
			newM.apply(projection);
			newM.clipNearPlane();
			newM.dehomogenize();

			//get to screen perspective
			newM.apply((new TransformationMatrix.XAxisRotation(Math.toRadians(180)).apply(
					new TransformationMatrix.Transpose(1,1,1)).apply(
							new TransformationMatrix.Scale((double)screenWidth / 2, (double)screenHeight / 2,1)
			)));

			for (Face f : newM.getFaces())
				{
				renderTriangles.add(new RenderTriangle(
						f.getVertices()[0].getPoint().clone(),
						f.getVertices()[1].getPoint().clone(),
						f.getVertices()[2].getPoint().clone(),
						f.getColor()
				));
				}
			for (Edge e : newM.getEdges())
				{
				if (e.getThickness() != 0)
					{
					renderLines.add(new RenderLine(
							e.getVertices()[0].getPoint().clone(),
							e.getVertices()[1].getPoint().clone(),
							e.getThickness(),
							e.getColor()
					));
					}
				}
			}

		view.update();
		}

	/**
	 * applies a transformation matrix to the camera matrix and then updates the view meshes
	 * @param trans a transformation matrix
	 */
	public void moveCamera(TransformationMatrix trans)
		{
		camera.apply(trans);
		updateViewMeshes();
		}

	/**
	 * called when the screen size needs changing. after which projection meshes are updated and view is notified
	 * @param screenWidth the new screen width
	 * @param screenHeight the new screen height
	 */
	public void resizeScreen(int screenWidth, int screenHeight)
		{
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		projection = new TransformationMatrix.ViewToProjection(
				-screenWidth / 100,
				screenWidth / 100,
				-screenHeight / 100,
				screenHeight / 100,
				3,
				200);
		updateProjectionMeshes();
		}


	/**
	 * gets the camera matrix
	 * @return the camera matrix
	 */
	public TransformationMatrix getCamera()
		{ return camera; }

	/**
	 * a functional interface for modifying model mesh lists
	 */
	public interface ModelMeshesModifier
		{ void run(Collection<Object3D> meshList); }

	/**
	 * gets the current screen height
	 * @return the current screen height
	 */
	public int getScreenHeight()
		{
		return screenHeight;
		}

	/**
	 * gets the current screen width
	 * @return the current screen width
	 */
	public int getScreenWidth()
		{
		return screenWidth;
		}

	/**
	 * gets the list of render triangles for the view object
	 * @return list of render triangles
	 */
	public ArrayList<RenderTriangle> getRenderTriangles()
		{
		return renderTriangles;
		}

	/**
	 * gets the list of render lines
	 * @return the list of render lines
	 */
	public ArrayList<RenderLine> getRenderLines()
		{
		return renderLines;
		}
	}
