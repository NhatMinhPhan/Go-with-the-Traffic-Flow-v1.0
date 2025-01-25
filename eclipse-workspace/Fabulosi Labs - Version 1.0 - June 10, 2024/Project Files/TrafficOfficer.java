import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

/**
* This is a template for traffic officers in the simulation. They have default settings.
* @version 1.0
* @author Fabulosi Labs
*/

public class TrafficOfficer extends TrafficController {
   /**
   * Full class constructor.
   * @param i Identifier
   * @param type Type of traffic officer
   * @param x X-position
   * @param y Y-position
   * @param localLayer Local layer within the simulation
   * @param miliseconds Waiting period
   */
   public TrafficOfficer(String i, String type, int x, int y, int localLayer, int miliseconds){
      super(i, type, x, y, localLayer, miliseconds);
      LayerManager.add(this);
   }
   
   /**
   * Class constructor without setting up the waiting period
   * @param i Identifier
   * @param type Type of traffic officer
   * @param x X-position
   * @param y Y-position
   * @param localLayer Local layer within the simulation
   */
   public TrafficOfficer(String i, String type, int x, int y, int localLayer){
      this(i, type, x, y, localLayer, (int) (Math.random() * 6 + 4) * 1000);
   }
   
   /**
   * Draws this object
   * @param g Graphics used to draw it.
   */
   public void draw (Graphics g){
      //Head
      g.setColor(new Color(225, 220, 177));
      g.fillOval(getX() - 12 - 14, getY() - 24 - 14, 24, 24);
      g.setColor(Color.black);
      g.drawOval(getX() - 12 - 14, getY() - 24 - 14, 24, 24);
      
      //Torso
      int[] triX = {getX() - 12 - 14, getX() - 14, getX() + 12 - 14};
      int[] triY = {getY() + 30 - 14, getY() - 14, getY() + 30 - 14};
      g.setColor(new Color(52, 116, 235));
      g.fillPolygon(triX, triY, 3);
      g.setColor(Color.black);
      g.drawPolygon(triX, triY, 3);
      
      //Hat
      g.setColor(new Color(52, 116, 235));
      g.fillRect(getX() - 12 - 14, getY() - 32 - 14, 24, 10);
      g.setColor(Color.black);
      g.drawRect(getX() - 12 - 14, getY() - 32 - 14, 24, 10);
      g.drawLine(getX() - 12 - 14, getY() - 32 - 14 + 10, getX() - 12 - 14 + 30, getY() - 32 - 14 + 10);
      
      //Uniform
      g.drawLine(getX() - 14, getY() + 30 - 14, getX() - 14, getY() - 14);
      
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
      
      onTick();
   }
   
   /**
   * Upgrades this object to a higher level one (unused).
   */
   public void upgrade(){
      TrafficLights lights = new TrafficLights(getIdentifier(), getType(), getX(), getY(), getLocalLayer());
      lights.setChosenStatus(true);
      LayerManager.remove(this);
      SimulationData.changeBudget(-180000);
      System.out.println(getIdentifier() + " upgraded!");
      if (ProgramDriver.getProgramState().equals("level 2")){
         for (int i = 0; i < SimulationData.list2.length; i++){
            if (SimulationData.list2[i].getIdentifier().equals(getIdentifier())){
               SimulationData.list2[i] = lights;
               break;
            }
         }
      }
   }
   
   /**
   * Degrades this object to a lower-level one.
   */
   public void degrade(){
      return;
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
   
}