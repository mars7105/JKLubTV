package de.turnierverwaltung.view;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class FirstStartWizard extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private FirstStartNextPanelView firstStartNextPanel;

	public FirstStartWizard() {
		this.setAlwaysOnTop(true);
		setTitle(Messages.getString("FirstStartWizard.0"));
		
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		firstStartNextPanel = new FirstStartNextPanelView();
		firstStartNextPanel.makeAllButtons();
		makeSecondPage();
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		getContentPane().add(firstStartNextPanel, BorderLayout.SOUTH);
		pack();
		setEnabled(true);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	public FirstStartNextPanelView getFirstStartNextPanel() {
		return firstStartNextPanel;
	}
	public void makeFirstPage() {
		firstStartNextPanel.getPrevButton().setEnabled(false);
		FirstStartWizardPage1 page1 = new FirstStartWizardPage1();
		contentPanel.add(page1, BorderLayout.CENTER);

		

	}

	public void makeSecondPage() {
		firstStartNextPanel.getPrevButton().setEnabled(true);
		FirstStartWizardPage2 page2 = new FirstStartWizardPage2();
		contentPanel.add(page2, BorderLayout.CENTER);

		

	}

	public void setFirstStartNextPanel(FirstStartNextPanelView firstStartNextPanel) {
		this.firstStartNextPanel = firstStartNextPanel;
	}
	
	
}
