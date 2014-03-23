
public class GreenTank extends Monster
{
	private static String[] M_PATH = {"greenTank-R.png", "greenTank-L.png", "greenTank-D.png", "greenTank.png"}; 
	public GreenTank(int cR, int cU)
	{
		super(M_PATH, cR, cU, 0.6, 0.6, 4, 30);
		this.setR(this.getR() + 0.5);
		this.setU(this.getU() - 0.5);
	}
}
