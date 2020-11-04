package model;

public class Node {

	private String mirror;
	private String contain;
	private String discover;
	private String reference;
	private Node up;
	private Node down;
	private Node right;
	private Node left;
	private int row;
	private int col;
	
	public Node(int r, int c) {
		row = r;
		col = c;
		mirror = "";
		discover = "";
		contain = "[ ]";
		reference = (row+1) + "" +getNameCol() + "";
	}
	public String getMirror() {
		return mirror;
	}
	public void setMirror(String mirror) {
		this.mirror = mirror;
	}
	public String getContain() {
		return contain;
	}
	public void setContain(String contain) {
		this.contain = contain;
	}
	public String getDiscover() {
		return discover;
	}
	public void setDiscover(String discover) {
		this.discover = discover;
	}
	public String getReference() {
		return reference;
	}
	public Node getUp() {
		return up;
	}
	public void setUp(Node up) {
		this.up = up;
	}
	public Node getDown() {
		return down;
	}
	public void setDown(Node down) {
		this.down = down;
	}
	public Node getRight() {
		return right;
	}
	public void setRight(Node right) {
		this.right = right;
	}
	public Node getLeft() {
		return left;
	}
	public void setLeft(Node left) {
		this.left = left;
	}	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	/**
	 * This method obtain the name of the column
	 * @return the name of the column
	 */
	public char getNameCol() {
		return (char)('A'+col);
	}
	/**
	 * This method print the content of the node
	 */
	public String toString() {
		return contain;
	}
	
}
