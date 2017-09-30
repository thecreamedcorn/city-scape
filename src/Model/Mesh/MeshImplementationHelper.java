package Model.Mesh;

import MathLib.MatrixMath;
import MathLib.WrongSizeMatrixException;
import Model.HomogeneousPoint;
import Model.Point3D;
import Model.TransformationMatrix;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by 18wgaboury on 4/29/2017.
 * imlements all the functionality of Mesh and provides helpful functions for any Mesh objcects that want to compose
 * themselves of multiple MeshImplementationHelpers
 */
public abstract class MeshImplementationHelper implements Mesh, Cloneable
	{
	protected HashSet<Vertex> vertices;
	protected HashSet<Face> faces;
	protected HashSet<Edge> edges;

	protected boolean isVertexData;

	/**
	 * the super() constructor to be called to instantiate all data
	 */
	protected MeshImplementationHelper()
		{
		vertices = new HashSet<>();
		faces = new HashSet<>();
		edges = new HashSet<>();
		isVertexData = false;
		}

	/**
	 *  makes a clone of the current mesh
	 * @return a clone of this object
	 */
	public MeshImplementationHelper clone()
		{
		try
			{
			MeshImplementationHelper result = (MeshImplementationHelper) super.clone();
			HashMap<Vertex, Vertex> vertexTransfer = new HashMap<>();

			//set transfer lists
			result.vertices = (HashSet<Vertex>) vertices.clone();
			for (Vertex v : vertices)
				{
				result.vertices.remove(v);
				vertexTransfer.put(v, v.clone());
				result.vertices.add(vertexTransfer.get(v));
				}

			result.faces = new HashSet<Face>();
			for (Face f : faces)
				{
				Face faceClone = f.clone();
				faceClone.setVertices(f.getVertices().clone());
				for (int i = 0; i < f.getVertices().length; i++)
					{ faceClone.getVertices()[i] =  vertexTransfer.get(f.getVertices()[i]); }
				result.faces.add(faceClone);
				}

			result.edges = new HashSet<Edge>();
			for (Edge e : edges)
				{
				Edge edgeClone = e.clone();
				edgeClone.setVertices(e.getVertices().clone());
				for (int i = 0; i < e.getVertices().length; i++)
					{ edgeClone.getVertices()[i] =  vertexTransfer.get(e.getVertices()[i]); }
				result.edges.add(edgeClone);
				}

			result.isVertexData = false;
			return result;
			}
		catch (CloneNotSupportedException e)
			{ throw new AssertionError(e); }
		}

	/**
	 * gets all the vertices in the mesh
	 * @return all the vertices
	 */
	public HashSet<Vertex> getVertices()
		{ return vertices; }

	/**
	 * {@link Mesh#getFaces()}
	 */
	public HashSet<Face> getFaces()
		{ return faces; }

	/**
	 * {@link Mesh#getEdges()}
	 */
	public HashSet<Edge> getEdges()
		{ return edges; }

	/**
	 * generates all the data for vertices on order for the mesh to be operated on.
	 */
	private void generateVertexData()
		{
		for (Vertex v : vertices)
			{
			v.setFaces(new HashSet<>());
			v.setEdges(new HashSet<>());
			}

		for (Face f : faces)
			{
			for (Vertex v : f.getVertices())
				{
				if (!v.getFaces().contains(f))
					{ v.getFaces().add(f); }
				}
			}
		for (Edge e : edges)
			{
			for (Vertex v : e.getVertices())
				{
				if (!v.getEdges().contains(e))
					{ v.getEdges().add(e); }
				}
			}
		}

	/**
	 * deletes all the vertex data for when Meshes need to be copied because it saves storage
	 */
	private void deleteVertexData()
		{
		for (Vertex v : vertices)
			{
			v.setEdges(null);
			v.setFaces(null);
			}
		}

	/**
	 * {@link Mesh#apply(TransformationMatrix)}
	 */
	public Mesh apply(TransformationMatrix trans)
		{
		for (Vertex v : vertices)
			{ v.getPoint().apply(trans); }
		return this;
		}

	/**
	 * {@link Mesh#clipNearPlane()}
	 */
	public void clipNearPlane()
		{
		generateVertexData();

		double [] nearPlane = {0,0,1,1};

		HashSet<Vertex> outOfRangeVerticies = new HashSet<>();

		for (Vertex v : vertices)
			{
			if (v.getPoint().getZ() < -v.getPoint().getW())
				{ outOfRangeVerticies.add(v); }
			}

		for (Vertex v : outOfRangeVerticies)
			{
			HashSet<Edge> edgeList = (HashSet<Edge>) v.getEdges().clone();
			for (Edge e : edgeList)
				{
				if (!outOfRangeVerticies.contains(e.getVertices()[0] == v ? e.getVertices()[1] : e.getVertices()[0]))
					{
					Vertex in = e.getVertices()[0] == v ? e.getVertices()[1] : e.getVertices()[0];

					double a;
					double b;

					try
						{
						a = MatrixMath.dotProduct(v.getPoint().toArray(), nearPlane);
						b = MatrixMath.dotProduct(in.getPoint().toArray(), nearPlane);
						}
					catch (WrongSizeMatrixException ex)
						{
						throw new AssertionError(ex);
						}

					double c = a / (a - b);

					double[] newPoint = MatrixMath.vectorAdd(
							MatrixMath.scaleVector(v.getPoint().toArray(), 1 - c),
							MatrixMath.scaleVector(in.getPoint().toArray(), c)
					);

					Vertex middleVertex = new Vertex(new HomogeneousPoint(newPoint[0], newPoint[1], newPoint[2], newPoint[3]));
					middleVertex.setFaces(new HashSet<>());
					middleVertex.setEdges(new HashSet<>());
					vertices.add(middleVertex);

					Edge newEdge = new Edge(v, middleVertex, 0, 0);
					e.getVertices()[0] = in;
					e.getVertices()[1] = middleVertex;

					edges.add(newEdge);
					v.getEdges().add(newEdge);
					v.getEdges().remove(e);

					middleVertex.getEdges().add(newEdge);
					middleVertex.getEdges().add(e);

					HashSet<Face> faceList = (HashSet<Face>) v.getFaces().clone();
					for (Face f : faceList)
						{
						if (f.contains(v) && f.contains(in))
							{
							Vertex other;
							if (f.getVertices()[0] != in && f.getVertices()[0] != v)
								{ other = f.getVertices()[0]; }
							else if (f.getVertices()[1] != in && f.getVertices()[1] != v)
								{ other = f.getVertices()[1]; }
							else
								{ other = f.getVertices()[2]; }

							faces.remove(f);
							v.getFaces().remove(f);
							in.getFaces().remove(f);
							other.getFaces().remove(f);

							Face f1 = new Face(other, v, middleVertex, f.getColor());
							Face f2 = new Face(other, middleVertex, in, f.getColor());
							Edge splitEdge = new Edge(middleVertex, other, 0, 0);

							edges.add(splitEdge);
							middleVertex.getEdges().add(splitEdge);
							other.getEdges().add(splitEdge);

							faces.add(f1);
							faces.add(f2);

							other.getFaces().add(f1);
							v.getFaces().add(f1);
							middleVertex.getFaces().add(f1);

							other.getFaces().add(f2);
							middleVertex.getFaces().add(f2);
							in.getFaces().add(f2);
							}
						}
					}
				}
			}

		for (Vertex v : outOfRangeVerticies)
			{
			for (Face f : v.getFaces())
				{ faces.remove(f); }
			for (Edge e : v.getEdges())
				{ edges.remove(e); }
			vertices.remove(v);
			}

		for (Vertex v : vertices)
			{
			Collection<Face> facesCopy = (Collection<Face>) v.getFaces().clone();
			for (Face f : facesCopy)
				{
				if (!faces.contains(f))
					{ v.getFaces().remove(f); }
				}
			Collection<Edge> edgesCopy = (Collection<Edge>) v.getEdges().clone();
			for (Edge e : edgesCopy)
				{
				if (!edges.contains(e))
					{ v.getEdges().remove(e); }
				}
			}

		deleteVertexData();
		}

	/**
	 * {@link Mesh#homogenize()}
	 */
	public void homogenize()
		{
		for (Vertex v : vertices)
			{ v.setPoint(new HomogeneousPoint(v.getPoint())); }
		}

	/**
	 * {@link Mesh#dehomogenize()}
	 */
	public void dehomogenize()
		{
		for (Vertex v : vertices)
			{
			v.setPoint(new Point3D(
					v.getPoint().getX() / v.getPoint().getW(),
					v.getPoint().getY() / v.getPoint().getW(),
					v.getPoint().getZ() / v.getPoint().getW()
			));
			}
		}
	}
