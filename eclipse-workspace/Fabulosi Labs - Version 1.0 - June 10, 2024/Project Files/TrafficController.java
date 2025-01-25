import java.util.*;

/**
* This is a template for traffic controllers in the simulation.
* @version 1.0
* @author Fabulosi Labs
*/

public abstract class TrafficController extends SimulationObject {
   /**
   * Lets vehicles in x-axis go if true, otherwise lets vehicles in y-axis go
   */
   private boolean xAxis = false;
   /**
   * Traffic intersection types = 3N (Y-junction w/o north path), 3S (Y-junction w/o south path), 3W, 3E (same), 4 (T-junction with all 4 paths)
   */
   private String type;
   /**
   * The period of waiting in miliseconds, subject to change
   */
   private int period; //in miliseconds
   /**
   * The last time frame used for timekeeping
   */
   private long lastTime;
   /**
   * Indicates if this object has been selected by the player.
   */
   private boolean chosen = false;
   
   /**
   * Class constructor.
   * @param i Identifier
   * @param type Type of Traffic Controller
   * @param x X-position
   * @param y Y-position
   * @param localLayer Local layer within the simulation
   * @param miliseconds Number of miliseconds for setting period
   */
   public TrafficController(String i, String type, int x, int y, int localLayer, int miliseconds){
      super(i, x, y, localLayer);
      try {
         if (type.equals("3N") || type.equals("3S") || type.equals("3W") ||
            type.equals("3E") || type.equals("4")){
            this.type = type;
            this.period = miliseconds;
            lastTime = System.currentTimeMillis() - period;
         }
         else {
            throw new Exception();
         }
      }
      catch (Exception e){
         System.out.println("INVALID INTERSECTION TYPE!");
         e.printStackTrace();
      }
   }
   
   /**
   * Toggles between horizontal and verticle axis, in other words toggles xAxis
   */
   public void switchAxis(){
      xAxis = !xAxis;
   }
   
   /**
   * Updates time. If a period has passed, switch axes.
   */
   public void onTick(){
      long thisTime = System.currentTimeMillis();
      if (!SimulationData.simStatus().equals("RUNNING")) return;
      if (thisTime - lastTime >= period){
         lastTime = thisTime;
         switchAxis();
         System.out.println(getIdentifier() + " HAS SWITCHED AXES!");
      }
   }
   
   /**
   * Returns the Traffic Controller's type
   * @return the Traffic Controller's type
   */
   public String getType(){
      return type;
   }
   
   /**
   * Returns if xAxis is true or not
   * @return xAxis
   */
   public boolean isXAxis(){
      return xAxis;
   }
   
   /**
   * Returns the current period.
   * @return current period
   */
   public int getPeriod(){
      return period;
   }
   
   /**
   * Sets the period of the traffic controller to val.
   * @param val a certain value
   */
   public void setPeriod(int val){
      period = val;
   }
   
   /**
   * Returns the boolean value of chosen
   * @return value of chosen
   */
   public boolean getChosenStatus(){
      return chosen;
   }
   
   /**
   * Upgrades this object to a higher level one (unused).
   */
   public abstract void upgrade();
   /**
   * Degrades this object to a lower-level one.
   */
   public abstract void degrade(); // For undos
   
   /**
   * Checks if the mouse click is on the traffic controller.
   * @param x The mouse's x-position
   * @param y The mouse's y-position
   * @return true if clicked, false if not.
   */
   public boolean onMouseClick(int x, int y){ //Returns true if clicked, false if not
      if (x >= getX() - 12 - 14 && x <= getX() + 24 - 12 - 14 && y >= getY() - 24 - 14 && y <= getY() + 54 - 14){
         ArrayList<TrafficController> traff = LayerManager.getTrafficList();
         for (int i = 0; i < traff.size(); i++){
            traff.get(i).setChosenStatus(false);
         }
         chosen = true; //Enables/disables chosen
         ProgramDriver.updateUI("individual");
         return true;
      }
      else return false;
   }
   
   /**
   * Sets chosen to a certain boolean value
   * @param val a certain boolean value
   */
   public void setChosenStatus(boolean val){
      chosen = val;
   }
}