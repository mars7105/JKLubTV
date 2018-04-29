package de.turnierverwaltung.control.export;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.model.CopyToClipboard;
import de.turnierverwaltung.model.table.CrossTable;
import de.turnierverwaltung.view.export.HTMLToClipBoardDialogView;
import de.turnierverwaltung.view.export.HTMLToClipBoardView;

public class HTMLCopyToClipboardControl {

	private MainControl mainControl;

	public HTMLCopyToClipboardControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;

	}

	public void copyToClipboard() {
		Boolean ready = mainControl.getPairingsControl().checkNewTurnier();
		if (ready) {
			int anzahlGruppen = this.mainControl.getTournament().getAnzahlGruppen();

			String webserverPath = mainControl.getPropertiesControl().getWebserverPath();

			final HTMLToClipBoardDialogView htmlToClipboardDialog = new HTMLToClipBoardDialogView();

			ArrayList<HTMLToClipBoardView> htmlToClipboardArray = new ArrayList<HTMLToClipBoardView>();

			for (int i = 0; i < anzahlGruppen; i++) {

				String wfn = mainControl.getTournament().getTurnierName();
				wfn += Messages.getString("PDFSaveControler.2")
						+ mainControl.getTournament().getGruppe()[i].getGruppenName() + ".pdf";
				String webfilename1 = wfn.replaceAll(" ", "");

				String wfn2 = mainControl.getTournament().getTurnierName();
				wfn2 += Messages.getString("PDFSaveControler.8")
						+ mainControl.getTournament().getGruppe()[i].getGruppenName() + ".pdf";
				String webfilename2 = wfn2.replaceAll(" ", "");

				String ical = mainControl.getTournament().getTurnierName()
						+ mainControl.getTournament().getGruppe()[i].getGruppenName() + ".ics";
				String webfilename3 = ical.replaceAll(" ", "");

				final String crossLabel = mainControl.getTournament().getTurnierName() + " "
						+ mainControl.getTournament().getGruppe()[i].getGruppenName() + " "
						+ Messages.getString("PDFSaveControler.22");
				final String meetingLabel = mainControl.getTournament().getTurnierName() + " "
						+ mainControl.getTournament().getGruppe()[i].getGruppenName() + " "
						+ Messages.getString("PDFSaveControler.23");
				final String allLabel = mainControl.getTournament().getTurnierName() + " "
						+ mainControl.getTournament().getGruppe()[i].getGruppenName() + " "
						+ Messages.getString("PDFSaveControler.21");
				if (this.mainControl.getCrossTable()[i] == null) {
					this.mainControl.getCrossTableControl().makeSimpleTableView(i);

					this.mainControl.getMeetingTableControl().makeSimpleTableView(i);

				}

				CrossTable turnierTabelle = mainControl.getCrossTable()[i];

				int spalte = this.mainControl.getCrossTableView()[i].getTable().getModel().getColumnCount();
				int zeile = this.mainControl.getCrossTableView()[i].getTable().getModel().getRowCount();
				for (int x = 0; x < spalte; x++) {
					for (int y = 0; y < zeile; y++) {

						turnierTabelle.getTabellenMatrix()[x][y + 1] = (String) this.mainControl.getCrossTableView()[i]
								.getTable().getValueAt(y, x);

					}
				}

				Boolean ohneHeaderundFooter = mainControl.getPropertiesControl().getOnlyTables();
				Boolean showLink = mainControl.getPropertiesControl().getPDFLinks();

				HTMLToClipBoardView crosshtmlToClipboard = new HTMLToClipBoardView();
				crosshtmlToClipboard.getLabel().setText(crossLabel);
				final String crosstable = this.mainControl.getCrossTable()[i].getHTMLTable(ohneHeaderundFooter,
						webserverPath, webfilename1, showLink);
				crosshtmlToClipboard.getCopyToClipBoardButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						CopyToClipboard clipBoardcopy = new CopyToClipboard();
						clipBoardcopy.copy(crosstable);
						htmlToClipboardDialog.getStatusLabel().getTitleLabel().setText("Clipboard: " + crossLabel);
					}

				});

				htmlToClipboardArray.add(crosshtmlToClipboard);

				HTMLToClipBoardView meetinghtmlToClipboard = new HTMLToClipBoardView();
				meetinghtmlToClipboard.getLabel().setText(meetingLabel);

				final String meetingtable = this.mainControl.getMeetingTableControl().getTerminTabelle()[i]
						.getHTMLTable(ohneHeaderundFooter, webserverPath, webfilename2, webfilename3, showLink);
				meetinghtmlToClipboard.getCopyToClipBoardButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						CopyToClipboard clipBoardcopy = new CopyToClipboard();
						clipBoardcopy.copy(meetingtable);
						htmlToClipboardDialog.getStatusLabel().getTitleLabel().setText("Clipboard: " + meetingLabel);
					}

				});

				htmlToClipboardArray.add(meetinghtmlToClipboard);
				HTMLToClipBoardView allToClipboard = new HTMLToClipBoardView();
				allToClipboard.getLabel().setText(allLabel);
				final String crosstableall = this.mainControl.getCrossTable()[i]
						.getHTMLTableOnlyWithHeader(ohneHeaderundFooter, webserverPath, webfilename1, showLink);
				final String meetingtableall = this.mainControl.getMeetingTableControl().getTerminTabelle()[i]
						.getHTMLTableOnlyWithFooter(ohneHeaderundFooter, webserverPath, webfilename2, webfilename3,
								showLink);
				allToClipboard.getCopyToClipBoardButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						CopyToClipboard clipBoardcopy = new CopyToClipboard();
						clipBoardcopy.copy(crosstableall + "\n" + meetingtableall);
						htmlToClipboardDialog.getStatusLabel().getTitleLabel().setText("Clipboard: " + allLabel);
					}

				});

				htmlToClipboardArray.add(allToClipboard);
			}
			htmlToClipboardDialog.makeDialog(htmlToClipboardArray);

			htmlToClipboardDialog.getButtonPanel().getOkButton().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					htmlToClipboardDialog.dispose();

				}

			});
			htmlToClipboardDialog.showDialog();
		} else {
			JOptionPane.showMessageDialog(null,
					Messages.getString("HTMLSaveControler.21") + Messages.getString("HTMLSaveControler.22")); //$NON-NLS-1$ //$NON-NLS-2$

		}
	}
}
