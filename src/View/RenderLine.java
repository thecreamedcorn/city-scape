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

package View;

import Model.Point3D;
import Model.TransformationMatrix;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by 18wgaboury on 4/21/2017.
 */
public class RenderLine implements Cloneable
	{
	private Point3D point1;
	private Point3D point2;
	private double thickness;
	private int color;
	
	public RenderLine(Point3D point1, Point3D point2, double thickness, int color)
		{
		this.point1 = point1;
		this.point2 = point2;
		this.color = color;
		this.thickness = thickness;
		}
	public RenderLine()
		{
		this.point1 = new Point3D(0,0,0);
		this.point2 = new Point3D(0,0,0);
		}
	
	public Collection<Point3D> getPoints()
		{
		ArrayList<Point3D> points = new ArrayList<>();
		points.add(point1);
		points.add(point2);
		return points;
		}
	
	void setPoints(Point3D point1, Point3D point2)
		{
		this.point1 = point1;
		this.point2 = point2;
		}
	
	public void setColor(int color)
		{ this.color = color; }
	
	public int getColor()
		{ return color; }
	
	public double getThickness()
		{ return thickness; }
	
	public double getXYSlope()
		{ return (point1.getY() - point2.getY()) / (point1.getX() - point2.getX()); }
	
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
	
	public String toString()
		{ return "{" + point1 + ", " + point2 + "}"; }
	
	public RenderLine apply(TransformationMatrix m)
		{
		point1.apply(m);
		point2.apply(m);
		return this;
		}
	
	public RenderLine compound(TransformationMatrix m)
		{
		RenderLine result = new RenderLine();
		result.color = color;
		result.thickness = thickness;
		result.point1 = point1.clone().apply(m);
		result.point2 = point2.clone().apply(m);
		return result;
		}
	
	public RenderLine clone()
		{
		try
			{ return (RenderLine) super.clone(); }
		catch (CloneNotSupportedException e)
			{ throw new AssertionError(e); }
		}
	}
