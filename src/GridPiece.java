/**
 * Jeremy Belt
 * 2/16
 * GridPiece.java
 * class used to create gridsqaures for Battleship
 */

import acm.graphics.GCompound;
import acm.graphics.GOval;
import acm.graphics.GRect;

import java.awt.*;

public class GridPiece extends GCompound {

  private GRect gridSquare;
  private GOval shotPositon;
  private boolean clicked;

  public boolean isClicked() {
    return clicked;
  }

  public void setClicked(boolean clicked) {
    this.clicked = clicked;
  }



  //Constructor
  GridPiece(Color color){

    clicked = false;

    gridSquare = new GRect(50,50);
    gridSquare.setFilled(false);
    gridSquare.setFillColor(color);
    add(gridSquare);

    shotPositon = new GOval(20,20,12,12);
    shotPositon.setFilled(true);
    shotPositon.setFillColor(new Color(0,0,0));
    add(shotPositon);

  }

  //change color of circle if grid is clicked
  public void setShotPositionColor(Color color) {
      shotPositon.setFillColor(color);
  }

}
