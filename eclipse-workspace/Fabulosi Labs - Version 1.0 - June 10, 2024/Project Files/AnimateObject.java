import java.util.*;

/**
* This is a template for animate objects in the simulation: Pedestrians and Vehicles.
* @version 0.3
* @author Fabulosi Labs
*/

public abstract class AnimateObject extends SimulationObject {
   /**
   * The Animate Object's speed
   */
   private double speed;
   
   /**
   * If true, this object is still moving to its destination.
   * Otherwise, it is not.
   */
   private boolean onTheMove;
   
   /**
   * If true, it is being stopped by a nearby Traffic Controller.
   * Otherwise, it is not being limited.
   */
   private boolean stopped;
   
   /**
   * Dimension of the Animate Object
   */
   private int width, height;
   
   /**
   * Coordinates of the current destination
   */
   private int[] destination = new int[2];
   
   /**
   * The number of remaining steps left before exiting the simulation.
   */
   private int remainingSteps = (int) (Math.random() * 51 + 100);
   
   /**
   * Stores the closest Traffic Controller
   */
   private TrafficController controller;
   
   /**
   * The object's patience level, which affects the satisfaction level in the simulation.
   */
   private int patienceLevel;
   
   /**
   * The last time frame before waiting.
   */
   private long lastTime;

   /**
   * Class constructor.
   * @param i Identifier
   * @param speed Speed
   * @param x X-position
   * @param y Y-position
   * @param width Width
   * @param height Height
   * @param localLayer Local layer
   */
   public AnimateObject(String i, double speed, int x, int y, int width, int height, int localLayer){
      super(i, x, y, localLayer);
      this.speed = 1/speed;
      onTheMove = false;
      stopped = false;
      this.width = width;
      this.height = height;
      
      destination[0] = x;
      destination[1] = y;
      
      patienceLevel = (int) (Math.random() * 5 + 3) * 1000;
      lastTime = System.currentTimeMillis();
      
      LayerManager.add(this);
   }
   
   /**
   * Approaches a certain coordinate.
   * @param x X-position
   * @param y Y-position
   */
   public void approachXY(int x, int y){
      if (Math.abs(getX() - x) < 35 && Math.abs(getY() - y) < 10){
         onTheMove = false;
         return;
      }
      
      int distX = Math.abs(getX() - x);
      int distY = Math.abs(getY() - y);
      
      double headX, headY;
      headX = headY = 0;
      
      if (x >= getX()) headX = Math.round(getX() + Math.round(distX / speed));
      else if (x < getX()) headX = Math.round(getX() - Math.round(distX / speed));
      
      if (y >= getY()) headY = Math.round(getY() + Math.round(distY / speed));
      else if (y < getY()) headY = Math.round(getY() - Math.round(distY / speed));
      
      setX((int) headX);
      setY((int) headY);
   }
   
   /**
   * Decides the next move for the object
   */      
   public void nextMove(){ 
      if (stopped){
         System.out.println(getIdentifier() + " should be stopped!!!");
         int random = (int) Math.random() * 11;
         if (random < 4){
            decideDestination();
         }
         else stopAndWait();
         return;
      }
      if (onTheMove){
         lastTime = System.currentTimeMillis();
         if ((getX() >= 0 && getX() <= ProgramDriver.getMainFrameWidth())
            && (getY() >= 0 && getY() <= ProgramDriver.getMainFrameHeight())){
            approachXY(destination[0], destination[1]);
         }
         else LayerManager.remove(this);
         return;
      }
      
      if (!stopped) decideDestination();
      
      if (stopped){
      }
   }
   
   /**
   * If waiting longer than patience level, decrease satisfaction level
   */
   public void stopAndWait(){
      long thisTime = System.currentTimeMillis();
      
      if (thisTime - lastTime > patienceLevel & (thisTime - lastTime) % patienceLevel == 0){
         SimulationData.changeSatisfaction((int) (-5 * (thisTime / lastTime)));
      }
   }
   
   /**
   * Get the true y-position (taking height into account) for rendering.
   */
   public int trueX(){
      return getX() + (width / 2);
   }
   
   /**
   * Get the true y-position (taking height into account) for rendering.
   */
   public int trueY (){
      return getY() + height;
   }
   
   /**
   * Decides the next destination for the object.
   */
   public abstract void decideDestination();
   
   /**
   * Sets stopped to a certain value.
   * @param val The new value.
   */
   public void setStopped(boolean val){
      stopped = val;
   }
   
   /**
   * Sets onTheMove to a certain value
   * @param val The new value.
   */
   public void setOnTheMove(boolean val){
      onTheMove = val;
   }
   
   /**
   * Returns onTheMove.
   * @return onTheMove's boolean value
   */
   public boolean getOnTheMove(){
      return onTheMove;
   }
   
   /**
   * Returns stopped.
   * @return stopped's boolean value
   */
   public boolean getStopped(){
      return stopped;
   }
   
   /**
   * Returns the current destination.
   * @return The object's current destination
   */
   public int[] getDestination(){
      return destination;
   }
   
   /**
   * Returns the object's current width.
   * @return The object's width
   */
   public int getWidth(){
      return width;
   }
   
   /**
   * Returns the object's current height.
   * @return The object's height
   */
   public int getHeight(){
      return height;
   }
   
   /**
   * Sets destination to a new coordinate.
   * @param x X-position
   * @param y Y-position
   */
   public void setDestination(int x, int y){
      destination = new int[2];
      destination[0] = x;
      destination[1] = y;
   }
   
   /**
   * Returns the object's speed.
   * @return The object's speed
   */
   public double getSpeed(){
      return speed;
   }
   
   /**
   * Returns the object's remaining steps.
   * @return The object's remaining steps
   */
   public int getRemainingSteps(){
      return remainingSteps;
   }
   
   /**
   * Returns the object's closest traffic controller.
   * @return The object's closest traffic controller
   */
   public TrafficController getTrafficController(){
      return controller;
   }
   
   /**
   * Decrement the object's remaining steps by 1.
   */
   public void makeAStep(){
      remainingSteps--;
   }
   
   /**
   * Gets the closest Traffic Controller.
   */
   public void findNearestController(){
      boolean found = false;
      TrafficController temp = controller;
      ArrayList<TrafficController> list = LayerManager.getTrafficList();
      double shortest = Double.MAX_VALUE;
      double shortestX = Double.MAX_VALUE;
      int[] pos = {trueX(), trueY()};
      for (int i = 0; i < list.size(); i++){
         TrafficController obj = list.get(i);
         int[] cpos = {obj.trueX(), obj.trueY()};
         if (calc2dDist(obj) > 205) continue;
         if (calc2dDist(pos, cpos) <= shortest && Math.abs(trueX() - obj.trueX()) <= shortestX){
            shortest = calc2dDist(pos, cpos);
            shortestX = Math.abs(trueX() - obj.trueX());
            controller = obj;
            found = true;
         }
      }
      //System.out.println("Nearest Traff Cont. is " + calc2dDist(controller) + " units away.");
      if (!found && temp == controller) controller = null;
      else if (controller != null) System.out.println(getIdentifier() + "'s Traffic Controller: " + controller.getIdentifier() + " " + calc2dDist(controller));
      if (controller == null) System.out.println(getIdentifier() + " fails to find a nearby Traffic Controller!");
   }
   
   /**
   * Swaps dimensions (width to height, height to width).
   * Only Vehicle objects use this method.
   */
   public void swapDimensions(){
      int temp = width;
      width = height;
      height = temp;
   }
}
