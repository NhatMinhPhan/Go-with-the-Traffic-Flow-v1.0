import java.util.*;
import java.awt.*;
import javax.swing.*;

/**
* This is a template for vehicles in the simulation.
* @version 1.0
* @author Fabulosi Labs
*/

public class Vehicle extends AnimateObject {
   /**
   * Colour of the vehicle
   */
   private Color color;
   /**
   * Indicates if oriented horizontally
   */
   private boolean orientedX = false;
   
   /**
   * Indicates which element of path of index posNum the vehicle is heading towards
   */
   private int posNum = 0;
   /**
   * Stores the path of the vehicle.
   */
   private int[][] path = new int[0][2];
   
   /**
   * Path for this version of the script.
   */
   private int[][] tempPath = {{1, 390}, {44, 390}, {218, 390}, {661, 390}, {786, 390}};
   
   /*
   private int[][] entrances = {{1, 390}};
   private int[][] exits = {{1058, 659}};
   */
   
   /**
   * The orientation of the vehicle
   * Values: 0 = North, 1 = East, 2 = South, 3 = West
   */
   private int direction = 0;
   
   /**
   * Class constructor.
   * @param i Identifier
   * @param speed Vehicle's speed
   * @param x X-position
   * @param y Y-position
   * @param localLayer Local layer within the simulation
   */
   public Vehicle(String i, double speed, int x, int y, int localLayer){
      super(i, speed, x, y, 50, 30, localLayer);
      color = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
      planPath();
      setTrueX(path[0][0]);
      setTrueY(path[0][1]);
      System.out.println(getIdentifier() + " created!");
   }
   
   /**
   * Draws this object.
   * @param g Graphics used to draw it.
   */
   public void draw (Graphics g){
      g.setColor(color);
      g.fillRect(getX(), getY(), getWidth(), getHeight());
      g.setColor(Color.black);
      g.drawRect(getX(), getY(), getWidth(), getHeight());
      
      findNearestController();
      int[] myPos = {trueX(), trueY()};
      int[] cPos = new int[2];
      if (getTrafficController() != null){
         cPos[0] = getTrafficController().trueX();
         cPos[1] = getTrafficController().trueY();
      }
      
      if (getTrafficController() != null
         && ((getTrafficController().isXAxis() && (direction == 1 || direction == 3))
         || (!getTrafficController().isXAxis() && (direction == 0 || direction == 2)))) 
      {
         setStopped(false);
         System.out.println(getIdentifier() + " just gets let go by " + getTrafficController().getIdentifier() + "!");
      }
      else if (getTrafficController() != null
         && ((!getTrafficController().isXAxis() && (direction == 1 || direction == 3))
         || (getTrafficController().isXAxis() && (direction == 0 || direction == 2)))){
         setStopped(true);
         System.out.println(getIdentifier() + " just gets stopped by " + getTrafficController().getIdentifier() + "!");
      }
      nextMove();
   }
   
   /**
   * Decides the next destination (unused)
   */
   public void decideDestination(){
      return;
   }
   
   /*
   private String getCoordinateType(int x, int y){
      int[] c = {x, y};
      for (int[] arr : horizDown){
         if (Arrays.equals(c, arr)) return "horizDown";
      }
      for (int[] arr : horizUp){
         if (Arrays.equals(c, arr)) return "horizUp";
      }
      for (int[] arr : vertBottomUp){
         if (Arrays.equals(c, arr)) return "vertBottomUp";
      }
      for (int[] arr : vertBottomDown){
         if (Arrays.equals(c, arr)) return "vertBottomDown";
      }
      for (int[] arr : top1Left){
         if (Arrays.equals(c, arr)) return "top1Left";
      }
      for (int[] arr : top1Right){
         if (Arrays.equals(c, arr)) return "top1Right";
      }
      for (int[] arr : top2Left){
         if (Arrays.equals(c, arr)) return "top2Left";
      }
      for (int[] arr : top2Right){
         if (Arrays.equals(c, arr)) return "top2Right";
      }
      for (int[] arr : cornerTop){
         if (Arrays.equals(c, arr)) return "cornerTop";
      }
      for (int[] arr : cornerBottom){
         if (Arrays.equals(c, arr)) return "cornerBottom";
      }
      return "none";
   }
   
   private String getCoordinateType(int[] c){
      return getCoordinateType(c[0], c[1]);
   }*/
      
   /**
   * Plans out the vehicle's path upon being constructed
   */
   public void planPath(){
      for (int[] s : tempPath){
         addToPath(s[0], s[1]);
      }
      addToPath(ProgramDriver.getMainFrameWidth() + 100, path[path.length - 1][1]);
   }
   
   /**
   * Decides the vehicle's next move.
   */
   public void nextMove(){
      if (getStopped()) return; //If this object is still on the move, keep going
      
      if ((getX() >= 0 && getX() <= ProgramDriver.getMainFrameWidth())
         && (getY() >= 0 && getY() <= ProgramDriver.getMainFrameHeight())){
         approachXY(path[posNum][0], path[posNum][1]);
      }
      else LayerManager.remove(this);
      return;
   }
   
   /**
   * Approaches a certain coordinate.
   * @param x X-position of destination
   * @param y Y-position of destination
   */
   public void approachXY(int x, int y){
      if (Math.abs(getX() - x) < 5 && Math.abs(getY() - y) < 5){
         posNum++;
      }
      
      int distX = Math.abs(getX() - x);
      int distY = Math.abs(getY() - y);
      
      double headX, headY;
      headX = headY = 0;
      
      if (x >= getX()) headX = Math.round(getX() + Math.round(distX / getSpeed()));
      else if (x < getX()) headX = Math.round(getX() - Math.round(distX / getSpeed()));
      
      if (y >= getY()) headY = Math.round(getY() + Math.round(distY / getSpeed()));
      else if (y < getY()) headY = Math.round(getY() - Math.round(distY / getSpeed()));
      
      setX((int) headX);
      setY((int) headY);
   }
   
   /**
   * Adds a coordinate to a path
   * @param x X-position of new destination
   * @param y Y-position of new destination
   */
   private void addToPath(int x, int y){
      int[][] temp = new int[path.length + 1][2];
      for (int i = 0 ; i < path.length; i++){
         temp[i] = path[i];
      }
      temp[temp.length - 1][0] = x;
      temp[temp.length - 1][1] = y;
      path = temp;
   }
   
   /**
   * Checks if this vehicle collides with a Pedestrian (used only in Level 2)
   */
   public void collisionDetection(){
      if (!ProgramDriver.getProgramState().equals("level 2")) return;
   
      ArrayList<AnimateObject> list = LayerManager.getAnimateList();
      for (AnimateObject obj : list){
         if (obj instanceof Pedestrian){
            if (  getX() + getWidth() >= obj.getX() &&
                  getX() <= obj.getX() + obj.getWidth() &&
                  getY() + getHeight() >= obj.getY() &&
                  getY() <= obj.getY() + obj.getHeight()
               ){
               for (int i = 0; i < list.size(); i++){
                  list.get(i).setStopped(true);
                  list.get(i).setOnTheMove(false);
               }
               LayerManager.setCamera(new Camera(this, 112, 90));
               LayerManager.setCameraActivity(true);
            }
         }
      }
   }
   
   /**
   * Sets the x-position as seen on the simulation.
   * @param x the x-position according to the simulation.
   */
   public void setTrueX(int x){
      setX(x - (getWidth() / 2));
   }
   
   /**
   * Sets the y-position as seen on the simulation.
   * @param y the y-position according to the simulation.
   */
   public void setTrueY(int y){
      setY(y - getHeight());
   }
   
   /**
   * Returns the x-position as seen on the simulation.
   * @return the x-position according to the simulation
   */
   public int trueX(){
      return getX() + (getWidth() / 2);
   }
   
   /**
   * Returns the y-position as seen on the simulation.
   * @return the y-position according to the simulation
   */
   public int trueY(){
      return getY() + (getHeight() / 2);
   }
   
   /**
   * Swaps the width with the height and vice versa.
   */
   public void swapDimensions(){
      super.swapDimensions();
      orientedX = !orientedX;
   }
}