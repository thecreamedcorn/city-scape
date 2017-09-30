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
