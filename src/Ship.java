import acm.graphics.GCompound;
import acm.graphics.GImage;

import java.awt.*;

/**
 * Created by Belt on 3/2/2016.
 */
public class Ship extends GCompound{
  private int shipHoles;


  public Ship(int shipHoleCount){
    this.shipHoles = shipHoleCount;

    buildShip();
  }

  private void buildShip(){
    int xMove = 10;
    int yMove = 10;

      for (int i = 0; i < shipHoles; i++) {
        GridPiece gridPiece = new GridPiece(Color.GRAY);
        add(gridPiece, xMove, yMove);
        xMove += 50;
      }
  }

}
