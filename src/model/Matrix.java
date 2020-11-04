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
			if(lookAt == null) {
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
		if(current != null) {
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
	public String shoot(String reference) {
		int x = reference.length();
		String row, col, letter, ref;
		if (Character.isLetter(reference.charAt(x-2))) {
			row = reference.substring(0, x-2);
			col = reference.substring(x-2, x);
			letter = reference.substring(x-2, x-1);
			ref = row + letter;
		}
		else {
			row = reference.substring(0, x-1);
			col = reference.substring(x-1, x);
			ref = row + col;
		}
		int m = Integer.parseInt(row);
		char n = col.charAt(0); 
		Node searched = searchNodeWithReference(ref, first);
		if (searched == null) {
			return "Invalid reference!";
		}
		else {
			if(n==65) {
				searched = searchNodeWithReference(ref, first);
				searched.setContain("[S]");
				if(m == 1) {
					char d = col.charAt(1);
					if (d == 'H') 
						laserGoRight(searched);
					else
						laserGoDown(searched);
				}
				else if (m == numRows) {
					char d = col.charAt(1);
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
					char d = col.charAt(1);
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
					char d = col.charAt(1);
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
		return "";
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
				else {
					current.setContain("[E]");
					return;
				}
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
				else
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
	/**
	 * This method reset the contain of the matrix
	 * @param m is the current row
	 * @param n is the current column
	 */
	public void resetContain(int m, int n) {
		Node reset = searchNode(m, n, first);
		if (reset != null) {
			//			if (reset.getContain().equals("[S]") || reset.getContain().equals("[E]") || reset.getContain().equals("[X]")) {
			reset.setContain("[ ]");
			//			}
			resetContainCols(m, n+1);
			resetContain(m+1, n);
		}
	}
	/**
	 * This method reset the contain of the columns of the matrix
	 * @param m is the current row
	 * @param n is the current column
	 */
	private void resetContainCols(int m, int n) {
		Node reset = searchNode(m, n, first);
		if (reset != null) {
			reset.setContain("[ ]");
			resetContainCols(m, n+1);
		}
	}
	/**
	 * This method put again the mirrors discovered by the player in the matrix
	 * @param m is the current row
	 * @param n is the current column
	 */
	public void putMirrorAgain(int m, int n) {
		Node reset = searchNode(m, n, first);
		if (reset != null) {
			if (!(reset.getDiscover().equals(""))) {
				reset.setContain(reset.getDiscover());
			}
			putMirrorAgainCols(m, n+1);
			putMirrorAgain(m+1, n);
		}
	}
	/**
	 * This method put again the mirrors discovered by the player in the columns of the matrix
	 * @param m is the current row
	 * @param n is the current column
	 */
	private void putMirrorAgainCols(int m, int n) {
		Node reset = searchNode(m, n, first);
		if (reset != null) {
			if (!reset.getDiscover().equals("")) {
				reset.setContain(reset.getDiscover());
			}
			putMirrorAgainCols(m, n+1);
		}
	}
	/**
	 * This method put all the mirrors in the matrix
	 * @param m is the current row
	 * @param n is the current column
	 */
	public void cheatMode(int m, int n) {
		Node reset = searchNode(m, n, first);
		if (reset != null) {
			if (!(reset.getMirror().equals(""))) {
				reset.setContain("[" + reset.getMirror() + "]");
			}
			else
				reset.setContain("[ ]");
			cheatModeCols(m, n+1);
			cheatMode(m+1, n);
		}
	}
	/**
	 * This method put the mirrors in the columns of the matrix
	 * @param m is the current row
	 * @param n is the current column
	 */
	private void cheatModeCols(int m, int n) {
		Node reset = searchNode(m, n, first);
		if (reset != null) {
			if (!(reset.getMirror().equals(""))) {
				reset.setContain("[" + reset.getMirror() + "]");
			}
			else
				reset.setContain("[ ]");
			cheatModeCols(m, n+1);
		}
	}
	/**
	 * This method locate a mirror in the matrix
	 * @param location is a String with the info of the reference and inclination of the mirror
	 */
	public int locateMirror(String location, int k) {
		int mirrors = k;
		int x = location.length();
		String row, col, letter, ref;
		if (Character.isLetter(location.charAt(x-2))) {
			row = location.substring(0, x-2);
			col = location.substring(x-2, x);
			letter = location.substring(x-2, x-1);
			ref = row + letter;
		}
		else {
			row = location.substring(0, x-1);
			col = location.substring(x-1, x);
			ref = row + col;
		}
		char inclination = col.charAt(1);
		Node searched = searchNodeWithReference(ref, first);
		if (searched.getMirror().equals("")) {
			searched.setContain("[X]");
		}
		else if (searched.getMirror().equals("/") && inclination == 'R') {
			searched.setDiscover("[" + searched.getMirror() + "]");
			mirrors -= 1;
		}
		else if (searched.getMirror().equals("/") && inclination == 'L') 
			searched.setContain("[X]");
		else if (searched.getMirror().equals("\\") && inclination == 'L') {
			searched.setDiscover("[" + searched.getMirror() + "]");
			mirrors -= 1;
		}
		else
			searched.setContain("[X]");
		return mirrors;
	}
	private String printInOrder(Player ply, String infoScores) {
        if (ply == null) 
            return infoScores; 

        /* first recur on left child /
        printInOrder(ply.getLeft(), infoScores);

        / then print the data of node /
        infoScores += "Nickname: "+ply.getNickName()+"\nScore: "+ply.getScore()+"\n"; 

        / now recur on right child */
        return printInOrder(ply.getSonR(), infoScores); 

    }
	public void addPlayer(String nickName, long score) {
        Player toAdd = new Player(nickName, score);
        if(root == null) {
            root = toAdd;
        } else {
            addPlayer(root, toAdd);
        }
    }
	/**
	 * This method 
	 * @param currentPlayer
	 * @param newPlayer
	 */
	private void addPlayer(Player currentPlayer, Player newPlayer) {
		if (newPlayer.getScore() <= currentPlayer.getScore() && currentPlayer.getSonL() == null) {
			currentPlayer.setSonL(newPlayer);
		} else if (newPlayer.getScore() >= currentPlayer.getScore() && currentPlayer.getSonR() == null) {
			currentPlayer.setSonR(newPlayer);
		} else {
			if(newPlayer.getScore() <= currentPlayer.getScore() && currentPlayer.getSonL() != null) {
				addPlayer(currentPlayer.getSonL(), newPlayer);
				return;
			} 
			else {
				addPlayer(currentPlayer.getSonR(), newPlayer);
				return;
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
