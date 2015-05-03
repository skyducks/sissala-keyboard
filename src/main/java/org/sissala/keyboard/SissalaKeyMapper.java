/**
 * Copyright 2015 Sissala Heritage Foundation
 * Copyright 2015 Yeongdeok Suh <skyducks111@gmail.com>
 */

package org.sissala.keyboard;

public class SissalaKeyMapper {
	private static final String SISSALA_E = "\u0190", SISSALA_I = "\u0196",
			SISSALA_N = "\u014A", SISSALA_O = "\u0254",
			SISSALA_U = "\u01B2";

	private static int mapping(int keyCode) {
		switch (keyCode) {
		case (int) 'E':
			keyCode = (int) SISSALA_E.toUpperCase().charAt(0);
			break;
		case (int) 'e':
			keyCode = (int) SISSALA_E.toLowerCase().charAt(0);
			break;
		case (int) 'I':
			keyCode = (int) SISSALA_I.toUpperCase().charAt(0);
			break;
		case (int) 'i':
			keyCode = (int) SISSALA_I.toLowerCase().charAt(0);
			break;
		case (int) 'N':
			keyCode = (int) SISSALA_N.toUpperCase().charAt(0);
			break;
		case (int) 'n':
			keyCode = (int) SISSALA_N.toLowerCase().charAt(0);
			break;
		case (int) 'O':
			keyCode = (int) SISSALA_O.toUpperCase().charAt(0);
			break;
		case (int) 'o':
			keyCode = (int) SISSALA_O.toLowerCase().charAt(0);
			break;
		case (int) 'U':
			keyCode = (int) SISSALA_U.toUpperCase().charAt(0);
			break;
		case (int) 'u':
			keyCode = (int) SISSALA_U.toLowerCase().charAt(0);
			break;
		}

		return keyCode;
	}

	public static int getConvertedKey(int key) {
		return mapping(key);
	}

}