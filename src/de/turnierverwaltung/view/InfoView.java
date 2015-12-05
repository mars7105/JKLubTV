package de.turnierverwaltung.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class InfoView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private JButton lizenzenButton; 
	private JTabbedPane lizenzenPane; 

	private JPanel contentPanel;
	private JScrollPane scrollPane;
	private JPanel centerPane;
	JTextArea textArea;
	public InfoView() {
		contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout());
//		contentPanel.setBackground(new Color(249, 222, 112));
		setLayout(new BorderLayout());
		
		JLabel titleLabel = new JLabel("Informationen zum Programm");
		JPanel titlepanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.PAGE_AXIS));
		titlepanel.add(titleLabel);
//		lizenzenButton = new JButton("Lizenzen");
//		topPanel.add(lizenzenButton);
		lizenzenPane = new JTabbedPane();
//		topPanel.add(lizenzenPane);
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(lizenzenPane);
		northPanel.add(titlepanel);
		northPanel.add(topPanel);
		add(northPanel, BorderLayout.NORTH);
		textArea = new JTextArea();
		add(scrollPane, BorderLayout.CENTER);
		centerPane = new JPanel();
		centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
//		centerPane.add(textArea);
		contentPanel.add(centerPane);

		
	}
	public JTextArea getTextArea() {
		return textArea;
	}
	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}
//	public JButton getLizenzenButton() {
//		return lizenzenButton;
//	}
//	public void setLizenzenButton(JButton lizenzenButton) {
//		this.lizenzenButton = lizenzenButton;
//	}
	public JTabbedPane getLizenzenPane() {
		return lizenzenPane;
	}
	public void setLizenzenPane(JTabbedPane lizenzenPane) {
		this.lizenzenPane = lizenzenPane;
		scrollPane.setViewportView(lizenzenPane);
		this.updateUI();
	}
	
}
