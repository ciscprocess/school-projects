
public class Monster extends MapRasterObject
{
	private int mSpeed = 0;
	private int mCallCount = -1;
	private int mHealth = 0;
	private boolean mStopped = true;

	private double mWidth = 0;
	private double mLength = 0;
	
	public Monster(String[] cSpritePath, int cR, int cU, double cWidth, double cLength, int cSpeed, int cHealth)
	{
		super(cSpritePath, cR, cU);
		setSpeed(cSpeed);
		setHealth(cHealth);
		setWidth(cWidth);
		setLength(cLength);
	}

	public boolean getMoveTurn()
	{
		mCallCount++;
		if (mCallCount == mSpeed)
			mCallCount = 0;
		if (mCallCount == 0)
			return true;
		else return false;
	}
	
	/**
	 * @return the speed
	 */
	public int getSpeed()
	{
		return mSpeed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed)
	{
		mSpeed = speed;
	}

	/**
	 * @return the health
	 */
	public int getHealth()
	{
		return mHealth;
	}

	/**
	 * @param health the health to set
	 */
	public void setHealth(int health)
	{
		mHealth = health;
	}

	/**
	 * @return the stopped
	 */
	public boolean isStopped()
	{
		return mStopped;
	}

	/**
	 * @param stopped the stopped to set
	 */
	public void setStopped(boolean stopped)
	{
		mStopped = stopped;
	}


	/**
	 * @return the width
	 */
	public double getWidth()
	{
		return mWidth;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(double width)
	{
		mWidth = width;
	}

	/**
	 * @return the length
	 */
	public double getLength()
	{
		return mLength;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(double length)
	{
		mLength = length;
	}

}
