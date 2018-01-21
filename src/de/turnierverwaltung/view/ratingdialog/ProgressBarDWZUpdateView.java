package de.turnierverwaltung.view.ratingdialog;
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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import de.turnierverwaltung.view.Messages;

public class ProgressBarDWZUpdateView extends JDialog {

	// JProgressBar-Objekt wird erzeugt

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JProgressBar progressBar;
	private JPanel panel;
	private JLabel textLabel;
	double num = 0;
	double addNumber;
	private int gruppenAnzahl;
	private String message;

	public ProgressBarDWZUpdateView(int gruppenAnzahl) {
		setTitle(Messages.getString("ProgressBarDWZUpdateView.0")); //$NON-NLS-1$

		this.gruppenAnzahl = gruppenAnzahl;
		this.message = Messages.getString("ProgressBarDWZUpdateView.1");
		createProgressBar();
	}

	public ProgressBarDWZUpdateView(String message, String title, int gruppenAnzahl) {
		setTitle(title); // $NON-NLS-1$
		this.gruppenAnzahl = gruppenAnzahl;
		this.message = message;
		createProgressBar();
	}

	private void createProgressBar() {

		this.setAlwaysOnTop(true);
		addNumber = 100 / (double) gruppenAnzahl;
		textLabel = new JLabel(message);
		progressBar = new JProgressBar(0, 100);
		// Call setStringPainted now so that the progress bar height
		// stays the same whether or not the string is shown.
		progressBar.setOpaque(true);
		progressBar.setVisible(true);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(textLabel);
		panel.add(progressBar);
		// panel.setOpaque(true);
		panel.setVisible(true);
		add(panel);

		// add(progressBar, BorderLayout.PAGE_START);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		pack();
		// setContentPane(panel);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	public JProgressBar getMeinLadebalken() {
		return progressBar;
	}

	public void iterate() {
		num += addNumber;

		int value = (int) Math.round(num);
		progressBar.setValue(value);
		progressBar.paint(progressBar.getGraphics());
		textLabel.paint(textLabel.getGraphics());
		if (value >= 100) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException ex) {
			}
			this.dispose();
		}

	}

	public void iterate(int loop) {
		num += addNumber * loop;

		int value = (int) Math.round(num);
		progressBar.setValue(value);
		progressBar.paint(progressBar.getGraphics());
		textLabel.paint(textLabel.getGraphics());
		if (value >= 100) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException ex) {
			}
			this.dispose();
		}

	}

	public void setMeinLadebalken(JProgressBar meinLadebalken) {
		this.progressBar = meinLadebalken;
	}

	public void setValue(int value) {
		progressBar.setValue(value);
	}
}
