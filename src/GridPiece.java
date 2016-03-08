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

  GridPiece(){

    gridSquare = new GRect(50,50);
    gridSquare.setFilled(true);
    gridSquare.setFillColor(new Color(50,35,200));
    add(gridSquare);

    shotPositon = new GOval(20,20,12,12);
    shotPositon.setFilled(true);
    shotPositon.setFillColor(Color.BLACK);
    add(shotPositon);

  }

  public void setShotPositionColor(Color color) {
      shotPositon.setFillColor(color);
  }
  public Color getShotPositionColor(){
    return shotPositon.getColor();
  }
}
