

/*
public class NSVD
{
	private Matrix mA;
	private Matrix mAtA;
	private Matrix mU;
	private Matrix mV;
	private double[] mSing;
	private Matrix mSigma;
	
	public NSVD(Matrix cA)
	{
		mA = cA;
		mAtA = cA.transpose().times(mA);
		getEigenvalues(mAtA);
	}
	
	private static Matrix getEigenvalues(Matrix aMat)
	{
		QRDecomposition decomp;
		Matrix mat2 = (Matrix)aMat.clone();
		for (int i = 0; i < 10; i++)
		{
			decomp = new QRDecomposition(mat2);
			mat2 = decomp.getR().times(decomp.getQ());
		}
		
		return mat2;
	}

	public Matrix getU()
	{
		return mU;
	}

	public void setU(Matrix u)
	{
		mU = u;
	}

	public Matrix getV()
	{
		return mV;
	}

	public void setV(Matrix v)
	{
		mV = v;
	}

	public double[] getSingularValues()
	{
		return mSing;
	}

	public void setSingularValues(double[] sing)
	{
		mSing = sing;
	}
}
*/