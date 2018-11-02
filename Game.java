
public class Game {
	
	Board board;
	boolean win;
	boolean died;
	String difficulty;
	
	
	public Game() {
		Difficulty start = new Difficulty();
		this.difficulty = start.getDifficulty();
		board = new Board(start.getSize(), start.getMines());
		board.generateMines(start.getMines());	
		
		
		play();
	}
	
	public void play() {
		do {
			died = board.setPosition();
			win = board.hasWon();
		}
		while(!win && !died);
			if(died) { System.out.println("\t\t"+"*****LOSER*****"); }
			if(win)  { 
				System.out.println("\t\t"+"*****WINNER*****");
				System.out.println("completed in: "+ board.Stopwatch());
			}
			if (win && difficulty.equals("easy")) {
				System.out.println("Too easy for you try MEDIUM");
			}
			if (win && difficulty.equals("medium")) {
				System.out.println("Too easy for you try HARD");
			}
			if (win && difficulty.equals("hard")) {
				System.out.println("born minesweeper");
			}
			System.out.println("\nTry again? \n y / n   : ");
			board.tryAgain();
			if(board.tryAgain().equals("y")) {
				Game nextGame = new Game();
				}
			else {
				System.out.println(":(");
			}
	}

	public static void main(String[] args) {
		Game game = new Game();
	}
}
