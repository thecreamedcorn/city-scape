package Model;

import Model.Mesh.Edge;
import Model.Mesh.MeshImplementationHelper;
import Model.Mesh.Vertex;
import View.RenderLine;
import View.RenderTriangle;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by 18wgaboury on 5/4/2017.
 * Its a simple line Mesh that represents a single edge. Used for drawing a single line on the scree
 */
public class Line extends MeshImplementationHelper
	{
	/**
	 * creates the line using length, thickness and color
	 * @param length
	 * @param thickness
	 * @param color
	 */
	public Line(double length, double thickness, int color)
		{
		Vertex[] points = {
				new Vertex(new Point3D(0, 0, 0)),
				new Vertex(new Point3D(0,length,0))
		};

		vertices.add(points[0]);
		vertices.add(points[1]);

		edges.add(new Edge(points[0], points[1], thickness, color));
		}
	}
