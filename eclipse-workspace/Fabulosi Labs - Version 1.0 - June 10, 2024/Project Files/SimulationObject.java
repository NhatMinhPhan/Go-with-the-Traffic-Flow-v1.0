/**
* This is the template for SimulationObject objects, which are used in the simulation itself.
* @version 0.4
* @author Fabulosi Labs
*/

public abstract class SimulationObject extends LayeredObject {
   /**
   * Stores the local layer in the simulation.
   */
   private int localLayer;
   
   /**
   * Class constructor
   * @param i Identifier
   * @param x X-position
   * @param y Y-position
   * @param localLayer Local layer in the simulation
   */
   public SimulationObject(String i, int x, int y, int localLayer){
      super(i, x, y);
      this.localLayer = localLayer;
   }
   
   /**
   * Returns the x-position as seen on the simulation.
   * @return the x-position according to the simulation
   */
   public abstract int trueX ();
   
   /**
   * Returns the y-position as seen on the simulation.
   * @return the y-position according to the simulation
   */
   public abstract int trueY ();
   
   /**
   * Sets the x-position as seen on the simulation.
   * @param x the x-position according to the simulation.
   */
   public abstract void setTrueX(int x);
   
   /**
   * Sets the y-position as seen on the simulation.
   * @param y the y-position according to the simulation.
   */
   public abstract void setTrueY(int y);
   
   /**
   * Swaps local layers with other Simulation Object
   * @param otherObj another Simulation Object
   */
   public void swapLocalLayers (SimulationObject otherObj){
      int temp = localLayer;
      localLayer = otherObj.getLocalLayer();
      otherObj.setLocalLayer(temp);
   }
   
   /**
   * Calculates the 2D distance between pos1 and pos2.
   * @param pos1 A coordinate
   * @param pos2 Another coordinate
   * @return the 2D distance between pos1 and pos2
   */
   public double calc2dDist(int[] pos1, int[] pos2){
      return Math.sqrt(Math.pow(pos1[0] - pos2[0], 2) + Math.pow(pos1[1] - pos2[1], 2));
   }
   
   /**
   * Calculates the 2D distance between this object and another SimulationObject.
   * @param other Another Simulation Object
   * @return the 2D distance between the two objects
   */
   public double calc2dDist(SimulationObject other){
      int[] pos1 = {trueX(), trueY()};
      int[] pos2 = {other.trueX(), other.trueY()};
      return calc2dDist(pos1, pos2);
   }
   
   /**
   * Calculates the 2D distance between this object and a coordinate.
   * @param pos a certain coordinate
   * @return the 2D distance between this object and a coordinate
   */
   public double calc2dDist(int[] pos){
      int[] p = {trueX(), trueY()};
      return calc2dDist(p, pos);
   }
   
   /**
   * Sets the local layer of this Simulation Object
   * @param val New local layer
   */
   public void setLocalLayer(int val){
      localLayer = val;
   }
   
   /**
   * Returns the local layer of this Simulation Object
   * @return the local layer of this Simulation Object
   */
   public int getLocalLayer(){
      return localLayer;
   }
}
