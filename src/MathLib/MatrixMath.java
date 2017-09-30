package MathLib;

/**
 * This is a kind of fragile library for handling all the matrix math that is needed by the program. Most of it's
 * functionality is just static functions called on for operating on arrays. All of this is covered up to program by the
 * TransformationMatrix classes.
 */
public class MatrixMath
	{
	/**
	 * adds together two matrices
	 * @param m1 first matrix
	 * @param m2 second matrix
	 * @return a new matrix containing the result
	 * @throws WrongSizeMatrixException if the matrices are not the same dimensions
	 */
	public static double[][] add(double[][] m1, double[][] m2) throws WrongSizeMatrixException
		{
		if ( m1.length != m2.length || m1[0].length != m2[0].length)
			{ throw new WrongSizeMatrixException(m1, m2); }
		
		double[][] result = new double[m1.length][m1[0].length];
		for (int x = 0; x < m1.length; x++)
			{
			for (int y = 0; y < m1[0].length; y++)
				{ result[x][y] = m1[x][y] + m2[x][y]; }
			}
		return result;
		}

	/**
	 * multiplies the matrix by a scalar
	 * @param m the matrix
	 * @param scalar the scalar value
	 * @return a new matrix containing the result
	 */
	public static double[][] scalar(double[][] m, double scalar)
		{
		double[][] result = new double[m.length][m[0].length];
		
		for (int y = 0; y < m.length; y++)
			{
			for (int x = 0; x < m[0].length; x++)
				{ result[y][x] = scalar * m[y][x]; }
			}
		return result;
		}

	/**
	 * subtracts two matrices
	 * @param m1 first matrix
	 * @param m2 second matrix
	 * @return a new matrix containing the result
	 * @throws WrongSizeMatrixException thrown if the matrices are not the same size
	 */
	public static double[][] subtract(double[][] m1, double[][] m2) throws WrongSizeMatrixException
		{
		if ( m1.length != m2.length || m1[0].length != m2[0].length)
			{ throw new WrongSizeMatrixException(m1, m2); }
		
		double[][] result = new double[m1.length][m1[0].length];
		for (int x = 0; x < m1.length; x++)
			{
			for (int y = 0; y < m1[0].length; y++)
				{ result[x][y] = m1[x][y] + m2[x][y]; }
			}
		return result;
		}

	/**
	 * multiplies together two matrices
	 * @param m1 first matrix
	 * @param m2 second matrix
	 * @return a new matrix containing the result
	 * @throws WrongSizeMatrixException thrown if the length of the first is not the same as the height of the second
	 */
	public static double[][] multiply(double[][] m1, double[][] m2) throws WrongSizeMatrixException
		{
		if ( m1[0].length != m2.length)
			{ throw new WrongSizeMatrixException(m1, m2); }
		
		double[][] result = new double[m1.length][m2[0].length];
		for (int y = 0; y < result.length; y++)
			{
			for (int x = 0; x < result[0].length; x++)
				{
				result[y][x] = 0;
				//dot product
				for (int i = 0; i < m1[0].length; i++)
					{ result[y][x] += m1[y][i] * m2[i][x]; }
				}
			}
		return result;
		}

	/**
	 * finds the determinant value of a matrix. Important in performing inverses and other matrix operations
	 * @param m a matrix
	 * @return the determinant value
	 * @throws WrongSizeMatrixException if the matrix is not a square then a determinant is not possible
	 */
	public static double determinant(double[][] m) throws WrongSizeMatrixException
		{
		if (m.length < 2 || m[0].length < 2 || m.length != m[0].length)
			{ throw new WrongSizeMatrixException(); }
		
		return determinantHelper(m);
		}

	/**
	 * helper recursive function in finding determinant because it is a recursive process
	 * @param m a matrix
	 * @return the determinant of a given submatrix
	 */
	private static double determinantHelper(double[][] m)
		{
		if (m.length == 2)
			{ return m[0][0] * m[1][1] - m[0][1] * m[1][0]; }
		
		double result = 0;
		
		for (int i = 0; i < m[0].length; i++)
			{
			double[][] subArray = new double[m.length - 1][m.length -1];
			
			//fill subArray
			int xOrig = 0;
			int xNew = 0;
			while (xOrig < m[0].length)
				{
				if (xOrig == i)
					{
					xOrig++;
					continue;
					}
				
				for (int y = 1; y < m.length; y++)
					{ subArray[y - 1][xNew] = m[y][xOrig]; }
				
				xOrig++;
				xNew++;
				}
			
			if ((i & 1) == 0)
				{ result += m[0][i] * determinantHelper(subArray); }
			else
				{ result -= m[0][i] * determinantHelper(subArray); }
			}
		return result;
		}

	/**
	 * finds the inverse matrix for a given input matrix
	 * @param m a matrix
	 * @return the inverse of the given matrix
	 * @throws WrongSizeMatrixException if the matrix is too or not square small it will be thrown
	 */
	public static double[][] inverse(double[][] m) throws WrongSizeMatrixException
		{
		if (m.length < 2 || m[0].length < 2 || m.length != m[0].length)
			{ throw new WrongSizeMatrixException(); }
		
		double[][] result = new double[m.length][m.length];
		
		//matrix of minors
		if (m.length == 2)
			{
			double mult = 1 / determinant(m);
			result[0][0] = mult * m[1][1];
			result[1][1] = mult * m[0][0];
			result[0][1] = -mult * m[0][1];
			result[1][0] = -mult * m[1][0];
			return result;
			}
		
		for (int i = 0; i < m.length; i++) // go through ys
			{
			for (int r = 0; r < m.length; r++) // go through xs
				{
				double[][] subArray = new double[m.length - 1][m.length - 1];
				
				//fill subArray
				int yOrig = 0;
				int yNew = 0;
				while (yOrig < m.length)
					{
					if (yOrig == i)
						{
						yOrig++;
						continue;
						}
					
					int xOrig = 0;
					int xNew = 0;
					while (xOrig < m[0].length)
						{
						if (xOrig == r)
							{
							xOrig++;
							continue;
							}
						
						subArray[xNew][yNew] = m[xOrig][yOrig];
						
						xOrig++;
						xNew++;
						}
					
					yNew++;
					yOrig++;
					}
				
				result[r][i] = determinant(subArray);
				}
			}
		
		//apply matrix of coFactors
		for (int y = 0; y < m.length; y++)
			{
			for (int x = 0; x < m.length; x++)
				{
				if ((x & 1) == 0)
					{ result[x][y] *= -1; }
				if ((y & 1) == 0)
					{ result[x][y] *= -1; }
				}
			}
		
		//perform adjugate
		for (int y = 0; y < m.length; y++)
			{
			for (int x = y + 1; x < m.length; x++)
				{
				double temp = result[y][x];
				result[y][x] = result[x][y];
				result[x][y] = temp;
				}
			}
		
		double det = determinant(m);
		if (det == 0)
			{ return result; }
		else
			{ return scalar(result, 1/det); }
		}

	/**
	 * Takes 2D double array representing a matrix and creates a string representation of it
	 * @param m a matrix
	 * @return the string representing it
	 */
	public static String toString(double[][] m)
		{
		String result = "{ ";
		for (double d1[] : m)
			{
			result += "{";
			for (double d2 : d1)
				{ result += d2 + ","; }
			result += "} ";
			}
		return result + "}";
		}

	/**
	 * makes a copy of a matrix
	 * @param m a matrix
	 * @return the copy
	 */
	public static double[][] copy(double[][] m)
		{
		double[][] newMatrix = new double[m.length][m[0].length];
		for (int y = 0; y < m.length; y++)
			{
			for (int x = 0; x < m[0].length; x++)
				{ newMatrix[y][x] = m[y][x]; }
			}
		return newMatrix;
		}

	/**
	 * finds the dot product of two vectors (1D matrices)
	 * @param v1 first vector
	 * @param v2 second vector
	 * @return the dot product
	 * @throws WrongSizeMatrixException if the two vectors are not the same size
	 */
	public static double dotProduct(double[] v1, double[] v2) throws WrongSizeMatrixException
		{
		if (v1.length != v2.length)
			{ throw new WrongSizeMatrixException("Vectors are incompatible sizes in order to calculate dot product"); }

		double result = 0;

		for (int i = 0; i < v1.length; i++)
			{ result += v1[i] * v2[i]; }

		return result;
		}

	/**
	 * scales a vector by a given value
	 * @param v a vector
	 * @param scalar the scalar value
	 * @return a new vector containing the result
	 */
	public static double[] scaleVector(double[] v, double scalar)
		{
		double[] result = new double[v.length];
		for (int i = 0; i < v.length; i++)
			{ result[i] = v[i] * scalar; }
		return result;
		}

	/**
	 * adds two vectors
	 * @param v1 first vector
	 * @param v2 second vector
	 * @return new vector containing result
	 */
	public static double[] vectorAdd(double[] v1, double[] v2)
		{
		double[] result = new double[v1.length];
		for (int i = 0; i < v1.length; i++)
			{ result[i] = v1[i] + v2[i]; }
		return result;
		}
	}
