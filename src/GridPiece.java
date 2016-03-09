import acm.graphics.*;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Belt on 2/23/2016.
 */
public class GridPiece extends GCompound {

  private GRect gridSquare;
  private GOval shotPositon;

  public boolean isClicked() {
    return clicked;
  }

  public void setClicked(boolean clicked) {
    this.clicked = clicked;
  }

  private boolean clicked;

  GridPiece(Color color){


    clicked = false;

    gridSquare = new GRect(50,50);
    gridSquare.setFilled(true);
    gridSquare.setFillColor(color);
    add(gridSquare);

    shotPositon = new GOval(20,20,12,12);
    shotPositon.setFilled(true);
    shotPositon.setFillColor(new Color(0,0,0));
    add(shotPositon);

  }

  public void setShotPositionColor(Color color) {
      shotPositon.setFillColor(color);
  }
  public Color getShotPositionColor(){
    return shotPositon.getColor();
  }
}
