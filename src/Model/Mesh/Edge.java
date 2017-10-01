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

/**
 * Created by 18wgaboury on 5/10/2017.
 * Represents an edge in a mesh object, this is given by two points, the thickness of the edge, and the color
 */
public class Edge implements Cloneable
	{
	private Vertex[] points;
	private int color;
	private double thickness;

	/**
	 * creates an edge using two points, thickness, and color
	 * @param v1 first vertex
	 * @param v2 second vertex
	 * @param thickness thickness of the edge line
	 * @param color color of the edge line
	 */
	public Edge(Vertex v1, Vertex v2, double thickness, int color)
		{
		points = new Vertex[2];
		points[0] = v1;
		points[1] = v2;
		this.thickness = thickness;
		this.color = color;
		}

	/**
	 * creates an empty edge
	 */
	public Edge()
		{
		points = new Vertex[2];
		color = 0;
		}

	/**
	 * gets an array of the two vertices
	 * @return the two vertices
	 */
	public Vertex[] getVertices()
		{ return points; }

	/**
	 * sets the points used for the edge
	 * @param points the points
	 */
	public void setVertices(Vertex[] points)
		{ this.points = points; }

	/**
	 * gets the color of the edge
	 * @return the color of the edge
	 */
	public int getColor()
		{ return color; }

	/**
	 * sets the color of the edge
	 * @param color the color of the edge
	 */
	public void setColor(int color)
		{ this.color = color; }

	/**
	 * gets the thickness of the edge
	 * @param thickness the thickness of the edge
	 */
	public void setThickness(double thickness)
		{ this.thickness = thickness; }

	/**
	 * sets the thickness of the edge
	 * @return the thickness of the edge
	 */
	public double getThickness()
		{ return thickness; }

	/**
	 * clones the edge
	 * @return a clone of the edge
	 */
	public Edge clone()
		{
		try
			{
			Edge result = (Edge) super.clone();
			result.points = points.clone();
			return result;
			}
		catch (CloneNotSupportedException e)
			{ throw new AssertionError(e); }
		}
	}
