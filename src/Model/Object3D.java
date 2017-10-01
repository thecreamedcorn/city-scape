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

import Model.Mesh.Mesh;
import Model.Mesh.MeshImplementationHelper;

/**
 * Created by 18wgaboury on 5/7/2017.
 * stores both a mesh and an initial model to world transformation matrix
 */
public class Object3D
	{
	private Mesh model;
	private TransformationMatrix modelToWorld;

	/**
	 * makes a new object 3D using a Mesh and a transformation matrix
	 * @param model a model
	 * @param modelToWorld a transformation matrix
	 */
	public Object3D(Mesh model, TransformationMatrix modelToWorld)
		{
		this.model = model;
		this.modelToWorld = modelToWorld;
		}

	/**
	 * creates an empty object3D
	 */
	public Object3D()
		{
		model = new MeshImplementationHelper(){};
		modelToWorld = new TransformationMatrix.IdentityMatrix();
		}

	/**
	 * gets the mesh
	 * @return the mesh
	 */
	public Mesh getModel()
		{ return model; }

	/**
	 * sets the mesh object
	 * @param model a mesh object
	 */
	public void setModel(Mesh model)
		{ this.model = model; }

	/**
	 * gets the current model to world transformation matrix
	 * @return the current model to world transformation matrix
	 */
	public TransformationMatrix getModelToWorld()
		{ return modelToWorld; }

	/**
	 * sets the current model to world transformation matrix
	 * @param modelToWorld the current model to world transformation matrix
	 */
	public void setModelToWorld(TransformationMatrix modelToWorld)
		{ this.modelToWorld = modelToWorld; }
	}
