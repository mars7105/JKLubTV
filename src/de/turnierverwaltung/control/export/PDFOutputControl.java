package de.turnierverwaltung.control.export;

import java.io.File;

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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.model.Tournament;

public class PDFOutputControl {
	public static PdfPTable createTerminTabelle(final String[][] stringTable) throws DocumentException {

		final Font font = new Font(FontFamily.TIMES_ROMAN, 14);
		final int spalten = stringTable[0].length;
		final int zeilen = stringTable.length;
		for (int i = 0; i < zeilen; i++) {
			final String replacedStr = stringTable[i][0].replaceAll("<br />", ""); //$NON-NLS-1$ //$NON-NLS-2$
			stringTable[i][0] = replacedStr;
		}

		final PdfPTable table = new PdfPTable(zeilen);

		for (int x = 0; x < spalten; x++) {

			for (int y = 0; y < zeilen; y++) {

				final Phrase ph = new Phrase(stringTable[y][x]);
				ph.setFont(font);
				final PdfPCell cell = new PdfPCell(ph);

				table.addCell(cell);

			}

		}

		return table;

	}

	public static PdfPTable createTurnierTabelle(final Tournament turnier, final String[][] stringTable)
			throws DocumentException {
		final int spalten = stringTable[0].length;
		final int zeilen = stringTable.length;
		for (int i = 0; i < zeilen; i++) {
			final String replacedStr = stringTable[i][0].replaceAll("<br />", ""); //$NON-NLS-1$ //$NON-NLS-2$
			stringTable[i][0] = replacedStr;
		}

		final PdfPTable table = new PdfPTable(zeilen);
		final float[] fl = new float[zeilen];
		fl[0] = 5;
		int count = 0;
		if (turnier.getNoDWZCalc() == true) {
			fl[1] = 1;

		} else {
			fl[1] = 3;
			count++;
		}
		if (turnier.getNoFolgeDWZCalc() == true) {

			fl[2] = 1;

		} else {
			fl[2] = 4;
			count++;
		}
		if (turnier.getNoELOCalc() == false) {
			fl[1 + count] = 3;

		} else {
			fl[1 + count] = 1;
		}
		if (turnier.getNoFolgeELOCalc() == false) {
			fl[2 + count] = 4;
		} else {
			fl[2 + count] = 1;
		}
		for (int i = 0; i <= zeilen; i++) {
			if (i > 2 + count && i < zeilen - 3) {
				fl[i] = 1;
			}
			if (i >= zeilen - 2) {
				fl[i - 1] = 2;
			}
		}
		final Font font = new Font(FontFamily.TIMES_ROMAN, 14);

		for (int x = 0; x < spalten; x++) {

			for (int y = 0; y < zeilen; y++) {

				final Phrase ph = new Phrase(stringTable[y][x]);
				ph.setFont(font);
				final PdfPCell cell = new PdfPCell(ph);

				table.addCell(cell);

			}

		}
		table.setWidths(fl);

		return table;

	}

	private Boolean fileExist;

	public PDFOutputControl() {
		fileExist = false;

	}

	public void createTerminPdf(final String titel, final String absolutePath, final String[][] tabellenMatrix) {
		int n = 0;

		final File file = new File(absolutePath);
		if (file.exists() && fileExist == false) {
			fileExist = true;
			final Object[] options = { Messages.getString("SaveDialog.2"), Messages.getString("SaveDialog.3") };
			n = JOptionPane.showOptionDialog(null,
					Messages.getString("SaveDialog.0") + file.getAbsolutePath() + Messages.getString("SaveDialog.1"),
					"Dateioperation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
					options[1]);
		}
		if (n == 0) {
			// step 1
			final Document document = new Document();
			// step 2
			try {
				PdfWriter.getInstance(document, new FileOutputStream(file));
			} catch (FileNotFoundException | DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// step 3
			document.open();
			// step 4
			try {
				document.add(new Paragraph(titel));
				document.add(new Paragraph(" ")); //$NON-NLS-1$
				document.add(createTerminTabelle(tabellenMatrix));
			} catch (final DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// step 5
			document.close();
		}
	}

	/**
	 * Creates a PDF with information about the movies
	 *
	 * @param turnier
	 *
	 * @param filename
	 *                     the name of the PDF file that will be created.
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void createTurnierPdf(final Tournament turnier, final String titel, final String absolutePath,
			final String[][] tabellenMatrix) {
		int n = 0;

		final File file = new File(absolutePath);
		if (file.exists() && fileExist == false) {
			fileExist = true;
			final Object[] options = { Messages.getString("SaveDialog.2"), Messages.getString("SaveDialog.3") };
			n = JOptionPane.showOptionDialog(null,
					Messages.getString("SaveDialog.0") + file.getAbsolutePath() + Messages.getString("SaveDialog.1"),
					"Dateioperation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
					options[1]);
		}
		if (n == 0) {
			// step 1
			final Document document = new Document();
			// step 2
			try {
				PdfWriter.getInstance(document, new FileOutputStream(file));
			} catch (final FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// step 3
			document.open();
			// step 4
			try {
				document.add(new Paragraph(titel));
				document.add(new Paragraph(" ")); //$NON-NLS-1$
				document.add(createTurnierTabelle(turnier, tabellenMatrix));
			} catch (final DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// step 5
			document.close();
		}
	}

}