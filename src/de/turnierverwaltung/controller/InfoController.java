package de.turnierverwaltung.controller;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.text.html.parser.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.itextpdf.text.Document;

import de.turnierverwaltung.view.InfoView;
import de.turnierverwaltung.view.InfoTexteView;

public class InfoController  {
	private MainControl mainControl;
	private InfoView infoView;
//	private JButton lizenzButton;
	private JTabbedPane lizenzenPane;
	private InfoTexteView infoTexteView;

	/**
	 * @param mainControl
	 */
	public InfoController(MainControl mainControl) {
		this.mainControl = mainControl;
		infoView = new InfoView();
//		lizenzButton = infoView.getLizenzenButton();
//		lizenzButton.addActionListener(this);
		lizenzenPane = new JTabbedPane();
		infoTexteView = new InfoTexteView();
		try {
			lizenzenPane.addTab("Lizenzen", infoTexteView.getLizenzText());
			infoView.setLizenzenPane(lizenzenPane);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		makeInfoPanel();

	}

	/**
	 * 
	 */
	public void makeInfoPanel() {
		JPanel hauptPanel = this.mainControl.getHauptPanel();
		hauptPanel.removeAll();
		this.mainControl.getNaviController().makeNaviPanel();
		

		hauptPanel.add(infoView, BorderLayout.CENTER);
		hauptPanel.updateUI();

	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
//	public void actionPerformed(ActionEvent arg0) {
//		if (arg0.getSource() == lizenzButton) {
//			//infoView.getTextArea().setText(infoTexteView.getLizenzText());
//			//custom title, custom icon
//			JFrame frame = new JFrame();
//			try {
//				frame.setContentPane(infoTexteView.getLizenzText());
//			} catch (URISyntaxException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			try {
//				JOptionPane.showMessageDialog(new JFrame(),
//						infoTexteView.getLizenzText(),
//				    "Lizenzen",
//				    JOptionPane.INFORMATION_MESSAGE,
//				    new ImageIcon(
//							Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/emblem-notice.png"))));
//			} catch (HeadlessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (URISyntaxException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//	}
}
