import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by Belt on 2/16/2016.
 */
public class Game extends GraphicsProgram{
  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  double width = screenSize.getWidth();
  double height = screenSize.getHeight();



  GObject gridClick;
  int boardSize =10;
  int winningScore = 14;
  int currentScore = 0;
  ArrayList<GridPiece> playerGrid = new ArrayList<>();
  ArrayList<GridPiece> CPUGrid = new ArrayList<>();

  Ship destroyer, carrier;

  GPoint lastClick;
  ArrayList<Integer> hitArray = new ArrayList<>();
  //ArrayList<Integer> boatArray = new ArrayList<>();

  public void init(){
    setSize(600,600);
    //setSize((int)width,(int)height);
    addMouseListeners();
  }
  public void run(){
  setupPlayerBoard();


    buildBoard(playerGrid, 10, 10);
    buildBoard(CPUGrid, 600, 10);



    destroyer = new Ship(5);
    add(destroyer,500,100);


  }
  private void buildBoard(ArrayList<GridPiece> boardArray, int xOrigin, int yOrigin) {

    int xMove = 10;
    int yMove = 10;
    for (int j = 0; j < boardSize; j++) {
      for (int i = 0; i < boardSize; i++) {
        GridPiece gridPiece = new GridPiece(Color.BLUE);
        boardArray.add(gridPiece);
        add(gridPiece, xOrigin + xMove, yOrigin + yMove);
        xMove += 50;
      }
      xMove = 10;
      yMove += 50;
    }
  }


  public  void mousePressed(MouseEvent event){
    double x = event.getX();
    double y = event.getY();
    lastClick = new GPoint(event.getPoint());

    gridClick = getElementAt(x,y);
    if( playerGrid.indexOf(gridClick) != -1) {
      GridPiece piece = playerGrid.get(playerGrid.indexOf(gridClick));
      checkForHit(piece);
    }










  }//mousePressed method

  private void checkForHit(GridPiece clickedGrid) {

    for(ListIterator<Integer> integerIterator = hitArray.listIterator(); integerIterator.hasNext();){
      Integer hit = integerIterator.next();



      if(playerGrid.indexOf(clickedGrid) == hit  && clickedGrid.isClicked() == false) {
        clickedGrid.setClicked(true);
        clickedGrid.setShotPositionColor(Color.RED);
        currentScore++;
      }
      else if (clickedGrid.isClicked() == false){
        clickedGrid.setShotPositionColor(Color.WHITE);
      }
    }

    println(playerGrid.indexOf(gridClick));
  }

  public void mouseDragged(MouseEvent e)
  {
    if (gridClick == carrier || gridClick == destroyer)
    {
      gridClick.move(e.getX() - lastClick.getX(), e.getY() - lastClick.getY());
      lastClick = new GPoint(e.getPoint());
    } //mouseDragged
  }
  public void setupPlayerBoard() {
    RandomGenerator generator = new RandomGenerator();

    for (double boatSize = 2; boatSize <= 5; boatSize++) {

      double startingBoatPosition = generator.nextDouble(0, 99);
//      println("Start: " + startingBoatPosition);

      //No boats off the bottom of the board
      while (startingBoatPosition + boatSize >= 100){
        startingBoatPosition = generator.nextDouble(0,99);
      }

      //Make sure there are no broken horizontal boats
      while(startingBoatPosition/10 + boatSize/10 > Math.ceil(startingBoatPosition/10)){
          startingBoatPosition = generator.nextDouble(0,99);
      }

//      //Check for duplicates
//      for(Integer boatHit : boatArray){
//        println("boatHit = " + boatHit);
//        for (Integer hit : hitArray){
//          println("hit = " + hit);
//          if(boatHit == hit){
//            boatArray.clear();
//            //boatSize--;
//          }
//        }
//      }



      ArrayList<Integer> boat = boatMaker(boatSize,startingBoatPosition);
      for (Integer hit: hitArray) {
        if (boat.contains(hit)){
            boat.clear();
            boatSize--;
        }

      }
      hitArray.addAll(boat);
    }
    println(hitArray);

  }

  public ArrayList<Integer> boatMaker(double boatSize, double startingBoatPosition){

    ArrayList<Integer> boatArray = new ArrayList<>();
    int horizontalBoat = 0;

    for (int boat = 0; boat < boatSize; boat++) {
      boatArray.add((int)startingBoatPosition + horizontalBoat);


//      println("hitArray: " + hitArray);

      horizontalBoat++;
    }
    return boatArray;
  }

}
