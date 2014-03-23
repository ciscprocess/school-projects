import javax.swing.JPanel;


public class MainPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	double rads = 0;
	private GraphicsPanel mGfxPanel;
	private ControlPanel mCtrlPanel;
	public MainPanel()
	{
		//Dimension dims = new Dimension(660,600);
		//this.setPreferredSize(dims);
		mGfxPanel = new GraphicsPanel();
		mCtrlPanel = new ControlPanel(mGfxPanel);
		add(mCtrlPanel);
		add(mGfxPanel);
	}
}
