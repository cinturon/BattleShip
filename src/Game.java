import acm.graphics.GImage;
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


  RandomGenerator generator = new RandomGenerator();
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
    GImage background = new GImage("rsz_ocean.jpg");
    background.scale(2.5);
    add(background);
    setSize(1350,800);
    addKeyListeners();
    addMouseListeners();
  }
  public void run(){


    buildBoard(playerGrid, 50, 100);
    buildBoard(CPUGrid, 775, 100);
    setupPlayerBoard();



    destroyer = new Ship(5);
    add(destroyer,1200,100);


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

//    println(playerGrid.indexOf(gridClick));
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


    for (double boatSize = 2; boatSize <= 5; boatSize++) {

      double startingBoatPosition = generator.nextDouble(0, 99);

      //No boats off the bottom of the board
      while (startingBoatPosition + boatSize >= 100){
        startingBoatPosition = generator.nextDouble(0,99);
      }

      //Make sure there are no broken horizontal boats
      while(startingBoatPosition/10 + boatSize/10 > Math.ceil(startingBoatPosition/10)){
          startingBoatPosition = generator.nextDouble(0,99);
      }


      ArrayList<Integer> boat = boatMaker(boatSize,startingBoatPosition);

      println("Boat Array: " + boat);

      //Check if boat went off the board (over Position 100)
      if(offTheBoard(boat)){
        boat.clear();
        boatSize--;
      }

      //For loop checks for duplicate numbers in temp array
      for (Integer hit: hitArray) {
        if (boat.contains(hit)){
            boat.clear();
            boatSize--;
        }
      }

      hitArray.addAll(boat);

    }
    println("HIT ARRAY: " + hitArray);
    println("HIT ARRAY SIZE = " + hitArray.size());

  }

  public ArrayList<Integer> boatMaker(double boatSize, double startingBoatPosition){

    ArrayList<Integer> boatArray = new ArrayList<>();
    int buildDirection;
    int nextPosition = 0;
    int directionPicker = generator.nextInt(1,2);
    if(directionPicker == 1){
      buildDirection = 1;
    }else {
      buildDirection = 10;
    }


    for (int boat = 0; boat < boatSize; boat++) {

      boatArray.add((int)startingBoatPosition + nextPosition);
      nextPosition += buildDirection;

    }
    return boatArray;
  }
  private boolean offTheBoard(ArrayList<Integer> boatArray){
    for (Integer boatNum : boatArray){
      if(boatNum >= 100){
        return true;
      }
    }

    return false;
  }

}
