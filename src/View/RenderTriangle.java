package View;

import Model.Point3D;
import Model.TransformationMatrix;
import MathLib.Plane3D;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This represents a triangle in 3D space with three 3D points
 */
public class RenderTriangle implements Cloneable
	{
	private Point3D point1;
	private Point3D point2;
	private Point3D point3;
	private int color;

	/**
	 * gets the list of points
	 * @return the list of points
	 */
	public Collection<Point3D> getPoints()
		{
		ArrayList<Point3D> res = new ArrayList<>();
		res.add(point1);
		res.add(point2);
		res.add(point3);
		return res;
		}

	/**
	 * creates a render triangle using three points and its color
	 * @param point1
	 * @param point2
	 * @param point3
	 * @param color
	 */
	public RenderTriangle(Point3D point1, Point3D point2, Point3D point3, int color)
		{
		this.point1 = point1;
		this.point2 = point2;
		this.point3 = point3;
		this.color = color;
		}

	/**
	 * creates an empty render triangle
	 */
	public RenderTriangle()
		{
		point1 = new Point3D();
		point2 = new Point3D();
		point3 = new Point3D();
		color = 0;
		}

	/**
	 * sets all the points at once
	 * @param point1
	 * @param point2
	 * @param point3
	 */
	public void setPoints(Point3D point1, Point3D point2, Point3D point3)
		{
		this.point1 = point1;
		this.point2 = point2;
		this.point3 = point3;
		}

	/**
	 * sets the color
	 * @param color the new color
	 */
	public void setColor(int color)
		{ this.color = color; }

	/**
	 * gets the color
	 * @return the color
	 */
	public int getColor()
		{ return color; }

	//self explanator setters and getters

	public Point3D getPoint1()
		{
		return point1;
		}
	
	public void setPoint1(Point3D point1)
		{
		this.point1 = point1;
		}
	
	public Point3D getPoint2()
		{
		return point2;
		}
	
	public void setPoint2(Point3D point2)
		{
		this.point2 = point2;
		}
	
	public Point3D getPoint3()
		{
		return point3;
		}
	
	public void setPoint3(Point3D point3)
		{
		this.point3 = point3;
		}

	/**
	 * makes a plane3d object out of the three points of the triangle
	 * @return
	 */
	Plane3D getPlane()
		{ return new Plane3D(point1, point2, point3); }

	/**
	 * organizes the points in order according to decending y values
	 * @return list of points
	 */
	Point3D[] getPointsInYOrder()
		{
		Point3D[] order = new Point3D[3];
		order[0] = point1;
		order[1] = point2;
		order[2] = point3;
		
		if (order[0].getY() > order[1].getY())
			{
			Point3D temp = order[0];
			order[0] = order[1];
			order[1] = temp;
			}
		if (order[1].getY() > order[2].getY())
			{
			Point3D temp = order[1];
			order[1] = order[2];
			order[2] = temp;
			}
		if (order[0].getY() > order[1].getY())
			{
			Point3D temp = order[0];
			order[0] = order[1];
			order[1] = temp;
			}
		
		return order;
		}

	/**
	 * applies a transformation matrix
	 * @param m a transformation matrix
	 * @return this
	 */
	public RenderTriangle apply(TransformationMatrix m)
		{
		point1.apply(m);
		point2.apply(m);
		point3.apply(m);
		return this;
		}

	/**
	 * creates a string representation of the object
	 * @return a string representation of the object
	 */
	public String toString()
		{ return "{ " + point1 + ", " + point2 + ", " + point3 + "}"; }

	/**
	 * creates a clone of the render triangle
	 * @return a clone of the render triangle
	 */
	public RenderTriangle clone()
		{
		try
			{
			RenderTriangle result = (RenderTriangle) super.clone();
			result.point1 = point1.clone();
			result.point2 = point2.clone();
			result.point3 = point3.clone();
			return result;
			}
		catch (CloneNotSupportedException e)
			{ throw new AssertionError(e); }
		}
	}
