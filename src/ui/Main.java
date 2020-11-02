package ui;

import model.Matrix;

public class Main {
	
	public static void main(String[] args) {
		Matrix m = new Matrix(3, 3);
		m.createRandomMirror(9);
		System.out.println(m);
	}

}
