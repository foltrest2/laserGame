package ui;

import java.io.IOException;
import java.util.Scanner;

import model.GeneralController;

public class Menu {

	static Scanner lector;
	private GeneralController gc = new GeneralController();
	public final static int EXIT_MENU = 3;

	/**
	 * This method is the constructor of Menu
	 */
	public Menu() {
		lector = new Scanner(System.in);
		instructions();
		try {
			gc.loadScore();
		} catch (ClassNotFoundException e) {
			System.err.println("Data can't be loaded");
		} catch (IOException e) {
			System.err.println("Data can't be loaded");
		}
	}
	private void instructions() {
		System.out.println("Hello! There's some instructions\n");
		System.out.println("Before start playing, when the instructions ends give me your");
		System.out.println("nickname and three numbers (n, m and k)\n");
		System.out.println("n for the number of columns of your table");
		System.out.println("m for the number of rows of your table");
		System.out.println("k for the number of mirrors in your table\n");
		System.out.println("Write this with a space between them please :3\n");
		System.out.println("For shooting and locating references are:\n");
		System.out.println("1 to n for the rows");
		System.out.println("A to Z for the columns\n");
		System.out.println("If you choose a corner of the table, you need to specify the direction");
		System.out.println("with 'H' to shoot horizontally or 'V' to shoot vertically\n");
		System.out.println("To locate a mirror, write like this:\n");
		System.out.println("L1AR\n");
		System.out.println("L for 'locate'");
		System.out.println("1A is the reference");
		System.out.println("R is the inclination of the mirror, the inclination can be:\n");
		System.out.println("R if the mirror is inclinated to the right");
		System.out.println("L if the mirror is inclinated to the left\n");
		System.out.println("Type 'cheat mode' to activate a cheat. This cheat let you see all the mirrors\n");
		System.out.println("Instrucions ends\n");
	}
	/**
	 * This method display the instructions of the game
	 */
	private void initialize() {
		String start = lector.nextLine();
		String[] info = start.split(" ");
		System.out.println();
		if (Integer.parseInt(info[1]) > 26) {
			System.out.println("As maxim, the table can have 26 columns\n");
			initialize();
		}
		else {
			gc.addMatrix(Integer.parseInt(info[2]), Integer.parseInt(info[1]));
			gc.createRandomMirror(Integer.parseInt(info[3]));
			System.out.println(gc.getMatrix());
			int score = play(info[0], Integer.parseInt(info[3]), 0);
			gc.addPlayer(info[0], score);
			try {
				gc.saveScore();
			} catch (IOException e) {
				System.err.println("Data can't be saved");
			}
		}
	}
	/**
	 * This method execute the game
	 * @param nickName is the nick name of the player
	 * @param k is the number of mirrors remaining
	 */
	private int play(String nickName, int k, int score) {
		if(k > 0) {
			System.out.println("You will shoot or you know where is the mirror?\n");
			String reference = lector.nextLine();
			if (reference.isEmpty()) {
				return play(nickName, k, score);
			}
			else {
				if (!reference.equalsIgnoreCase("menu")) {
					if (reference.charAt(0) == 'L') {
						if (reference.charAt(reference.length()-1) != 'L' && reference.charAt(reference.length()-1) != 'R') {
							System.out.println("The direction is with R or L!\n");
							return play(nickName, k, score);
						}
						else {
							int mirrors = gc.locateMirror(reference, k);
							gc.putMirrorAgain(0, 0);
							System.out.println(gc.getMatrix());
							gc.resetContain(0, 0);
							gc.putMirrorAgain(0, 0);
							System.out.println(nickName + ": " + mirrors + " mirrors remaining\n");
							if (mirrors != k) {
								score = gc.score(score);
							}
							return play(nickName, mirrors, score);
						}
					}
					else if(Character.isDigit(reference.charAt(0))){
						if (Character.isLetter(reference.charAt(reference.length()-1)) || Character.isLetter(reference.charAt(reference.length()-2))) {
							if (Character.isLetter(reference.charAt(reference.length()-1)) && Character.isLetter(reference.charAt(reference.length()-2))) {
								if (reference.charAt(reference.length()-1) != 'H' && reference.charAt(reference.length()-1) != 'V') {
									System.out.println("The direcition of the shoot, when it's a corner, is with H or V!");
									return play(nickName, k, score);
								}
								else {
									String invalid = gc.shoot(reference);
									if (invalid != "") {
										System.out.println(invalid + "\n");
										return play(nickName, k, score);
									}
									else {
										gc.putMirrorAgain(0, 0);
										System.out.println(gc.getMatrix());
										gc.resetContain(0, 0);
										gc.putMirrorAgain(0, 0);
										System.out.println(nickName + ": " + k + " mirrors remaining\n");
										return play(nickName, k, score);
									}
								}
							}
							else {
								String invalid = gc.shoot(reference);
								if (invalid != "") {
									System.out.println(invalid + "\n");
									return play(nickName, k, score);
								}
								else {
									gc.putMirrorAgain(0, 0);
									System.out.println(gc.getMatrix());
									gc.resetContain(0, 0);
									gc.putMirrorAgain(0, 0);
									System.out.println(nickName + ": " + k + " mirrors remaining\n");
									return play(nickName, k, score);
								}
							}
						}
						else {
							System.out.println("Type a valid reference please!");
							return play(nickName, k, score);
						}
					}
					else if(reference.equalsIgnoreCase("cheat mode")) {
						gc.cheatMode(0, 0);
						System.out.println(gc.getMatrix());
						gc.resetContain(0, 0);
						gc.putMirrorAgain(0, 0);
						System.out.println(nickName + ": " + k + " mirrors remaining\n");
						return play(nickName, k, score);
					}
					else {
						System.out.println("Type a valid reference please!");
						return play(nickName, k, score);
					}
				}
			}
		}
		else
			System.out.println("You won!");
		return score;
	}
	/**
	 * This method directs the petition to the respective method
	 * @param option is the petition
	 */
	private void executeOperation(int option){
		switch (option) {
		case 1:
			initialize();
			break;
		case 2:
			System.out.println(gc.displayTree());
			break;
		case 3:
			System.out.println("Thanks for playing!");
			System.out.println("See you later <3");
			break;
		default:
			break;
		}
	}
	/**
	 * This method show the menu
	 * @return the menu
	 */
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
	/**
	 * This method read the petition
	 * @return the petition
	 */
	private int readOption() {
		String x = lector.nextLine();
		if (x.equals("")) {
			return readOption();
		}
		else {
			int choice = Integer.parseInt(x);
			System.out.println();
			return choice;
		}
	}
	/**
	 * This method initialize the program
	 */
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
