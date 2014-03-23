import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Jama.Matrix;


public class ImageMatrix
{
	private Matrix[] mLayers;
	
	public ImageMatrix(String cSource) throws IOException 
	{
		Image srcImg = ImageIO.read(new File(cSource));
		mLayers = bitmapToMatrix(srcImg);
	}
	
	public ImageMatrix(Matrix[] cLayers)
	{
		for (Matrix lMat : cLayers)
		{
			for (int forY = 0; forY < lMat.getRowDimension(); forY++)
			{
				for (int forX = 0; forX < lMat.getColumnDimension(); forX++)
				{
					double val = lMat.get(forY, forX);
					val = Math.round(val);
					if (val < 0)
						val = 0;
					if (val > 255)
						val = 255;
					lMat.set(forY, forX, val);
				}
			}
		}
		mLayers = cLayers;
	}
	
	public void writeImage(String aPath) throws IOException
	{
		BufferedImage img = new BufferedImage(mLayers[0].getColumnDimension(), mLayers[0].getRowDimension(), BufferedImage.TYPE_INT_RGB);
		Graphics gfx = img.getGraphics();

		for (int forY = 0; forY < mLayers[0].getRowDimension(); forY++)
		{
			for (int forX = 0; forX < mLayers[0].getColumnDimension(); forX++)
			{
				Color tCol = new Color((int)mLayers[0].get(forY, forX), (int)mLayers[1].get(forY, forX), (int)mLayers[2].get(forY, forX));
				gfx.setColor(tCol);
				gfx.drawRect(forX, forY, 1, 1);
			}
		}
		
		System.out.println(ImageIO.write(img, "bmp", new File(aPath)));
	}
	
	private static Matrix[] bitmapToMatrix(Image aBitmap) throws IOException 
	{	
		BufferedImage img = new BufferedImage(aBitmap.getWidth(null), aBitmap.getHeight(null), BufferedImage.TYPE_INT_RGB);
		img.getGraphics().drawImage(aBitmap, 0, 0, null);
		double[][][] rgbVals = new double[3][img.getHeight()][img.getWidth()];
		
		for (int forY = 0; forY < img.getHeight(); forY++)
		{
			for (int forX = 0; forX < img.getWidth(); forX++)
			{
				Color color = new Color(img.getRGB(forX, forY));
				rgbVals[0][forY][forX] = color.getRed();
				rgbVals[1][forY][forX] = color.getGreen();
				rgbVals[2][forY][forX] = color.getBlue();
			}
		}
		
		Matrix[] imageMatrix = new Matrix[] { new Matrix(rgbVals[0]), new Matrix(rgbVals[1]), new Matrix(rgbVals[2]) };
		
		return imageMatrix;
	}

	public int getColumnDim()
	{
		return mLayers[0].getColumnDimension();
	}
	
	public int getRowDim()
	{
		return mLayers[0].getRowDimension();
	}
	
	public Matrix[] getLayers()
	{
		return mLayers;
	}
}
