package model;

public class Matrix {

	private Node first;
	private int numRows;
	private int numCols;

	/**
	 * This method is the constructor of Matrix
	 * @param m	Is the number of rows
	 * @param n is the number of columns
	 */
	public Matrix(int m, int n) {
		numRows = m;
		numCols = n;
		createBoard();
	}
	/**
	 * This method calls the methods to create the matrix
	 */
	public void createBoard() {
		//		System.out.println("Creemos esta mierda");
		first = new Node(0,0);
		//		System.out.println("Se crea el first");
		makeRow(0,0, first);
	}
	/**
	 * This method create the row of the matrix and their relations
	 * 
	 * @param i is the current row to create
	 * @param j is the current column to create
	 * @param currentRow is the current node in the row
	 */
	private void makeRow(int i, int j, Node currentRow) {
		//		System.out.println("En makeRow con la fila " + i);
		makeCol(i,j+1, currentRow, currentRow.getUp());
		if (i+1<numRows) {
			Node newRow = new Node(i+1,j);
			currentRow.setDown(newRow);
			newRow.setUp(currentRow);
			makeRow(i+1,j, newRow);
		}
	}
	/**
	 * This method create the columns of the matrix and their relations
	 * 
	 * @param i is the current row to create
	 * @param j is the current column to create
	 * @param prev is the previous node
	 * @param rowPrev is the node of the previous row in the same column
	 */
	private void makeCol(int i, int j, Node prev, Node rowPrev) {
		//		System.out.println("	En makeCol con la columna " + j);
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
	 * This method receives the quantity of mirrors to create and generate the random
	 * coordinate to put it
	 * @param k is the number of mirrors
	 */
	public void createRandomMirror(int k) {
		if(k > 0 && !(k > (numRows*numCols))) {
			Node current = first;
			int m = (int) (Math.random() * numRows); 
			int n = (int) (Math.random() * numCols);
			boolean haveMirror = false;
			boolean created = createRandomMirror(m, n, current, haveMirror);
			if (created == false) {
				createRandomMirror(k-1);
			}
			else
				createRandomMirror(k);
		}
	}
	/**
	 * This method checks if some node in the rows have a mirror, and if it hasn't and 
	 * is the node with the random location, choose it to assign the mirror  
	 * @param m is the random coordinate of the row
	 * @param n is the random coordinate of the col
	 * @param current is the current node
	 * @param haveMirror is a boolean verifying if the node have a mirror
	 * @return a boolean saying if the searched node have a mirror
	 */
	private boolean createRandomMirror(int m, int n, Node current, boolean haveMirror) {
		if(current != null) {
			if(current.getRight()!=null)
				haveMirror = lookAtTheCols(m, n, current.getRight(), haveMirror);
			if(current.getRow() == m && current.getCol() == n) {
				if(current.getContain().equals("[ ]")) {
					assignMirror(current);
				}
				else
					haveMirror = true;
			}
			current = current.getDown();
			return createRandomMirror(m, n, current, haveMirror);
		}
		return haveMirror;
	}
	/**
	 * This method checks if some node in the columns have a mirror, and if it hasn't and 
	 * is the node with the random location, choose it to assign the mirror  
	 * @param m is the random coordinate of the row
	 * @param n is the random coordinate of the col
	 * @param current is the current node
	 * @param haveMirror is a boolean verifying if the node have a mirror
	 * @return a boolean saying if the searched node have a mirror
	 */
	private boolean lookAtTheCols(int m, int n, Node current, boolean haveMirror) {
		if(current != null) {
			if(current.getRow() == m && current.getCol() == n) {
				if(current.getContain().equals("[ ]")) {
					assignMirror(current);
				}
				else
					haveMirror = true;
			}
			current = current.getRight();
			return lookAtTheCols(m, n, current, haveMirror);
		}
		return haveMirror;
	}
	/**
	 * This method decides the inclination of the mirror aleatory
	 * @param current is the node to assign the mirror
	 */
	private void assignMirror(Node current) {
		if((int)(Math.random()*100+1) % 2 == 0)
			current.setContain("[/]");
		if((int)(Math.random()*100+1) % 2 != 0)
			current.setContain("[\\]");
	}
	/**
	 *This method obtains the info of all the matrix
	 * @return the info
	 */
	public String toString() {
		String info;
		info = toStringRow(first);
		return info;
	}
	/**
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

}
