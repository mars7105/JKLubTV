package de.turnierverwaltung.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

public class StartpageDBPanelView extends StartpagePanelView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon dbNewIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/db_add.png")));
	private JButton newDatabseButton;
	private JTextPane textArea;

	public StartpageDBPanelView() {
		super();
		setLayout(new BorderLayout());
		Color titleColor = new Color((SystemColor.text).getRGB());
		Color titleTextColor = new Color((SystemColor.textText).getRGB());

		setBorder(new EmptyBorder(25, 25, 25, 25));
		this.setLayout(new BorderLayout());
		newDatabseButton = new JButton(Messages.getString("NaviView.11"), dbNewIcon); //$NON-NLS-1$
		newDatabseButton.setPreferredSize(new Dimension(275, 40));
		newDatabseButton.setHorizontalAlignment(SwingConstants.LEFT);
		JPanel dbPanel = new JPanel();
		dbPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		dbPanel.add(newDatabseButton);

		JPanel panel = new JPanel();

		panel.setLayout(new BorderLayout());
		textArea = new JTextPane();
		textArea.setBackground(titleColor);
		textArea.setForeground(titleTextColor);
		JScrollPane scrollPane = new JScrollPane(textArea);
		textArea.setEditable(false);
		String[] initString = { "Hallo ", "Test" };
		String[] initStyles = { "large", "small" };
		StyledDocument doc = textArea.getStyledDocument();
		// Load the text pane with styled text.
		try {
			for (int i = 0; i < initString.length; i++) {
				doc.insertString(doc.getLength(), initString[i], doc.getStyle(initStyles[i]));
			}
		} catch (BadLocationException ble) {
			System.err.println("Couldn't insert initial text into text pane.");
		}
		panel.add(scrollPane, BorderLayout.NORTH);
		panel.add(dbPanel, BorderLayout.CENTER);
		add(panel);

	}

}
