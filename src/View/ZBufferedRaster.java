package View;

import Model.Point3D;
import MathLib.Line2D;
import MathLib.Plane3D;

import java.awt.image.BufferedImage;

/**
 * Created by 18wgaboury on 4/20/2017.
 * this creates a raster image using the buffered image class for storing pixels along with holding a z buffer so that
 * no pixels get overwritten during processing
 */
public class ZBufferedRaster
	{
	private int sizex;
	private int sizey;
	private double[][] zBuffer;
	BufferedImage rasterImage;

	/**
	 * creates new ZBufferedRaster of given height and width
	 * @param sizex width
	 * @param sizey hegiht
	 */
	public ZBufferedRaster(int sizex, int sizey)
		{
		rasterImage = new BufferedImage(sizex, sizey, BufferedImage.TYPE_INT_RGB);
		resetSize(sizex, sizey);
		}

	/**
	 * resets the height and width of the raster
	 * @param sizex width
	 * @param sizey height
	 */
	public void resetSize(int sizex, int sizey)
		{
		this.sizex = sizex;
		this.sizey = sizey;
		zBuffer = new double[sizey][sizex];
		for (int y = 0; y < zBuffer.length; y++)
			{
			for (int x = 0; x < zBuffer[0].length; x++)
				{ zBuffer[y][x] = 0; }
			}
		rasterImage = new BufferedImage(sizex, sizey, BufferedImage.TYPE_INT_RGB);
		}

	/**
	 * resets all the pixels in the raster
	 */
	public void reset()
		{
		for (int y = 0; y < zBuffer.length; y++)
			{
			for (int x = 0; x < zBuffer[0].length; x++)
				{ zBuffer[y][x] = 0; }
			}
		rasterImage = new BufferedImage(sizex, sizey, BufferedImage.TYPE_INT_RGB);
		}

	/**
	 * gets the internal and actually useful BufferedImage object
	 * @return a buffered image object
	 */
	public BufferedImage getRasterImage()
		{ return rasterImage; }

	/**
	 * gets the width
	 * @return the width
	 */
	public int getX()
		{ return sizex; }

	/**
	 * gets the height
	 * @return height
	 */
	public int getY()
		{ return sizey; }
	
	/**
	 * takes in a render triangle and then draws the triangle line by line onto the buffer but makes sure not to
	 * overwrite any pixels with a smaller Z value then it
	 * @param tri a triangle to render
	 */
	public void renderTriangle(RenderTriangle tri)
		{
		Point3D[] points = tri.getPointsInYOrder();
		Plane3D zFinder = new Plane3D(  tri.getPoint1(),
										tri.getPoint2(),
										tri.getPoint3() );

		Line2D leftTracer = new Line2D();
		Line2D rightTracer = new Line2D();
		Point3D leftTracersSecondPoint;
		
		int y = (int)(points[0].getY() + 0.5);
		if (points[0].getY() != points[1].getY())
			{
			if (points[2].getX() > (new Line2D(points[0], points[1]).solveForX(points[2].getY())))
				{
				leftTracer.setWithPoints(points[0], points[1]);
				leftTracersSecondPoint = points[1];
				rightTracer.setWithPoints(points[0], points[2]);
				}
			else
				{
				leftTracer.setWithPoints(points[0], points[2]);
				leftTracersSecondPoint = points[2];
				rightTracer.setWithPoints(points[0], points[1]);
				}
			
			while (y < (int)(points[1].getY() + 0.5))
				{
				if ( y >= 0 && y < sizey)
					{
					for (int x = (int) (leftTracer.solveForX(y + 0.5) + 0.5);
					     x <= (int) (rightTracer.solveForX(y + 0.5) - 0.5);
					     x++)
						{
						if (x >= 0 && x < sizex)
							{
							double z = zFinder.solveForZ(x + 0.5, y + 0.5);
							if (z >= zBuffer[y][x] && z >= 0 && z <= 1)
								{
								zBuffer[y][x] = z;
								rasterImage.setRGB(x, y, tri.getColor());
								}
							}
						}
					}
				
				y++;
				}
			
			if (leftTracersSecondPoint == points[1])
				{ leftTracer.setWithPoints(points[1], points[2]); }
			else
				{ rightTracer.setWithPoints(points[1], points[2]); }
			}
		else
			{
			if (points[0].getX() < points[1].getX())
				{
				leftTracer.setWithPoints(points[0], points[2]);
				rightTracer.setWithPoints(points[1], points[2]);
				}
			else
				{
				leftTracer.setWithPoints(points[1], points[2]);
				rightTracer.setWithPoints(points[0], points[2]);
				}
			}
		
		while (y < (int)(points[2].getY() + 0.5))
			{
			if ( y >= 0 && y < sizey)
				{
				for (int x = (int) (leftTracer.solveForX(y + 0.5) + 0.5);
				     x <= (int) (rightTracer.solveForX(y + 0.5) - 0.5);
				     x++)
					{
					if ( x >= 0 && x < sizex)
						{
						double z = zFinder.solveForZ(x + 0.5, y + 0.5);
						if (z >= zBuffer[y][x] && z >= 0 && z <= 1)
							{
							zBuffer[y][x] = z;
							rasterImage.setRGB(x, y, tri.getColor());
							}
						}
					}
				}
			
			y++;
			}
		}

	/**
	 * takes a render line and draws it onto the buffer
	 * @param line a render line
	 */
	public void renderLine(RenderLine line)
		{
		double angle = -(Math.atan(line.getXYSlope()));
		
		Point3D corner1 = new Point3D(
				line.getPoint1().getX() + Math.sin(angle) * line.getThickness(),
				line.getPoint1().getY() + Math.cos(angle) * line.getThickness(),
				line.getPoint1().getZ()
			);
		Point3D corner2 = new Point3D(
				line.getPoint1().getX() - Math.sin(angle) * line.getThickness(),
				line.getPoint1().getY() - Math.cos(angle) * line.getThickness(),
				line.getPoint1().getZ()
			);
		Point3D corner3 = new Point3D(
				line.getPoint2().getX() + Math.sin(angle) * line.getThickness(),
				line.getPoint2().getY() + Math.cos(angle) * line.getThickness(),
				line.getPoint2().getZ()
			);
		Point3D corner4 = new Point3D(
				line.getPoint2().getX() - Math.sin(angle) * line.getThickness(),
				line.getPoint2().getY() - Math.cos(angle) * line.getThickness(),
				line.getPoint2().getZ()
			);
		
		renderTriangle(new RenderTriangle(corner1, corner2, corner4, line.getColor()));
		renderTriangle(new RenderTriangle(corner3, corner4, corner1, line.getColor()));
		}
	}
