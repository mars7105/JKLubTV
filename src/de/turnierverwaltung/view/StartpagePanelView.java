package de.turnierverwaltung.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class StartpagePanelView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea textfeld;

	public StartpagePanelView() {
		super();
		// 5-zeiliges und 20-spaltiges Textfeld wird erzeugt
		textfeld = new JTextArea(20, 40);
//		textfeld.setFont(new Font("Arial", Font.PLAIN, 18));
		// Text für das Textfeld wird gesetzt
		textfeld.setText("Lorem ipsum dolor sit amet, " + "consetetur sadipscing elitr, sed diam nonumy "
				+ "eirmod tempor invidunt ut labore et " + "dolore magna aliquyam erat, sed diam voluptua. "
				+ "At vero eos et accusam et justo duo dolores et " + "ea rebum.");
		// Zeilenumbruch wird eingeschaltet
		textfeld.setLineWrap(true);

		// Zeilenumbrüche erfolgen nur nach ganzen Wörtern
		textfeld.setWrapStyleWord(true);
		// nicht editierbar
		textfeld.setEditable(false);
		// Ein JScrollPane, der das Textfeld beinhaltet, wird erzeugt
		JScrollPane scrollpane = new JScrollPane(textfeld);

		// Scrollpane wird unserem Panel hinzugefügt
		add(scrollpane);
	}

	public JTextArea getTextfeld() {
		return textfeld;
	}

	public void setTextfeld(JTextArea textfeld) {
		this.textfeld = textfeld;
	}

}
