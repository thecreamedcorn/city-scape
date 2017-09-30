package Model;

/**
 * This class represents a point in 3D space with an x, y, and z coordinate
 */
public class Point3D implements Cloneable
	{
	protected double x;
	protected double y;
	protected double z;

	/**
	 * makes an point at the origin (0,0,0)
	 */
	public Point3D()
		{
		this.x = 0;
		this.y = 0;
		this.z = 0;
		}

	/**
	 * sets the coordinates of the new point
	 * @param x
	 * @param y
	 * @param z
	 */
	public Point3D(double x, double y, double z)
		{
		this.x = x;
		this.y = y;
		this.z = z;
		}

	/**
	 * resets all the coordinates of the point
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setAll(double x, double y, double z)
		{
		this.x = x;
		this.y = y;
		this.z = z;
		}

	//setters and getters

	public double getX()
		{ return x; }
	
	public void setX(double x)
		{ this.x = x; }
	
	public double getY()
		{ return y; }
	
	public void setY(double y)
		{ this.y = y; }
	
	public double getZ()
		{ return z; }
	
	public void setZ(double z)
		{ this.z = z; }

	public double getW()
		{ return 1; }

	public void setW(double w)
		{}

	/**
	 * gets a string representation of the object
	 * @return a string with x, y, and z in it
	 */
	public String toString()
		{ return "{" + x + ", " + y + ", " + z + ", " + 1 + "}"; }

	/**
	 * creates an array containing all the coordinates
	 * @return an array containing all the coordinates
	 */
	public double[] toArray()
		{
		double[] result = {x, y, z};
		return result;
		}

	/**
	 * applies a transformation matrix to the point
	 * @param trans a transformation matrix
	 * @return this
	 */
	public Point3D apply(TransformationMatrix trans)
		{
		double[][] pointMatrix = { {x}, {y}, {z}, {1} };
		TransformationMatrix m = new TransformationMatrix.CustomTransform(pointMatrix);
		m.apply(trans);

		x = m.get(0,0);
		y = m.get(0,1);
		z = m.get(0,2);

		return this;
		}
	
	public Point3D clone()
		{
		try
			{ return (Point3D) super.clone(); }
		catch (CloneNotSupportedException e)
			{ throw new AssertionError(e); }
		}
	}
