import java.awt.*;
import javax.swing.*;
import java.io.*;

import javax.imageio.*;
import java.awt.image.*;

/**
* This is a template for background images in simulations.
* @version 0.3
* @author Fabulosi Labs
*/

public class BackgroundImg extends SimulationObject {
   /**
   * If true, the object is using an image. Otherwise it is using a color matte.
   */
   private boolean imgMode;
   
   /**
   * File path to an image, if imgMode is true.
   */
   private String filePath;
   
   /**
   * The Color used for the background, if imgMode is false.
   */
   private Color color;
   
   /**
   * Image Mode Constructor
   * @param i Identifier
   * @param filePath File path to an image
   */
   public BackgroundImg(String i, String filePath){
      super(i, 0, 0, Integer.MIN_VALUE);
      if (filePath == null) imgMode = false;
      else {
         imgMode = true;
         this.filePath = filePath;
      }
      LayerManager.setBGImg(this);
   }
   
   /**
   * Color Mode Constructor
   * @param i Identifier
   * @param color Color matte used for the background
   */
   public BackgroundImg(String i, Color color){
      super(i, 0, 0, Integer.MIN_VALUE);
      imgMode = false;
      this.color = color;
      LayerManager.setBGImg(this);
   }
   
   /**
   * Draws the object.
   * @param g Graphics used to draw the object.
   */
   public void draw(Graphics g){
      if (!imgMode){
         if (color == null) g.setColor(Color.green);
         else g.setColor(color);
         g.fillRect(0, 0, ProgramDriver.getMainFrameWidth(), ProgramDriver.getMainFrameHeight());
         return;
      }
      
      try {
         File f = new File(filePath);
         BufferedImage img = ImageIO.read(f);
         g.drawImage(img, 0, 0, ProgramDriver.getMainFrameWidth(), ProgramDriver.getMainFrameHeight(), null);
      }
      catch (IOException e){
         System.out.println("Drawing " + getIdentifier() + " Failed.");
         e.printStackTrace();
      }
   }
   
   /**
   * Returns the x-position according to the simulation.
   * @return the x-position according to the simulation.
   */
   public int trueX (){
      return getX() + (ProgramDriver.getMainFrameWidth() / 2);
   }
   
   /**
   * Returns the y-position according to the simulation, which is always Integer.MIN_VALUE.
   * @return the y-position according to the simulation, which is always Integer.MIN_VALUE.
   */
   public int trueY (){ //Get the true y-position (taking height into account) for rendering.
      return Integer.MIN_VALUE; //The bottommost layer, as this is a BackgroundImg.
   }
   
   /**
   * Sets the x-position according to the simulation to a certain value.
   * @param x New true x-position
   */
   public void setTrueX(int x){
      setX(x - (ProgramDriver.getMainFrameWidth() / 2));
   }
   
   /**
   * Always sets the x-position according to the simulation to Integer.MIN_VALUE.
   * @param y New true y-position (unused)
   */
   public void setTrueY(int y){
      setY(Integer.MIN_VALUE);
   }
}