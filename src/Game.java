import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Created by Belt on 2/16/2016.
 */
public class Game extends GraphicsProgram{
  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  double width = screenSize.getWidth();
  double height = screenSize.getHeight();
//  GridPiece gridPiece = new GridPiece();
  GObject gridClick;
  int boardSize =10;
  ArrayList<GridPiece> grid = new ArrayList<>();
  GImage carrier = new GImage("Carrier.png");
  GImage destroyer = new GImage("destroyer.png");
  GPoint lastClick;
  ArrayList<Integer> hitArray = new ArrayList<>();

  public void init(){
//    setSize((int)width,(int)height);
    setSize(600,600);
    addMouseListeners();
  }
  public void run(){
    hitArray.add(10);
    hitArray.add(11);
    hitArray.add(12);
    hitArray.add(14);

    buildBoard();
    carrier.scale(1.5);
    destroyer.scale(1.5);

    add(carrier,500,15);
    add(destroyer,500,100);
  }
  private void buildBoard() {
    int xMove = 10;
    int yMove = 10;
    for (int j = 0; j < boardSize; j++) {
      for (int i = 0; i < boardSize; i++) {
        GridPiece gridPiece = new GridPiece();
        grid.add(gridPiece);
        add(gridPiece, xMove, yMove);
        xMove += 50;
      }
      xMove = 10;
      yMove += 50;
    }
  }
  public void checkShipPosition(){

  }


  public  void mousePressed(MouseEvent event){
    double x = event.getX();
    double y = event.getY();
    lastClick = new GPoint(event.getPoint());

    gridClick = getElementAt(x,y);

    GridPiece clickedGrid = grid.get(grid.indexOf(gridClick));

    clickedGrid.setShotPositionColor(Color.WHITE);
    checkForHit(clickedGrid);


  }//mousePressed method

  private void checkForHit(GridPiece clickedGrid) {
    for(ListIterator<Integer> integerIterator = hitArray.listIterator(); integerIterator.hasNext();){
      Integer hit = integerIterator.next();
      println(hit);
      if(grid.indexOf(clickedGrid) == hit) {
        clickedGrid.setShotPositionColor(Color.RED);
        integerIterator.remove();
      }
    }

  }

  public void mouseDragged(MouseEvent e)
  {
    if (gridClick == carrier || gridClick == destroyer)
    {
      gridClick.move(e.getX() - lastClick.getX(), e.getY() - lastClick.getY());
      lastClick = new GPoint(e.getPoint());
    } //mouseDragged
  }


}
