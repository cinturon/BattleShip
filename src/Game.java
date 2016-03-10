/**
 * Jeremy Belt
 * 2/16
 * Game.java
 * Main class for Battleship game
 */

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;
import acm.util.SoundClip;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;


import java.net.URL;
import java.util.ArrayList;
import java.util.ListIterator;


public class Game extends GraphicsProgram{

  final int APP_WIDTH = 625;
  final int APP_HEIGHT = 800;

  RandomGenerator generator = new RandomGenerator();
  GObject gridClick;

  int boatSpotsLeft;
  int shotsRemaining;

  GLabel shotsDisplay;
  GLabel shipDisplay;

  GImage missile;
  GImage explosion;
  GImage background;

  GLabel result;


  ArrayList<GridPiece> playerGrid;
  ArrayList<Integer> hitArray;

  public void init(){
    setSize(APP_WIDTH,APP_HEIGHT);
    addMouseListeners();

  }
  public void run(){
    //Uncomment to listen to background music
    // playMusic();
     playGame();
  }

  private void playGame() {

    //MAIN LOOP
     while (true) {

       //Ensure that all elements are gone before creating new game
       removeAll();
       // create a new set of arrays each time the game starts
       playerGrid = new ArrayList<>();
       hitArray = new ArrayList<>();

       buildBoard(playerGrid, 50, 100);
       setupPlayerBoard();

       missile = new GImage("mis.gif");
       explosion = new GImage("explosionA.gif");
       add(missile, APP_WIDTH / 2 - missile.getWidth(), APP_HEIGHT - missile.getHeight()- 15);

       //loop used to control missiles and explosions
       while (boatSpotsLeft != 0 && shotsRemaining >= 0) {
         println(shotsRemaining);


         if (gridClick != null) {

           if (missile.getY() > gridClick.getY() + missile.getHeight() / 10 && missile.getY() < 1200) {
             explosion.setVisible(true);
             missile.setVisible(true);
             missile.move(0, -2);
             pause(2);
           }
           else if (missile.getY() <= gridClick.getY() + missile.getHeight() / 10) {
             missile.setVisible(false);
             add(explosion, gridClick.getX() - explosion.getWidth() / 2.5, gridClick.getY() - explosion.getHeight() / 2);
             pause(500);

             missile.setLocation(2000, 2000);
             remove(explosion);

           }
         }
       }
       giveResults();
       pause(5000);
       }
  }
  private void buildBoard(ArrayList<GridPiece> boardArray, int xOrigin, int yOrigin) {

    addHud();
    int boardSize = 10;
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




    gridClick = getElementAt(x,y);


    fireMissile();

    if( playerGrid.indexOf(gridClick) != -1) {
        GridPiece piece = playerGrid.get(playerGrid.indexOf(gridClick));
        checkForHit(piece);
        println("Check " + gridClick.equals(result));
    }
  }//mousePressed method

  private void checkForHit(GridPiece clickedGrid) {

    updateShotsTaken();
    for(ListIterator<Integer> integerIterator = hitArray.listIterator(); integerIterator.hasNext();){
      Integer hit = integerIterator.next();

      if(playerGrid.indexOf(clickedGrid) == hit  && clickedGrid.isClicked() == false) {
        clickedGrid.setClicked(true);
        clickedGrid.setShotPositionColor(Color.RED);
        if(clickedGrid.isClicked() == true);
        updateBoatsLeft();
      }
      else if (clickedGrid.isClicked() == false){
        clickedGrid.setShotPositionColor(Color.WHITE);
      }
    }


//    println(playerGrid.indexOf(gridClick));
  }

  //use to add single boats to the list of the gameboard
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

      //boat is a temp. arrayList to help check requirements before being added to the gameboard array
      ArrayList<Integer> boat = boatMaker(boatSize,startingBoatPosition);

      //check what was stored in temp array
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

      //if boat passed checks add it to main array
      hitArray.addAll(boat);

    }
    //use these to cheat at the game
    println("HIT ARRAY: " + hitArray);
    println("HIT ARRAY SIZE = " + hitArray.size());
  }

  //use to make a single boat
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
  //normal importing didn't work, so I made a work around
  private void playMusic(){
    URL bg;
    SoundClip soundClip;
    try {
      bg = new URL("http://jibjack.com/beerbib/bgMusic.wav");
      soundClip = new SoundClip(bg);
      soundClip.setVolume(.25);
      soundClip.loop();
    } catch (MalformedURLException e){
      e.printStackTrace();
    }
  }

  //add visual elements
  //also set the shot counter here to adjust difficulty
  private void addHud() {
    background = new GImage("rsz_ocean.jpg");
    background.scale(2.5);
    add(background);

    GImage logo = new GImage("logo.png");
    logo.scale(.25);
    add(logo,APP_WIDTH/3,10);

    shotsRemaining = 60;
    shotsDisplay = new GLabel("Missiles Remaining: " + shotsRemaining);
    shotsDisplay.setColor(Color.WHITE);
    add(shotsDisplay,60,100);

    boatSpotsLeft = 14;
    shipDisplay = new GLabel("Remaining Boat Hits: " + boatSpotsLeft);
    shipDisplay.setColor(Color.RED);
    add(shipDisplay,430,100);

    GLabel instructions = new GLabel("Click on a grid square to launch a missile");
    add(instructions, APP_WIDTH/2 - instructions.getWidth()/2,APP_HEIGHT - 20);
  }

  private void updateShotsTaken(){
    --shotsRemaining;
    if(shotsRemaining >= 0) {
      shotsDisplay.setLabel("Missiles Remaining: " + shotsRemaining);
    }
  }

  private void updateBoatsLeft(){
    --boatSpotsLeft;
    shipDisplay.setLabel("Remaining Boat Hits: " + boatSpotsLeft);
  }
  //Move missle off the screen so it can be shot at a grid square
  public void fireMissile(){
   missile.setLocation(gridClick.getX(),APP_HEIGHT);
  }
  //call if the game loop is exited and display a message
  private void giveResults(){
    println(shotsRemaining);
    result = new GLabel("");
    result.setColor(Color.WHITE);

    if (shotsRemaining == -1) {

      result.setLabel("YOU RAN OUT OF MISSILES NEW GAME IN 5 SECONDS");
      add(result, APP_WIDTH / 2 - result.getWidth() / 2, APP_HEIGHT - 100);
    }
    else if(boatSpotsLeft == 0) {
      result.setLabel("YOU WIN NEW GAME IN 5 SECONDS");
      add(result, APP_WIDTH / 2 - result.getWidth() / 2, APP_HEIGHT - 100);
    }
  }
}
