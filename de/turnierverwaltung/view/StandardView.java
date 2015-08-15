package de.turnierverwaltung.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.turnierverwaltung.model.TurnierKonstanten;

public class StandardView extends JPanel{

	private JPanel mainPane;
	private JPanel contentPanel;
	private JScrollPane scrollPane;
	private JPanel centerPane;
//	private ImageIcon dbNew = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-new.png")));
//	private ImageIcon dbLoad = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-open-4.png")));
//	private JButton newDatabse;
//	private JButton loadDatabase;

	public StandardView() {
		int windowWidth = TurnierKonstanten.WINDOW_WIDTH - 100;
		int windowHeight = TurnierKonstanten.WINDOW_HEIGHT - 100;
		setPreferredSize(new Dimension(windowWidth, windowHeight));
//		newDatabse = new JButton("Neue Datenbank", dbNew);
//		loadDatabase = new JButton("Datenbank laden", dbLoad);
		JLabel titleLabel = new JLabel("JKlubTV");
		JPanel titlepanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel newStandardPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		titlepanel.add(titleLabel);
		
//		newStandardPanel.add(newDatabse);
//		newStandardPanel.add(loadDatabase);
		mainPane = new JPanel();
		mainPane.setLayout(new BorderLayout());
		mainPane.add(titlepanel, BorderLayout.NORTH);
		mainPane.add(newStandardPanel, BorderLayout.CENTER);

		add(mainPane);

		contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBackground(new Color(249, 222, 112));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(contentPanel);
		scrollPane.setPreferredSize(new Dimension(windowWidth, windowHeight));
		add(scrollPane, BorderLayout.CENTER);
		centerPane = new JPanel();
		centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
		contentPanel.add(centerPane);
	}


}
