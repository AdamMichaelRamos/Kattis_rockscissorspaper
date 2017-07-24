import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class Main 
{
	public static void main(String[] args) 
	{
		System.out.println("rockscissorspaper");
		
		InputStream in = null;
		
		try {
			in = new FileInputStream(new File("./samples/1.in"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Scanner scanner = new Scanner(in);
		int cases = scanner.nextInt();
		for (int i = 0; i < cases; i++) {
			int rows = scanner.nextInt(),
				columns = scanner.nextInt(),
				days = scanner.nextInt();
			
			System.out.println("Rows: "+rows+" | Columns: "+columns+" | Days: "+days);
			
			int[][] board = Main.createBoard(scanner, rows, columns);
			
			Main.solveBoard(board, days);
			
			System.out.println("(R): "+(int)'R'+" | (S): "+(int)'S'+" | (P): "+(int)'P');
			System.out.println("/*** NEXT BOARD ***/");
		}
		
		System.out.println("*** ANSWER ***");
		
		scanner.close();
		try { if (in != null) in.close(); } 
		catch (IOException e) { e.printStackTrace(); }
	}

	private static int[][] createBoard(Scanner scanner, int rows, int columns)
	{
		int[][] board = new int[rows][columns];
		for (int row = 0; row < rows; row++) {
			String boardRow = scanner.next();
			
			for (int col = 0; col < boardRow.length(); col++) {
				char boardCell = boardRow.charAt(col);
				board[row][col] = Main.convertBoardPositionToInteger(boardCell);
			}
		}
		return board;
	}
	
	private static void solveBoard(int[][] board, int days) 
	{
		int[][] newBoard = new int[board.length][board[0].length];
		
		for (int day = 0; day < days; day++) {
			System.out.println("DAY: "+(day+1));
			
			for (int row = 0; row < board.length; row++) {
				int[] boardRow = board[row];
				for (int col = 0; col < boardRow.length; col++) {
					int boardCell = boardRow[col];
					boardRow[col] = Main.incrementBoardPositionValue(boardCell);
				}
			}
			
			for (int[] row : board) {
				System.out.println(Arrays.toString(row));
			}
		}
	}
	
	private static int convertBoardPositionToInteger(char boardPosition)
	{
		int convertedPosition = -1;
		
		if (boardPosition == 'R') convertedPosition = 0;
		else if (boardPosition == 'S') convertedPosition = 1;
		else if (boardPosition == 'P') convertedPosition = 2;
		
		return convertedPosition;
	}
	
	private static int incrementBoardPositionValue(int positionValue)
	{
		return (positionValue+1)%3;
	}
}
