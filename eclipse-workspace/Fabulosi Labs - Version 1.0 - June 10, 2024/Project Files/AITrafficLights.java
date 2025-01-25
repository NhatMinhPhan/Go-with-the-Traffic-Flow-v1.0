import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

/**
* This is a template for traffic lights "with AI implementation" in the simulation.
* @version 0.3
* @author Fabulosi Labs
*/

public class AITrafficLights extends TrafficLights {
   /**
   * Traffic flow count, to be reset if it equals countMax.
   */
   private int flowCount = 1;
   
   /**
   * The count of respective elements up until flowCount is reset.
   */
   private int pedestrianCount, vehicleCount;
   
   /**
   * How many traffic flows must pass before flowCount resets.
   */
   public int countMax;
   
   /**
   * How many a change in the number of cars must be to be considered "exceeding".
   */
   public int carChange;
   
   /**
   * How many expectedCar should increase/decrease by.
   */
   public int expectedCarChange;
   
   /**
   * How many a change in the number of cars must be to be considered "exceeding".
   */
   public int pedChange;
   
   /**
   * How many expectedPed should increase/decrease by.
   */
   public int expectedPedChange;
   
   /**
   * Expected number of respective elements.
   */
   private int expectedCar, expectedPed;

   /**
   * Standard constructor
   * @param i Identifier
   * @param type Type of Traffic Light
   * @param x X-position
   * @param y Y-position
   * @param localLayer Local layer
   * @param miliseconds Wait time in miliseconds
   */
   public AITrafficLights(String i, String type, int x, int y, int localLayer, int miliseconds){
      super(i, type, x, y, localLayer, miliseconds);
      pedestrianCount = vehicleCount = 0;
      LayerManager.add(this);
   }
   
   /**
   * Constructor without wait time in miliseconds, which is set to 2000 by default.
   * @param i Identifier
   * @param type Type of Traffic Light
   * @param x X-position
   * @param y Y-position
   * @param localLayer Local layer
   */
   public AITrafficLights(String i, String type, int x, int y, int localLayer){
      this(i, type, x, y, localLayer, 2000);
   }
   
   /**
   * Draws the object.
   * @param g Graphics used to draw on.
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
      g.setColor(Color.red);
      g.fillPolygon(triX, triY, 3);
      g.setColor(Color.black);
      g.drawPolygon(triX, triY, 3);
      
      */
      
      try {
         File f = new File("Assets/lights-ai.png");
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
      
      track();
      onTick();
   }
   
   /**
   * Upgrades this object to a higher level one (unused).
   */
   public void upgrade(){
      return;
   }
   
   /**
   * Tracks nearby Animate Objects to adapt its programming.
   */
   public void track(){
      if (flowCount == countMax){
         if (pedestrianCount - pedChange > 0){
            expectedPed += expectedPedChange;
            setPedestrianWait((int) (pedestrianWait() * 0.6));
         }
         else if (pedestrianCount - pedChange < 0){
            expectedPed -= expectedPedChange;
            setPedestrianWait((int) (pedestrianWait() + pedestrianWait() * 0.4));
         }
         if (vehicleCount - carChange > 0){
            expectedCar += expectedCarChange;
            setVehicleWait((int) (vehicleWait() * 0.6));
         }
         else if (vehicleCount - carChange < 0){
            expectedCar -= expectedCarChange;
            setVehicleWait((int) (vehicleWait() + vehicleWait() * 0.4));
         }
         pedestrianCount = vehicleCount = 0;
         flowCount = 1;
      }
      
      //Prioritize pedestrians or vehicles
      ArrayList<AnimateObject> traffList = LayerManager.getAnimateList();
      for (int i = 0 ; i < traffList.size(); i++){
         if (traffList.get(i) instanceof Pedestrian && calc2dDist(traffList.get(i)) < 40) pedestrianCount++;
         else if (traffList.get(i) instanceof Vehicle && calc2dDist(traffList.get(i)) < 40) vehicleCount++;
      }
      
      if (pedestrianCount >= vehicleCount) setPeriod(pedestrianWait());
      else setPeriod(vehicleWait());
   }
   
   /**
   * Degrades this object to a lower-level one.
   */
   public void degrade(){
      TrafficLights down = new TrafficLights(getIdentifier(), getType(), getX(), getY(), getLocalLayer());
      if (getChosenStatus()) down.setChosenStatus(true);
      setChosenStatus(false);
      LayerManager.remove(this);
      SimulationData.changeBudget(20000);
      System.out.println(getIdentifier() + " degraded!");
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
      super.setTrueX(x);
   }
   
   /**
   * Sets the y-position as seen on the simulation.
   * @param y the y-position according to the simulation.
   */
   public void setTrueY(int y){
      super.setTrueY(y);
   }
   
   /**
   * Returns the x-position as seen on the simulation.
   * @return the x-position according to the simulation
   */
   public int trueX(){
      return super.trueX();
   }
   
   /**
   * Returns the y-position as seen on the simulation.
   * @return the y-position according to the simulation
   */
   public int trueY(){
      return super.trueY();
   }
   
   /**
   * Switches axes of traffic when waiting times are over.
   */
   public void switchAxis(){
      super.switchAxis();
      flowCount++;
   }
}