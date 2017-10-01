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

package MathLib;

import Model.Point3D;

/**
 * Created by 18wgaboury on 4/22/2017.
 * represents a line on a 2D plane, only xy
 */
public class Line2D
	{
	private double a;
	private double b;

	/**
	 * constructor that takes in two points and finds the equation of the line though them
	 * @param p1 point number 1
	 * @param p2 point number 2
	 */
	public Line2D(Point3D p1, Point3D p2)
		{ setWithPoints(p1, p2); }

	/**
	 * a constructor for an empty line
	 */
	public Line2D()
		{
		a = 0;
		b = 0;
		}

	/**
	 * allows you to reset the line with two new points
	 * @param p1 first point
	 * @param p2 second point
	 */
	public void setWithPoints(Point3D p1, Point3D p2)
		{
		double[][] result = { {1}, {1} };
		double[][] pass = {
				{p1.getX(), p1.getY()},
				{p2.getX(), p2.getY()}
		};
		try
			{
			result = MatrixMath.multiply(MatrixMath.inverse(pass), result);
			}
		catch (WrongSizeMatrixException e)
			{ System.out.print(e); }
		
		a = result[0][0];
		b = result[1][0];
		}

	/**
	 * finds the X value given any y value
	 * @param y any y value
	 * @return the X value
	 */
	public double solveForX(double y)
		{ return (1 - (y * b)) / a; }

	/**
	 * finds the Y value given any X volue
	 * @param x any X value
	 * @return the X value
	 */
	public double solveForY(double x)
		{ return (1 - (x * a)) / b;}

	/**
	 * outputs a and b; the line is represented by the equation ax + by = 1
	 * @return the lines data in string form
	 */
	public String toString()
		{ return "a: " + a + ", b: " + b; }

	/**
	 * outputs the line in y = mx + b form
	 * @return the lines data
	 */
	public String mxPlusBForm()
		{
		return "y = (" + -(a / b) + ")x + (" + 1/b + ")";
		}
	}
