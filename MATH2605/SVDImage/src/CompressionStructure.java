import Jama.Matrix;
import Jama.SingularValueDecomposition;


public class CompressionStructure
{
	private Matrix mApproxMatrix;
	private double mSingVal1;
	private double mSingValK1;
	
	public CompressionStructure(Matrix cApproxMatrix, double cSingVal1, double cSingValK1)
	{
		mApproxMatrix = cApproxMatrix;
		mSingVal1 = cSingVal1;
		mSingValK1 = cSingValK1;
	}

	public static CompressionStructure[] generateStructures(ImageMatrix aMatrix, int aRank)
	{
		CompressionStructure[] retStructs = new CompressionStructure[3];
		SingularValueDecomposition[] svds = new SingularValueDecomposition[] 
		{ 
			new SingularValueDecomposition(aMatrix.getLayers()[0]), 
			new SingularValueDecomposition(aMatrix.getLayers()[1]),
			new SingularValueDecomposition(aMatrix.getLayers()[2]) 
		};
		
		int index = 0;
		for (SingularValueDecomposition lSvd : svds)
		{
			if (aRank > lSvd.getSingularValues().length)
				return null;
			
			Matrix approx = new Matrix(aMatrix.getRowDim(), aMatrix.getColumnDim());
			Matrix temp = new Matrix(aMatrix.getRowDim(), aMatrix.getColumnDim());
			
			for (int forIndex = 0; forIndex <= aRank; forIndex++)
			{
				// this is for SVD composition where
				// A = U*D*V'
				Matrix vk = lSvd.getV().getMatrix(0, lSvd.getV().getRowDimension() - 1, forIndex, forIndex);
				Matrix uk = lSvd.getU().getMatrix(0, lSvd.getU().getRowDimension() - 1, forIndex, forIndex);
				double sigmak = lSvd.getSingularValues()[forIndex];
				temp = (uk.times(vk.transpose()).times(sigmak));
				approx = approx.plus(temp);
			}
			retStructs[index] = new CompressionStructure(approx, lSvd.getSingularValues()[0], lSvd.getSingularValues()[aRank]);
			index++;
		}
		return retStructs;
	}
	
	public static Matrix[] getChannels(CompressionStructure[] aStructs)
	{
		Matrix[] retChannels = new Matrix[aStructs.length];
		for (int forIndex = 0; forIndex < aStructs.length; forIndex++)
		{
			retChannels[forIndex] = aStructs[forIndex].getApproxMatrix();
		}
		return retChannels;
	}
	
	public Matrix getApproxMatrix()
	{
		return mApproxMatrix;
	}

	public void setApproxMatrix(Matrix approxMatrix)
	{
		mApproxMatrix = approxMatrix;
	}

	public double getSingVal1()
	{
		return mSingVal1;
	}

	public void setSingVal1(double singVal1)
	{
		mSingVal1 = singVal1;
	}

	public double getSingValK1()
	{
		return mSingValK1;
	}

	public void setSingValK1(double singValK1)
	{
		mSingValK1 = singValK1;
	}
}
