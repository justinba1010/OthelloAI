import java.util.*;
//Justin Baum
public class OthelloBoard{
  private int[][] gameboard;
  private static final int[][] vectors = {{1,1},{1,-1},{-1,1},{-1,-1},{1,0},{0,1},{-1,0},{0,-1}};
  private static final int size = 8;

  public OthelloBoard() {
    init(true);
  }

  public void init(boolean build) {
    gameboard = new int[size][size];
    int i = 1;
    if(!build) return;
    for(int x = size/2-1; x < size/2+1; x++) {
      for(int y = size/2-1; y < size/2+1; y++) {
        gameboard[x][y] = i;
        i *= -1;
      }//for y
      i *= -1;
    }//Set board for start
  }//init

  public int evaluate() {
    int score = 0;
    for(int x = 0; x < size; x++) {
      for(int y = 0; y < size; y++) {
        score += gameboard[x][y];
      }// for y
    }//for x
    return score;
  }//evaluate

  public ArrayList<OthelloMove> generateLegalMoves(boolean turn) {
    ArrayList<OthelloMove> legalMoves = new ArrayList<OthelloMove>();
    for(int x = 0; x < size; x++) {
      for(int y = 0; y < size; y++) {
        OthelloMove move = new OthelloMove(turn, x,y);
        if(isLegalMove(move)) {
          legalMoves.add(move);
        }// add move
      }// for y
    }// for x
    return legalMoves;
  }//generateLegalMoves

  public void makeMove(OthelloMove move) {
    if(isLegalMove(move)) {
      for(int[] block : takeBlocks(move)) {
        gameboard[block[0]][block[1]] = turnToBoard(move.turn);
      }//for block
      gameboard[move.x][move.y] = turnToBoard(move.turn);
    }//if legal
  }//makeMove

  public boolean isLegalMove(OthelloMove move) {
    int x = move.x;
    int y = move.y;
    if(whoIs(x,y) != 0) return false;

    return !takeBlocks(move).isEmpty();
  }//isLegalMove

  public OthelloBoard deepCopy() {
    OthelloBoard newBoard = new OthelloBoard();
    for(int x = 0; x < size; x++) {
      for(int y = 0; y < size; y++) {
        newBoard.gameboard[x][y] = gameboard[x][y];
      }//for y
    }// for x
    return newBoard;
  }//deepCopy

  public String toString() {
    String s = "  0|1|2|3|4|5|6|7|\n";
    for(int x = 0; x < size; x++) {
      s += x+"|";
      for(int y = 0; y < size; y++) {
        s += (gameboard[x][y] == 0) ? " " : (gameboard[x][y] == 1) ? "X" : "O";
        s += "|";
      } //for y
      s += "\n";
    }// for x
    return s;
  }//toString

  //Insider Stuff

  private ArrayList<int[]> vectorBlocks(int x1, int y1, int[] vector) {
    ArrayList<int[]> blocks = new ArrayList<int[]>();
    while(onBoard(x1,y1)) {
      int[] newspaces = addVector(x1, y1, vector);
      x1 = newspaces[0];
      y1 = newspaces[1];
      if(onBoard(x1,y1)) {
        int[] block = {x1,y1};
        blocks.add(block);
      }//if
    }//while
    return blocks;
  }//vectorBlocks

  private boolean onBoard(int x, int y) {
    return (x >= 0) && (y >= 0) && (x < size) && (y < size);
  }//onBoard

  private boolean onBoard(int[] xy) {
    if(xy.length != 2) return false;
    return onBoard(xy[0],xy[1]);
  }//onBoard

  private int[] addVector(int x1, int y1, int[] vector) {
    if(vector.length != 2) return new int[0];
    int[] newblock = {x1+vector[0],y1+vector[1]};
    return newblock;
  }//addVector

  private int whoIs(int[] xy) {
    if(xy.length != 2) return 0;
    if(!onBoard(xy)) return 0;
    return gameboard[xy[0]][xy[1]];
  }//whoIs

  private int whoIs(int x, int y) {
    int[] xy = {x,y};
    return whoIs(xy);
  }

  private ArrayList<int[]> takeBlocks(OthelloMove move) {
    //So we need at least one vector to have the other player indefinitely until we hit the player who made the move.
    int startx = move.x;
    int starty = move.y;
    ArrayList<int[]> takeBlocks = new ArrayList<int[]>();
    for(int[] vector : vectors) {
      ArrayList<int[]> blocks = new ArrayList<int[]>();
      boolean sandwich = false; //Check to make sure the pieces we are taking are sandwiched
      for(int[] block : vectorBlocks(startx, starty, vector)) {
        //Check if block is enemy, else we just break
        if(whoIs(block) == turnToBoard(move.turn)) {//If it is the same player as the move maker, we just break.
          break;
        }
        if(whoIs(block) == -1*turnToBoard(move.turn)) {//If it is the opposite player we want to add this block.
          sandwich = true;
        } else if(whoIs(block) == 0) {//If it is not a taken spot, then we can cut our search because we never got a sandwich.
          sandwich = false;
          break;
        }// else if
        blocks.add(block);//Add block because we did not hit our conditions
      }//for block
      if(sandwich) {
        for(int[] block : blocks) {
          takeBlocks.add(block);
        }//for block
      }//if sandwich
    }//for vector
    return takeBlocks;
  }//takeBlocks

  private int turnToBoard(boolean turn) {
    return ((turn) ? 1 : -1);
  }
}//OthelloBoard
