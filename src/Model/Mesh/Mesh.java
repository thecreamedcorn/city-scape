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

package Model.Mesh;

import Model.TransformationMatrix;

import java.util.Collection;

/**
 * Created by 18wgaboury on 5/10/2017.
 * an interface of all the things that a mesh should be able to do
 */
public interface Mesh
	{
	/**
	 * gets all the edges
	 * @return all the edges
	 */
	Collection<Edge> getEdges();

	/**
	 * gets all the faces
	 * @return all the faces
	 */
	Collection<Face> getFaces();

	/**
	 * applies a transformation matrix to all the vertices
	 * @param trans a transformation matrix
	 * @return a copy of the mesh
	 */
	Mesh apply(TransformationMatrix trans);

	/**
	 * makes a clone of the mesh
	 * @return a clone of the mesh
	 */
	Mesh clone();

	/**
	 * clips a homogenized mesh on the near clipping frame
	 */
	void clipNearPlane();

	/**
	 * homogenizes all the Point3Ds in each vertex. This means that instead of each point having just an x, y, and z
	 * in space it also has an extra w coordinate that is used in projection transformations to represent the projection
	 * plane and also so that homogeneous clipping may be applied.
	 */
	void homogenize();

	/**
	 * makes all the homogenized Point3Ds in each vertex regular points
	 */
	void dehomogenize();
	}
