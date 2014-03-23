import java.util.Random;

/**
 * This class represents a player of the game of 
 * dice war, and accordingly has functions and properties
 * essential to that of a real dice war player
 * @author Nathan Korzekwa
 * @version 0.1
 * 
 */
public class Player 
{
	private Random mGenerator;
	private String mName;
	private int mScore;
	
	public Player(String cName)
	{
		mGenerator = new Random();
		setScore(0);
		setName(cName);
	}
	
	public void addToScore(int aAddenda)
	{
		mScore += aAddenda;
	}
	
	public int rollDie()
	{
		return 1 + mGenerator.nextInt(6);
	}
	
	
	// Getters and Setters
	
	public int getScore() 
	{
		return mScore;
	}

	public void setScore(int mScore) 
	{
		this.mScore = mScore;
	}

	public String getName() 
	{
		return mName;
	}

	public void setName(String mName) 
	{
		this.mName = mName;
	}
}
