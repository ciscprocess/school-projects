import java.io.IOException;

/**
 * Entry point for the program
 * @author Nathan
 *
 */
public class TestDriver
{

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args)
	{
		long time = System.currentTimeMillis();
		ImageMatrix mat;
		try
		{
			for (int i = 0; i < 40; i++)
			{
				mat = new ImageMatrix("C:\\Users\\Nathan\\Desktop\\more.png");
				CompressionStructure[] sturs = CompressionStructure.generateStructures(mat, i);
				mat = new ImageMatrix(CompressionStructure.getChannels(sturs));
				mat.writeImage("C:\\Users\\Nathan\\Desktop\\jama\\img" + i + ".bmp");
				System.out.println(i);
			}
		} 
		catch (IOException e)
		{
			System.out.println("GASP");
		}
		
		time = System.currentTimeMillis() - time;
		System.out.println("Time taken: " + (double)time/(1000*60) + "min");
	}

}
