package de.turnierverwaltung.view;

//JKlubTV - Ein Programm zum verwalten von Schach Turnieren
//Copyright (C) 2015  Martin Schmuck m_schmuck@gmx.net
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import de.turnierverwaltung.model.TurnierKonstanten;

public class HTMLTabelleView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton saveButton;
	JButton closeButton;

	public HTMLTabelleView() {
	}

	public void closeWindow() {
		this.dispose();
	}

	public JButton getCloseButton() {
		return closeButton;
	}

	public JButton getSaveButton() {
		return saveButton;
	}

	public void makeHTMLFrame(String tabelleToHTM, String title) {
		setTitle(title);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		int windowWidth = TurnierKonstanten.WINDOW_WIDTH - 50;
		int windowHeight = TurnierKonstanten.WINDOW_HEIGHT - 50;
		setBounds(TurnierKonstanten.WINDOW_BOUNDS_X, TurnierKonstanten.WINDOW_BOUNDS_Y, windowWidth, windowHeight);
		setMinimumSize(new Dimension(windowWidth, windowHeight));
		setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		String help = "Mit der rechten Maustaste können Sie den Text markieren und kopieren.";
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		JTextArea helpText = new JTextArea();
		helpText.setText(help);
		helpText.setEditable(false);

		JPanel hauptPanel = new JPanel();
		hauptPanel.setBackground(new Color(249, 222, 112));
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(hauptPanel);
		scrollPane.setPreferredSize(new Dimension(windowWidth, windowHeight));
		JTextArea jtextArea = new JTextArea();
		jtextArea.setText(tabelleToHTM);
		jtextArea.addMouseListener(new ContextMenuMouseListener());
		hauptPanel.add(jtextArea);
		JPanel helpPanel = new JPanel();
		helpPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		helpPanel.add(helpText);
		mainPanel.add(scrollPane);
		saveButton = new JButton("Als HTML Datei speichern");
		closeButton = new JButton("Ansicht schliessen");
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(saveButton);
		buttonPanel.add(closeButton);
		mainPanel.add(helpPanel);
		mainPanel.add(buttonPanel);
		setContentPane(mainPanel);
		hauptPanel.updateUI();
		mainPanel.updateUI();
		setEnabled(true);
		setVisible(true);
	}

	public void setCloseButton(JButton closeButton) {
		this.closeButton = closeButton;
	}

	public void setSaveButton(JButton saveButton) {
		this.saveButton = saveButton;
	}

}
