package de.turnierverwaltung.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

public class FirstStartWizardPage2 extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel htmlAll; 
	private ImageIcon germanFlag = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/de.png")));
	private ImageIcon englishFlag = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/flag-gb.png")));
	private JRadioButton germanLanguageCheckBox;
	private JRadioButton englishLanguageCheckBox;

	public FirstStartWizardPage2() {
		htmlAll = new JPanel();
		htmlAll.setLayout(new BoxLayout(htmlAll, BoxLayout.PAGE_AXIS));
		setLayout(new BorderLayout());
		ButtonGroup group = new ButtonGroup();
		// deutsch
		JPanel htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel labelHeader = new JLabel("Deutsch (erst nach Neustart sichtbar)"); //$NON-NLS-1$
		JLabel germanFlagLabel = new JLabel(germanFlag);
		TitleLabelView titleView = new TitleLabelView("Sprache / Language");
		titleView.setFlowLayoutLeft();

		htmlAll.add(titleView);
		
		germanLanguageCheckBox = new JRadioButton();
		germanLanguageCheckBox.setSelected(true);
		group.add(germanLanguageCheckBox);
		htmlPanel.add(germanLanguageCheckBox);
		htmlPanel.add(germanFlagLabel);
		htmlPanel.add(labelHeader);

		htmlAll.add(htmlPanel);

		// englisch
		labelHeader = new JLabel("English (visible after restart)"); //$NON-NLS-1$
		JLabel englishFlagLabel = new JLabel(englishFlag);

		englishLanguageCheckBox = new JRadioButton();
		group.add(englishLanguageCheckBox);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(englishLanguageCheckBox);
		htmlPanel.add(englishFlagLabel);

		htmlPanel.add(labelHeader);

		htmlAll.add(htmlPanel);

		htmlAll.add(new JSeparator());
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(htmlAll);
		add(scrollPane, BorderLayout.CENTER);
		
	}

}
