package sudoku;

import java.util.*;

public class Grid {
	private int[][] values;

	// Constructs a Grid instance from a string[] as provided by
	// TestGridSupplier.
	// See TestGridSupplier for examples of input.
	// Dots in input strings represent 0s in values[][].
	//
	public Grid(String[] rows) {
		values = new int[9][9];
		for (int j = 0; j < 9; j++) {
			String row = rows[j];
			char[] charray = row.toCharArray();
			for (int i = 0; i < 9; i++) {
				char ch = charray[i];
				if (ch != '.')
					values[j][i] = ch - '0';
			}
		}
	}

	public String toString() {
		String s = "";
		for (int j = 0; j < 9; j++) {
			for (int i = 0; i < 9; i++) {
				int n = values[j][i];
				if (n == 0)
					s += '.';
				else
					s += (char) ('0' + n);
			}
			s += "\n";
		}
		return s;
	}

	// Duplicates its source. You'll call this 9 times in
	// next9Grids.
	//
	Grid(Grid src) {
		values = new int[9][9];
		for (int j = 0; j < 9; j++)
			for (int i = 0; i < 9; i++)
				values[j][i] = src.values[j][i];
	}

	/*
	 * Creates 9 different grids
	 */
	public ArrayList<Grid> next9Grids() {
		int xOfNextEmptyCell = 0;
		int yOfNextEmptyCell = 0;
		boolean done = false;

		for (int x = 0; x < values.length; x++) {
			for (int y = 0; y < values[0].length; y++) {

				if (!done && values[x][y] == 0) {
					xOfNextEmptyCell = x;
					yOfNextEmptyCell = y;
					done = true;

				}
			}
		}
		if (done == false) {
			return null;
		}

		ArrayList<Grid> grids = new ArrayList<Grid>();

		// creates grids and adds it to the ArrayList of grids
		for (int loop = 0; loop < 9; loop++) {
			Grid newGrid = new Grid(this);
			newGrid.addValue(xOfNextEmptyCell, yOfNextEmptyCell, loop + 1);
			grids.add(newGrid);

		}

		return grids;
	}

	/*
	 * The add value method holds three parameter that add an 2d array value to
	 * grids
	 */
	public void addValue(int x, int y, int values) {

		this.values[x][y] = values;
	}

	/*
	 * Check Row method checks if there are duplicates in the row
	 */
	public boolean checkRow(int[][] legal) {
		for (int row = 0; row < values.length; row++) {
			for (int col = 0; col < values[0].length; col++) {
				for (int check = col + 1; check < values[0].length; check++) {
					// this loop checks the column after to see if the number at
					// values[i][j] is the same as values[i][j+1], where k = j+1
					if (values[row][col] != 0 && values[row][check] != 0 && values[row][col] == values[row][check]) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/*
	 * Checks Column method checks if there are duplicates in columns
	 */
	public boolean checkCol(int[][] legal) {
		for (int row = 0; row < values.length; row++) {
			for (int col = 0; col < values[0].length; col++) {
				for (int check = row + 1; check < values[0].length; check++) {
					// this loop checks the row after to compare the number at
					// values[i][j] to values[i+1][j], where k = i+1 the row
					// after
					if (values[row][col] != 0 && values[check][col] != 0 && values[row][col] == values[check][col]) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/*
	 * Create Box method creates a 3x3 box by setting the boundaries to 3
	 */
	public int[][] createBox(int row, int col) {
		int[][] box = new int[3][3];
		for (int r = row; r < 3; r++) {
			for (int c = col; c < 3; c++) {
				box[r][c] = values[r + row][c + col];

			}
		}

		return box;
	}

	/*
	 * Check Block checks the 3x3 box to see if all the values in the box are
	 * adequate
	 */
	public boolean checkBlock() {
		for (int row = 0; row < values.length; row += 3) {
			for (int col = 0; col < values.length; col += 3) {
				int[][] check = createBox(row, col);
				if (!(checkRow(check) && checkCol(check))) {
					// checking the rows and column we can determine whether or
					// not the box is legal / true
					// if not then false
					return false;
				}
			}
		}
		return true;
	}

	/*
	 * Makes sure that the sudoku board is legal with no duplicates in rows
	 * columns and block
	 */
	public boolean isLegal() {

		if (checkRow(values) == false) {
			return false;
		}
		if (checkCol(values) == false) {
			return false;
		}
		if (checkBlock() == false) {
			return false;
		}

		return true;
	}

	/*
	 * Checks is the board is full
	 */
	public boolean full() {

		for (int x = 0; x < values.length; x++) {
			for (int y = 0; y < values.length; y++) {
				int num = values[x][y];
				if (num == 0) {
					// zero means that there is a missing values, if
					// values[x][y] is zero then the board is not full
					return false;
				}
			}
		}
		return true;
	}

	/*
	 * Checks if the board is full
	 */
	public boolean isFull() {
		// call the work done in full() method
		if (full() == false) {
			return false;
		}
		return true;
	}

	/*
	 * Will compare grids to check if the values in the grid match Returns true
	 * if x is a Grid and, for every (i,j)
	 */
	public boolean equals(Object x) {

		Grid g = (Grid) x;
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[0].length; j++) {
				if (this.values[i][j] != g.values[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

}
