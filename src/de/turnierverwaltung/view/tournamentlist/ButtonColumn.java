package de.turnierverwaltung.view.tournamentlist;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 * The ButtonColumn class provides a renderer and an editor that looks like a
 * JButton. The renderer and editor will then be used for a specified column in
 * the table. The TableModel will contain the String to be displayed on the
 * button.
 *
 * The button can be invoked by a mouse click or by pressing the space bar when
 * the cell has focus. Optionally a mnemonic can be set to invoke the button.
 * When the button is invoked the provided Action is invoked. The source of the
 * Action will be the table. The action command will contain the model row
 * number of the button that was clicked.
 *
 */
public class ButtonColumn extends AbstractCellEditor
		implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final JTable table;
	private final Action action;
	private int mnemonic;
	private final Border originalBorder;
	private Border focusBorder;

	private final JButton renderButton;
	private final JButton editButton;
	private Object editorValue;
	private boolean isButtonColumnEditor;
	private final ImageIcon userDelete = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/user-delete-2.png"))); //$NON-NLS-1$
	private final ImageIcon userAdd = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/user-new-3.png"))); //$NON-NLS-1$

	/**
	 * Create the ButtonColumn to be used as a renderer and editor. The renderer and
	 * editor will automatically be installed on the TableColumn of the specified
	 * column.
	 *
	 * @param table
	 *                   the table containing the button renderer/editor
	 * @param action
	 *                   the Action to be invoked when the button is invoked
	 * @param column
	 *                   the column to which the button renderer/editor is added
	 */
	public ButtonColumn(final JTable table, final Action action, final int column) {
		this.table = table;
		this.action = action;

		renderButton = new JButton();
		editButton = new JButton();
		editButton.setFocusPainted(false);
		editButton.addActionListener(this);
		originalBorder = editButton.getBorder();
		setFocusBorder(new LineBorder(Color.BLUE));

		final TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(column).setCellRenderer(this);
		columnModel.getColumn(column).setCellEditor(this);
		table.addMouseListener(this);
	}

	/**
	 * Get foreground color of the button when the cell has focus
	 *
	 * @return the foreground color
	 */
	public Border getFocusBorder() {
		return focusBorder;
	}

	/**
	 * The foreground color of the button when the cell has focus
	 *
	 * @param focusBorder
	 *                        the foreground color
	 */
	public void setFocusBorder(final Border focusBorder) {
		this.focusBorder = focusBorder;
		editButton.setBorder(focusBorder);
	}

	public int getMnemonic() {
		return mnemonic;
	}

	/**
	 * The mnemonic to activate the button when the cell has focus
	 *
	 * @param mnemonic
	 *                     the mnemonic
	 */
	public void setMnemonic(final int mnemonic) {
		this.mnemonic = mnemonic;
		renderButton.setMnemonic(mnemonic);
		editButton.setMnemonic(mnemonic);
	}

	@Override
	public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected,
			final int row, final int column) {
		if (value == null) {
			editButton.setText("");
			editButton.setIcon(null);
		} else if (value instanceof Icon) {
			editButton.setText("");
			editButton.setIcon((Icon) value);
		} else {
			editButton.setText(value.toString());
			if (value.toString().equals("Löschen")) {
				editButton.setIcon(userDelete);
			} else {
				editButton.setIcon(userAdd);
			}

		}

		editorValue = value;
		return editButton;
	}

	@Override
	public Object getCellEditorValue() {
		return editorValue;
	}

	//
	// Implement TableCellRenderer interface
	//
	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected,
			final boolean hasFocus, final int row, final int column) {
		if (isSelected) {
			renderButton.setForeground(table.getSelectionForeground());
			renderButton.setBackground(table.getSelectionBackground());
		} else {
			renderButton.setForeground(table.getForeground());
			renderButton.setBackground(UIManager.getColor("Button.background"));
		}

		if (hasFocus) {
			renderButton.setBorder(focusBorder);
		} else {
			renderButton.setBorder(originalBorder);
		}

		// renderButton.setText( (value == null) ? "" : value.toString() );
		if (value == null) {
			renderButton.setText("");
			renderButton.setIcon(null);
		} else if (value instanceof Icon) {
			renderButton.setText("");
			renderButton.setIcon((Icon) value);
		} else {
			renderButton.setText(value.toString());
			if (value.toString().equals("Löschen")) {
				renderButton.setIcon(userDelete);
			} else {
				renderButton.setIcon(userAdd);
			}
		}

		return renderButton;
	}

	//
	// Implement ActionListener interface
	//
	/*
	 * The button has been pressed. Stop editing and invoke the custom Action
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		final int row = table.convertRowIndexToModel(table.getEditingRow());
		// System.out.println("row:" + row);

		fireEditingStopped();

		// Invoke the Action

		final ActionEvent event = new ActionEvent(table, ActionEvent.ACTION_PERFORMED, "" + row);
		action.actionPerformed(event);

	}

	//
	// Implement MouseListener interface
	//
	/*
	 * When the mouse is pressed the editor is invoked. If you then then drag the
	 * mouse to another cell before releasing it, the editor is still active. Make
	 * sure editing is stopped when the mouse is released.
	 */
	@Override
	public void mousePressed(final MouseEvent e) {
		if (table.isEditing() && table.getCellEditor() == this) {
			isButtonColumnEditor = true;
		}
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		if (isButtonColumnEditor && table.isEditing()) {
			table.getCellEditor().stopCellEditing();
		}

		isButtonColumnEditor = false;
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
	}

	@Override
	public void mouseExited(final MouseEvent e) {
	}
}
