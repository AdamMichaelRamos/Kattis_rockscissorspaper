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
			in = new FileInputStream(new File("./samples/test.txt"));
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
	
	private static int[][] solveBoard(int[][] board, int days) 
	{
		for (int day = 0; day < days; day++) {
			System.out.println("DAY: "+(day+1));
			
			board = Main.simulateWar(board);
			
			for (int[] row : board) {
				for (int cell : row) {
					System.out.print(cell);
				}
				System.out.println(Arrays.toString(row));
			}
		}
		
		return board;
	}
	
	private static int[][] simulateWar(int[][] currentBoard) 
	{
		int height = currentBoard.length, width = currentBoard[0].length;
		int[][] newBoard = new int[height][width];
		
		for (int row = 0; row < height; row++) {
			int[] boardRow = currentBoard[row];
			
			for (int col = 0; col < width; col++) {
				int boardCell = boardRow[col];
				
				int[] adjacentPositions = Main.getAdjacentPositions(boardCell, row, col, currentBoard);
				
				newBoard[row][col] = Main.positionValueIsDefeated(boardCell, adjacentPositions) ?
						Main.incrementBoardPositionValue(boardCell) : boardCell;
			}
		}
		
		return newBoard;
	}
	
	private static int convertBoardPositionToInteger(char positionValue)
	{
		return positionValue == 'R' ? 2 : positionValue == 'S' ? 1 : positionValue == 'P' ? 0 : -1;
	}
	
	private static int convertBoardPositionToCharacter(int positionValue)
	{
		return positionValue == 2 ? 'R' : positionValue == 1 ? 'S' : positionValue == 0 ? 'P' : '-';
	}
	
	private static int[] getAdjacentPositions(int positionValue, int rowPos, int colPos, int[][] board)
	{
		int rowLength = board.length, colLength = board[0].length;
		int 
			up = rowPos-1 >= 0 ? board[rowPos-1][colPos] : -1, 
			right = colPos+1 < colLength ? board[rowPos][colPos+1] : -1, 
			down = rowPos+1 < rowLength ? board[rowPos+1][colPos] : -1, 
			left = colPos-1 >= 0 ? board[rowPos][colPos-1] : -1;
			
		return new int[]{up, right, down, left};
	}
	
	private static boolean positionValueIsDefeated(int positionValue, int[] adjacentPositions)
	{
		for (int adjacent : adjacentPositions)
			if (adjacent == (adjacent >= 0 ? Main.incrementBoardPositionValue(positionValue) : positionValue))
				return true;
		
		return false;
	}
	
	private static int incrementBoardPositionValue(int positionValue)
	{
		return (positionValue+1)%3;
	}
}
