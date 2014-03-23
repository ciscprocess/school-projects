
public class Tower extends MapRasterObject
{
	private int mCost;
	private int mDamage;
	private int mRate;

	public Tower(String[] cSpritePath, int cR, int cU, int cCost, int cDamage, int cRate)
	{
		super(cSpritePath, cR, cU);
		setCost(cCost);
		setDamage(cDamage);
		setRate(cRate);
		setDrawOffsetR(0.6);
		setDrawOffsetU(-0.4);
	}

	public int getCost()
	{
		return mCost;
	}

	public void setCost(int mCost)
	{
		this.mCost = mCost;
	}

	public int getDamage()
	{
		return mDamage;
	}

	public void setDamage(int mDamage)
	{
		this.mDamage = mDamage;
	}

	public int getRate()
	{
		return mRate;
	}

	public void setRate(int mRate)
	{
		this.mRate = mRate;
	}

}
