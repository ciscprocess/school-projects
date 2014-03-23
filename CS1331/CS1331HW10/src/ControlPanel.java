import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ControlPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JSpinner mSpinTileSize;
	private JLabel mSpinLabel;
	private JSpinner mPerspSpinner;
	private JLabel mPerspLabel;
	private JButton mTowerTool;
	private JButton mEraseTool;
	private JButton mTileTool;
	private MapTool mCurrentTool;
	private JLabel mListLabel;
	private JList mTowerList;
	private JLabel mTileListLabel;
	private JList mTileList;
	private GraphicsPanel mGfxPanel;
	private JSpinner mSpin;
	public ControlPanel(GraphicsPanel cGfxPanel)
	{
		Dimension dims = new Dimension(150,600);
		this.setPreferredSize(dims);
		//this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel anglePanel = new JPanel();
		
		mGfxPanel = cGfxPanel;
		mSpinLabel = new JLabel("Angle: ");
		SpinnerModel model0 = new SpinnerNumberModel(2, -100, 100, 1);
		mSpinTileSize = new JSpinner(model0);
		mSpinTileSize.setValue(mGfxPanel.getZoomLevel());
		mSpinTileSize.setSize(new Dimension(50, 30));
		mSpinTileSize.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				mGfxPanel.setZoomLevel((Integer)mSpinTileSize.getValue());
			} 
			});
		Dimension spinDims = mSpinTileSize.getPreferredSize();
		spinDims.width = 70;
		mSpinTileSize.setPreferredSize(spinDims);
		anglePanel.add(mSpinLabel);
		anglePanel.add(mSpinTileSize);
		anglePanel.setBackground(Color.decode("#DDDDDD"));
		add(anglePanel);
		
		JPanel perspectivePanel = new JPanel();
		mPerspLabel = new JLabel("Persp: ");
		SpinnerModel model1 = new SpinnerNumberModel(0.5, -12, 100, 0.02);
		mPerspSpinner = new JSpinner(model1);
		//mPerspSpinner.setValue(mGfxPanel.getPiMod());
		mPerspSpinner.setSize(new Dimension(50, 30));
		mPerspSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				//mGfxPanel.setPiMod(((Double)mPerspSpinner.getValue()));
				mGfxPanel.setYComponent((Double)mPerspSpinner.getValue());
			} 
			});
		
		perspectivePanel.add(mPerspLabel);
		perspectivePanel.add(mPerspSpinner);
		perspectivePanel.setBackground(Color.decode("#DDDDDD"));
		add(perspectivePanel);
		
		Dimension spinDims2 = mPerspSpinner.getPreferredSize();
		spinDims2.width = 70;
		mPerspSpinner.setPreferredSize(spinDims2);
		
		
		JPanel toolPanel = new JPanel();
		BoxLayout toolLayout = new BoxLayout(toolPanel, BoxLayout.Y_AXIS); 
		toolPanel.setLayout(toolLayout);
		ActionListener polyM = new ToolListener();
		mTowerTool = new JButton("Place Tower");
		mTowerTool.addActionListener(polyM);
		mEraseTool = new JButton("Erase");
		mEraseTool.addActionListener(polyM);
		mTileTool = new JButton("Place Path");
		mTileTool.addActionListener(polyM);
		toolPanel.add(mTowerTool);
		toolPanel.add(mEraseTool);
		toolPanel.add(mTileTool);
		toolPanel.setAlignmentX(LEFT_ALIGNMENT);
		add(toolPanel);
		
		JPanel towerPanel = new JPanel();
		towerPanel.setPreferredSize(new Dimension(dims.width - 20, 100));
		mListLabel = new JLabel("Towers Choices: ");
		mTowerList = new JList(new String[] {"BlastTower  ", "SpiddleTower  ", "GasserTower  "});
		mTowerList.addListSelectionListener(new ListListener());
		towerPanel.add(mListLabel);
		towerPanel.add(mTowerList);
		add(towerPanel);
		
		JPanel tilePanel = new JPanel();
		tilePanel.setPreferredSize(new Dimension(dims.width - 20, 100));
		mTileListLabel = new JLabel("Tile Choices: ");
		mTileList = new JList(new String[] {"Swamp", "Road", "Chasm", "Sand"});
		mTileList.addListSelectionListener(new ListListener());
		tilePanel.add(mTileListLabel);
		tilePanel.add(mTileList);
		add(tilePanel);
		setCurrentTool(MapTool.NONE);
		mTileList.setSelectedIndex(0);
		
		JPanel spinnerPanel = new JPanel();
		spinnerPanel.setPreferredSize(new Dimension(dims.width - 20, 100));
		spinnerPanel.add(new JLabel("Enemies: "));
		SpinnerModel model = new SpinnerNumberModel(2, 1, 6, 1);
		mSpin = new JSpinner(model);
		spinnerPanel.add(mSpin);
		add(spinnerPanel);
		
		JPanel controlsPanel = new JPanel();
		controlsPanel.setPreferredSize(new Dimension(dims.width - 20, 100));
		JButton startButton = new JButton("Start");
		startButton.addActionListener(
			new ActionListener() 
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					mGfxPanel.startGame((Integer)mSpin.getValue());
				}
			}
				);
		controlsPanel.add(startButton);
		add(controlsPanel);
	}
	
	private class ListListener implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent arg0)
		{
			if (arg0.getSource() == mTowerList)
				mGfxPanel.setCurrentTower(mTowerList.getSelectedIndex());
			else if (arg0.getSource() == mTileList)
			{
				mGfxPanel.setCurrentTile(MapTile.values()[mTileList.getSelectedIndex()]);
			}
		}
	}
	
	private class ToolListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent aEvt)
		{
			if (aEvt.getSource() == mTowerTool)
			{
				mTowerTool.setEnabled(false);
				mEraseTool.setEnabled(true);
				mTileTool.setEnabled(true);
				setCurrentTool(MapTool.PLACE_TOWER);
			}
			
			else if (aEvt.getSource() == mEraseTool)
			{
				mTowerTool.setEnabled(true);
				mEraseTool.setEnabled(false);
				mTileTool.setEnabled(true);
				setCurrentTool(MapTool.ERASE);
			}
			
			else if (aEvt.getSource() == mTileTool)
			{
				mTowerTool.setEnabled(true);
				mEraseTool.setEnabled(true);
				mTileTool.setEnabled(false);
				setCurrentTool(MapTool.PLACE_TILE);
			}
		}
	}
	
	public void paintComponent(Graphics aGfx)
	{
		super.paintComponent(aGfx);
		aGfx.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
	}
	
	public void setCurrentTool(MapTool aTool)
	{
		mCurrentTool = aTool;
		mGfxPanel.setCurrentTool(mCurrentTool);
	}
}
