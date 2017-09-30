package Model;

import Model.Mesh.Edge;
import Model.Mesh.Face;
import Model.Mesh.Mesh;
import Model.Mesh.Vertex;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * Created by 18wgaboury on 4/26/2017.
 * an object representing a tower mesh
 */

public class Tower implements Mesh, Cloneable
	{
	private MeshCube body;
	private MeshCube top1;
	private MeshCube top2;
	private double windowHeight;
	private double windowLength;
	private double length;
	private double height;
	private ArrayList<Rectangle3D> windows;
	private Line antenea;

	int bodyColor; //= 0xa1a1a1;
	int lineColor; //= 0x6b6b6b;
	double lineThickness = 1;

	/**
	 * creates a new tower with the following charachteristics
	 * @param baseLength
	 * @param minHeight
	 * @param maxHeight
	 * @param windowHeight
	 * @param windowLength
	 */
	public Tower(double baseLength, double minHeight, double maxHeight, double windowHeight, double windowLength)
		{
		super();

		windows = new ArrayList<>();

		this.length = baseLength;
		this.height = minHeight + (new Random()).nextDouble() * (maxHeight - minHeight);

		bodyColor = (new Random()).nextInt(0xFFFFFF + 1);
		lineColor = (new Random()).nextInt(0xFFFFFF + 1);

				body = new MeshCube(baseLength,
				height,
				baseLength,
				lineThickness,
				bodyColor,
				lineColor
		);

		top1 = new MeshCube(
				baseLength * (2.0/3.0),
				height * 0.1,
				baseLength * (2.0/3.0),
				lineThickness,
				bodyColor,
				lineColor
		);

		top1.apply(new TransformationMatrix.Transpose(baseLength * (1.0/6.0), height, baseLength * (1.0/6.0)));
		
		top2 = new MeshCube(
				baseLength * (1.0/3.0),
				height * 0.1,
				baseLength * (1.0/3.0),
				lineThickness,
				bodyColor,
				lineColor
		);

		antenea = new Line(
				top1.getHeight(),
				1,
				lineColor
		);

		antenea.apply(new TransformationMatrix.Transpose(baseLength * 0.5, height + top1.getHeight() + top2.getHeight(), baseLength * 0.5));

		this.windowHeight = windowHeight;
		this.windowLength = windowLength;

		setWindowArray(new TransformationMatrix.Transpose(0, 0, -1e-5));
		setWindowArray(new TransformationMatrix.Transpose(0, 0, 1e-5 + length));
		setWindowArray((new TransformationMatrix.YAxisRotation(Math.toRadians(-90)).compound(
				new TransformationMatrix.Transpose(1e-5 + length, 0, 0))));
		setWindowArray((new TransformationMatrix.YAxisRotation(Math.toRadians(-90)).compound(
				new TransformationMatrix.Transpose(-1e-5, 0, 0))));

		top2.apply(new TransformationMatrix.Transpose(baseLength * (1.0/3.0), height + top1.getHeight(), baseLength * (1.0/3.0)));
		}

	/**
	 * helper function to the constructor for making the array of windows and spacing them correctly
	 * @param trans a transformation matrix to apply to the windows
	 */
	private void setWindowArray(TransformationMatrix trans)
		{
		int numWindowsAcross = (int)((length * 0.75) / windowLength);
		int numWindowsDown = (int)((height * 0.75) / windowHeight);

		double lengthGap = (length - windowLength * numWindowsAcross) / (numWindowsAcross + 1);
		double heightGap = (height - windowHeight * numWindowsDown) / (numWindowsDown + 1);

		Random rand = new Random();

		for (int y = 0; y < numWindowsDown; y++)
			{
			for (int x = 0; x < numWindowsAcross; x++)
				{
				if (rand.nextInt(4) % 4 != 0)
					{
					Rectangle3D rect = new Rectangle3D(windowHeight, windowLength, rand.nextInt(0xFFFFFF + 1));
					rect.apply((new TransformationMatrix.Transpose(
							(lengthGap * (x + 1)) + (windowLength * x),
							(heightGap * (y + 1)) + (windowHeight * y),
							0
					).compound(trans)));
					windows.add(rect);
					}
				}
			}
		}

	/**
	 * {@link Mesh#apply(TransformationMatrix)}
	 */
	public Mesh apply(TransformationMatrix m)
		{
		body.apply(m);
		top1.apply(m);
		top2.apply(m);
		antenea.apply(m);
		for (Rectangle3D r : windows)
			{ r.apply(m); }
		return this;
		}

	/**
	 * {@link Mesh#clipNearPlane()}
	 */
	public void clipNearPlane()
		{
		body.clipNearPlane();
		top1.clipNearPlane();
		top2.clipNearPlane();
		antenea.clipNearPlane();

		for (Rectangle3D r : windows)
			{ r.clipNearPlane(); }
		}

	/**
	 * {@link Mesh#getEdges()}
	 */
	public Collection<Edge> getEdges()
		{
		ArrayList<Edge> edges = new ArrayList<>();
		edges.addAll(body.getEdges());
		edges.addAll(top1.getEdges());
		edges.addAll(top2.getEdges());
		edges.addAll(antenea.getEdges());
		for (Rectangle3D r : windows)
			{ edges.addAll(r.getEdges()); }
		return edges;
		}

	/**
	 * gets the list of vertices in the mesh
	 * @return the list of vertices
	 */
	public Collection<Vertex> getVertices()
		{
		ArrayList<Vertex> vertices = new ArrayList<>();
		vertices.addAll(body.getVertices());
		vertices.addAll(top1.getVertices());
		vertices.addAll(top2.getVertices());
		vertices.addAll(antenea.getVertices());
		for (Rectangle3D r : windows)
			{ vertices.addAll(r.getVertices()); }
		return vertices;
		}

	/**
	 * {@link Mesh#getFaces()}
	 */
	public Collection<Face> getFaces()
		{
		ArrayList<Face> faces = new ArrayList<>();
		faces.addAll(body.getFaces());
		faces.addAll(top1.getFaces());
		faces.addAll(top2.getFaces());
		faces.addAll(antenea.getFaces());
		for (Rectangle3D r : windows)
			{ faces.addAll(r.getFaces()); }
		return faces;
		}

	/**
	 * {@link Mesh#clone()}
	 */
	public Tower clone()
		{
		try
			{
			Tower result = (Tower) super.clone();
			result.body = (MeshCube) body.clone();
			result.top1 = (MeshCube) top1.clone();
			result.top2 = (MeshCube) top2.clone();
			result.antenea = (Line) antenea.clone();
			result.windows = (ArrayList<Rectangle3D>) windows.clone();
			for (int i = 0; i < windows.size(); i++)
				{ result.windows.set(i, (Rectangle3D)windows.get(i).clone()); }
			return result;
			}
		catch (CloneNotSupportedException e)
			{ throw new AssertionError(e); }
		}

	/**
	 * {@link Mesh#homogenize()}
	 */
	public void homogenize()
		{
		body.homogenize();
		top1.homogenize();
		top2.homogenize();
		antenea.homogenize();
		for (Rectangle3D r : windows)
			{ r.homogenize(); }
		}

	/**
	 * {@link Mesh#dehomogenize()}
	 */
	public void dehomogenize()
		{
		body.dehomogenize();
		top1.dehomogenize();
		top2.dehomogenize();
		antenea.dehomogenize();
		for (Rectangle3D r : windows)
			{ r.dehomogenize(); }
		}
	}