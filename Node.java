import java.util.*;
//Justin Baum

public class Node {
  public OthelloBoard board;
  public int value;
  public OthelloMove bestMove;
  public Node bestNode;
  public OthelloMove lastMove;
  public boolean turn;
  public ArrayList<Node> children;

  public Node(OthelloBoard aBoard) {
    board = aBoard;
    value = board.evaluate();
    turn = true;
    bestMove = null;
    bestNode = null;
    lastMove = null;
    children = new ArrayList<Node>();
  }//Node
  public Node(OthelloBoard aBoard, OthelloMove aMove, boolean aTurn) {
    board = aBoard;
    value = board.evaluate();
    bestMove = null;
    bestNode = null;
    lastMove = aMove;
    turn = aTurn;
    children = new ArrayList<Node>();
    turn = !aMove.turn;//Opposite of the last move. Just for additional security.
  }//Node

  public void generate(int depth) {
    //Get arraylist of moves made, for easy search so we don't double up.
    if(depth <= 0) return;//Halt
    for(OthelloMove move : board.generateLegalMoves(turn)) {
      OthelloBoard newBoard = board.deepCopy();
      newBoard.makeMove(move);
      Node newNode = new Node(newBoard, move, !turn);
      newNode.generate(depth-1);
      children.add(newNode);
    }
    Collections.shuffle(children);
  }//generate

  public int minimax(int depth) {
    if(depth <= 0) return value;//Halt
    if(children.isEmpty()) return value;//Bottom of tree
    int maximin = children.get(0).value;
    for(Node child : children) {
      child.value = child.minimax(depth-1); //First traverse to bottom of the tree
      if((child.value > maximin && turn) || (child.value < maximin && !turn)) {
        maximin = child.value;
        bestMove = child.lastMove;
        bestNode = child;
      }
    }
    return maximin;
  }
  public Node makeBestMove() {
    return bestNode;
  }

}//Node
