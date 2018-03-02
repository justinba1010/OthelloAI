import java.util.*;
//Justin Baum
public class OthelloMove {
  public int x;
  public int y;
  public boolean turn; //White is true

  public OthelloMove(boolean aTurn, int aX, int aY) {
    x = aX;
    y = aY;
    turn = aTurn;
  }

  public boolean equals(OthelloMove move2) {
    return x == move2.x && y == move2.y && turn == move2.turn;
  }

  public String toString() {
    return ((turn) ? "White" : "Black") + ": " + x +"|" + y;
  }

}
