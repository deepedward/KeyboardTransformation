package com.kb;

import java.util.HashMap;
import java.util.Map;

public class KeyboardTransformation {

	public static void main(String[] args) {

		testCase1();

		testCase2();

		testCase3();

	}

	private static void processMessage(char[][] original_keyboard, char[][] keyboard,
			Map<Character, Character> characterMapping, String[] operations) {
		printKeyboard(keyboard);

		// transform log (operations to transform the keyboard)
		performOperations(keyboard, operations);
		updatedCharacterMapping(characterMapping, keyboard, original_keyboard);
	}

	private static void performOperations(char[][] keyboard, String[] operations) {
		for (String str : operations) {
			if (str.equalsIgnoreCase("H")) {

				horizontalFlip(keyboard);
				printKeyboard(keyboard);
			} else if (str.equalsIgnoreCase("V")) {

				verticalFlip(keyboard);
				printKeyboard(keyboard);
			} else {

				// Shift operation
				shift(keyboard, str);
				printKeyboard(keyboard);
			}
		}
	}

	private static void updatedCharacterMapping(Map<Character, Character> characterMapping,
			char[][] transformed_keyboard, char[][] original_keyboard) {

		for (int i = 0; i < original_keyboard.length; i++) {
			for (int j = 0; j < original_keyboard[0].length; j++) {
				characterMapping.put(original_keyboard[i][j], transformed_keyboard[i][j]);
			}
		}
	}

	private static void printKeyboard(char[][] keyboard) {
		// Print the transformed keyboard
		for (char[] elems : keyboard) {
			for (char c : elems) {
				System.out.print(c + " ");
			}
			System.out.println();
		}

		System.out.println();
	}

	private static void horizontalFlip(char[][] keyboard) {
		int rows = keyboard.length;

		for (int i = 0; i < rows; i++) {
			int colsStartIdx = 0;
			int colsEndIdx = keyboard[0].length - 1;

			while (colsStartIdx < colsEndIdx) {
				char temp = keyboard[i][colsStartIdx];
				keyboard[i][colsStartIdx] = keyboard[i][colsEndIdx];
				keyboard[i][colsEndIdx] = temp;
				colsStartIdx++;
				colsEndIdx--;
			}
		}
	}

	private static void verticalFlip(char[][] keyboard) {
		int cols = keyboard[0].length;

		for (int i = 0; i < cols; i++) {
			int rowsEndIdx = keyboard.length - 1;
			int rowsStartIdx = 0;

			while (rowsStartIdx < rowsEndIdx) {
				char temp = keyboard[rowsStartIdx][i];
				keyboard[rowsStartIdx][i] = keyboard[rowsEndIdx][i];
				keyboard[rowsEndIdx][i] = temp;
				rowsStartIdx++;
				rowsEndIdx--;
			}
		}

	}

	private static void shift(char[][] keyboard, String shift) {
		int val = Integer.parseInt(shift);
		if (val > 0) {
			rightShift(keyboard, val);
		} else {
			leftShift(keyboard, Math.abs(val));
		}
	}

	/**
	 * 
	 * 1) each row is a 1d array string 
	 * 2) appended each row shift element in the string
	 * 3) add it back tranformed matrix and return
	 */
	private static void rightShift(char[][] keyboard, int shiftValue) {
		int rowLength = keyboard.length;
		int colLength = keyboard[0].length;

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < rowLength; i++) {
			char[] chars = keyboard[i];
			sb.append(chars);
		}

		String str = sb.toString();
		char[] elems = str.toCharArray();

		for (int j = 0; j < shiftValue; j++) {
			char lastElment = elems[elems.length - 1];

			// perform right shift
			int idx = elems.length - 1;
			while (idx > 0) {
				elems[idx] = elems[idx - 1];
				idx--;
			}
			elems[0] = lastElment;
		}
		// update the keyboard from the transformed string
		int k = 0;
		for (int i = 0; i < rowLength; i++) {
			for (int j = 0; j < colLength; j++) {
				keyboard[i][j] = elems[k];
				k++;
			}
		}
	}

	private static void leftShift(char[][] keyboard, int shiftValue) {
		int rowLength = keyboard.length;
		int colLength = keyboard[0].length;

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < rowLength; i++) {
			char[] chars = keyboard[i];
			sb.append(chars);
		}

		String str = sb.toString();
		char[] elems = str.toCharArray();

		for (int j = 0; j < shiftValue; j++) {
			char firstElement = elems[0];

			// perform left shift
			int idx = 0;
			while (idx < elems.length - 1) {
				elems[idx] = elems[idx + 1];
				idx++;
			}
			elems[elems.length - 1] = firstElement;
		}
		// update the keyboard from the transformed string
		int k = 0;
		for (int i = 0; i < rowLength; i++) {
			for (int j = 0; j < colLength; j++) {
				keyboard[i][j] = elems[k];
				k++;
			}
		}
	}

	private static String transform(Map<Character, Character> characterMapping, String message) {
		char[] chars = message.toCharArray();
		int arr_length = chars.length;

		int counter = 0;
		while (counter < arr_length) {
			char c = chars[counter];
			if (characterMapping.get(c) != null && counter < arr_length) {
				chars[counter] = characterMapping.get(c);
			}
			counter++;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(chars);
		return sb.toString();
	}

	/**
	 * ###############################################
	 * 					Test 1
	 * ###############################################
	 */
	private static void testCase1() {
		
		// keyboard layout
		char[][] original_keyboard = { { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' },
				{ 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p' },
				{ 'a', 's', 'd', 'd', 'f', 'g', 'h', 'j', 'k', 'l' },
				{ 'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.', '/' } };

		char[][] keyboard = { { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' },
				{ 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p' },
				{ 'a', 's', 'd', 'd', 'f', 'g', 'h', 'j', 'k', 'l' },
				{ 'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.', '/' } };

		Map<Character, Character> characterMapping = new HashMap<Character, Character>();
				
		String[] operation1 = { "5" };
		String message = "A sample message";


		processMessage(original_keyboard, keyboard, characterMapping, operation1);

		// Pass the transformed keyboard to the String
		String transformedMessage1 = transform(characterMapping, message);

		// Return the resultant string
		System.out.println("Original message: " + message + "\n ");

		// Return the resultant string
		System.out.println("Transformed message: " + transformedMessage1 + "\n ");
	}

	/**
	 * ############################################### 
	 * 					Test 2
	 * ###############################################
	 */
	private static void testCase2() {
		
		char[][] original_keyboard = { { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' },
				{ 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p' },
				{ 'a', 's', 'd', 'd', 'f', 'g', 'h', 'j', 'k', 'l' },
				{ 'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.', '/' } };

		char[][] keyboard = { { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' },
				{ 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p' },
				{ 'a', 's', 'd', 'd', 'f', 'g', 'h', 'j', 'k', 'l' },
				{ 'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.', '/' } };
		
		Map<Character, Character> characterMapping = new HashMap<Character, Character>();
		
		String[] operation2 = { "-12" };
		String message2 = "This is another message";


		processMessage(original_keyboard, keyboard, characterMapping, operation2);
		String transformedMessage2 = transform(characterMapping, message2);

		System.out.println("Original message: " + message2 + "\n ");
		System.out.println("Transformed message: " + transformedMessage2 + "\n ");
	}

	/**
	 * ############################################### 
	 * 					Test 3
	 * ###############################################
	 */
	private static void testCase3() {
		
		// keyboard layout
		char[][] original_keyboard = { { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' },
				{ 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p' },
				{ 'a', 's', 'd', 'd', 'f', 'g', 'h', 'j', 'k', 'l' },
				{ 'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.', '/' } };

		char[][] keyboard = { { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' },
				{ 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p' },
				{ 'a', 's', 'd', 'd', 'f', 'g', 'h', 'j', 'k', 'l' },
				{ 'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.', '/' } };
		
		Map<Character, Character> characterMapping = new HashMap<Character, Character>();
		
		String[] operation3 = { "H", "V", "H", "5", "V", "-12" };
		String message3 = "all human beings are born free and equal in dignity and rights.\n"
				+ "they are endowed with reason and conscience and should act towards\n"
				+ "one another in a spirit of brotherhood.";
		

		processMessage(original_keyboard, keyboard, characterMapping, operation3);
		String transformedMessage3 = transform(characterMapping, message3);

		System.out.println("Original message: " + message3 + "\n ");
		System.out.println("Transformed message: " + transformedMessage3 + "\n ");
	}

}
