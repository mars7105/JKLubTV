package de.dwzberechnung.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

public class WertungsdifferenzenPunktePartienModel {
	public static final int NULL = 1000;
	private HashMap<String, int[]> hmap;

	public WertungsdifferenzenPunktePartienModel() {
		super();
		this.hmap = new HashMap<String, int[]>();
		fillHMap();
	}

	private void fillHMap() {
		{
			int[] col = { -191, -274, -325, -362, -391, -414, -434, -451, -465, -478, -490, -500, -510, -519, -527,
					-534, -542, -548, -554 };
			hmap.put("0,5", col);
		}

		{
			int[] col = { -0, -122, -191, -238, -274, -302, -325, -345, -362, -378, -391, -403, -414, -425, -434, -443,
					-451, -458, -465 };
			hmap.put("1,0", col);
		}

		{
			int[] col = { 191, 0, -90, -148, -191, -224, -251, -274, -293, -310, -325, -339, -351, -362, -373, -382,
					-391, -399, -407 };
			hmap.put("1,5", col);
		}

		{
			int[] col = { NULL, 122, 0, -72, -122, -160, -191, -216, -238, -257, -274, -289, -302, -314, -325, -336,
					-345, -354, -362 };
			hmap.put("2,0", col);
		}

		{
			int[] col = { NULL, 274, 90, 0, -60, -104, -138, -167, -191, -212, -230, -246, -260, -274, -286, -297, -307,
					-316, -325 };
			hmap.put("2,5", col);
		}

		{
			int[] col = { NULL, NULL, 191, 72, 0, -51, -90, -122, -148, -171, -191, -208, -224, -238, -251, -263, -274,
					-284, -293 };
			hmap.put("3,0", col);
		}

		{
			int[] col = { NULL, NULL, 325, 148, 60, 0, -44, -80, -109, -134, -155, -174, -191, -206, -220, -232, -244,
					-254, -264 };
			hmap.put("3,5", col);
		}

		{
			int[] col = { NULL, NULL, NULL, 238, 122, 51, 0, -40, -72, -99, -122, -142, -160, -176, -191, -204, -216,
					-228, -238 };
			hmap.put("4,0", col);
		}

		{
			int[] col = { NULL, NULL, NULL, 362, 191, 104, 44, 0, -36, -65, -90, -112, -131, -148, -164, -178, -191,
					-203, -214 };
			hmap.put("4,5", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, 274, 160, 90, 40, 0, -32, -60, -83, -104, -122, -138, -153, -167,
					-179, -191 };
			hmap.put("5,0", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, 391, 224, 138, 80, 36, 0, -30, -55, -77, -96, -114, -130, -144, -157,
					-169 };
			hmap.put("5,5", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, 302, 191, 122, 72, 32, 0, -27, -51, -72, -90, -107, -122, -136,
					-148 };
			hmap.put("6,0", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, 414, 251, 167, 109, 65, 30, 0, -25, -47, -67, -85, -101, -115,
					-128 };
			hmap.put("6,5", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, 325, 216, 148, 99, 60, 27, 0, -24, -44, -63, -80, -95,
					-109 };
			hmap.put("7,0", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, 434, 274, 191, 134, 90, 55, 25, 0, -22, -42, -60, -76,
					-90 };
			hmap.put("7,5", col);
		}
		// ***
		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, NULL, 345, 238, 171, 122, 83, 51, 24, 0, -21, -40, -56,
					-72 };
			hmap.put("8,0", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, NULL, 451, 293, 212, 155, 112, 77, 47, -22, 0, -20, -37,
					-53 };
			hmap.put("8,5", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 362, 257, 191, 142, 104, 72, 44, 21, 0, -19,
					-36 };
			hmap.put("9,0", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 465, 310, 230, 174, 131, 90, 67, 42, 20, 0,
					-18 };
			hmap.put("9,5", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 378, 274, 208, 160, 122, 90, 63, 40, 19,
					0 };
			hmap.put("10,0", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 478, 325, 246, 191, 148, 114, 85, 60,
					37, 18 };
			hmap.put("10,5", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 391, 289, 176, 138, 107, 80, 56,
					36, 11 };
			hmap.put("11,0", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 490, 339, 260, 206, 164, 130, 101,
					76, 53 };
			hmap.put("11,5", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 403, 302, 238, 191, 153,
					122, 95, 72 };
			hmap.put("12,0", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 500, 351, 274, 220, 178,
					144, 115, 90 };
			hmap.put("12,5", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 414, 314, 251, 204,
					167, 136, 109 };
			hmap.put("13,0", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 510, 362, 286, 232,
					191, 157, 128 };
			hmap.put("13,5", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 425, 325, 263,
					216, 179, 148 };
			hmap.put("14,0", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 519, 373, 297,
					244, 203, 169 };
			hmap.put("14,5", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 434, 336,
					274, 228, 191 };
			hmap.put("15,0", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 527, 382,
					307, 254, 214 };
			hmap.put("15,5", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 443,
					345, 284, 238 };
			hmap.put("16,0", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 534,
					391, 316, 264 };
			hmap.put("16,5", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
					NULL, 451, 354, 293 };
			hmap.put("17,0", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
					NULL, NULL, 542, 399, 325 };
			hmap.put("17,5", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
					NULL, NULL, NULL, 458, 362 };
			hmap.put("18,0", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
					NULL, NULL, NULL, 548, 407 };
			hmap.put("18,5", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
					NULL, NULL, NULL, NULL, 465 };
			hmap.put("19,0", col);
		}

		{
			int[] col = { NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
					NULL, NULL, NULL, NULL, 554 };
			hmap.put("19,5", col);
		}

	}

	public HashMap<String, int[]> getHmap() {
		return hmap;
	}

	public double getValue(double anzahlPunkte, int anzahlPartien) {

		Locale locale = new Locale("de", "DE");
		DecimalFormat numberFormat = (DecimalFormat) NumberFormat.getInstance(locale);
		numberFormat.applyLocalizedPattern("#,0");

		String key = numberFormat.format(anzahlPunkte);

//		System.out.println(key);

		return hmap.get(key)[anzahlPartien - 2];

	}

	public void setHmap(HashMap<String, int[]> hmap) {
		this.hmap = hmap;
	}
}
