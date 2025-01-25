//PEDESTRIANS' COORDINATES:
//Bottommost horizontal: (18, 526), (84, 546), (287, 549), (415, 543), (725, 551), (860, 549), (1103, 542)
//2nd topmost horizontal: (42, 314), (427, 326), (729, 329), (873, 329), (1102, 327)
//Sandwich horizontal: (428, 236), (725, 237), (875, 236), (1099, 236)
//Topmost horizontal: (423, 42), (842, 41), (1138, 42)

//Inaccessible bottommost horizontal: (89, 827), (296, 826), (410, 826), (727, 826), (857, 828), (1106, 825)

//Exit positions: (18, 256) - left, (42, 314) - left, (423, 42) - top, (842, 41) - top, (1138, 42) - right, (1102, 327) - right,
//Exits (cont.): (89, 827) - down, (296, 826) - down, (410, 826) - down, (727, 826) - down, (857, 828) - down, (1106, 825) - down.

import java.util.*;

import java.awt.*;
import javax.swing.*;

/**
* This is the template for Pedestrian objects, which "walk" around the environment.
* @version 1.0
* @author Fabulosi Labs
*/

public class Pedestrian extends AnimateObject {
   /**
   * Coordinates used in Level 1
   */
   private static int[][] coordinates1 = {{18, 526}, {84, 546}, {287, 549}, {415, 543}, {725, 551}, {860, 549},
      {1103, 542}, {42, 314}, {427, 314}, {729, 314}, {873, 314}, {1102, 314}, {423, 42}, {842, 41}, {1138, 42},
      {428, 236}, {725, 237}, {875, 236}, {1099, 236},
      {89, 827}, {296, 826}, {410, 826}, {727, 826}, {857, 828}, {1106, 825}};
   
   /**
   * Coordinates used in Level 2
   */
   private static int[][] coordinates2 = {{330, 280}, {571, 280}, {878, 280},
      {1127, 280}, {1127, 534}, {878, 534}, {571, 534}, {318, 534},
      {34, 280}, {34, 526}, {330, 66}, {571, 66}, {878, 66}, {1127, 66}, {1127, 669}, {878, 669}, {571, 669}, {318, 669}};
   /**
   * Random colour for their clothing.
   */
   private Color color;
   
   /**
   * Direction this pedestrian is going
   */
   private int direction;
   
   /**
   * Class constructor.
   * @param i Identifier
   * @param speed The pedestrian's speed
   * @param x The pedestrian's x-position
   * @param y The pedestrian's y-position
   * @param localLayer The pedestrian's local layer
   */
   public Pedestrian(String i, double speed, int x, int y, int localLayer){
      super(i, speed, x, y, 24, 54, localLayer);
      color = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
   }
   
   /**
   * Determines if a point is within a certain range of the target
   * @param target The coordinates of the target (any object)
   * @param point The coordinates of a certain point
   * @return a boolean value that indicates if the point is within a certain range of the target
   */
   private boolean closeEnough(int[] target, int[] point){
      if (Arrays.equals(target, point)) return true;
      //if (Math.abs(point[0] - target[0]) <= 20 && Math.abs(point[1] - target[1]) <= 20) return true;
      if (Math.sqrt(Math.pow(point[0] - target[0], 2) + Math.pow(point[1] - target[1], 2)) <= 35) return true;
      return false;
   }
   
   /**
   * Checks if the current position of this object is an exit.
   * This is used in Level 1.
   * @return Null if it is not an exit or exitable, or The type of exit: "left", "up", "right" or "down"
   */
   private String isExitable1(){
      int[][] leftExits = {{18, 526}, {42, 314}};
      int[][] upExits = {{423, 42}, {842, 41}};
      int[][] rightExits = {{1138, 42}, {1099, 236}};
      int[][] downExits = {{89, 827}, {296, 826}, {410, 826}, {727, 826}, {857, 828}, {1106, 825}};
      
      int[][] finalExits = {{-50, getY()}, {getX(), -50}, {ProgramDriver.getMainFrameWidth() + 50, getY()}, {getX(), ProgramDriver.getMainFrameHeight() + 50}};
      
      int[] currentPos = {trueX(), trueY()};
      
      for (int[] exit : finalExits){
         if (closeEnough(currentPos, exit)) return "over";
      }
      for (int[] exit : leftExits){
         if (closeEnough(currentPos, exit)) return "left";
      }
      for (int[] exit : upExits){
         if (closeEnough(currentPos, exit)) return "up";
      }
      for (int[] exit : rightExits){
         if (closeEnough(currentPos, exit)) return "right";
      }
      for (int[] exit : downExits){
         if (closeEnough(currentPos, exit)) return "down";
      }
      
      return null;
   }
   
   /**
   * Checks if the current position of this object is an exit.
   * This is used in Level 2.
   * @return Null if it is not an exit or exitable, or The type of exit: "left", "up", "right" or "down"
   */
   private String isExitable2(){ //Returns "left", "up", "right" or "down"
      int[][] leftExits = {{34, 280}, {34, 526}};
      int[][] upExits = {{330, 66}, {571, 66}, {878, 66}, {1127, 66}};
      int[][] downExits = {{1127, 669}, {878, 669}, {571, 669}, {318, 669}};
      
      int[][] finalExits = {{-50, getY()}, {getX(), -50}, {ProgramDriver.getMainFrameWidth() + 50, getY()}, {getX(), ProgramDriver.getMainFrameHeight() + 50}};
      
      int[] currentPos = {trueX(), trueY()};
      
      for (int[] exit : finalExits){
         if (closeEnough(currentPos, exit)) return "over";
      }
      for (int[] exit : leftExits){
         if (closeEnough(currentPos, exit)) return "left";
      }
      for (int[] exit : upExits){
         if (closeEnough(currentPos, exit)) return "up";
      }
      for (int[] exit : downExits){
         if (closeEnough(currentPos, exit)) return "down";
      }
      
      return null;
   }
   
   /**
   * Finds the pedestrian's next destination.
   * This is used in Level 1.
   */
   public void decideDestination1(){
      
   
      String e = isExitable1();
      
      if (e != null && !e.equals(null)){
         if (getRemainingSteps() <= 0){
            switch (e){
               case "left":
                  setDestination(-50, getY());
                  break;
               case "up":
                  setDestination(getX(), -50);
                  break;
               case "right":
                  setDestination(ProgramDriver.getMainFrameWidth() + 50, getY());
                  break;
               case "down":
                  setDestination(getX(), ProgramDriver.getMainFrameHeight() + 50);
                  break;
               case "over":
                  LayerManager.remove(this);
                  return;
            }
            setOnTheMove(true);
            return;
         }
      }
      
      direction = 0;
      int[] pedestrianPos = {getX(), getY()};
      int[] pos1 = {42, 314};
      int[] pos2 = {18, 526};
      int[] pos3 = {423, 42}; 
      int[] pos4 = {1138, 42};
      int[] pos5 = {1102, 327};
      int[] pos6 = {1103, 542};
      System.out.println(getIdentifier() + "'s Y: " + getY());
      if (getY() > 680 + 30 - 14) //Inaccessible bottommost coordinates1
      {
         System.out.println("Y-pos of " + getIdentifier() + " is over 680! " + getY());
         direction = 0;
      }
      else if (Math.abs(trueY() - pos1[1]) > Math.abs(trueY() - pos2[1]) && closeEnough(pos1, pedestrianPos)){
         System.out.println("Pos1  " + calc2dDist(pedestrianPos, pos1) + " / " + calc2dDist(pedestrianPos, pos2));
         direction = 1;
      }
      
      else if (closeEnough(pos2, pedestrianPos) || closeEnough(pos3, pedestrianPos)){
         if (getY() <= pos2[1]){
            System.out.println("Pos2  " + calc2dDist(pedestrianPos, pos1) + " / " + calc2dDist(pedestrianPos, pos2));
            direction = (int) (Math.random() * 2 + 1);
         }
         else {
            System.out.println("Pos3");
            direction = (int) (Math.random() * 2 + 1);
         }
      }
      
      else if (closeEnough(pos4, pedestrianPos)){
         System.out.println("Pos4");
         direction = (int) (Math.random() * 2 + 2);
      }
      else if (closeEnough(pos5, pedestrianPos) || closeEnough(pos6, pedestrianPos)){
         System.out.println("Pos5/6");
         direction = (int) (Math.random() * 3 + 2);
         if (direction >= 4) direction = 0;
      }
      else {
         System.out.println("General!  " + calc2dDist(pedestrianPos, pos1) + " / " + calc2dDist(pedestrianPos, pos2));
         direction = (int) (Math.random() * 4); // 0: N, 1: E, 2: S, 3: W
      }
      
      System.out.println(getIdentifier() + "'s direction is: " + direction);
      
      //Find a nearby traffic controller, check:
      findNearestController();
      int[] myPos = {trueX(), trueY()};
      int[] cPos = new int[2];
      if ((getTrafficController() != null && !getTrafficController().equals(null))
         && ((getTrafficController().isXAxis() && (direction == 1 || direction == 3)) || (!getTrafficController().isXAxis() && (direction == 0 || direction == 2)))) 
      {
         setStopped(true);
         System.out.println(getIdentifier() + " just gets stopped by " + getTrafficController().getIdentifier() + "!");
      }
      
      double shortestX = Double.MAX_VALUE;
      double shortestY = Double.MAX_VALUE;
      for (int i = 0; i < coordinates1.length; i++){
         int[] position = coordinates1[i];
         double distX = Math.abs(position[0] - trueX());
         double distY = Math.abs(position[1] - trueY());
         int[] truePos = {trueX(), trueY()};
         if (distX < shortestX && !Arrays.equals(position, truePos)){
            if ((direction == 1 && position[0] > getDestination()[0] && Math.abs(position[0] - getDestination()[0]) <= 35 && onSameDirection(direction, position[1] - trueY(), position[0] - trueX()))
               || (direction == 3 && position[1] < getDestination()[1] && Math.abs(position[1] - getDestination()[1]) <= 35) && onSameDirection(direction, position[1] - trueY(), position[0] - trueX())){
                  shortestX = distX;
               
               setDestination(position[0], position[1]);
            }
         }
         if (distY < shortestY && !Arrays.equals(position, truePos)){
            if ((direction == 0 && position[1] > getDestination()[1] && Math.abs(position[1] - getDestination()[1]) <= 35 && onSameDirection(direction, position[1] - trueY(), position[0] - trueX()))
               || (direction == 2 && position[0] < getDestination()[0] && Math.abs(position[0] - getDestination()[0]) <= 35) && onSameDirection(direction, position[1] - trueY(), position[0] - trueX())){
                  shortestY = distY;
               
               setDestination(position[0], position[1]);
            }
         }
      }
      
      setOnTheMove(true);
      System.out.println(getIdentifier() + "'s new destination: (" + getDestination()[0] + ", " + getDestination()[1] + ")");
      makeAStep();
   }
   
   /**
   * Finds the pedestrian's next destination.
   * This is used in Level 2.
   */
   public void decideDestination2(){
      
      
      direction = 0;
      String e = isExitable2();
      
      if (e != null && !e.equals(null)){
         if (getRemainingSteps() <= 0){
            switch (e){
               case "left":
                  setDestination(-50, getY());
                  new Pedestrian(getIdentifier() + " copy", getSpeed(), 25, getY(), getLocalLayer());
                  break;
               case "up":
                  setDestination(getX(), -50);
                  new Pedestrian(getIdentifier() + " copy", getSpeed(), getX(), 25, getLocalLayer());
                  break;
               case "right":
                  setDestination(ProgramDriver.getMainFrameWidth() + 50, getY());
                  new Pedestrian(getIdentifier() + " copy", getSpeed(), ProgramDriver.getMainFrameWidth() - 25, getY(), getLocalLayer());
                  break;
               case "down":
                  setDestination(getX(), ProgramDriver.getMainFrameHeight() + 50);
                  new Pedestrian(getIdentifier() + " copy", getSpeed(), getX(), ProgramDriver.getMainFrameHeight() - 25, getLocalLayer());
                  break;
               case "over":
                  LayerManager.remove(this);
                  return;
            }
            setOnTheMove(true);
            return;
         }
         else {
            switch (e){
               case "left":
                  direction = 1;
                  setDestination(330, getY());
                  break;
               case "up":
                  direction = 2;
                  setDestination(getX(), 534);
                  break;
               case "right":
                  direction = 3;
                  setDestination(878, getY());
                  break;
               case "down":
                  direction = 0;
                  setDestination(getX(), 280);
                  break;
            }
         }
      }
      
      int[] pedestrianPos = {getX(), getY()};
      
      if (e == null || e.equals(null)) direction = (int) (Math.random() * 4); // 0: N, 1: E, 2: S, 3: W
      
      System.out.println(getIdentifier() + "'s direction is: " + direction);
      
      //Find a nearby traffic controller, check:
      findNearestController();
      int[] myPos = {trueX(), trueY()};
      int[] cPos = new int[2];
      if (getTrafficController() != null){
         cPos[0] = getTrafficController().trueX();
         cPos[1] = getTrafficController().trueY();
      }
      if ((getTrafficController() != null && !getTrafficController().equals(null)) && calc2dDist(myPos, cPos) < 70
         && ((getTrafficController().isXAxis() && (direction == 1 || direction == 3)) || (!getTrafficController().isXAxis() && (direction == 0 || direction == 2)))) 
      {
         setStopped(true);
         System.out.println(getIdentifier() + " just gets stopped by " + getTrafficController().getIdentifier() + "!");
      }
      
      double shortestX = Double.MAX_VALUE;
      double shortestY = Double.MAX_VALUE;
      for (int i = 0; i < coordinates2.length; i++){
         int[] position = coordinates2[i];
         double distX = Math.abs(position[0] - trueX());
         double distY = Math.abs(position[1] - trueY());
         int[] truePos = {trueX(), trueY()};
         
         if (e != null && !e.equals(null)) break;
         if (Arrays.equals(position, truePos)) continue;
         
         if (direction == 1 && position[0] > truePos[0] && distX < shortestX && Math.abs(truePos[1] - position[1]) < 50){
            shortestX = distX;
            setDestination(position[0], position[1]);
         }
         else if (direction == 2 && position[1] > truePos[1] && distY < shortestY && Math.abs(truePos[0] - position[0]) < 50){
            shortestY = distY;
            setDestination(position[0], position[1]);
         }
         else if (direction == 3 && position[0] < truePos[0] && distX < shortestX && Math.abs(truePos[1] - position[1]) < 50){
            shortestX = distX;
            setDestination(position[0], position[1]);
         }
         else if (direction == 0 && position[1] < truePos[1] && distY < shortestY && Math.abs(truePos[0] - position[0]) < 50){
            shortestY = distY;
            setDestination(position[0], position[1]);
         }
         
      }
      
      setOnTheMove(true);
      System.out.println(getIdentifier() + "'s new destination: (" + getDestination()[0] + ", " + getDestination()[1] + ")");
      makeAStep();
   }
   
   /**
   * Finds the pedestrian's next destination according to the level the program is on.
   */
   public void decideDestination(){
      if (ProgramDriver.getProgramState().equals("level 1"))
         decideDestination1();
      else decideDestination2();
   }
   
   /**
   * Checks if the next destination is on the same direction as the direction this object is facing.
   * @param direction The direction the object is facing
   * @param y Vertical distance
   * @param x Horizontal distance
   * @return a boolean value indicating if the next destination is on the same direction as the direction this object is facing.
   */
   private boolean onSameDirection(int direction, int y, int x){
      double degrees = Math.toDegrees(Math.atan2(y, x));
      while (degrees < 0){
         degrees += 360.0;
      }
      while (degrees > 360){
         degrees -= 360.0;
      }
      switch (direction){
         case 0:
            return degrees >= 5 && degrees <= 130;
         case 1:
            return (degrees <= 85 && degrees >= 0) || (degrees >= 275 && degrees <= 360);
         case 2:
            return degrees >= 185 && degrees <= 355;
         case 3:
            return degrees >= 95 && degrees <= 265;
      }
      return false;
   }
   
   /**
   * Draws the pedestrian.
   * @param g Graphics used to draw this object
   */
   public void draw(Graphics g){
      //Head
      g.setColor(new Color(225, 220, 177));
      g.fillOval(getX() - 12 - 14, getY() - 24 - 14, 24, 24);
      g.setColor(Color.black);
      g.drawOval(getX() - 12 - 14, getY() - 24 - 14, 24, 24);
      
      //Torso
      int[] triX = {getX() - 12 - 14, getX() - 14, getX() + 12 - 14};
      int[] triY = {getY() + 30 - 14, getY() - 14, getY() + 30 - 14};
      g.setColor(color);
      g.fillPolygon(triX, triY, 3);
      g.setColor(Color.black);
      g.drawPolygon(triX, triY, 3);
      
      /*
      //Test true coordinates1:
      g.setColor(Color.black);
      g.fillRect(trueX() - 5, trueY() - 5, 10, 10);
      */
      
      //Find a nearby traffic controller, check:
      findNearestController();
      int[] myPos = {trueX(), trueY()};
      int[] cPos = new int[2];
      if (getTrafficController() != null){
         cPos[0] = getTrafficController().trueX();
         cPos[1] = getTrafficController().trueY();
      }
      
      if (getTrafficController() != null
         && ((!getTrafficController().isXAxis() && (direction == 1 || direction == 3))
         || (getTrafficController().isXAxis() && (direction == 0 || direction == 2)))) 
      {
         setStopped(false);
         System.out.println(getIdentifier() + " just gets let go by " + getTrafficController().getIdentifier() + "!");
      }
      else if (getTrafficController() != null
         && ((getTrafficController().isXAxis() && (direction == 1 || direction == 3))
         || (!getTrafficController().isXAxis() && (direction == 0 || direction == 2)))){
         setStopped(true);
         System.out.println(getIdentifier() + " just gets stopped by " + getTrafficController().getIdentifier() + "!");
      }
      System.out.println("I'm alive");
      nextMove();
   }
   
   /**
   * Get the available coordinates according to the level the program is on, which may come in handy when spawning pedestrians.
   * @return A 2D array of all coordinates the object can go to in a certain level.
   */
   public static int[][] getCoordinates(){
      if (ProgramDriver.getProgramState().equals("level 1")) return coordinates1;
      return coordinates2;
   }
   
   /**
   * Returns the x-position as seen on the simulation.
   * @return the x-position according to the simulation
   */
   public int trueX(){
      return getX() - 14;
   }
   
   /**
   * Returns the y-position as seen on the simulation.
   * @return the y-position according to the simulation
   */
   public int trueY(){
      return getY() + 30 - 14;
   }
   
   /**
   * Sets the x-position as seen on the simulation.
   * @param x the x-position according to the simulation.
   */
   public void setTrueX(int x){
      setX(x + 14);
   }
   
   /**
   * Sets the y-position as seen on the simulation.
   * @param y the y-position according to the simulation.
   */
   public void setTrueY(int y){
      setY(y - 30 + 14);
   }
}
