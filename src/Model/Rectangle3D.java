package Model;

import Model.Mesh.*;
import View.RenderLine;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Administrator on 4/28/2017.
 * creates a flat rectangel mesh
 */

public class Rectangle3D extends MeshImplementationHelper
	{
	/**
	 * creates a rectangle mesh using width, height, and color
	 * @param width
	 * @param height
	 * @param color
	 */
	public Rectangle3D(double width, double height, int color)
		{
		super();
		Vertex[] vs = new Vertex[4];

		vs[0] = new Vertex(new Point3D(0, 0 , 0));
		vs[1] = new Vertex(new Point3D(width, 0 ,0));
		vs[2] = new Vertex(new Point3D(0, height, 0));
		vs[3] = new Vertex(new Point3D(width, height ,0));

		vertices.add(vs[0]);
		vertices.add(vs[1]);
		vertices.add(vs[2]);
		vertices.add(vs[3]);

		edges.add(new Edge(vs[0], vs[1], 0,0));
		edges.add(new Edge(vs[0], vs[2], 0,0));
		edges.add(new Edge(vs[3], vs[2], 0,0));
		edges.add(new Edge(vs[3], vs[1], 0,0));
		edges.add(new Edge(vs[0], vs[3], 0,0));

		faces.add(new Face(vs[0], vs[1], vs[3], color));
		faces.add(new Face(vs[0],vs[2],vs[3], color));

		//generateVerticesData();
		}
	}
