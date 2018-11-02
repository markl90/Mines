import java.util.Scanner;

public class Difficulty {
	
	Scanner scanner;
	int size;
	int mines;
	String difficulty;
	
	public Difficulty() {
		scanner = new Scanner(System.in);
		if(chooseDifficulty().equals("c")) {
		chooseSize() ;
		chooseMines();
		}
	}
	
	public String chooseDifficulty() {
		System.out.println("  ========MINESWEEPER=========\n");
		System.out.println(" 'e' for easy");
		System.out.println(" 'm' for medium");
		System.out.println(" 'h' for hard\n");
		System.out.println("  Choose your difficulty...\n");
		switch(scanner.nextLine()) {
		case "e":
			this.size = 8;
			this.mines = 10;
			this.difficulty = "easy";
			break;
		case "m":
			this.size = 14;
			this.mines = 25;
			this.difficulty = "medium";
			break;
		case "h":
			this.size = 20;
			this.mines = 40;
			this.difficulty = "hard";
			break;
		case "c":
			this.difficulty = "custom";
			return "c";
		default:
			this.size = 8;
			this.mines = 10;
			this.difficulty = "easy";
			break;
		}
		return "";
	}
	
    public void chooseSize() {
    	System.out.println("Choose your board size: ");
    	int x = scanner.nextInt();
    	this.size = x;
    }
    
    public void chooseMines() {
    	System.out.println("Choose the number of mines: ");
    	mines = scanner.nextInt();
    }
 
	

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getMines() {
		return mines;
	}

	public void setMines(int mines) {
		this.mines = mines;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	 
	 
	 

}
