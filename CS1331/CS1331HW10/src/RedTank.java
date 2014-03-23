
public class RedTank extends Monster
{
	private static String[] M_PATH = {"redTank-R.png", "redTank-L.png", "redTank-D.png", "redTank.png"}; 
	public RedTank(int cR, int cU)
	{
		super(M_PATH, cR, cU, 0.6, 0.6, 1, 40);
		this.setR(this.getR() + 0.5);
		this.setU(this.getU() - 0.5);
	}

}
