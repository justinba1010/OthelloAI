import java.util.*;
//Justin Baum

public class Othello2AI {
  public static void main(String[] args) {
    int diff = 3;
    OthelloBoard ot = new OthelloBoard();
    Node AI = new Node(ot);
    AI.generate(3);
    AI.minimax(3);
    while(!AI.board.generateLegalMoves(AI.turn).isEmpty()) {
      AI.generate(diff);
      AI.generate(diff);
      AI.minimax(diff);
      AI.minimax(diff);
      System.out.println(AI.board);
      System.out.println(AI.bestMove);
      AI = AI.makeBestMove();
      }
    }//main
}//OthelloGame2Player
