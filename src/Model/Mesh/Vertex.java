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

import Model.Point3D;

import java.util.HashSet;

/**
 * Created by 18wgaboury on 5/10/2017.
 * class representing a vertex in a mesh using a {@link Point3D} object.
 */
public class Vertex implements Cloneable
	{
	private Point3D point;
	private HashSet<Face> faces;
	private HashSet<Edge> edges;

	/**
	 * creates a vertex object using a point in 3D space
	 * @param point
	 */
	public Vertex(Point3D point)
		{
		this.point = point;
		faces = null;
		edges = null;
		}

	/**
	 * gets a list of all the faces
	 * @return list of faces
	 */
	public HashSet<Face> getFaces()
		{ return faces; }

	/**
	 * returns the points used for internal representation
	 * @return coordinates of vertex
	 */
	public Point3D getPoint()
		{ return point; }

	/**
	 * resets the point to a new point
	 * @param point a point in 3D space
	 */
	public void setPoint(Point3D point)
		{ this.point = point; }

	/**
	 * makes a clone of the vertex
	 * @return a clone of the vertex
	 */
	public Vertex clone()
		{
		try
			{
			Vertex result = (Vertex) super.clone();
			result.point = point.clone();

			if (faces != null)
				{ result.faces = (HashSet<Face>) faces.clone(); }
			if (edges != null)
				{ result.edges = (HashSet<Edge>) edges.clone(); }

			return result;
			}
		catch (CloneNotSupportedException e)
			{ throw new AssertionError(e); }
		}

	/**
	 * gets a list of all the edges of the vertex
	 * @return a list of edges
	 */
	public HashSet<Edge> getEdges()
		{
		return edges;
		}

	/**
	 * sets the list of faces
	 * @param faces a list of faces
	 */
	public void setFaces(HashSet<Face> faces)
		{
		this.faces = faces;
		}

	/**
	 * sets the list of edges
	 * @param edges a list of edges
	 */
	public void setEdges(HashSet<Edge> edges)
		{
		this.edges = edges;
		}
	}
