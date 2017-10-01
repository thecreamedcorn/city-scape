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

import Model.Mesh.*;

/**
 * Created by 18wgaboury on 5/9/2017.
 * creates a rectangular prism mesh object
 */
public class MeshCube extends MeshImplementationHelper
	{
	private double width;
	private double height;
	private double length;

	/**
	 * gets input data about the cube and then constructs a mesh that is 3D rectangular prism
	 * @param width
	 * @param height
	 * @param length
	 * @param lineWidth the thickness of the edges
	 * @param sideColor the color of each face
	 * @param lineColor the color of the edges
	 */
	public MeshCube(double width, double height, double length, double lineWidth, int sideColor, int lineColor)
		{
		super();

		this.width = width;
		this.height = height;
		this.length = length;

		Vertex[] points = new Vertex[8];
		points[0] = new Vertex(new Point3D(0, 0, 0));
		points[1] = new Vertex(new Point3D(width, 0, 0));
		points[2] = new Vertex(new Point3D(0, height, 0));
		points[3] = new Vertex(new Point3D(width, height, 0));
		points[4] = new Vertex(new Point3D(0, 0, length));
		points[5] = new Vertex(new Point3D(width, 0, length));
		points[6] = new Vertex(new Point3D(0, height, length));
		points[7] = new Vertex(new Point3D(width, height, length));

		for (Vertex v : points)
			{ vertices.add(v); }

		edges.add(new Edge(points[0], points[1], lineWidth, lineColor));
		edges.add(new Edge(points[1], points[3], lineWidth, lineColor));
		edges.add(new Edge(points[3], points[2], lineWidth, lineColor));
		edges.add(new Edge(points[2], points[0], lineWidth, lineColor));
		edges.add(new Edge(points[4], points[5], lineWidth, lineColor));
		edges.add(new Edge(points[5], points[7], lineWidth, lineColor));
		edges.add(new Edge(points[7], points[6], lineWidth, lineColor));
		edges.add(new Edge(points[6], points[4], lineWidth, lineColor));
		edges.add(new Edge(points[0], points[4], lineWidth, lineColor));
		edges.add(new Edge(points[1], points[5], lineWidth, lineColor));
		edges.add(new Edge(points[3], points[7], lineWidth, lineColor));
		edges.add(new Edge(points[2], points[6], lineWidth, lineColor));

		edges.add(new Edge(points[0], points[3], 0.0, 0));
		edges.add(new Edge(points[1], points[7], 0.0, 0));
		edges.add(new Edge(points[5], points[6], 0.0, 0));
		edges.add(new Edge(points[4], points[2], 0.0, 0));
		edges.add(new Edge(points[7], points[2], 0.0, 0));
		edges.add(new Edge(points[1], points[4], 0.0, 0));


		faces.add(new Face(points[0],points[3],points[2],sideColor));
		faces.add(new Face(points[0],points[3],points[1],sideColor));
		faces.add(new Face(points[1],points[7],points[5],sideColor));
		faces.add(new Face(points[1],points[7],points[3],sideColor));
		faces.add(new Face(points[5],points[6],points[4],sideColor));
		faces.add(new Face(points[5],points[6],points[7],sideColor));
		faces.add(new Face(points[4],points[2],points[6],sideColor));
		faces.add(new Face(points[4],points[2],points[0],sideColor));
		faces.add(new Face(points[1],points[4],points[0],sideColor));
		faces.add(new Face(points[1],points[4],points[5],sideColor));
		faces.add(new Face(points[2],points[7],points[3],sideColor));
		faces.add(new Face(points[2], points[7], points[6], sideColor));
		}

	//getters for dimensions of cube

	public double getWidth()
		{
		return width;
		}

	public double getHeight()
		{
		return height;
		}

	public double getLength()
		{
		return length;
		}
	}
