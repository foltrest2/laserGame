package ui;

import model.Matrix;

public class Main {
	
	public static void main(String[] args) {
		Matrix m = new Matrix(5, 5);
		m.createRandomMirror(8);
		System.out.println(m);
		m.shoot("5EH");
		System.out.println(m);
	}

}
