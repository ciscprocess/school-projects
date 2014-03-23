import java.awt.Image;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * @author nathan
 *
 */
public class MapRasterObject
{
	private Image[] mSprite;
	protected  double mR = 0;
	private double mU = 0;
	private double mDrawOffsetR = 0;
	private double mDrawOffsetU = 0;
	private int mDirection = 0;
	
	public MapRasterObject(String[] cSpritePath, int cR, int cU)
	{
		mR = cR;
		mU = cU;
		mSprite = new Image[cSpritePath.length];
		try
		{
			for (int forIndex = 0; forIndex < cSpritePath.length; forIndex++)
			{
				mSprite[forIndex] = ImageIO.read(new File(cSpritePath[forIndex]));
			}
		} 
		catch (IOException e)
		{

		}
	}

	/**
	 * @return the r
	 */
	public double getR()
	{
		return mR;
	}

	/**
	 * @param r the r to set
	 */
	public void setR(double r)
	{
		mR = r;
	}

	/**
	 * @return the u
	 */
	public double getU()
	{
		return mU;
	}

	/**
	 * @param u the u to set
	 */
	public void setU(double u)
	{
		mU = u;
	}

	public Point2D getPoint()
	{
		return new Point2D.Double((int)mR, (int)mU);
	}
	
	/**
	 * @return the sprite
	 */
	public Image getSprite()
	{
		return mSprite[mDirection];
	}
	
	public void setSprite(Image[] aSprite)
	{
		mSprite = aSprite;
	}

	/**
	 * @return the drawOffsetR
	 */
	public double getDrawOffsetR()
	{
		return mDrawOffsetR;
	}

	/**
	 * @param drawOffsetR the drawOffsetR to set
	 */
	public void setDrawOffsetR(double drawOffsetR)
	{
		mDrawOffsetR = drawOffsetR;
	}

	/**
	 * @return the drawOffsetU
	 */
	public double getDrawOffsetU()
	{
		return mDrawOffsetU;
	}

	/**
	 * @param drawOffsetU the drawOffsetU to set
	 */
	public void setDrawOffsetU(double drawOffsetU)
	{
		mDrawOffsetU = drawOffsetU;
	}
	
	/**
	 * @return the direction
	 */
	public int getDirection()
	{
		return mDirection;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(int direction)
	{
		mDirection = direction;
		//this.setSprite();
	}
}
