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
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

public class ContextMenuMouseListener extends MouseAdapter {
	private JPopupMenu popup = new JPopupMenu();
	private AbstractAction copyAction;
	private AbstractAction pasteAction;
	private AbstractAction selectAllAction;

	private AbstractAction selectAllAndCopyAction;
	private JTextComponent textComponent;

	public ContextMenuMouseListener() {
		// undoAction = new AbstractAction("Undo") {
		//
		// @Override
		// public void actionPerformed(ActionEvent ae) {
		// textComponent.setText("");
		// textComponent.replaceSelection(savedString);
		//
		// lastActionSelected = Actions.UNDO;
		// }
		// };
		//
		// popup.add(undoAction);
		// popup.addSeparator();

		// cutAction = new AbstractAction("ausschneiden") {
		//
		// @Override
		// public void actionPerformed(ActionEvent ae) {
		// lastActionSelected = Actions.CUT;
		// savedString = textComponent.getText();
		// textComponent.cut();
		// }
		// };
		//
		// popup.add(cutAction);
		selectAllAndCopyAction = new AbstractAction("Alles markieren und kopieren") {

			/**
					 * 
					 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent ae) {
				textComponent.selectAll();
				textComponent.copy();
			}
		};
		popup.add(selectAllAndCopyAction);
		popup.addSeparator();
		selectAllAction = new AbstractAction("Alles markieren") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent ae) {
				textComponent.selectAll();
			}
		};

		popup.add(selectAllAction);

		copyAction = new AbstractAction("Kopieren") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent ae) {
				textComponent.copy();
			}
		};

		popup.add(copyAction);

		pasteAction = new AbstractAction("Einfügen") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent ae) {
				textComponent.getText();
				textComponent.paste();
			}
		};

		popup.add(pasteAction);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getModifiers() == InputEvent.BUTTON3_MASK) {
			if (!(e.getSource() instanceof JTextComponent)) {
				return;
			}

			textComponent = (JTextComponent) e.getSource();
			textComponent.requestFocus();

			boolean enabled = textComponent.isEnabled();
			boolean editable = textComponent.isEditable();
			boolean nonempty = !(textComponent.getText() == null || textComponent.getText().equals(""));
			boolean marked = textComponent.getSelectedText() != null;

			boolean pasteAvailable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null)
					.isDataFlavorSupported(DataFlavor.stringFlavor);

			// undoAction
			// .setEnabled(enabled
			// && editable
			// && (lastActionSelected == Actions.CUT || lastActionSelected ==
			// Actions.PASTE));
			// cutAction.setEnabled(enabled && editable && marked);
			copyAction.setEnabled(enabled && marked);
			pasteAction.setEnabled(enabled && editable && pasteAvailable);
			selectAllAction.setEnabled(enabled && nonempty);
			selectAllAndCopyAction.setEnabled(enabled && nonempty);
			int nx = e.getX();

			if (nx > 500) {
				nx = nx - popup.getSize().width;
			}

			popup.show(e.getComponent(), nx, e.getY() - popup.getSize().height);
		}
	}
}
