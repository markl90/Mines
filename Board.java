import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class Board {
	
	private int [][]mines;
	private String[][]gameBoard;
	private String[] gridGuide;
	public Scanner scanner;
	private Random random;
	int turns = 0;
	long startTime;
	int bombs;
	int mineCount;
	
	private int gridSize;
	private int boundary;
	private int totalSize;
	
	private  static final int GRID_MIN = 1;
	private static final String SHELL = " ";
	
	public Board(int size, int mines) {
		this.mineCount = mines;
		gridSize = size;
		boundary = gridSize+1;
		totalSize = boundary+1;
		startTime = System.nanoTime();
		random = new Random();
		scanner = new Scanner(System.in);
		setup();
		
	}
	
	
	public void setup() {
		
		mines = new int[totalSize][totalSize];
		gameBoard = new String [totalSize][totalSize];
		gridGuide = new String [boundary];
		initialiseBoard();
		showBoard();
		
	}
	
	public void initialiseBoard() {
		for (int i = GRID_MIN; i < boundary ; i++) {
			for (int j = GRID_MIN; j < boundary; j++) {
				gameBoard[i][j] = " ";
				gridGuide[i]= i+" ";
			}
		}	
	}
	
	public void showBoard() {
		System.out.println(""+"\n");

		System.out.println("");
		System.out.println("turns: "+turns+"\t"+"SquaresLeft:\t "+squaresLeft()+"\nMines: "+mineCount + "\t" + "gameTime:\t" + Stopwatch()+"\n");

		for (int row = gridSize; row > 0 ; row-- ) {
		
				if (row<10) {
					System.out.print( "\t"+" "+row+" |");
				}
				else {
					if(row==gridSize) {
						System.out.print("rows\t"+ gridSize+" |");
					}
					else {
					System.out.print( "\t"+row+""+" |");
					}
				}
			for (int column = GRID_MIN; column < boundary; column ++) {
				System.out.print(""+gameBoard[row][column]+" |");
			}
			System.out.println("");
		}
		System.out.println("\t"+" \0   "+gridGuideFormat()+"   cols");

	}
	
	public boolean setPosition() {
		int setColumn;
		int setRow;
		do {
			System.out.print("Set (cols) X:");
			setColumn = scanner.nextInt();
			System.out.print("Set (rows) Y:");
			setRow = scanner.nextInt();
				if((setRow > gridSize || setRow < GRID_MIN) ||(setColumn > gridSize || setColumn < GRID_MIN)) {
					System.out.println("Out of Bounds");
					setColumn = 0;
					setRow = 0;
				}
//		TODO: Input MisMatch & Already Picked
			if(mines[setRow][setColumn]== -1) {
				showMines();
				return true;
			}
		}
		while((setColumn>gridSize || setColumn<GRID_MIN)||(setRow>gridSize || setRow<GRID_MIN));
			gameBoard[setRow][setColumn] = "x";
			openNeighbors(setRow, setColumn);
			clearZeros();
			showHints();
			turns++;
			showBoard();	
		return false;
	}
	
	public void clearMines() {
		for (int rows = 0; rows < mines.length; rows++) {
			for (int columns = 0; columns < mines.length; columns++) {
				mines[rows][columns] = 0;
			}
		}
	}
	
	public void generateMines(int totalMines) {
		clearMines();
		boolean hasMine;
		int row = 0;
		int column = 0;
		for (int mine = 0; mine < totalMines; mine++) {
			do {
				row = random.nextInt(gridSize) + GRID_MIN;
				column = random.nextInt(gridSize) + GRID_MIN;
				if (mines[row][column]== -1) {
					hasMine = true;
				}
				else {
					hasMine = false;
				}
			}
			while(hasMine);
			mines[row][column]= -1;
		}
		generateHints();
	}
	
	public void showMines() {
		for (int row = GRID_MIN; row<boundary; row++) {
			for(int column=GRID_MIN; column<boundary; column++) {
				if(mines[row][column]== -1) {
					gameBoard[row][column] = "#";
				}
			}
		}
		showBoard();
	}
	
	public void generateHints() {
		for (int row = GRID_MIN; row<boundary; row++) {
			for(int column=GRID_MIN; column<boundary; column++) {
				for (int i = -1 ; i <=1 ; i ++) {
					for (int j = -1; j <=1 ; j++){
						if (mines[row][column] != -1) {
							if(mines[row+i][column+j]== -1) {
								mines[row][column]++;
							}
						}
					}
				}
			}
		}
	}
	
	//temp
	public void showAllHints() {
		for (int row = GRID_MIN; row<boundary; row++) {
			for(int column=GRID_MIN; column<boundary; column++) {
				if(mines[row][column]> 0) {
					gameBoard[row][column] = String.valueOf(mines[row][column]);
				}
			}
		}
	}
	
    public void openNeighbors(int row, int column) {
    	
		for (int i = row-1 ; i < row+2 ; i++) {
			for (int j = column-1; j <column+2 ; j++) {
				if (mines[i][j]==0) {
					gameBoard[i][j] = String.valueOf(mines[i][j]);
				}
			}
		}
    }
    
    public void clearZeros() {
    	for(int i = 0; i<boundary; i++) {
	    	for(int a = GRID_MIN; a < boundary; a++) {
				for (int b = 1; b < boundary; b++) {
					if (gameBoard[a][b].equals("0")) {
						openNeighbors(a,b);
					}
				}
	    	}
    	}
    showHints();
    }
    
    public void showHints() {
    	for (int i = GRID_MIN; i<boundary ; i++) {
    		for (int j = GRID_MIN; j<boundary; j++) {
    			if(gameBoard[i][j].equals("0")||gameBoard[i][j].equals("x")) {
    				for (int x = i-1; x<(i+2); x++) {
    					for (int y = j-1; y<(j+2); y++) {
    						if (mines[x][y]>0) {
    							
    							gameBoard[x][y] = String.valueOf(mines[x][y]);
    						}
    					}
    				}
    			}
    		}
    	}
    }
    
    public boolean hasWon() {
    	for (int i = GRID_MIN; i<boundary; i++) {
    		for (int j = GRID_MIN; j<boundary; j++) {
    			if (gameBoard[i][j].equals(SHELL) && mines[i][j] != -1) {
    				return false;
    			}	
    		}
    	}
    	showMines();
    	return true;
    }
    
    public String gridGuideFormat() {
    	String gridGuide = "";
    	for (int i = 1; i < boundary; i++) {
    		if(i<10) {
    			gridGuide += i+"  ";
    		}
    		else {
    			gridGuide+=i+" ";
    		}
    	}
    	return gridGuide;
    }
    
    
    public int squaresLeft() {
    	int count = 0;
    	for (int i = 1; i < boundary; i++) {
    		for(int j = 1; j < boundary; j++) {
    			if (gameBoard[i][j].equals(SHELL)&&mines[i][j] != -1) {
    				count++;
    				
    			}
    			
    		}
    	}
    	return count;   	
    }
    
    
    public String Stopwatch() {
    	long estimatedTime = System.nanoTime() - startTime;
    	long seconds =  TimeUnit.NANOSECONDS.toSeconds(estimatedTime);
    	long minutes = seconds / 60;
    	long sec = seconds%60;
    	return " "+minutes + " Mins " + sec + " Seconds";
    }
    
    
    public String chooseDifficulty() {
    	System.out.println("Choose Difficulty");
    	String x = scanner.nextLine();
    	return x;
    }
 
    
	public String tryAgain(){
		String retry = scanner.nextLine();
			switch (retry) {
			case "y":
				return "y";
			case "Y":
				return "y";
			default: 
				return "n";
			}
	}
    
	
}
