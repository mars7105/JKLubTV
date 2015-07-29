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
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class LadebalkenView extends JDialog {

	// JProgressBar-Objekt wird erzeugt

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JProgressBar progressBar;
	int num = 0;

	public LadebalkenView() {
		this.setAlwaysOnTop(true);
		setTitle("Speichervorgang");
		progressBar = new JProgressBar(0, 100);

		// Call setStringPainted now so that the progress bar height
		// stays the same whether or not the string is shown.
		progressBar.setOpaque(true);
		progressBar.setVisible(true);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());

		panel.add(progressBar);
		// panel.setOpaque(true);
		panel.setVisible(true);
		add(panel);

		// add(progressBar, BorderLayout.PAGE_START);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		// setContentPane(panel);
		setVisible(true);
		setLocationRelativeTo(null);

	}

	public JProgressBar getMeinLadebalken() {
		return progressBar;
	}

	public void iterate() {
		num += 20;
		progressBar.setValue(num);
		progressBar.paint(progressBar.getGraphics());
		if (num >= 100) {
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
