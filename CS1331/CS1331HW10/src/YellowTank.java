
public class YellowTank extends Monster
{
	private static String[] M_PATH = {"yellowTank-R.png", "yellowTank-L.png", "yellowTank-D.png", "yellowTank.png"}; 
	public YellowTank(int cR, int cU)
	{
		super(M_PATH, cR, cU, 0.6, 0.6, 1, 50);
		this.setR(this.getR() + 0.5);
		this.setU(this.getU() - 0.5);
	}
}
