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
	private void createBoard() {
		first = new Node(0,0);
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
		if(k > 0) {
			int m = (int) (Math.random() * numRows); 
			int n = (int) (Math.random() * numCols);
			Node choosen = searchNode(m, n, first);
			if(choosen.getMirror().equals("")) {
				assignMirror(choosen);
				createRandomMirror(k-1);
			}
			else
				createRandomMirror(k);
		}
	}
	/**
	 * This method search a node in the rows of the matrix and calls the method who's
	 * look at the columns
	 * @param m is the random number of the row searched
	 * @param n is the random number of the column searched
	 * @param current is the current node
	 * @return the searched node
	 */
	private Node searchNode(int m, int n, Node current) {
		Node lookAt = null;
		if(current!=null) {
			if(current.getRow() == m && current.getCol() == n) {
				return current;
			}else {
				lookAt = lookAtTheCols(m,n, current.getRight());
			}
			if(lookAt==null) {
				lookAt = searchNode(m,n, current.getDown());
			}
		}
		return lookAt; 
	}
	/**
	 * This method search a node in the columns of the matrix
	 * @param m is the random number of the row searched
	 * @param n is the random number of the column searched
	 * @param current is the current node
	 * @return the searched node
	 */
	private Node lookAtTheCols(int m, int n, Node current) {
		Node lookAt = null;
		if(current!=null) {
			if(current.getRow() == m && current.getCol() == n) {
				return current;
			}else {
				lookAt = lookAtTheCols(m, n, current.getRight());
			}
		}
		return lookAt;
	}
	/**
	 * This method search a node by his reference in the rows of the matrix and calls the method who's
	 * look at the columns
	 * @param reference is the reference searched
	 * @param current is the current node
	 * @return the searched node
	 */
	private Node searchNodeWithReference(String reference, Node current) {
		Node lookAt = null;
		if(current!=null) {
			if(current.getReference().equals(reference)) {
				return current;
			}else {
				lookAt = lookAtTheColsForTheReference(reference, current.getRight());
			}
			if(lookAt==null) {
				lookAt = searchNodeWithReference(reference, current.getDown());
			}
		}
		return lookAt; 
	}
	/**
	 * This method search a node by his reference in the columns
	 * @param reference is the reference searched
	 * @param current is the current node
	 * @return the searched node
	 */
	private Node lookAtTheColsForTheReference(String reference, Node current) {
		Node lookAt = null;
		if(current!=null) {
			if(current.getReference().equals(reference)) {
				return current;
			}else {
				lookAt = lookAtTheColsForTheReference(reference, current.getRight());
			}
		}
		return lookAt;
	}
	/**
	 * This method decides the inclination of the mirror randomly
	 * @param node is the node to assign the mirror
	 */
	private void assignMirror(Node node) {
		int random = (int)(Math.random()*2);
		if(random == 0) {
			node.setMirror("/");
		}
		if(random == 1) {
			node.setMirror("\\");
		}
	}
	/**
	 * This method addresses the laser in the matrix
	 * @param reference is the start node
	 */
	public void shoot(String reference) {
		int m = Integer.parseInt(Character.toString(reference.charAt(0)));
		char n = reference.charAt(1); 
		String ref = Character.toString(reference.charAt(0)) + Character.toString(reference.charAt(1));
		Node searched;
		if(n==65) {
			searched = searchNodeWithReference(ref, first);
			searched.setContain("[S]");
			if(m == 1) {
				char d = reference.charAt(2);
				if (d == 'H') 
					laserGoRight(searched);
				else
					laserGoDown(searched);
			}
			else if (m == numRows) {
				char d = reference.charAt(2);
				if (d == 'H') 
					laserGoRight(searched);
				else
					laserGoUp(searched);
			}
			else
				laserGoRight(searched);	
		}
		else if(m == 1) {
			searched = searchNodeWithReference(ref, first);
			searched.setContain("[S]");
			if (n-(numCols-1) == 65) {
				char d = reference.charAt(2);
				if (d == 'H') 
					laserGoLeft(searched);
				else
					laserGoDown(searched);
			}
			else
				laserGoDown(searched);

		}
		else if(m == numRows) {
			searched = searchNodeWithReference(ref, first);
			searched.setContain("[S]");
			if(n-(numCols-1) == 65) {
				char d = reference.charAt(2);
				if (d == 'H') 
					laserGoLeft(searched);
				else
					laserGoUp(searched);
			}
			else
				laserGoUp(searched);
		}
		else if(n-(numCols-1) == 65) {
			searched = searchNodeWithReference(ref, first);
			searched.setContain("[S]");
			laserGoLeft(searched);
		}
	}
	/**
	 * This method addresses the laser below
	 * @param current is the current position of the laser
	 */
	private void laserGoDown(Node current) {
		if(current != null) {
			if(current.getMirror().equals("")) {
				if(current.getDown() != null) {
					current = current.getDown();
					laserGoDown(current);
				}
				else
					current.setContain("[E]");
			}
			else if(current.getMirror().equals("/")) {
				if (current.getLeft() != null) {
					current = current.getLeft();
					laserGoLeft(current);
				}
				else
					current.setContain("[E]");
			}
			else {
				if (current.getRight() != null) {
					current = current.getRight();
					laserGoRight(current);
				}
				else
					current.setContain("[E]");
			}
		}	
	}
	/**
	 * This method addresses the laser to the right
	 * @param current is the current position of the laser
	 */
	private void laserGoRight(Node current) {
		if(current != null) {
			if(current.getMirror().equals("")) {
				if (current.getRight() != null) {
					current = current.getRight();
					laserGoRight(current);
				}
				else
					current.setContain("[E]");
			}
			else if(current.getMirror().equals("/")) {
				if (current.getUp() != null) {
					current = current.getUp();
					laserGoUp(current);
				}
				else
					current.setContain("[E]");
			}
			else {
				if (current.getDown() != null) {
					current = current.getDown();
					laserGoDown(current);
				}
				current.setContain("[E]");
			}
		}
	}
	/**
	 * This method addresses the laser above
	 * @param current is the current position of the laser
	 */
	private void laserGoUp(Node current) {
		if(current != null) {
			if(current.getMirror().equals("")) {
				if (current.getUp() != null) {
					current = current.getUp();
					laserGoUp(current);
				}
				else
					current.setContain("[E]");
			}
			else if(current.getMirror().equals("/")) {
				if (current.getRight() != null) {
					current = current.getRight();
					laserGoRight(current);
				}
				else
					current.setContain("[E]");
			}
			else {
				if (current.getLeft() != null) {
					current = current.getLeft();
					laserGoLeft(current);
				}
				else
					current.setContain("[E]");
			}
		}
	}
	/**
	 * This method addresses the laser to the left
	 * @param current is the current position of the laser
	 */
	private void laserGoLeft(Node current) {
		if(current != null) {
			if(current.getMirror().equals("")) {
				if (current.getLeft() != null) {
					current = current.getLeft();
					laserGoLeft(current);
				}
				else
					current.setContain("[E]");
			}
			else if(current.getMirror().equals("/")) {
				if (current.getDown() != null) {
					current = current.getDown();
					laserGoDown(current);
				}
				else
					current.setContain("[E]");
			}
			else {
				if (current.getUp() != null) {
					current = current.getUp();
					laserGoUp(current);
				}
				else
					current.setContain("[E]");
			}
		}
	}

	public void resetContainRows(int m, int n) {
		Node reset = searchNode(m, n, first);
		if (reset != null) {
			reset.setContain("[ ]");
			resetContainCols(m, n, reset);
			reset = searchNode(m+1, n, reset.getDown());
		}
	}
	
	public void resetContainCols(int m, int n, Node current) {
		if (current != null) {
			current.setContain("[ ]");
			current = searchNode(m, n+1, current);
			resetContainCols(m, n, current.getRight());
		}
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
