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

	public Node searchNode(int m, int n, Node current) {
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
	public Node lookAtTheCols(int m, int n, Node current) {
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

	public Node searchNodeWithReference(String reference, Node current) {
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
	public Node lookAtTheColsForTheReference(String reference, Node current) {
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
			node.setContain("[" + node.getMirror() + "]");
		}
		if(random == 1) {
			node.setMirror("\\");
			node.setContain("[" + node.getMirror() + "]");
		}
	}

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
					bulletGoRight(searched);
				else
					bulletGoDown(searched);
			}
			else if (m == numRows) {
				char d = reference.charAt(2);
				if (d == 'H') 
					bulletGoRight(searched);
				else
					bulletGoUp(searched);
			}
			else
				bulletGoRight(searched);	
		}
		else if(m == 1) {
			searched = searchNodeWithReference(ref, first);
			searched.setContain("[S]");
			if (n-(numCols-1) == 65) {
				char d = reference.charAt(2);
				if (d == 'H') 
					bulletGoLeft(searched);
				else
					bulletGoDown(searched);
			}
			else
				bulletGoDown(searched);

		}
		else if(m == numRows) {
			searched = searchNodeWithReference(ref, first);
			searched.setContain("[S]");
			if(n-(numCols-1) == 65) {
				char d = reference.charAt(2);
				if (d == 'H') 
					bulletGoLeft(searched);
				else
					bulletGoUp(searched);
			}
			else
				bulletGoUp(searched);
		}
		else if(n-(numCols-1) == 65) {
			searched = searchNodeWithReference(ref, first);
			searched.setContain("[S]");
			bulletGoLeft(searched);
		}
	}

	public void bulletGoDown(Node start) {
		if(start != null) {
			if(start.getMirror().equals("")) {
				if(start.getDown() != null) {
					start = start.getDown();
					bulletGoDown(start);
				}
				else
					start.setContain("[E]");
			}
			else if(start.getMirror().equals("/")) {
				if (start.getLeft() != null) {
					start = start.getLeft();
					bulletGoLeft(start);
				}
				else
					start.setContain("[E]");
			}
			else {
				if (start.getRight() != null) {
					start = start.getRight();
					bulletGoRight(start);
				}
				else
					start.setContain("[E]");
			}
		}	
	}
	private void bulletGoRight(Node start) {
		if(start != null) {
			if(start.getMirror().equals("")) {
				if (start.getRight() != null) {
					start = start.getRight();
					bulletGoRight(start);
				}
				else
					start.setContain("[E]");
			}
			else if(start.getMirror().equals("/")) {
				if (start.getUp() != null) {
					start = start.getUp();
					bulletGoUp(start);
				}
				else
					start.setContain("[E]");
			}
			else {
				if (start.getDown() != null) {
					start = start.getDown();
					bulletGoDown(start);
				}
				start.setContain("[E]");
			}
		}
	}
	private void bulletGoUp(Node start) {
		if(start != null) {
			if(start.getMirror().equals("")) {
				if (start.getUp() != null) {
					start = start.getUp();
					bulletGoUp(start);
				}
				else
					start.setContain("[E]");
			}
			else if(start.getMirror().equals("/")) {
				if (start.getRight() != null) {
					start = start.getRight();
					bulletGoRight(start);
				}
				else
					start.setContain("[E]");
			}
			else {
				if (start.getLeft() != null) {
					start = start.getLeft();
					bulletGoLeft(start);
				}
				else
					start.setContain("[E]");
			}
		}
	}
	private void bulletGoLeft(Node start) {
		if(start != null) {
			if(start.getMirror().equals("")) {
				if (start.getLeft() != null) {
					start = start.getLeft();
					bulletGoLeft(start);
				}
				else
					start.setContain("[E]");
			}
			else if(start.getMirror().equals("/")) {
				if (start.getDown() != null) {
					start = start.getDown();
					bulletGoDown(start);
				}
				else
					start.setContain("[E]");
			}
			else {
				if (start.getUp() != null) {
					start = start.getUp();
					bulletGoUp(start);
				}
				else
					start.setContain("[E]");
			}
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
