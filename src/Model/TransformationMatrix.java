package Model;

import MathLib.MatrixMath;
import MathLib.WrongSizeMatrixException;

/**
 * Created by 18wgaboury on 4/24/2017.
 * A base class for transformation matrix classes that represent the transformation of a 3D object using an internal
 * 4x4 matrix of doubles
 */
public class TransformationMatrix implements Cloneable
	{
	protected double[][] matrix;

	/**
	 * creates an empty transformation matrix
	 */
	protected TransformationMatrix() {}

	/**
	 * copy constructor
	 * @param copyFrom anouther tnasformation matrix
	 */
	public TransformationMatrix(TransformationMatrix copyFrom)
		{ matrix = MatrixMath.copy(copyFrom.matrix); }

	/**
	 * multiplies together two matrices and stores it in this
	 * @param trans anouther transformation matrix
	 * @return this
	 */
	public TransformationMatrix apply(TransformationMatrix trans)
		{
		try
			{
			matrix = MatrixMath.multiply(trans.matrix, matrix);
			}
		catch (WrongSizeMatrixException e)
			{ throw new AssertionError(e); }
		return this;
		}

	/**
	 * creates a brand new transformation matrix and stores the result of a multiplication in that
	 * @param trans transformation matrix to apply
	 * @return new transformation matrix containing result
	 */
	public TransformationMatrix compound(TransformationMatrix trans)
		{ return makeCopy().apply(trans); }
	
	public TransformationMatrix makeCopy()
		{
		TransformationMatrix res = new TransformationMatrix();
		res.matrix = MatrixMath.copy(matrix);
		return res;
		}

	/**
	 * gets the value of the matrix at a given x and y value
	 * @param x
	 * @param y
	 * @return the value at given coordinates
	 */
	public double get(int x, int y)
		{
		return matrix[y][x];
		}

	/**
	 * returns the 2D matrix of the internal represenstation
	 * @return
	 */
	public double[][] getMatrix()
		{ return matrix; }

	/**
	 * outputs a string of the 2D matrix
	 * @return a string of object state
	 */
	public String toString()
		{ return MatrixMath.toString(matrix); }

	/**
	 * creates a clone of this
	 * @return a clone of this object
	 */
	public TransformationMatrix clone()
		{
		try
			{
			TransformationMatrix result = (TransformationMatrix) super.clone();
			result.matrix = MatrixMath.copy(matrix);
			return result;
			}
		catch (CloneNotSupportedException e)
			{ throw new AssertionError(e); }
		}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//all the classes --- note to self: these could be put in a separate package for organizational purposes

	/**
	 * creates an identity matrix (A matrix that when multiplied by another matrix does not change the other matrix at
	 * all, its kind of like multiplying by 1 but for matrices)
	 */
	public static class IdentityMatrix extends TransformationMatrix
		{
		public IdentityMatrix()
			{
			double[][] temp = {
					{1, 0, 0, 0},
					{0, 1, 0, 0},
					{0, 0, 1, 0},
					{0, 0, 0, 1}
			};
			matrix = temp;
			}
		}

	/**
	 * creates a transformation matrix using a given 2D double array
	 */
	public static final class CustomTransform extends TransformationMatrix
		{
		public CustomTransform(double[][] matrix)
			{ this.matrix = MatrixMath.copy(matrix); }
		}

	/**
	 * creates a rotation matrix around the X axis given an angle of rotation
	 */
	public static final class XAxisRotation extends TransformationMatrix
		{
		public XAxisRotation(double angle)
			{
			double[][] applyMatrix = {
					{1, 0, 0, 0},
					{0, Math.cos(angle), -Math.sin(angle), 0},
					{0, Math.sin(angle), Math.cos(angle), 0},
					{0, 0, 0, 1}
			};
			this.matrix = applyMatrix;
			}
		}

	/**
	 * creates a rotation matrix around the Y axis given an angle of rotation
	 */
	public static final class YAxisRotation extends TransformationMatrix
		{
		public YAxisRotation(double angle)
			{
			double[][] applyMatrix = {
					{Math.cos(angle), 0, Math.sin(angle), 0},
					{0, 1, 0, 0},
					{-Math.sin(angle), 0, Math.cos(angle), 0},
					{0, 0, 0, 1}
			};
			matrix = applyMatrix;
			}
		}

	/**
	 * creates a rotation matrix around the Z axis given an angle of rotation
	 */
	public static final class ZAxisRotation extends TransformationMatrix
		{
		public ZAxisRotation(double angle)
			{
			double[][] applyMatrix = {
					{Math.cos(angle), -Math.sin(angle), 0, 0},
					{Math.sin(angle), Math.cos(angle), 0, 0},
					{0, 0, 1, 0},
					{0, 0, 0, 1}
			};
			matrix = applyMatrix;
			}
		}

	/**
	 * creates a translational transformation matrix that moves all points by the direction specified in each coordinal
	 * direction
	 */
	public static final class Transpose extends TransformationMatrix
		{
		public Transpose(double x, double y, double z)
			{
			double[][] applyMatrix = {
					{1, 0, 0, x},
					{0, 1, 0, y},
					{0, 0, 1, z},
					{0, 0, 0, 1}
			};
			matrix = applyMatrix;
			}
		}

	/**
	 * creates a scale transformation matrix that scales by the factor provided on each axis
	 */
	public static final class Scale extends TransformationMatrix
		{
		public Scale(double x, double y, double z)
			{
			double[][] applyMatrix = {
					{x, 0, 0, 0},
					{0, y, 0, 0},
					{0, 0, z, 0},
					{0, 0, 0, 1}
			};
			matrix = applyMatrix;
			}
		}

	/**
	 * this rotates an object on its own local X axis and not the global one
	 */
	public static final class WeirdXRotate extends TransformationMatrix
		{
		public WeirdXRotate(TransformationMatrix m, double angle)
			{
			double u = m.matrix[0][0];
			double v = m.matrix[1][0];
			double w = m.matrix[2][0];
			
			double[][] applyMatrix = {
					{u * u + (1 - u * u) * Math.cos(angle),
							u * v * (1 - Math.cos(angle)) - w * Math.sin(angle),
							u * w * (1 - Math.cos(angle)) + v * Math.sin(angle),
							0},
					{u * v * (1 - Math.cos(angle)) + w * Math.sin(angle),
							v * v + (1 - v * v) * Math.cos(angle),
							v * w * (1 - Math.cos(angle)) - u * Math.sin(angle),
							0},
					{u * w * (1 - Math.cos(angle)) - v * Math.sin(angle),
							v * w * (1 - Math.cos(angle)) + u * Math.sin(angle),
							w * w + (1 - w * w) * Math.cos(angle),
							0},
					{0, 0, 0, 1}
			};
			matrix = applyMatrix;
			}
		}

	/**
	 * this rotates an object around its own local Y axis and not the global one
	 */
	public static final class WeirdYRotate extends TransformationMatrix
		{
		public WeirdYRotate(TransformationMatrix m, double angle)
			{
			double u = m.matrix[0][1];
			double v = m.matrix[1][1];
			double w = m.matrix[2][1];
			
			double[][] applyMatrix = {
					{u * u + (1 - u * u) * Math.cos(angle),
							u * v * (1 - Math.cos(angle)) - w * Math.sin(angle),
							u * w * (1 - Math.cos(angle)) + v * Math.sin(angle),
							0},
					{u * v * (1 - Math.cos(angle)) + w * Math.sin(angle),
							v * v + (1 - v * v) * Math.cos(angle),
							v * w * (1 - Math.cos(angle)) - u * Math.sin(angle),
							0},
					{u * w * (1 - Math.cos(angle)) - v * Math.sin(angle),
							v * w * (1 - Math.cos(angle)) + u * Math.sin(angle),
							w * w + (1 - w * w) * Math.cos(angle),
							0},
					{0, 0, 0, 1}
			};
			matrix = applyMatrix;
			}
		}

	/**
	 * finds the inverse matrix
	 */
	public static final class Inverse extends TransformationMatrix
		{
		public Inverse(TransformationMatrix m)
			{
			try
				{ matrix = MatrixMath.inverse(m.getMatrix()); }
			catch (WrongSizeMatrixException e)
				{ throw new AssertionError(e); }
			}
		}

	/**
	 * creates a view to projection transformation matrix. Takes objects form view space into projection space
	 */
	public static class ViewToProjection extends TransformationMatrix
		{
		/**
		 * @param fovx angle of screen x
		 * @param fovy angle of screen y
		 * @param znear near plane
		 * @param zfar fare plane
		 */
		public ViewToProjection (double fovx, double fovy, double znear, double zfar)
			{
			double[][] result = {
					{Math.atan(fovx/2), 0, 0, 0},
					{0, Math.atan(fovy/2), 0, 0},
					{0, 0, -((zfar + znear)/(zfar - znear)), -((2 * znear * zfar)/(zfar - znear))},
					{0, 0, -1, 0}
			};
			matrix = result;
			}

		/**
		 * @param left screen size left of middle
		 * @param right screen size right of middle
		 * @param bottom screen size down from middle
		 * @param top screen size up from middle
		 * @param near near plane
		 * @param far far plane
		 */
		public ViewToProjection (double left, double right, double bottom, double top, double near, double far)
			{
			double[][] result = {
					{(2 * near)/(right - left), 0, (right + left)/(right - left), 0},
					{0, (2 * near)/(top - bottom), (top + bottom)/(top - bottom), 0},
					{0, 0, -((far + near)/(far - near)), -((2 * near * far)/(far - near))},
					{0, 0, -1, 0}
			};
			matrix = result;
			}
		}
	}
