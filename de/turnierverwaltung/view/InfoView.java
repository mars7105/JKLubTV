package de.turnierverwaltung.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import de.turnierverwaltung.model.TurnierKonstanten;

public class InfoView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String bildLizenzen;
	private String jdatePickerLizenz;
	private String mysqlDriverLizenz;
	private String sqliteLizenz;
	private JButton lizenzenButton; 
	private JPanel contentPanel;
	private JScrollPane scrollPane;
	private JPanel centerPane;
	JTextArea textArea;
	public InfoView() {
		int windowWidth = TurnierKonstanten.WINDOW_WIDTH - 100;
		int windowHeight = TurnierKonstanten.WINDOW_HEIGHT - 175;

		contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBackground(new Color(249, 222, 112));
		setLayout(new BorderLayout());
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(contentPanel);
		scrollPane.setPreferredSize(new Dimension(windowWidth, windowHeight));
		JLabel titleLabel = new JLabel("Informationen zum Programm");
		JPanel titlepanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.PAGE_AXIS));
		titlepanel.add(titleLabel);
		lizenzenButton = new JButton("Lizenzen");
		topPanel.add(lizenzenButton);
		northPanel.add(titlepanel);
		northPanel.add(topPanel);
		add(northPanel, BorderLayout.NORTH);
		textArea = new JTextArea();
		add(scrollPane, BorderLayout.CENTER);
		centerPane = new JPanel();
		centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
		centerPane.add(textArea);
		contentPanel.add(centerPane);

		
	}
	public JTextArea getTextArea() {
		return textArea;
	}
	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}
	public JButton getLizenzenButton() {
		return lizenzenButton;
	}
	public void setLizenzenButton(JButton lizenzenButton) {
		this.lizenzenButton = lizenzenButton;
	}
	
}
