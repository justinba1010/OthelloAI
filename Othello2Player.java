import java.util.*;
//Justin Baum

public class Othello2Player {
  public static void main(String[] args) {
    OthelloBoard board = new OthelloBoard();
    boolean turn = true;
    Scanner keyboard = new Scanner(System.in);
    int x = 0;
    int y = 0;
    while(!board.generateLegalMoves(turn).isEmpty()) {
      System.out.println(board);
      System.out.println("The score is: " + board.evaluate());
      if(turn) {
        System.out.print("WHITE(X) turn enter row and column separated by spaces: ");
        x = keyboard.nextInt();
        y = keyboard.nextInt();
      } else {
        System.out.print("BLACK(O) turn enter row and column separated by spaces: ");
        x = keyboard.nextInt();
        y = keyboard.nextInt();
      }
      OthelloMove move = new OthelloMove(turn, x, y);
      if(board.isLegalMove(move)) {
        board.makeMove(move);
        turn = !turn;
      }
    }
  }//main
}//OthelloGame2Player
