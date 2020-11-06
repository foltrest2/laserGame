package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GeneralController {

	private final static String SERIALIZE = "data/player's_scores.xd";
	
	private Matrix matrix;
	private Player head;
	private String printTree;

	/**
	 * This method is the constructor of Matrix
	 * @param m	Is the number of rows
	 * @param n is the number of columns
	 */
	public GeneralController() {
		matrix = null;
		head = null;
		printTree = "";
	}
	
	/**
	 * This method receives the quantity of mirrors to create and generate the random
	 * coordinate to put it
	 * @param k is the number of mirrors
	 */
	public void createRandomMirror(int k) {
		if(k > 0) {
			int m = (int) (Math.random() * matrix.getNumRows()); 
			int n = (int) (Math.random() * matrix.getNumCols());
			Node choosen = searchNode(m, n, matrix.getFirst());
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
		String row, colAndDir, col, ref;
		if (Character.isLetter(reference.charAt(x-2))) {
			row = reference.substring(0, x-2);
			colAndDir = reference.substring(x-2, x);
			col = reference.substring(x-2, x-1);
			ref = row + col;
		}
		else {
			row = reference.substring(0, x-1);
			colAndDir = reference.substring(x-1, x);
			ref = row + colAndDir;
		}
		int m = Integer.parseInt(row);
		char n = colAndDir.charAt(0); 
		Node searched = searchNodeWithReference(ref, matrix.getFirst());
		if (searched == null) {
			return "Invalid reference!";
		}
		else {
			if(n==65) {
				searched = searchNodeWithReference(ref, matrix.getFirst());
				searched.setContain("[S]");
				if(m == 1) {
					char d = colAndDir.charAt(1);
					if (d == 'H') 
						laserGoRight(searched);
					else
						laserGoDown(searched);
				}
				else if (m == matrix.getNumRows()) {
					char d = colAndDir.charAt(1);
					if (d == 'H') 
						laserGoRight(searched);
					else
						laserGoUp(searched);
				}
				else
					laserGoRight(searched);	
			}
			else if(m == 1) {
				searched = searchNodeWithReference(ref, matrix.getFirst());
				searched.setContain("[S]");
				if (n-(matrix.getNumCols()-1) == 65) {
					char d = colAndDir.charAt(1);
					if (d == 'H') 
						laserGoLeft(searched);
					else
						laserGoDown(searched);
				}
				else
					laserGoDown(searched);

			}
			else if(m == matrix.getNumRows()) {
				searched = searchNodeWithReference(ref, matrix.getFirst());
				searched.setContain("[S]");
				if(n-(matrix.getNumCols()-1) == 65) {
					char d = colAndDir.charAt(1);
					if (d == 'H') 
						laserGoLeft(searched);
					else
						laserGoUp(searched);
				}
				else
					laserGoUp(searched);
			}
			else if(n-(matrix.getNumCols()-1) == 65) {
				searched = searchNodeWithReference(ref, matrix.getFirst());
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
				else {
					if (current.getContain().equals("[S]")) {
						current.setContain("[R]");
					}
					else
						current.setContain("[E]");
				}
			}
			else if(current.getMirror().equals("/")) {
				if (current.getLeft() != null) {
					current = current.getLeft();
					laserGoLeft(current);
				}
				else {
					if (current.getContain().equals("[S]")) {
						current.setContain("[R]");
					}
					else
						current.setContain("[E]");
				}
			}
			else {
				if (current.getRight() != null) {
					current = current.getRight();
					laserGoRight(current);
				}
				else {
					if (current.getContain().equals("[S]")) {
						current.setContain("[R]");
					}
					else
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
				else {
					if (current.getContain().equals("[S]")) {
						current.setContain("[R]");
					}
					else
						current.setContain("[E]");
				}
			}
			else if(current.getMirror().equals("/")) {
				if (current.getUp() != null) {
					current = current.getUp();
					laserGoUp(current);
				}
				else {
					if (current.getContain().equals("[S]")) {
						current.setContain("[R]");
					}
					else
						current.setContain("[E]");
				}
			}
			else {
				if (current.getDown() != null) {
					current = current.getDown();
					laserGoDown(current);
				}
				else {
					if (current.getContain().equals("[S]")) {
						current.setContain("[R]");
					}
					else
						current.setContain("[E]");
				}
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
				else {
					if (current.getContain().equals("[S]")) {
						current.setContain("[R]");
					}
					else
						current.setContain("[E]");
				}
			}
			else if(current.getMirror().equals("/")) {
				if (current.getRight() != null) {
					current = current.getRight();
					laserGoRight(current);
				}
				else {
					if (current.getContain().equals("[S]")) {
						current.setContain("[R]");
					}
					else
						current.setContain("[E]");
				}
			}
			else {
				if (current.getLeft() != null) {
					current = current.getLeft();
					laserGoLeft(current);
				}
				else {
					if (current.getContain().equals("[S]")) {
						current.setContain("[R]");
					}
					else
						current.setContain("[E]");
				}
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
				else {
					if (current.getContain().equals("[S]")) {
						current.setContain("[R]");
					}
					else
						current.setContain("[E]");
				}
			}
			else if(current.getMirror().equals("/")) {
				if (current.getDown() != null) {
					current = current.getDown();
					laserGoDown(current);
				}
				else {
					if (current.getContain().equals("[S]")) {
						current.setContain("[R]");
					}
					else
						current.setContain("[E]");
				}
			}
			else {
				if (current.getUp() != null) {
					current = current.getUp();
					laserGoUp(current);
				}
				else {
					if (current.getContain().equals("[S]")) {
						current.setContain("[R]");
					}
					else
						current.setContain("[E]");
				}
			}
		}
	}
	/**
	 * This method reset the contain of the matrix
	 * @param m is the current row
	 * @param n is the current column
	 */
	public void resetContain(int m, int n) {
		Node reset = searchNode(m, n, matrix.getFirst());
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
		Node reset = searchNode(m, n, matrix.getFirst());
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
		Node reset = searchNode(m, n, matrix.getFirst());
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
		Node reset = searchNode(m, n, matrix.getFirst());
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
		Node reset = searchNode(m, n, matrix.getFirst());
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
		Node reset = searchNode(m, n, matrix.getFirst());
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
		String inclination, ref;
		ref = location.substring(1, x-1);
		inclination = location.substring(x-1, x);
		Node searched = searchNodeWithReference(ref, matrix.getFirst());
		if (searched.getMirror().equals("")) {
			searched.setContain("[X]");
		}
		else if (searched.getMirror().equals("/") && inclination.equals("R")) {
			searched.setDiscover("[" + searched.getMirror() + "]");
			mirrors -= 1;
		}
		else if (searched.getMirror().equals("/") && inclination.equals("L")) 
			searched.setContain("[R]");
		else if (searched.getMirror().equals("\\") && inclination.equals("L")) {
			searched.setDiscover("[" + searched.getMirror() + "]");
			mirrors -= 1;
		}
		else
			searched.setContain("[L]");
		return mirrors;
	}
	/**
	 * This method increases the score given in one
	 * @param current is the current score
	 * @return the new score
	 */
	public int score(int current) {
		current += 1;
		return current;
	}
	/**
	 * This method print the binary tree
	 * @return the binary tree
	 */
	public String displayTree() {
		if(head != null) {
			printTree = "";
			displayTree(head, 1);
		}
		return printTree;
	}
	/**
	 * This method go to the binary tree and get's the info 
	 * @param p is the player
	 * @param info is the info obtained
	 * @return the info of the tree
	 */
	private void displayTree(Player ply, int position) {
		if (ply != null) {
			displayTree(ply.getSonR(), position+1);
			printTree += position+ ". " + ply.getNickName() + " with "+ply.getScore()+" points!\n"; 
			displayTree(ply.getSonL(), position+1); 
		}
	}
	/**
	 * This method create the player to add to the binary tree
	 * @param n is the nick name of the player
	 * @param s is his score
	 */
	public void addPlayer(String nickName, int score) {
        Player toAdd = new Player(nickName, score);
        if(head == null) {
            head = toAdd;
        } else {
            addPlayer(head, toAdd);
        }
    }
	/**
	 * This method add a player to the binary tree
	 * @param current is the current player
	 * @param newP is the new player
	 */
	private void addPlayer(Player currentPlayer, Player newPlayer) {
        if (newPlayer.getScore() < currentPlayer.getScore() && currentPlayer.getSonL() == null) {
            currentPlayer.setSonL(newPlayer);
        } else if (newPlayer.getScore() > currentPlayer.getScore() && currentPlayer.getSonR() == null) {
            currentPlayer.setSonR(newPlayer);
        } else {
            if(newPlayer.getScore() < currentPlayer.getScore() && currentPlayer.getSonL() != null) {
                addPlayer(currentPlayer.getSonL(), newPlayer);
            } else {
                addPlayer(currentPlayer.getSonR(), newPlayer);
            }
        }
    }
	public Matrix getMatrix() {
		return matrix;
	}
	/**
	 * This method creates a new matrix
	 * @param m are the rows
	 * @param n are the columns
	 */
	public void addMatrix(int m, int n) {
        matrix = new Matrix(m, n);
    }
	/**
	 * This method serialize the scores of the players
	 * @throws IOException when something failed
	 */
	public void saveScore() throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SERIALIZE));
		oos.writeObject(head);
		oos.close();
	}
	/**
	 * This method deserialize the scores of the players
	 * @throws IOException when something failed
	 * @throws ClassNotFoundException when the class isn't found
	 */
	public void loadScore() throws IOException, ClassNotFoundException{	
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SERIALIZE));
		head = (Player)ois.readObject();
		ois.close();
	}
}
