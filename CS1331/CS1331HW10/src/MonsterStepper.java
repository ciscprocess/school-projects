import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;


public class MonsterStepper implements ActionListener
{
	private Timer mStepper;
	private ArrayList<Monster> mMonsters;
	private Random mGen;
	private GraphicsPanel mParent;
	//private boolean mStopped = true;
	public MonsterStepper(ArrayList<Monster> cMonsters, GraphicsPanel cParent)
	{
		mGen = new Random();
		mMonsters = cMonsters;
		mStepper = new Timer(1, this);
		mParent = cParent;
	}
	
	public void start()
	{
		mStepper.start();
	}
	
	public boolean checkTraversibility(int aR, int aU)
	{
		Color[][] ttMap = mParent.getTileMap();
		MapRasterObject[][] trMap = mParent.getObjMap();
		Color test;
		try
		{
			test = ttMap[mParent.getuTiles()/2 + aU - 1][mParent.getrTiles()/2 + aR];
		}
		catch (ArrayIndexOutOfBoundsException ex)
		{
			return false;
		}
		if (test == null)
			return true;
		if (test.equals(Color.decode("#00981D")) || test.equals(Color.decode("#000000")))
			return false;
		if (trMap[mParent.getuTiles()/2 + aU][mParent.getrTiles()/2 + aR] != null)
			return false;
		
		return true;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		for (int forIndex = 0; forIndex < mMonsters.size(); forIndex++)
		{
			Monster lMonst = mMonsters.get(forIndex);
			//int adjR, adjU;
			
			//adjR = (int)Math.floor(lMonst.getR());	
			//adjU = (int)Math.ceil(lMonst.getU());
			int dir = 1; 
			
			if (lMonst.isStopped())
			{
				int tDir = mGen.nextInt(4);
				lMonst.setDirection(tDir);
				lMonst.setStopped(false);
			}
			
			dir = lMonst.getDirection();

			if (dir == 0)
			{
				double nR = lMonst.getR() + 0.04;
				int adjX = (int)Math.floor(lMonst.getR() + lMonst.getLength()/2);
				int adjY = (int)Math.ceil(lMonst.getU());
				if (!checkTraversibility(adjX, adjY))
				{
					lMonst.setStopped(true);
				}
				else
				{
					if (lMonst.getMoveTurn()) lMonst.setR(nR);
				}
				
			}
			else if (dir == 1)
			{
				double nR = lMonst.getR() - 0.04;
				int adjX = (int)Math.floor(lMonst.getR() - lMonst.getLength()/2);	
				int adjY = (int)Math.ceil(lMonst.getU());
				if (!checkTraversibility(adjX, adjY))
				{
					lMonst.setStopped(true);
				}
				else
				{
					if (lMonst.getMoveTurn()) lMonst.setR(nR);
				}
			}
			else if (dir == 2)
			{
				double nU = lMonst.getU() + 0.04;
				int adjX = (int)Math.floor(lMonst.getR());	
				int adjY = (int)Math.ceil(lMonst.getU() + lMonst.getLength()/2);
				if (!checkTraversibility(adjX, adjY))
				{
					lMonst.setStopped(true);
				}
				else
				{
					if (lMonst.getMoveTurn()) lMonst.setU(nU);
				}
			}
			else if (dir == 3)
			{
				double nU = lMonst.getU() - 0.04;
				int adjX = (int)Math.floor(lMonst.getR());	
				int adjY = (int)Math.ceil(lMonst.getU() - lMonst.getLength()/2);
				if (!checkTraversibility(adjX, adjY))
				{
					lMonst.setStopped(true);
				}
				else
				{
					if (lMonst.getMoveTurn()) lMonst.setU(nU);
				}
			}
			if (lMonst.getU() < -1 * mParent.getuTiles()/2 + 1)
			{
				mMonsters.remove(forIndex);
			}
		}
	}

}
