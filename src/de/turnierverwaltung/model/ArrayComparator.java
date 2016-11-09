package de.turnierverwaltung.model;

import java.util.Comparator;

//Der Comparator vergleicht zwei Arrays, hier wird nur der erste Eintrag berücksichtigt.
public class ArrayComparator implements Comparator<String> {

	@Override
	public int compare(String a, String b) {
		int arrayA = new Integer(a);
		int arrayB = new Integer(b);

		return arrayA - arrayB;
	}
}