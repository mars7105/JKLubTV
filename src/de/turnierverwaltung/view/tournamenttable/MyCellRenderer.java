package de.turnierverwaltung.view.tournamenttable;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
//MyCellRenderer.java

import de.turnierverwaltung.view.ratingdialog.ListItem;

/* Eventuell hier erst das Paket definieren, falls erforderlich! */

@SuppressWarnings("rawtypes")
public class MyCellRenderer extends JPanel implements ListCellRenderer {
	/**
	 *
	 */
	private static final long serialVersionUID = -9184411188243605427L;
	private JLabel label = null;

	public MyCellRenderer() {
		// Konstruktor des JPanels mit FlowLayout aufrufen
		super(new FlowLayout(FlowLayout.LEFT));

		// JPanel undurchsichtig machen
		setOpaque(true);

		// JLabel instanzieren, durchsichtig machen und hinzufügen
		label = new JLabel();
		label.setOpaque(false);
		add(label);
	}

	public JLabel getLabel() {
		return label;
	}

	@Override
	public Component getListCellRendererComponent(final JList list, // JList Objekt
			final Object value, // anzuzeigende Komponente
			final int index, // Zellenindex
			final boolean iss, // Ist selektiert?
			final boolean chf) // Hat den Fokus?
	{
		// JLabel das Icon aus unserem MyListItem zuweisen
		label.setIcon(((ListItem) value).getIcon());

		// JLabel den Text aus unserem MyListItem zuweisen
		label.setText(((ListItem) value).getText());

		// Hintergrundfarbe des JPanels bei Fokuswechseln definieren
		if (iss) {
			setBackground(list.getBackground()); // Hat den Fokus
		} else {
			setBackground(list.getBackground()); // Hat den Fokus nicht
		}
		return this;
	}

	public void setLabel(final JLabel label) {
		this.label = label;
	}

}
