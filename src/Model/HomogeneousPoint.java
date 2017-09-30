package Model;

/**
 * Created by 18wgaboury on 5/8/2017.
 * this is much like a Point3D except that it stores its homogenizing value in w after transformations. This is not needed
 * for normal 3D transformations but is critical to the clipping during the projection transformation step in the rendering
 * process
 */
public class HomogeneousPoint extends Point3D
	{
	protected double w;

	/**
	 * creates a homogeneous point using coordinates
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 */
	public HomogeneousPoint(double x, double y, double z, double w)
		{
		super(x,y,z);
		this.w = w;
		}

	/**
	 * creates a point using a 3DPoint and sets w to 1
	 * @param point coordinates in 3D space
	 */
	public HomogeneousPoint(Point3D point)
		{
		super(point.getX(), point.getY(), point.getZ());
		this.w = 1;
		}

	/**
	 * resets the homogeneous point
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 */
	public void setAll(double x, double y, double z, double w)
		{
		super.setAll(x,y,z);
		this.w = w;
		}

	@Override
	public double getW()
		{ return w; }

	@Override
	public void setW(double w)
		{ this.w = w; }

	@Override
	public String toString()
		{ return "{" + x + ", " + y + ", " + z + ", " + w + "}"; }

	@Override
	public HomogeneousPoint clone()
		{ return (HomogeneousPoint) super.clone(); }

	@Override
	public Point3D apply(TransformationMatrix trans)
		{
		double[][] pointMatrix = { {x}, {y}, {z}, {w} };
		TransformationMatrix m = new TransformationMatrix.CustomTransform(pointMatrix);
		m.apply(trans);

		x = m.get(0,0);
		y = m.get(0,1);
		z = m.get(0,2);
		w = m.get(0,3);

		return this;
		}

	@Override
	public double[] toArray()
		{
		double[] result = {x,y,z,w};
		return result;
		}
	}
