package ui;

import java.util.Scanner;

import model.Matrix;

public class Menu {

	static Scanner lector;
	private Matrix mx;
	public final static int EXIT_MENU = 3;

	/**
	 * This method is the constructor of Menu
	 */
	public Menu() {
		lector = new Scanner(System.in);
	}
	/**
	 * This method display the instructions of the game
	 */
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
		System.out.println("Instrucions ends\n");
		String start = lector.nextLine();
		String[] info = start.split(" ");
		System.out.println();
		mx = new Matrix(Integer.parseInt(info[1]), Integer.parseInt(info[2]));
		mx.createRandomMirror(Integer.parseInt(info[3]));
		System.out.println(mx);
		play(info[0], Integer.parseInt(info[3]));

	}
	/**
	 * This method execute the game
	 * @param nickName is the nick name of the player
	 * @param k is the number of mirrors remaining
	 */
	private void play(String nickName, int k) {
		if(k > 0) {
			System.out.println("Type a reference to shoot a laser\n");
			String reference = lector.nextLine();
			if (!reference.equals("menu")) {
				mx.shoot(reference);
				System.out.println(mx);
				mx.resetContain(0, 0);
				System.out.println("Where the mirror is?\n");
				String location = lector.nextLine();
				int mirrors = mx.locateMirror(location, k);
				System.out.println(mx);
				mx.resetContain(0, 0);
				System.out.println(nickName + ": " + mirrors + " mirrors remaining\n");
				play(nickName, mirrors);
			}
		}
	}
	/**
	 * This method directs the petition to the respective method
	 * @param option is the petition
	 */
	private void executeOperation(int option){
		switch (option) {
		case 1:
			instructions();
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
		int choice = Integer.parseInt(lector.nextLine());
		System.out.println();
		return choice;
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
