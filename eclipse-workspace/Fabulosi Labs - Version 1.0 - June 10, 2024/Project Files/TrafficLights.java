import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;

import java.awt.image.*;
import javax.imageio.*;

/**
* This is a template for traffic lights in the simulation.
* @version 1.0
* @author Fabulosi Labs
*/

public class TrafficLights extends TrafficController {
   /**
   * Wait time for the objects in the name
   */
   private int pedestrianWait, vehicleWait;
   
   /**
   * Full class constructor.
   * @param i Identifier
   * @param type Type of the traffic lights
   * @param x X-position
   * @param y Y-position
   * @param localLayer Local layer within the simulation
   * @param miliseconds Waiting period in miliseconds
   */
   public TrafficLights(String i, String type, int x, int y, int localLayer, int miliseconds){
      super(i, type, x, y, localLayer, miliseconds);
      pedestrianWait = vehicleWait = miliseconds;
      LayerManager.add(this);
   }
   
   /**
   * Class constructor without setting up the waiting period.
   * @param i Identifier
   * @param type Type of the traffic lights
   * @param x X-position
   * @param y Y-position
   * @param localLayer Local layer within the simulation
   */
   public TrafficLights(String i, String type, int x, int y, int localLayer){
      this(i, type, x, y, localLayer, 2000);
   }
   
   /**
   * Draws the object
   * @param g Graphics used to draw the object
   */
   public void draw (Graphics g){ //Temporary graphics
      /*//Head
      g.setColor(new Color(225, 220, 177));
      g.fillOval(getX() - 12 - 14, getY() - 24 - 14, 24, 24);
      g.setColor(Color.black);
      g.drawOval(getX() - 12 - 14, getY() - 24 - 14, 24, 24);
      
      //Torso
      int[] triX = {getX() - 12 - 14, getX() - 14, getX() + 12 - 14};
      int[] triY = {getY() + 30 - 14, getY() - 14, getY() + 30 - 14};
      g.setColor(new Color(50, 219, 35));
      g.fillPolygon(triX, triY, 3);
      g.setColor(Color.black);
      g.drawPolygon(triX, triY, 3);
      
      */
      
      try {
         File f = new File("Assets/lights-normal.png");
         BufferedImage img = ImageIO.read(f);
         g.drawImage(img, getX() - 28, getY() - 38, 24, 38 + 16, null);
      }
      catch (IOException e){
         System.out.println("Drawing " + getIdentifier() + " Failed.");
         e.printStackTrace();
      }
      
      g.setColor(Color.red);
      if (SimulationData.simStatus().equals("RUNNING") && isXAxis()) g.drawLine(getX() - 30, getY() - 58, getX() - 30 + 35, getY() - 58);
      else if (SimulationData.simStatus().equals("RUNNING") && !isXAxis()) g.drawLine(getX() - 14, getY() - 32 - 19, getX() - 14, getY() - 32 - 32);
      
      //Chosen halo
      if (getChosenStatus()){
         g.setColor(Color.orange);
         int w = 70;
         int h = 30;
         while (w >= 60 && h >= 20){
            g.drawArc(trueX() - (w / 2), trueY() - (h / 2), w, h, 135, 270);
            w = (int) (w * 49 / 50.0);
            h = (int) (h * 49 / 50.0);
         }
      }
      
      //Prioritize pedestrians or vehicles
      ArrayList<AnimateObject> traffList = LayerManager.getAnimateList();
      int pedestrianCount, vehicleCount;
      pedestrianCount = vehicleCount = 0;
      for (int i = 0 ; i < traffList.size(); i++){
         if (traffList.get(i) instanceof Pedestrian && calc2dDist(traffList.get(i)) < 40) pedestrianCount++;
         else if (traffList.get(i) instanceof Vehicle && calc2dDist(traffList.get(i)) < 40) vehicleCount++;
      }
      
      if (pedestrianCount >= vehicleCount) setPeriod(pedestrianWait);
      else setPeriod(vehicleWait);
      
      onTick();
   }
   
   /**
   * Upgrades this object to a higher level one (unused).
   */
   public void upgrade(){
      System.out.println(Thread.currentThread().getStackTrace()[2]);
      AITrafficLights up = new AITrafficLights(getIdentifier(), getType(), getX(), getY(), getLocalLayer());
      up.setChosenStatus(true);
      LayerManager.remove(this);
      SimulationData.changeBudget(-20000);
      System.out.println(getIdentifier() + " upgraded!");
      if (ProgramDriver.getProgramState().equals("level 2")){
         for (int i = 0; i < SimulationData.list2.length; i++){
            if (SimulationData.list2[i].getIdentifier().equals(getIdentifier())){
               SimulationData.list2[i] = up;
               break;
            }
         }
      }
   }
   
   /**
   * Degrades this object to a lower-level one.
   */
   public void degrade(){
      TrafficOfficer down = new TrafficOfficer(getIdentifier(), getType(), getX(), getY(), getLocalLayer());
      if (getChosenStatus()) down.setChosenStatus(true);
      setChosenStatus(false);
      LayerManager.remove(this);
      SimulationData.changeBudget(180000);
      if (ProgramDriver.getProgramState().equals("level 2")){
         for (int i = 0; i < SimulationData.list2.length; i++){
            if (SimulationData.list2[i].getIdentifier().equals(getIdentifier())){
               SimulationData.list2[i] = down;
               break;
            }
         }
      }
   }
   
   /**
   * Sets the x-position as seen on the simulation.
   * @param x the x-position according to the simulation.
   */
   public void setTrueX(int x){
      setX(x + 28 - 12);
   }
   
   /**
   * Sets the y-position as seen on the simulation.
   * @param y the y-position according to the simulation.
   */
   public void setTrueY(int y){
      setY(y - 16);
   }
   
   /**
   * Returns the x-position as seen on the simulation.
   * @return the x-position according to the simulation
   */
   public int trueX(){
      return getX() - 28 + 12;
   }
   
   /**
   * Returns the y-position as seen on the simulation.
   * @return the y-position according to the simulation
   */
   public int trueY(){
      return getY() + 16;
   }
   
   /**
   * Sets the waiting period for pedestrians to val.
   * @param val a certain value
   */
   public void setPedestrianWait (int val){
      pedestrianWait = val;
   }
   
   /**
   * Sets the waiting period for vehicles to val.
   * @param val a certain value
   */
   public void setVehicleWait(int val){
      vehicleWait = val;
   }
   
   /**
   * Returns the waiting period for pedestrians.
   * @return waiting period for pedestrians
   */
   public int pedestrianWait(){
      return pedestrianWait;
   }
   
   /**
   * Returns the waiting period for vehicles.
   * @return waiting period for vehicles
   */
   public int vehicleWait(){
      return vehicleWait;
   }
}