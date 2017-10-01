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
 * represents a line in 3D space using the equation ax + by = 1 = cz
 */
public class Line3D
	{
	private double a;
	private double b;
	private double c;

	/**
	 * creates a line in 3D space by using two 3D coordinates
	 * @param p1 first point
	 * @param p2 second point
	 */
	public Line3D(Point3D p1, Point3D p2)
		{ setWithPoints(p1, p2); }

	/**
	 * creates an empty line
	 */
	public Line3D()
		{
		a = 0;
		b = 0;
		}

	/**
	 * resets the line using two new points
	 * @param p1 first point
	 * @param p2 second point
	 */
	public void setWithPoints(Point3D p1, Point3D p2)
		{
		double[][] result = {{1}, {1}};
		double[][] pass = {
				{p1.getX(), p1.getY()},
				{p2.getX(), p2.getY()}
		};
		try
			{
			result = MatrixMath.multiply(MatrixMath.inverse(pass), result);
			}
		catch (WrongSizeMatrixException e)
			{
			System.out.print(e);
			}

		a = result[0][0];
		b = result[1][0];
		c = (a * p1.getX()) / p1.getZ();
		}

	/**
	 * solves for the X value given a Y value
	 * @param y a Y value
	 * @return the X value
	 */
	public double solveForXwithY(double y)
		{ return (1 - (y * b)) / a; }

	/**
	 * solves for the X value given the Z value
	 * @param z a Z value
	 * @return the X value
	 */
	public double solveForXwithZ(double z)
		{ return (c * z) / a; }

	/**
	 * solves for the Y value given an X value
	 * @param x an X value
	 * @return the Y value
	 */
	public double solveForYwithX(double x)
		{ return (1 - (x * a)) / b;}

	/**
	 * solves for the Y value given a Z value
	 * @param z a Z value
	 * @return the Y value
	 */
	public double solveForYwithZ(double z)
		{ return (1 - (c * z)) / b; }

	/**
	 * solves for the Z value given an X value
	 * @param x an X value
	 * @return the Z value
	 */
	public double solveForZwithX(double x)
		{ return (a * x) / c; }

	/**
	 * solves for the Y
	 * @param y
	 * @return
	 */
	public double solveForZwithY(double y)
		{ return (1 - (b * y)) / c; }

	/**
	 * creates a string with the a, b, and c values in it
	 * @return a string representing object state
	 */
	public String toString()
		{ return "a: " + a + ", b: " + b + ", c: " + c; }

	/**
	 * returns the a value
	 * @return a
	 */
	public double getA()
		{
		return a;
		}

	/**
	 * return the b value
	 * @return b
	 */
	public double getB()
		{
		return b;
		}

	/**
	 * returns the C value
	 * @return c
	 */
	public double getC()
		{
		return c;
		}
	}
