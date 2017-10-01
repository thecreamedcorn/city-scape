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
 * represents a 3D plane mathamatically in the form ax + by + cz = 1
 */
public class Plane3D
	{
	private double a;
	private double b;
	private double c;

	/**
	 * creates an empty plane object
	 */
	public Plane3D()
		{
		a = 0;
		b = 0;
		c = 0;
		}

	/**
	 * creates a Plane3D by finding the plane that passes through these three points
	 * @param p1 first point
	 * @param p2 second point
	 * @param p3 third point
	 */
	public Plane3D(Point3D p1, Point3D p2, Point3D p3)
		{
		double[][] pointMatrix = {
				p1.toArray(),
				p2.toArray(),
				p3.toArray()
		};
		double[][] result = {{1}, {1}, {1}};

		//matrices just act as a really simple system of equations solver
		try
			{
			result = MatrixMath.multiply(MatrixMath.inverse(pointMatrix), result);
			}
		catch (WrongSizeMatrixException e)
			{ System.out.print(e); }

		a = result[0][0];
		b = result[1][0];
		c = result[2][0];
		}

	/**
	 * finds a given X value
	 * @param y y value
	 * @param z z value
	 * @return y value
	 */
	public double solveForX(double y, double z)
		{ return (1 - (b * y + c * z)) / a;	}

	/**
	 * finds a given Y value
	 * @param x x value
	 * @param z z value
	 * @return y value
	 */
	public double solveForY(double x, double z)
		{ return (1 - (a * x + c * z)) / b; }

	/**
	 * finds a given z value
	 * @param x x value
	 * @param y y value
	 * @return z value
	 */
	public double solveForZ(double x, double y)
		{ return (1 - (a * x + b * y)) / c; }

	public String toString()
		{ return "a: " + a + ", b: " + b + ", c: " + c; }

	/**
	 * gets the value a
	 * @return the value of a
	 */
	public double getA()
		{
		return a;
		}

	/**
	 * gets the value b
	 * @return the value of b
	 */
	public double getB()
		{
		return b;
		}

	/**
	 * gets the value c
	 * @return the value of c
	 */
	public double getC()
		{
		return c;
		}
	}
