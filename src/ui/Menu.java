package ui;

import java.util.Scanner;

import model.Matrix;

public class Menu {

	static Scanner lector;
	private Matrix m;
	public final static int EXIT_MENU = 3;

	public Menu() {
		lector = new Scanner(System.in);
	}
	
	private void play() {
		System.out.println("Hello!");
		System.out.println("Before start playing, give me your nickname and three numbers (n, m and k)\n");
		System.out.println("n for the number of columns of your table");
		System.out.println("m for the number of rows of your table");
		System.out.println("k for the number of mirrors in your table\n");
		System.out.println("Write this with a space between them please :3");
		String start = lector.nextLine();
		String[] info = start.split(" ");
		m = new Matrix(Integer.parseInt(info[1]), Integer.parseInt(info[2]));
		m.createRandomMirror(Integer.parseInt(info[3]));
		
	}

	private void executeOperation(int option){
		switch (option) {
		case 1:
			play();
			break;
		case 2:

			break;
		case 3:
			System.out.println("Thanks for playing!");
			System.out.println("See you later <3");
			break;
		default:
			break;
		}
	}
	
	private String getMenu() {
		String menu = "";
		menu += "               ******               \n";
		menu += "          ****************          \n";
		menu += "     **************************     \n";
		menu += "************ Laser Game ************\n";
		menu += "     **************************     \n";
		menu += "          ****************          \n";
		menu += "               ******               \n";
		menu += "\n";
		menu += "Choose one my dear player!\n";
		menu += "1. Play!\n";
		menu += "2. Show positions table (I want to see you in the top!)\n";
		menu += "3. Play later (Exit, you know)\n";
		return menu;
	}

	private int readOption() {
		int choice = Integer.parseInt(lector.nextLine());
		System.out.println();
		return choice;
	}
	
	public void recursiveMenu() {
		System.out.println(getMenu());
		int option = readOption();
		if(option != EXIT_MENU) {
			executeOperation(option);
			recursiveMenu();
		}
		else {
			executeOperation(option);
			lector.close();
		}
	}

}
