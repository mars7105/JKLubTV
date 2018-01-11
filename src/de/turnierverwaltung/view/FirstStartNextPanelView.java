package de.turnierverwaltung.view;

import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class FirstStartNextPanelView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton nextButton;
	private JButton prevButton;
	private ImageIcon nextIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/go-next-view.png"))); //$NON-NLS-1$
	private ImageIcon prevIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/go-previous-view.png"))); //$NON-NLS-1$
	private JButton helpButton;
	private ImageIcon helpIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/help.png")));

	/**
	 * Create the panel.
	 */
	public FirstStartNextPanelView() {
		setLayout(new FlowLayout(FlowLayout.RIGHT));

	}

	public JButton getHelpButton() {
		return helpButton;
	}

	public JButton getNextButton() {
		return nextButton;
	}

	public JButton getPrevButton() {
		return prevButton;
	}

	public void makeAllButtons() {
		
		makePrevButton();
		makeNextButton();
	}



	public void makeHelpButton() {
		helpButton = new JButton(helpIcon);

		add(helpButton);
	}

	public void makeNextButton() {
		nextButton = new JButton(Messages.getString("FirstStartNextPanelView.0"), nextIcon);
		nextButton.setActionCommand(Messages.getString("FirstStartNextPanelView.0")); //$NON-NLS-1$

		add(nextButton);
	}

	public void makePrevButton() {
		prevButton = new JButton(Messages.getString("FirstStartNextPanelView.1"), prevIcon);
		prevButton.setActionCommand(Messages.getString("FirstStartNextPanelView.1")); //$NON-NLS-1$

		add(prevButton);
	}

	public void setHelpButton(JButton helpButton) {
		this.helpButton = helpButton;
	}

	public void setNextButton(JButton nextButton) {
		this.nextButton = nextButton;
	}

	public void setPrevButton(JButton prevButton) {
		this.prevButton = prevButton;
	}

}
