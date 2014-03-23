import javax.swing.JFrame;


public class TowerDefenseGame
{
	public static void main(String[] args)
	{
		JFrame meFrame = new JFrame("Programming With Purpose");
		meFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainPanel panel = new MainPanel();
		meFrame.add(panel);
		meFrame.pack();
		meFrame.setVisible(true);
	}
}
