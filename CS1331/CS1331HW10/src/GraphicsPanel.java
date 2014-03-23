import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JPanel;


public class GraphicsPanel extends JPanel
{

	private static final long serialVersionUID = 1L;
	
	public static final int M_OPTIMAL_ZOOM = 40;
	private BufferedImage mObjectLayer;
	private BufferedImage mGroundingPlane;
	private BufferedImage mFloatingLayer;
	private double[] mUnitR = {1, 0.5};
	private double[] mUnitU = {-1, 0.5};
	private int mZoomLevel = 40;
	private int mTranslateX;
	private int mTranslateY;
	private int rTiles = 16;
	private int uTiles = 16;
	private Point2D mMouseMove;
	private int mMouseButtonMask = 0;
	private Point mMousePress;
	private MapRasterObject[][] mObjMap;
	private Color[][] mTileMap;
	private MapTool mCurrentTool;
	private MapTile mCurrentTile;
	private int mCurrentTower = 0;
	private HashMap<MapTile, Color> mTileDic;
	private ArrayList<Monster> mMonsters;
	private MonsterStepper mMonstStep;
	private MapLoader mMapLoad;
	
	public void startGame(int numMonsts)
	{
		for (int forIndex = 0; forIndex < numMonsts; forIndex++)
		{
			if (forIndex % 2 == 0)
				mMonsters.add(new RedTank(0, uTiles/2 - forIndex));
			else if (forIndex % 3 == 0)
				mMonsters.add(new YellowTank(0, uTiles/2 - forIndex));
			else
				mMonsters.add(new GreenTank(0, uTiles/2 - forIndex));
		}
		this.repaint();
		mMonstStep.start();
	}
	
	public GraphicsPanel()
	{
		Dimension dims = new Dimension(800,600);
		this.setPreferredSize(dims);
		this.setSize(dims);
		this.setBackground(new Color(99, 99, 99));
		mTranslateX = 0;//dims.width / 2;
		mTranslateY = 0;//dims.height / 2;
		mMonsters = new ArrayList<Monster>();
		mObjectLayer = new BufferedImage((int)dims.getWidth(), (int)dims.getHeight(), BufferedImage.TYPE_INT_ARGB);
		mGroundingPlane = new BufferedImage((int)dims.getWidth(), (int)dims.getHeight(), BufferedImage.TYPE_INT_ARGB);
		mFloatingLayer = new BufferedImage((int)dims.getWidth(), (int)dims.getHeight(), BufferedImage.TYPE_INT_ARGB);
		addMouseListener(new ClickListener());
		addMouseMotionListener(new ClickListener());
		mTileMap = new Color[uTiles][rTiles];
		mObjMap = new MapRasterObject[uTiles][rTiles];
		mTileDic = new HashMap<MapTile, Color>();
		
		mTileDic.put(MapTile.SWAMP, Color.decode("#00981D"));
		mTileDic.put(MapTile.ROAD, Color.decode("#724000"));
		mTileDic.put(MapTile.CHASM, Color.decode("#000000"));
		mTileDic.put(MapTile.SAND, Color.decode("#ECE886"));
		
		for (int forIndex = 0; forIndex < mTileMap.length; forIndex++)
		{
			for (int forIndex2 = 0; forIndex2 < mTileMap[forIndex].length; forIndex2++)
			{
				//mTileMap[forIndex][forIndex2] = new Color(forIndex*19,forIndex2*19, forIndex*3+forIndex2*3);
				mTileMap[forIndex][forIndex2] = mTileDic.get(MapTile.SAND); 
			}
		}
		
		mMapLoad = new MapLoader(rTiles, uTiles);
		mObjMap = mMapLoad.loadObjectMap();
		mTileMap = mMapLoad.loadTileMap();
		
		mMonstStep = new MonsterStepper(mMonsters, this);
		drawGround();
		
	}
	
	private void addObject(Point2D aTranslatedPoint)
	{
		if (Math.abs(aTranslatedPoint.getX()) < rTiles/2 && Math.abs(aTranslatedPoint.getY()) < uTiles/2)
		{
			int adjX, adjY;
			
			adjX = (int)Math.floor(aTranslatedPoint.getX());	
			adjY = (int)Math.ceil(aTranslatedPoint.getY());
			
			MapRasterObject added;
			if (mCurrentTower == 0)
				added = new BlastTower(adjX, adjY);
			else if (mCurrentTower == 1)
				added = new SpiddleTower(adjX, adjY);
			else
				added = new GasserTower(adjX, adjY);
			mObjMap[uTiles/2 + adjY][rTiles/2 + adjX] = added;
			
		}
	}
	
	private boolean isInGrid(Point2D aPnt)
	{
		return Math.abs(aPnt.getX()) < rTiles/2 && Math.abs(aPnt.getY()) < uTiles/2;
	}
	
	private class ClickListener extends MouseAdapter
	{
		@Override
		public void mousePressed(MouseEvent aEvt)
		{
			mMouseButtonMask = aEvt.getButton();	
			mMousePress = aEvt.getPoint();
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			Point2D thing = transformXY_RU(arg0.getX() - mTranslateX, arg0.getY() - mTranslateY);
			//JOptionPane.showMessageDialog(null, "R: " + thing.getX() + ", U: " + thing.getY());
			if (mCurrentTool == MapTool.PLACE_TOWER)
				addObject(thing);
			else if (mCurrentTool == MapTool.ERASE)
			{
				int adjX = (int)Math.floor(thing.getX()) + rTiles/2;	
				int adjY = (int)Math.ceil(thing.getY()) + uTiles/2;
				if (isInGrid(new Point2D.Double(thing.getX(), thing.getY())))
				{
					mObjMap[adjY][adjX] = null;
					//mBuffer = new BufferedImage((int)getWidth(), (int)getHeight(), BufferedImage.TYPE_INT_ARGB);	
				}
			}
			else if (mCurrentTool == MapTool.PLACE_TILE)
			{
				Color tCol = null;

				tCol = mTileDic.get(mCurrentTile);
		
				int adjX = (int)Math.floor(thing.getX()) + rTiles/2;	
				int adjY = (int)Math.floor(thing.getY()) + uTiles/2;
				if (isInGrid(new Point2D.Double(thing.getX(), thing.getY())))
				{
					mTileMap[adjY][adjX] = tCol;
					bufferToClear(((Graphics2D)mObjectLayer.getGraphics()));
					//mBuffer = new BufferedImage((int)getWidth(), (int)getHeight(), BufferedImage.TYPE_INT_ARGB);	
					drawGround();
				}
			}
			
		}
		
		@Override
		public void mouseMoved(MouseEvent aEvt)
		{
			Point2D thingy = transformXY_RU(aEvt.getX() - mTranslateX, aEvt.getY() - mTranslateY);
			
			mMouseMove = thingy;
			
			//System.out.println("r: " + thingy.getX() + ", u: " + thingy.getY());
			//else mMouseMove = null;
			repaint();
		}
		
		@Override
		public void mouseDragged(MouseEvent aEvt)
		{
			if (mMouseButtonMask == MouseEvent.BUTTON3)
			{
				mTranslateX += aEvt.getPoint().x - mMousePress.x;
				mTranslateY += aEvt.getPoint().y - mMousePress.y;
				
				//bufferToClear((Graphics2D)mGroundingPlane.getGraphics());
				//drawGround();
				drawObjects();
				GraphicsPanel.this.repaint();
				mMousePress = aEvt.getPoint();
			}
			else
			{
				mouseMoved(aEvt);
				mouseClicked(aEvt);	
			}
			
		}
		
	}
	
	public void bufferToClear(Graphics2D aGfx)
	{
		Composite translucent = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f);
		aGfx.setComposite(translucent);
		aGfx.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	public void drawGround()
	{
		Graphics aGfx = mGroundingPlane.getGraphics();
		
		// Default tile color in case of a null tile
		aGfx.setColor(Color.decode("#44AA44"));
		fillTransformedRect(aGfx, -rTiles/2, -uTiles/2, rTiles, uTiles);
		
		for (int forIndex = 0; forIndex < mTileMap.length; forIndex++)
		{
			for (int forIndex2 = 0; forIndex2 < mTileMap[forIndex].length; forIndex2++)
			{
				if (mTileMap[forIndex][forIndex2] == null)
					continue;
				int u = forIndex - uTiles/2;
				int r = forIndex2 - rTiles/2;
				aGfx.setColor(mTileMap[forIndex][forIndex2]);
				fillTransformedRect(aGfx, r, u, 1, 1);
			}
		}
		
		// Here is drawn the Grid
		aGfx.setColor(Color.BLACK);
		for (int forIndex = -rTiles/2; forIndex <= rTiles/2; forIndex++)
		{
			Point u0 = transformRU_XY(forIndex, uTiles/2);
			Point u1 = transformRU_XY(forIndex, -uTiles/2);
			aGfx.drawLine(u0.x, u0.y, u1.x, u1.y);
		}
		
		for (int forIndex = -uTiles/2; forIndex <= uTiles/2; forIndex++)
		{
			Point r0 = transformRU_XY(rTiles/2, forIndex);
			Point r1 = transformRU_XY(-rTiles/2, forIndex);
			aGfx.drawLine(r0.x, r0.y, r1.x, r1.y);
		}
		
		// Here are drawn the Axes
		aGfx.setColor(Color.RED);
		drawLine(aGfx, -4, 0, 4, 0);
		drawLine(aGfx, 0, -4, 0, 4);
		
		continuousHalfCircle(aGfx, 0, 0, uTiles/2, false);
		continuousHalfCircle(aGfx, 0, 0, uTiles/2, true);
		aGfx.setColor(Color.BLACK);
	}
	
	public void continuousHalfCircle(Graphics aGfx, int aX, int aY, int aRadius, Boolean aFlipped)
	{
		int mod = aFlipped ? -1 : 1;
		double del = 0.01;
		for (double xCord = -aRadius; xCord <= aRadius; xCord+=del)
		{
			double yCord = mod * Math.pow((aRadius * aRadius - xCord * xCord), 0.5);
			Point mePoint = transformRU_XY(xCord, yCord);
			//aGfx.drawRect((int)Math.round(mePoint.x + aX), (int)Math.round(mePoint.y + aY), 1, 1);
			double yCord2 = mod * Math.pow((aRadius * aRadius - (xCord + del) * (xCord + del)), 0.5);
			Point mePoint2 = transformRU_XY(xCord + del, yCord2);
			aGfx.drawLine((int)Math.round(mePoint.x + aX), (int)Math.round(mePoint.y + aY), (int)Math.round(mePoint2.x + aX), (int)Math.round(mePoint2.y + aY));
		}
	}
	
	public void paintComponent(Graphics aGfx)
	{
		super.paintComponent(aGfx);
		
		// Clear the object layer
		bufferToClear(((Graphics2D)mObjectLayer.getGraphics()));
		
		// Clear the Floating (Extras) Layer
		bufferToClear((Graphics2D)mFloatingLayer.getGraphics());
		
		// Border for JPanel -- nothing amazing
		aGfx.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
		
		Graphics fGfx = mFloatingLayer.getGraphics();
		
		// Here is drawn the selection square
		if (mMouseMove != null)
		{
			int u = (int)Math.floor(mMouseMove.getY());
			int r = (int)Math.floor(mMouseMove.getX());
			
			if (isInGrid(new Point2D.Double(mMouseMove.getX(), mMouseMove.getY())))
				fGfx.setColor(Color.decode("#444488"));
			else
				fGfx.setColor(Color.decode("#b50808"));
			//double onePxInRU = 
			fillTransformedRect(fGfx, r, u, 1, 1);
			fGfx.setColor(Color.BLACK);
		}
		
		aGfx.drawImage(mGroundingPlane, this.mTranslateX, this.mTranslateY, this.getWidth(), this.getHeight(), null);
		aGfx.drawImage(mFloatingLayer, this.mTranslateX, this.mTranslateY, this.getWidth(), this.getHeight(), null);
		// Draw the sprites (the Towers) to the image buffer
		drawMonsters();
		drawObjects();
		
		// blit the image buffer to the screen
		aGfx.drawImage(mObjectLayer, this.mTranslateX, this.mTranslateY, this.getWidth(), this.getHeight(), null);
	}
	
	public void fillTransformedRect(Graphics aGfx, double r, double u, double aWidth, double aHeight)
	{
		int[] xVals = new int[4];
		int[] yVals = new int[4];
		xVals[0] = transformRU_XY(r, u).x;
		yVals[0] = transformRU_XY(r, u).y;
		xVals[1] = transformRU_XY(r + aWidth, u).x;
		yVals[1] = transformRU_XY(r + aWidth, u).y;
		xVals[2] = transformRU_XY(r + aWidth, u + aHeight).x;
		yVals[2] = transformRU_XY(r + aWidth, u + aHeight).y;
		xVals[3] = transformRU_XY(r, u + aHeight).x;
		yVals[3] = transformRU_XY(r, u + aHeight).y;
		
		aGfx.fillPolygon(xVals, yVals, 4);
	}
	
	public void drawLine(Graphics aGfx, int aR1, int aU1, int aR2, int aU2)
	{
		int x1 = this.transformRU_XY(aR1, aU1).x;
		int y1 = this.transformRU_XY(aR1, aU1).y;
		int x2 = this.transformRU_XY(aR2, aU2).x;
		int y2 = this.transformRU_XY(aR2, aU2).y;
		//aGfx.drawRect(center.x, center.y, 1, 1);
		aGfx.drawLine(x1, y1, x2, y2);
	}
	
	public Point transformRU_XY(double aR, double aU)
	{
		double uR0 = mUnitR[0] * mZoomLevel;
		double uU0 = mUnitU[0] * mZoomLevel;
		double uR1 = mUnitR[1] * mZoomLevel;
		double uU1 = mUnitU[1] * mZoomLevel;
		int realX = mGroundingPlane.getWidth()/2 + (int)Math.round((uR0 * aR + uU0 * aU));
		int realY = mGroundingPlane.getHeight()/2 + (int)Math.round((uR1 * aR + uU1 * aU));
		return new Point(realX, realY);
	}
	
	public Point2D transformXY_RU(int aX, int aY)
	{
		aX -= mGroundingPlane.getWidth()/2;
		aY -= mGroundingPlane.getHeight()/2;
		double uR0 = mUnitR[0] * mZoomLevel;
		double uU0 = mUnitU[0] * mZoomLevel;
		double uR1 = mUnitR[1] * mZoomLevel;
		double uU1 = mUnitU[1] * mZoomLevel;
		double finalR = ((double)aY/uU1 - (double)aX/uU0)
					   /((double)uR1/uU1 - (double)uR0/uU0);
		double finalU = ((double)aX/uR0 - (double)aY/uR1) 
					   /((double)uU0/uR0 - (double)uU1/uR1);
		return new Point2D.Double(finalR, finalU);
	}

	public void drawObjects()
	{
		for (int forIndex = 0; forIndex < mObjMap.length; forIndex++)
		{
			for (int forIndex2 = 0; forIndex2 < mObjMap[forIndex].length; forIndex2++)
			{
				if (mObjMap[forIndex][forIndex2] == null)
					continue;
				drawMapObject(mObjMap[forIndex][forIndex2]);
			}
		}
	}
	
	public void drawMapObject(MapRasterObject aObj)
	{
		Point linearPoint = this.transformRU_XY(aObj.getR() + aObj.getDrawOffsetR(), aObj.getU() + aObj.getDrawOffsetU());
		Image img = aObj.getSprite();
		int w = (int)(img.getWidth(null) * ((double)mZoomLevel/M_OPTIMAL_ZOOM));
		int h = (int)(img.getHeight(null) * ((double)mZoomLevel/M_OPTIMAL_ZOOM));
		((Graphics2D)mObjectLayer.getGraphics()).drawImage(img, linearPoint.x - w/2, linearPoint.y - h, w, h, null);
		repaint();
	}
	
	public void drawMonsters()
	{
		for (Monster lMonst : mMonsters)
		{
			drawMonster(lMonst);
		}
	}
	
	public void drawMonster(Monster aMonst)
	{
		Point linearPoint = this.transformRU_XY(aMonst.getR() + aMonst.getDrawOffsetR(), aMonst.getU() + aMonst.getDrawOffsetU());
		Image img = aMonst.getSprite();
		
		int w = (int)(img.getWidth(null) * ((double)mZoomLevel/M_OPTIMAL_ZOOM));
		int h = (int)(img.getHeight(null) * ((double)mZoomLevel/M_OPTIMAL_ZOOM));
		((Graphics2D)mObjectLayer.getGraphics()).drawImage(img, linearPoint.x - w/2, linearPoint.y - h, w, h, null);
		
		repaint();
	}

	/**
	 * @return the currentTool
	 */
	public MapTool getCurrentTool()
	{
		return mCurrentTool;
	}

	/**
	 * @param currentTool the currentTool to set
	 */
	public void setCurrentTool(MapTool currentTool)
	{
		mCurrentTool = currentTool;
	}

	/**
	 * @return the currentTower
	 */
	public int getCurrentTower()
	{
		return mCurrentTower;
	}

	/**
	 * @param currentTower the currentTower to set
	 */
	public void setCurrentTower(int currentTower)
	{
		mCurrentTower = currentTower;
	}

	/**
	 * @return the currentTile
	 */
	public MapTile getCurrentTile()
	{
		return mCurrentTile;
	}

	/**
	 * @param currentTile the currentTile to set
	 */
	public void setCurrentTile(MapTile currentTile)
	{
		mCurrentTile = currentTile;
	}

	/**
	 * @return the objMap
	 */
	public MapRasterObject[][] getObjMap()
	{
		return mObjMap;
	}

	/**
	 * @param objMap the objMap to set
	 */
	public void setObjMap(MapRasterObject[][] objMap)
	{
		mObjMap = objMap;
	}

	/**
	 * @return the rTiles
	 */
	public int getrTiles()
	{
		return rTiles;
	}

	/**
	 * @param rTiles the rTiles to set
	 */
	public void setrTiles(int rTiles)
	{
		this.rTiles = rTiles;
	}

	/**
	 * @return the uTiles
	 */
	public int getuTiles()
	{
		return uTiles;
	}

	/**
	 * @param uTiles the uTiles to set
	 */
	public void setuTiles(int uTiles)
	{
		this.uTiles = uTiles;
	}

	/**
	 * @return the tileMap
	 */
	public Color[][] getTileMap()
	{
		return mTileMap;
	}

	/**
	 * @param tileMap the tileMap to set
	 */
	public void setTileMap(Color[][] tileMap)
	{
		mTileMap = tileMap;
	}

	public int getZoomLevel()
	{
		return mZoomLevel;
	}
	
	public void setYComponent(double aYComp)
	{
		this.mUnitR[1] = aYComp;
		this.mUnitU[1] = aYComp;
		bufferToClear((Graphics2D)mGroundingPlane.getGraphics());
		this.drawGround();
		this.repaint();
	}
	
	public void setZoomLevel(int mZoomLevel)
	{
		int deltaZoom = mZoomLevel - this.mZoomLevel;
		//double deltaX = (deltaZoom * rTiles * mUnitR[0]);
		//double deltaY = (deltaZoom * uTiles * mUnitU[1]);
		int diag = Math.min(uTiles/2, rTiles/2);
		double deltaY = deltaZoom * mUnitU[1] * diag * 
						((this.mTranslateY)/(mUnitU[1]*mZoomLevel*diag));
		double deltaX = deltaZoom * mUnitR[0] * diag * 
						((this.mTranslateX)/(mUnitR[0]*mZoomLevel*diag));
		this.mTranslateX += Math.round(deltaX);
		this.mTranslateY += Math.round(deltaY);
		//this.mTranslateX += (Math.abs((this.getWidth()/2 - (double)mTranslateX))/(this.mZoomLevel * rTiles * mUnitR[0]))*deltaX;
		//this.mTranslateY += (Math.abs((this.getHeight()/2 - (double)mTranslateY))/(this.mZoomLevel * uTiles * mUnitU[1]))*deltaY;
		this.mZoomLevel = mZoomLevel;

		bufferToClear((Graphics2D)mGroundingPlane.getGraphics());
		this.drawGround();
		this.repaint();
	}
}
