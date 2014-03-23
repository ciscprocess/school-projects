import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class MapLoader
{
	private File mFileTile;
	private File mFileObj;
	private Scanner mTileReader = new Scanner("");
	private Scanner mObjReader = new Scanner("");
	private MapRasterObject[][] mObjMap;
	private Color[][] mTileMap;
	private int mRTiles;
	private int mUTiles;
	public MapLoader(int rTiles, int uTiles)
	{
		mFileTile = new File("tileMap.cfg");
		mFileObj = new File("objectMap.cfg");
		mObjMap = new MapRasterObject[uTiles + 1][rTiles  + 1];
		mTileMap = new Color[uTiles][rTiles];
		mRTiles = rTiles;
		mUTiles = uTiles;
		try
		{
			mTileReader = new Scanner(mFileTile);
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			mObjReader = new Scanner(mFileObj);
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public MapRasterObject getObjFromI(int aIndex, int aR, int aU)
	{
		MapRasterObject added;
		if (aIndex == 0)
			added = new BlastTower(aR, aU);
		else if (aIndex == 1)
			added = new SpiddleTower(aR, aU);
		else if (aIndex == 2)
			added = new GasserTower(aR, aU);
		else added = null;
		return added;
	}
	
	
	public Color getColFromI(int aIndex)
	{
		switch (aIndex)
		{
		case 0:
			return Color.decode("#00981D");
		case 1:
			return Color.decode("#724000");
		case 2:
			return Color.decode("#000000");
		case 3:
			return Color.decode("#ECE886");
		}
		return null;
	}
	
	public Color[][] loadTileMap()
	{
		boolean thingIt = true;
		int index = 0;
		while (thingIt)
		{
			try 
			{
				String meLin = mTileReader.nextLine();
				char[] meLin2 = meLin.toCharArray();
				for (int forIndex = 0; forIndex < meLin2.length; forIndex++)
				{
					mTileMap[index][forIndex] = getColFromI(Integer.parseInt(Character.toString(meLin2[forIndex])));
				}
			}
			catch (Exception ex)
			{
				thingIt = false;
			}
			index++;
		}
		return mTileMap;
	}
	
	public MapRasterObject[][] loadObjectMap()
	{
		boolean thingIt = true;
		int index = 0;
		while (thingIt)
		{
			try 
			{
				String meLin = mObjReader.nextLine();
				char[] meLin2 = meLin.toCharArray();
				
				for (int forIndex = 0; forIndex < meLin2.length; forIndex++)
				{
					mObjMap[index+1][forIndex] = getObjFromI(Integer.parseInt(Character.toString(meLin2[forIndex])), forIndex - mRTiles/2, index - mUTiles/2+1);
				}
				
				System.out.println(meLin);
			}
			catch (Exception ex)
			{
				thingIt = false;
			}
			index++;
		}
		return mObjMap;
	}
	
}
