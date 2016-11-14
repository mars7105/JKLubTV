/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package de.turnierverwaltung.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import de.turnierverwaltung.model.Sidepanel;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.GridLayout;

public class TreeDemo extends JPanel implements TreeSelectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1102504985625598532L;
	// private JEditorPane htmlPane;
	private JTree tree;
	// private String helpURL;
	private static boolean DEBUG = false;
	private ArrayList<Sidepanel> sidePanel;
	private JTextField headerTextField;
	private JTextArea bodyTextArea;
	// Optionally play with line styles. Possible values are
	// "Angled" (the default), "Horizontal", and "None".
	private static boolean playWithLineStyle = false;
	private static String lineStyle = "Horizontal";

	private int selectedItem;

	public TreeDemo(ArrayList<Sidepanel> sidePanel, JTextField headerTextField, JTextArea bodyTextArea,
			JPanel htmlAll) {

		super(new GridLayout(1, 0));
		selectedItem = -1;
		this.sidePanel = sidePanel;
		this.headerTextField = headerTextField;
		this.bodyTextArea = bodyTextArea;
		// Create the nodes.
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Webseite");
		createNodes(top);

		// Create a tree that allows one selection at a time.
		tree = new JTree(top);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Listen for when the selection changes.
		tree.addTreeSelectionListener(this);

		if (playWithLineStyle) {
			System.out.println("line style = " + lineStyle);
			tree.putClientProperty("JTree.lineStyle", lineStyle);
		}
		tree.expandRow(1);
		// Create the scroll pane and add the tree to it.
		JScrollPane treeView = new JScrollPane(tree);

		JScrollPane htmlView = new JScrollPane(htmlAll);

		// Add the scroll panes to a split pane.
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setTopComponent(treeView);
		splitPane.setBottomComponent(htmlView);

		Dimension minimumSize = new Dimension(100, 50);
		htmlView.setMinimumSize(minimumSize);
		treeView.setMinimumSize(minimumSize);
		splitPane.setDividerLocation(200);
		splitPane.setPreferredSize(new Dimension(800, 600));

		// Add the split pane to this panel.
		add(splitPane);
	}

	/** Required by TreeSelectionListener interface. */
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

		if (node == null)
			return;

		Object nodeInfo = node.getUserObject();
		if (node.isLeaf()) {
			BookInfo book = (BookInfo) nodeInfo;
			displayURL(book.getHeader(), book.getBody(), book.getIndex());
			selectedItem = book.getIndex();
			if (DEBUG) {
				System.out.print(book.getBody() + ":  \n    ");
			}
		} else {
			// displayURL(helpURL);
		}
		if (DEBUG) {
			System.out.println(nodeInfo.toString());
		}
	}

	private class BookInfo {
		public String header;
		public String body;
		public int index;

		public BookInfo(String header, String body, int index) {
			this.header = header;
			this.body = body;
			this.index = index;
		}

		public String getHeader() {
			// TODO Automatisch generierter Methodenstub
			return header;
		}

		public String getBody() {
			return body;
		}

		public String toString() {
			return header;

		}

		public int getIndex() {
			return index;
		}

	}

	private void displayURL(String hdr, String bdy, int index) {

		if (hdr != null) {
			// htmlPane.setText(bdy);
			headerTextField.setText(hdr);
			bodyTextArea.setText(bdy);
		} else { // null url
			// htmlPane.setText("File Not Found");
			if (DEBUG) {
				System.out.println("Attempted to display a null URL.");
			}
		}

	}

	private void createNodes(DefaultMutableTreeNode top) {
		DefaultMutableTreeNode category = null;
		DefaultMutableTreeNode book = null;

		category = new DefaultMutableTreeNode("Seitenpanel");
		
		int index = 0;
		for (Sidepanel sideP : sidePanel) {
			book = new DefaultMutableTreeNode(new BookInfo(sideP.getHeader(), sideP.getBody(), index));
			category.add(book);
			index++;

		}
		top.add(category);
		
	}

	public int getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(int selectedItem) {
		this.selectedItem = selectedItem;
	}

}
