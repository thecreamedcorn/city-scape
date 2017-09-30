package MathLib;

/**
 * Created by 18wgaboury on 4/20/2017.
 * Special exception class that is thrown during matrix algorithms if the matrices are not the correct sizes
 */
public class WrongSizeMatrixException extends Exception
	{
	private String str;

	/**
	 * just outputs the string as the error message
	 * @param str a string
	 */
	public WrongSizeMatrixException(String str)
		{ this.str = str; }

	/**
	 * an empty wrong error message
	 */
	public WrongSizeMatrixException()
		{ str = ""; }

	/**
	 * creates a message given the matrices given hand and outputs their sizes
	 * @param m1 first matrix
	 * @param m2 second matrix
	 */
	public  WrongSizeMatrixException(double[][] m1, double[][] m2)
		{
		int sizex1;
		int sizey1;
		int sizex2;
		int sizey2;

		sizex1 = (sizey1 = m1.length) > 0 ? m1[0].length : 0;
		sizex2 = (sizey2 = m2.length) > 0 ? m2[0].length : 0;

		str = "Matrices were of incompatible sizes, " +
				"matrix 1: {" + sizex1 + "," + sizey1 + "}, " +
				"matrix 2: {" + sizex2 + "," + sizey2 + "}";
		}

	/**
	 * creates the error message based off sizes manually entered
	 * @param sizex1 first matrix x dimension
	 * @param sizey1 first matrix y dimension
	 * @param sizex2 second matrix x dimension
	 * @param sizey2 second matrix y dimension
	 */
	public WrongSizeMatrixException(int sizex1, int sizey1, int sizex2, int sizey2)
		{
		str = "Matrices were of incompatible sizes, " +
				"matrix 1: {" + sizex1 + "," + sizey1 + "}, " +
				"matrix 2: {" + sizex2 + "," + sizey2 + "}";
		}

	/**
	 * outputs the error message
	 * @return the error message
	 */
	public String toString()
		{ return str; }
	}
