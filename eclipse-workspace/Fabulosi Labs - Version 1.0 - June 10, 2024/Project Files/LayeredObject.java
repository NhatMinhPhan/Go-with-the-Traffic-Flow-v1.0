/*
Here is a class hierarchy that the program is supposed to be based on:
   abstract class LayeredObject:
      class Camera
         class Level1Camera
      abstract class UIObject:
         class UIBackground
         class UILabel
            class UICutsceneText
         class UIButton
      abstract class SimulationObject:
         class BackgroundImg
         class StaticObject (buildings)
         abstract class AnimateObject:
            class Vehicle
            class Pedestrian
         abstract class TrafficController:
            class PoliceOfficer
            class TrafficLights:
               class AITrafficLights
         
*/

import java.awt.*;
import javax.swing.*;

/**
* This is a template for all layered objects including the UI and simulation objects.
* @version 0.3
* @author Fabulosi Labs
*/

public abstract class LayeredObject {
   /**
   * Coordinates of the Layered Object
   */
   private int x, y;
   
   /**
   * Name or identifier of the object
   */
   private String identifier;
   
   /**
   * Class constructor.
   * @param i Identifier
   * @param x X-position
   * @param y Y-position
   */
   public LayeredObject(String i, int x, int y){
      identifier = i;
      this.x = x;
      this.y = y;
   }
   
   /**
   * Returns the object's identifier.
   * @return The object's identifier
   */
   public String getIdentifier(){
      return identifier;
   }
   
   /**
   * Returns the x-position on the mainFrame.
   * @return The object's x-position
   */
   public int getX(){
      return x;
   }
   
   /**
   * Returns the y-position on the mainFrame.
   * @return The object's y-position
   */
   public int getY(){
      return y;
   }
   
   /**
   * Sets the x-position on the mainFrame to a certain value.
   * @param val New x-position
   */
   public void setX(int val){
      x = val;
   }
   
   /**
   * Sets the y-position on the mainFrame to a certain value.
   * @param val New y-position
   */
   public void setY(int val){
      y = val;
   }
   
   /**
   * Used when printing out the Layered Object
   * @return A sentence that follows the syntax: This (object's class), named "(its identifier)", is at (x-position, y-position).
   */
   public String toString(){
      return "This " + this.getClass().getSimpleName() + ", named \"" + getIdentifier() + "\", is at (" + getX() + ", " + getY() + ").";
   }
   
   /**
   * Draws a Layered Object
   * @param g Graphics used to draw the object
   */
   public abstract void draw(Graphics g);
}