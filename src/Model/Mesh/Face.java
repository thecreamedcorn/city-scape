package Model.Mesh;

/**
 * Created by 18wgaboury on 5/10/2017.
 * represents a face in a mesh object using three vertices and a color for the face. Each face is actually just a triangle
 */
public class Face implements Cloneable
	{
	private Vertex[] points;
	private int color;

	/**
	 * creates an empty face object
	 */
	public Face()
		{
		points = new Vertex[3];
		color = 0;
		}

	/**
	 * creates a face object using three vertices and a color
	 * @param p1 first point
	 * @param p2 second point
	 * @param p3 third point
	 * @param color a color
	 */
	public Face(Vertex p1, Vertex p2, Vertex p3, int color)
		{
		points = new Vertex[3];
		points[0] = p1;
		points[1] = p2;
		points[2] = p3;
		this.color = color;
		}

	/**
	 * gets all the vertices in the face object
	 * @return gets the vertices
	 */
	public Vertex[] getVertices()
		{ return points; }

	/**
	 * sets the vertices of the face
	 * @param vertices an array of three vertices
	 */
	public void setVertices(Vertex[] vertices)
		{ points = vertices; }

	/**
	 * sets all the vertices in the face object
	 * @param p1 first point
	 * @param p2 second point
	 * @param p3 third point
	 */
	public void setVertices(Vertex p1, Vertex p2, Vertex p3)
		{
		points[0] = p1;
		points[1] = p2;
		points[2] = p3;
		}

	/**
	 * gets the color of the face
	 * @return the color of the face
	 */
	public int getColor()
		{ return color; }

	/**
	 * sets the color of the face
	 * @param color the color of the face
	 */
	public void setColor(int color)
		{ this.color = color; }

	/**
	 * creates a clone of the face object
	 * @return a clone of this object
	 */
	public Face clone()
		{
		try
			{
			Face result = (Face) super.clone();
			result.points = points.clone();
			return result;
			}
		catch (CloneNotSupportedException e)
			{ throw new AssertionError(e); }
		}

	/**
	 * tests to see if the face contains a given vertex
	 * @param v a vertex
	 * @return true if this face contains vertex v and false otherwise
	 */
	public boolean contains(Vertex v)
		{
		return  points[0] == v ||
				points[1] == v ||
				points[2] == v;
		}

	/**
	 * outputs a string contianing all the points of the object
	 * @return a string representing object state
	 */
	public String toString()
		{ return "{" + points[0] + ", " + points[1] + ", " + points[2] + "}"; }

	}
