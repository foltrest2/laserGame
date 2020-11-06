package model;

public class Matrix {

	private Node first;
	private int numRows;
	private int numCols;
	
	public Matrix(int m, int n) {
		numRows = m;
		numCols = n;
		createBoard();
	}
	/**
	 * Obtained of: https://github.com/seyerman/java-intermediate-course-examples/tree/master/linked-matrix-base
	 * By teacher Juan Reyes
	 * This method calls the methods to create the matrix
	 */
	private void createBoard() {
		first = new Node(0,0);
		makeRow(0,0, first);
	}
	/**
	 * * Obtained of: https://github.com/seyerman/java-intermediate-course-examples/tree/master/linked-matrix-base
	 * By teacher Juan Reyes
	 * This method create the row of the matrix and their relations
	 * 
	 * @param i is the current row to create
	 * @param j is the current column to create
	 * @param currentRow is the current node in the row
	 */
	private void makeRow(int i, int j, Node currentRow) {
		makeCol(i,j+1, currentRow, currentRow.getUp());
		if (i+1<numRows) {
			Node newRow = new Node(i+1,j);
			currentRow.setDown(newRow);
			newRow.setUp(currentRow);
			makeRow(i+1,j, newRow);
		}
	}
	/**
	* Obtained of: https://github.com/seyerman/java-intermediate-course-examples/tree/master/linked-matrix-base
	 * By teacher Juan Reyes 
	 * This method create the columns of the matrix and their relations
	 * 
	 * @param i is the current row to create
	 * @param j is the current column to create
	 * @param prev is the previous node
	 * @param rowPrev is the node of the previous row in the same column
	 */
	private void makeCol(int i, int j, Node prev, Node rowPrev) {
		if(j<numCols) {
			Node current = new Node(i,j);
			current.setLeft(prev);
			prev.setRight(current);
			if (rowPrev != null) {
				rowPrev = rowPrev.getRight();
				current.setUp(rowPrev);
				rowPrev.setDown(current);
			}
			makeCol(i, j+1, current, rowPrev);
		}
	}
	/**
	 * Obtained of: https://github.com/seyerman/java-intermediate-course-examples/tree/master/linked-matrix-base
	 * By teacher Juan Reyes 
	 *This method obtains the info of all the matrix
	 * @return the info
	 */
	public String toString() {
		String info;
		info = toStringRow(first);
		return info;
	}
	/**
	 * Obtained of: https://github.com/seyerman/java-intermediate-course-examples/tree/master/linked-matrix-base
	 * By teacher Juan Reyes 
	 *This method obtains the info of the nodes in the rows of the matrix 
	 * @param current is the current node
	 * @return the info
	 */
	private String toStringRow(Node firstRow) {
		String info = "";
		if (firstRow!=null) {
			info = toStringCol(firstRow) + "\n";
			info += toStringRow(firstRow.getDown());
		}
		return info;
	}
	/**
	 * Obtained of: https://github.com/seyerman/java-intermediate-course-examples/tree/master/linked-matrix-base
	 * By teacher Juan Reyes 
	 *This method obtains the info of the nodes in the columns of the matrix 
	 * @param current is the current node
	 * @return the info
	 */
	private String toStringCol(Node current) {
		String info = "";
		if (current != null) {
			info = current.toString();
			info += toStringCol(current.getRight());
		}
		return info;
	}
	public Node getFirst() {
		return first;
	}
	public void setFirst(Node first) {
		this.first = first;
	}
	public int getNumRows() {
		return numRows;
	}
	public int getNumCols() {
		return numCols;
	}
	
}
